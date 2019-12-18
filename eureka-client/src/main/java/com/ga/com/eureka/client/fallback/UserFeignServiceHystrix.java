package com.ga.com.eureka.client.fallback;

import com.ga.com.eureka.client.fein.UserFeignService;
import com.ga.com.eureka.eurekacenter.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserFeignServiceHystrix implements UserFeignService {
    @Override
    public User getUserById(Long id) {
        return new User().setUsername("熔断账号1").setBalance(BigDecimal.ONE).setAge(24).setId(Long.MAX_VALUE);
    }
}
