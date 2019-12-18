package com.ga.com.eureka.eurekacenter.service;

import com.ga.com.eureka.eurekacenter.entity.Product;
import com.ga.com.eureka.eurekacenter.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IProductService {
    @RequestMapping("/get/product/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
