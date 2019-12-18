package com.ga.com.eureka.eurekacenter.base;

import com.ga.com.eureka.eurekacenter.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IUserService {
    @RequestMapping(value = "/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
