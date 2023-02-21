package com.flatRock.project.productService.models;

import com.flatRock.project.productService.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double totalPrice;
    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "clients_id", nullable = false)
    private Clients clients;
    @OneToMany
    @JoinColumn(name="ordered_product")
    private List<OrderedProduct> orderedProducts;
    @Temporal(TemporalType.DATE)
    private Date orderDate;

}
