package com.ga.com.eureka.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test1")
public class TestController1 {
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    String echo(@RequestParam("param") String parameter) {
        return "peace you ! " + parameter;
    }
}
