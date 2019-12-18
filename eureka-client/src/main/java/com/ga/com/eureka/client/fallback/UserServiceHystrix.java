package com.ga.com.eureka.client.fallback;

import com.ga.com.eureka.client.fein.UserService;
import com.ga.com.eureka.eurekacenter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHystrix implements UserService {
    @Override
    public User getUserById(Long id) {
        return new User().setUsername("熔断账号1").setUserTel("13506659256").setAge(24).setId(Long.MAX_VALUE);
    }
}
