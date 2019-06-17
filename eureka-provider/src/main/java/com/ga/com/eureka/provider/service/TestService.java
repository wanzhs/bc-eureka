package com.ga.com.eureka.provider.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "test-service", path = "/test")
public interface TestService {
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    String echo(@RequestParam("param") String parameter);
}
