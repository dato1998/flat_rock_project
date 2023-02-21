package com.flatRock.project.productService.services;

import com.flatRock.project.productService.dtos.ProductDTO;
import com.flatRock.project.productService.exceptions.EntityNotFoundException;
import com.flatRock.project.productService.models.Product;
import com.flatRock.project.productService.pagination.OffsetBasedPageRequest;
import com.flatRock.project.productService.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository repository;
    private final ModelMapper mapper;

    public ProductService(ProductRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ProductDTO add(ProductDTO dto) {
        Product product = repository.save(dtoToEntity(dto));
        return entityToDto(product);
    }

    @Transactional
    public void update(Long id, ProductDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("product was not found for given id: " + id));
        Product product = dtoToEntity(dto);
        product.setId(id);
        repository.save(product);
    }

    public ProductDTO get(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("sorry but, product was not found for given id: " + id));
        return entityToDto(product);
    }

    public List<ProductDTO> getAll(Integer offset, Integer limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        return repository.findAll(pageable).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Long getCount(String category) {
        if (category != null) {
            return repository.countByCategory(category);
        } else {
            return repository.count();
        }
    }

    public List<ProductDTO> getByCategoryId(String category, Integer offset, Integer limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        return repository.findAllByCategory(category, pageable).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("product was not find for given id: " + id));
        repository.deleteById(id);
    }

    private ProductDTO entityToDto(Product product) {
        return mapper.map(product, ProductDTO.class);
    }

    private Product dtoToEntity(ProductDTO dto) {
        return mapper.map(dto, Product.class);
    }
}
