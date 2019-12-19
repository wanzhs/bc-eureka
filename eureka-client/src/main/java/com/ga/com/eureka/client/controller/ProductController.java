package com.ga.com.eureka.client.controller;


import com.ga.com.eureka.client.fein.ProductFeignService;
import com.ga.com.eureka.client.fein.TestFeignService;
import com.ga.com.eureka.eurekacenter.entity.Product;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@EnableFeignClients
@RequestMapping("/product")
public class ProductController {
    @Resource
    TestFeignService testFeignService;
    @Resource
    ProductFeignService productFeignService;

    @GetMapping("/test1")
    public String getParameter(@RequestParam(value = "parameter", required = true) String parameter) {
        String rs = testFeignService.echo(parameter);
        return rs;
    }

    @GetMapping("/test2/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productFeignService.getProductById(id);
    }
}
