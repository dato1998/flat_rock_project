package com.flatRock.project.productService.services;

import com.flatRock.project.productService.configs.CustomPasswordEncoder;
import com.flatRock.project.productService.dtos.ClientDTO;
import com.flatRock.project.productService.enums.Role;
import com.flatRock.project.productService.exceptions.BadCredentialsException;
import com.flatRock.project.productService.exceptions.EntityNotFoundException;
import com.flatRock.project.productService.feign.SingleSignOnServiceProxy;
import com.flatRock.project.productService.models.Clients;
import com.flatRock.project.productService.pagination.OffsetBasedPageRequest;
import com.flatRock.project.productService.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientService {
    private final SingleSignOnServiceProxy singleSignOnServiceProxy;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final ClientRepository repository;
    private final ModelMapper mapper;

    public ClientService(SingleSignOnServiceProxy singleSignOnServiceProxy, CustomPasswordEncoder customPasswordEncoder, ClientRepository repository, ModelMapper mapper) {
        this.singleSignOnServiceProxy = singleSignOnServiceProxy;
        this.customPasswordEncoder = customPasswordEncoder;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ClientDTO add(Clients client) {
        Clients registeredClients = repository.findByEmail(client.getEmail());
        if (registeredClients != null) {
            throw new BadCredentialsException("sorry but client with this email is already registered please try another one");
        }
        client.setRole(Role.ROLE_CLIENT);
        client.setPassword(customPasswordEncoder.encoder().encode(client.getPassword()));
        Clients newClients = repository.save(client);
        return entityToDto(newClients);
    }


    @Transactional
    public void update(Long id, ClientDTO dto, String token) {
        Clients oldClient = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("client was not found for given id : " + id));
        Map<String, Object> userInfo = singleSignOnServiceProxy.getUserIdAndRole(token);
        validateUser(oldClient, dto, userInfo);
        Clients clients = dtoToEntity(dto);
        clients.setId(id);
        repository.save(clients);
    }

    private void validateUser(Clients oldClient, ClientDTO newClient, Map<String, Object> userInfo) {
        Long userId = (Long) userInfo.get("userId");
        if (oldClient.getId().equals(userId)) {
            return;
        }
        Role role = (Role) userInfo.get("role");
        if (oldClient.getRole() != newClient.getRole()
                && Arrays.asList(Role.ROLE_SELLER, Role.ROLE_ADMINISTRATOR).contains(newClient.getRole())
                && Role.ROLE_ADMINISTRATOR.equals(role)) {
            return;
        }
        throw new EntityNotFoundException("you have no rights to update client");
    }

    public ClientDTO get(Long id) {
        Clients clients = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("client was not found for given id : " + id));
        return entityToDto(clients);
    }

    public ClientDTO getByEmail(String email) {
        Clients clients = repository.findByEmail(email);
        if (clients == null) {
            throw new EntityNotFoundException("client was not found for given email: " + email);
        }
        return entityToDto(clients);
    }

    public List<ClientDTO> getAll(Integer offset, Integer limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        return repository.findAll(pageable).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Long getCount() {
        return repository.count();
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("client was not found for given id : " + id));
        repository.deleteById(id);
    }

    private ClientDTO entityToDto(Clients clients) {
        return mapper.map(clients, ClientDTO.class);
    }

    private Clients dtoToEntity(ClientDTO dto) {
        return mapper.map(dto, Clients.class);
    }
}
