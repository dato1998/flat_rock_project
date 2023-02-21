package com.flatRock.project.productService.controllers;

import com.flatRock.project.productService.dtos.ClientDTO;
import com.flatRock.project.productService.models.Clients;
import com.flatRock.project.productService.services.ClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<ClientDTO> add(@RequestBody final Clients clients) {
        return new ResponseEntity<>(service.add(clients), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") final Long id, @RequestBody final ClientDTO dto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        service.update(id, dto, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> get(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientDTO> getByEmailAndMarketId(@PathVariable("email") final String email) {
        return new ResponseEntity<>(service.getByEmail(email), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAll(final Integer offset, final Integer limit) {
        return new ResponseEntity<>(service.getAll(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return new ResponseEntity<>(service.getCount(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") final Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
