package com.flatRock.project.notificationService.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderedProductDTO {
    private ProductDTO product;
    private Long orderQuantity;
}

