package com.anikatelearning.orderservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderContainerDto {

    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
