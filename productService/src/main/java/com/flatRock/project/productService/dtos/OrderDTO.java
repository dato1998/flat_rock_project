package com.flatRock.project.productService.dtos;

import com.flatRock.project.productService.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private Long id;
    private String name;
    private Double totalPrice;
    private OrderStatus status;
    private ClientDTO client;
    private List<OrderedProductDTO> orderedProducts;
    private Date orderDate;

}
