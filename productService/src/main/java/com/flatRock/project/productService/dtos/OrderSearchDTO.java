package com.flatRock.project.productService.dtos;

import com.flatRock.project.productService.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchDTO {
    private Date startDate;
    private Date endDate;
    private Double priceFrom;
    private Double priceTo;
    private OrderStatus orderStatus;
}
