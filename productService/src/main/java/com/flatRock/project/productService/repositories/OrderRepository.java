package com.flatRock.project.productService.repositories;

import com.flatRock.project.productService.models.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Long countByClients_Id(Long clientId);

    List<Orders> findAllByClients_Id(Long clientId, Pageable pageable);

    void deleteAllByClients_Id(Long clientId);
}
