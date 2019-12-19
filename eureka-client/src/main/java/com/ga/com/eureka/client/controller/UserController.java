package com.ga.com.eureka.client.controller;

import com.ga.com.eureka.client.fein.UserFeignService;
import com.ga.com.eureka.eurekacenter.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Import(FeignClientsConfiguration.class)  // Spring Cloud为Feign默认提供的配置类
@RestController
@RequestMapping("/user")
public class UserController {

    private UserFeignService userFeignService;
    private UserFeignService adminFeignService;

    public UserController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        this.userFeignService = Feign.builder().client(client).encoder(encoder).contract(contract).decoder(decoder)
                .requestInterceptor(new BasicAuthRequestInterceptor("user", "user_password"))
                .target(UserFeignService.class, "http://eureka-provider/");

        this.adminFeignService = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("admin", "admin_password"))
                .target(UserFeignService.class, "http://eureka-provider/");
    }

    @GetMapping("/test1/{id}")
    @HystrixCommand(fallbackMethod = "getUserByIdFallback")
    public User getUserById(@PathVariable("id") Long id) {
        return userFeignService.getUserById(id);
    }

    public User getUserByIdFallback(Long id) {
        return new User().setUsername("Hystrix熔断1").setId(Long.MAX_VALUE).setAge(2).setBalance(BigDecimal.ONE);
    }

    @GetMapping("/test2/{id}")
    public User getAdminById(@PathVariable("id") Long id) {
        return adminFeignService.getUserById(id);
    }
}
