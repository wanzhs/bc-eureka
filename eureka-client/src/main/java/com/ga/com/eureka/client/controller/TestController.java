package com.ga.com.eureka.client.controller;

import com.ga.com.eureka.client.fein.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/parameter")
    public String getParameter(@RequestParam(value = "parameter", required = true) String parameter) {
        String rs = testService.echo(parameter);
        return rs;
    }
}
