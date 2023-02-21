package com.flatRock.project.productService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flatRock.project.productService.dtos.OrderDTO;
import com.flatRock.project.productService.dtos.OrderSearchDTO;
import com.flatRock.project.productService.dtos.OrderedProductDTO;
import com.flatRock.project.productService.enums.OrderStatus;
import com.flatRock.project.productService.exceptions.EntityNotFoundException;
import com.flatRock.project.productService.models.Orders;
import com.flatRock.project.productService.models.Product;
import com.flatRock.project.productService.pagination.OffsetBasedPageRequest;
import com.flatRock.project.productService.repositories.ClientRepository;
import com.flatRock.project.productService.repositories.OrderRepository;
import com.flatRock.project.productService.repositories.ProductRepository;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    private final RedisPubSubCommands<String, String> redisSynchronousPublisher;
    private final EntityManager entityManager;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository repository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final static String CHANNEL_NAME = "place_order";

    public OrderService(RedisPubSubCommands<String, String> redisSynchronousPublisher, EntityManager entityManager,
                        ClientRepository clientRepository, ProductRepository productRepository, OrderRepository repository,
                        ModelMapper mapper, ObjectMapper objectMapper) {
        this.redisSynchronousPublisher = redisSynchronousPublisher;
        this.entityManager = entityManager;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.repository = repository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    public void placeOrder(OrderDTO dto) {
        validateOrder(dto);
        Long publishCount = redisSynchronousPublisher.publish(CHANNEL_NAME, serializePurchaseDTO(dto));
        log.debug(String.format("Published message was sent to %d subscribers", publishCount));
    }

    private void validateOrder(OrderDTO dto) {
        Map<Long, Long> productsMap = new HashMap<>();
        if (dto.getOrderedProducts() != null && !dto.getOrderedProducts().isEmpty()) {
            for (OrderedProductDTO orderedProduct : dto.getOrderedProducts()) {
                productsMap.put(orderedProduct.getProduct().getId(), orderedProduct.getOrderQuantity());
            }
            List<Product> products = productRepository.findAllByIdIn(productsMap.keySet());
            if (!products.isEmpty()) {
                for (Product product : products) {
                    if (product.getQuantity() < productsMap.get(product.getId())) {
                        throw new EntityNotFoundException("sorry we have not enough product in storage, reduce amount and try again");
                    }
                }
            }
        }
    }

    @Transactional
    public OrderDTO add(OrderDTO dto) {
        purchaseProducts(dto);
        Optional<Double> totalPrice = calculateTotalPrice(dto);
        if (totalPrice.isPresent()) {
            dto.setTotalPrice(calculateTotalPrice(dto).get());
        }
        Orders orders = repository.save(dtoToEntity(dto));
        return entityToDto(orders);
    }

    private String serializePurchaseDTO(OrderDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Double> calculateTotalPrice(OrderDTO dto) {
        return dto.getOrderedProducts().stream()
                .map(o -> o.getOrderQuantity() * o.getProduct().getPrice())
                .reduce(Double::sum);
    }

    private void purchaseProducts(OrderDTO dto) {
        Map<Long, Long> productsMap = new HashMap<>();
        if (dto.getOrderedProducts() != null && !dto.getOrderedProducts().isEmpty()) {
            for (OrderedProductDTO orderedProduct : dto.getOrderedProducts()) {
                productsMap.put(orderedProduct.getProduct().getId(), orderedProduct.getOrderQuantity());
            }
            List<Product> products = productRepository.findAllByIdIn(productsMap.keySet());
            if (!products.isEmpty()) {
                for (Product product : products) {
                    if (product.getQuantity() < productsMap.get(product.getId())) {
                        throw new EntityNotFoundException("sorry we have not enough product in storage, reduce amount and try again");
                    }
                    product.setQuantity(product.getQuantity() - productsMap.get(product.getId()));
                }
            }
            productRepository.saveAll(products);
        }
    }

    @Transactional
    public void update(Long id, OrderDTO dto) {
        Orders existingOrders = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order was not found for given id: " + id));
        Orders orders = dtoToEntity(dto);
        orders.setId(id);
        if (OrderStatus.CANCELED.equals(dto.getStatus())) {
            updateFailedOrderProducts(dto);
            repository.save(orders);
        } else if (Arrays.asList(OrderStatus.PENDING, OrderStatus.DELIVERED).contains(dto.getStatus())) {
            if (!existingOrders.getStatus().equals(dto.getStatus())) {
                existingOrders.setStatus(dto.getStatus());
            }
            repository.save(existingOrders);
        }
    }

    private void updateFailedOrderProducts(OrderDTO dto) {
        if (dto.getOrderedProducts() != null && !dto.getOrderedProducts().isEmpty()) {
            Map<Long, Long> productsMap = new HashMap<>();
            for (OrderedProductDTO orderedProduct : dto.getOrderedProducts()) {
                productsMap.put(orderedProduct.getProduct().getId(), orderedProduct.getOrderQuantity());
            }
            List<Product> products = productRepository.findAllByIdIn(productsMap.keySet());
            if (!products.isEmpty()) {
                for (Product product : products) {
                    product.setQuantity(product.getQuantity() + productsMap.get(product.getId()));
                }
            }
            productRepository.saveAll(products);
        }
    }

    public OrderDTO get(Long id) {
        Orders orders = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order was not found for given id: " + id));
        return entityToDto(orders);
    }

    public List<OrderDTO> getAll(Integer offset, Integer limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        return repository.findAll(pageable).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> search(OrderSearchDTO searchDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orders> cq = cb.createQuery(Orders.class);

        Root<Orders> order = cq.from(Orders.class);
        List<Predicate> predicates = new ArrayList<>();
        if (searchDTO.getOrderStatus() != null) {
            predicates.add(cb.equal(order.get("orderStatus"), searchDTO.getOrderStatus()));
        }
        if (searchDTO.getStartDate() != null) {
            predicates.add(cb.greaterThan(order.get("orderDate"), searchDTO.getStartDate()));
        }
        if ((searchDTO.getEndDate() != null)) {
            predicates.add(cb.lessThan(order.get("orderDate"), searchDTO.getEndDate()));
        }
        if (searchDTO.getPriceFrom() != null) {
            predicates.add(cb.greaterThan(order.get("totalPrice"), searchDTO.getPriceFrom()));
        }
        if ((searchDTO.getPriceTo() != null)) {
            predicates.add(cb.lessThan(order.get("totalPrice"), searchDTO.getPriceTo()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(cq).getResultList()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Long getCount(Long clientId) {
        if (clientId != null) {
            return repository.countByClients_Id(clientId);
        } else {
            return repository.count();
        }
    }

    public List<OrderDTO> getClientOrders(Long clientId, Integer offset, Integer limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        return repository.findAllByClients_Id(clientId, pageable).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Orders orders = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order was not found for given id: " + id));
        repository.deleteById(id);
    }

    @Transactional
    public void deleteClientOrders(Long clientId) {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("client was not found for given id : " + clientId));
        repository.deleteAllByClients_Id(clientId);
    }

    private OrderDTO entityToDto(Orders orders) {
        return mapper.map(orders, OrderDTO.class);
    }

    private Orders dtoToEntity(OrderDTO dto) {
        return mapper.map(dto, Orders.class);
    }
}
