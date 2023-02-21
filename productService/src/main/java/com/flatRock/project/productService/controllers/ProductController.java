package com.flatRock.project.productService.controllers;

import com.flatRock.project.productService.dtos.ProductDTO;
import com.flatRock.project.productService.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> add(@RequestBody final ProductDTO dto) {
        return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") final Long id, @RequestBody final ProductDTO dto) {
        service.update(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAll(final Integer offset, final Integer limit) {
        return new ResponseEntity<>(service.getAll(offset, limit), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(@RequestParam(required = false) String category) {
        return new ResponseEntity<>(service.getCount(category), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductDTO>> getByCategory(final String category, final Integer offset, final Integer limit) {
        return new ResponseEntity<>(service.getByCategoryId(category, offset, limit), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") final Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}