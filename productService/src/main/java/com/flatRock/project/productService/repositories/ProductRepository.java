package com.flatRock.project.productService.repositories;

import com.flatRock.project.productService.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    long countByCategory(String category);
    List<Product> findAllByCategory(String category, Pageable pageable);
    List<Product> findAllByIdIn(Set<Long> ids);
}
