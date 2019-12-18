package com.ga.com.eureka.provider.controller.server;

import com.ga.com.eureka.eurekacenter.entity.User;
import com.ga.com.eureka.eurekacenter.service.IUserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResourceController implements IUserService {
    @Override
    public User getUserById(Long id) {
        return new User().setId(id).setAge(12)
                .setUsername("user" + id).setUserTel("13585653652");
    }
}
