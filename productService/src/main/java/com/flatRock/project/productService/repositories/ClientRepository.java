package com.flatRock.project.productService.repositories;

import com.flatRock.project.productService.models.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Clients, Long> {
    Clients findByEmail(String Email);
}
