package com.flatRock.project.productService.controllers;

import com.flatRock.project.productService.dtos.OrderDTO;
import com.flatRock.project.productService.dtos.OrderSearchDTO;
import com.flatRock.project.productService.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<OrderDTO> add(@RequestBody final OrderDTO dto) {
        return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<List<OrderDTO>> search(@RequestBody OrderSearchDTO dto) {
        return new ResponseEntity<>(service.search(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") final Long id, @RequestBody final OrderDTO dto) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> getAll(final Integer offset, final Integer limit) {
        return new ResponseEntity<>(service.getAll(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(@RequestParam(required = false) Long clientId) {
        return new ResponseEntity<>(service.getCount(clientId), HttpStatus.OK);
    }

    @GetMapping("/client")
    public ResponseEntity<List<OrderDTO>> getClientOrders(final Long clientId, final Integer offset, final Integer limit) {
        return new ResponseEntity<>(service.getClientOrders(clientId, offset, limit), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") final Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/client")
    public ResponseEntity deleteClientOrders(final Long clientId) {
        service.deleteClientOrders(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
