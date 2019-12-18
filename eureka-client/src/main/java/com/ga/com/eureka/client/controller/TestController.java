package com.ga.com.eureka.client.controller;


import com.ga.com.eureka.client.fein.ProductService;
import com.ga.com.eureka.client.fein.TestService;
import com.ga.com.eureka.client.fein.UserService;
import com.ga.com.eureka.eurekacenter.entity.Product;
import com.ga.com.eureka.eurekacenter.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    TestService testService;
    @Resource
    UserService userService;
    @Resource
    ProductService productService;

    @GetMapping("/test1")
    public String getParameter(@RequestParam(value = "parameter", required = true) String parameter) {
        String rs = testService.echo(parameter);
        return rs;
    }

    @GetMapping("/test2/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/test3/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }
}
