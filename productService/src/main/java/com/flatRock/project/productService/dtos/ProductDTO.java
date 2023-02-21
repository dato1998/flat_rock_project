package com.flatRock.project.productService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String barcode;
    private Double price;
    private Long quantity;
    private String category;
    private Date createdAt;
    private Date updatedAt;
}
