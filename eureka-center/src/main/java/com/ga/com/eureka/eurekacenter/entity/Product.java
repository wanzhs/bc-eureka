package com.ga.com.eureka.eurekacenter.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Product {
    private String productName;
    private BigDecimal price;
    private String desc;
}
