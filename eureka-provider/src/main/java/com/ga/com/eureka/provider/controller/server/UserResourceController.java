package com.ga.com.eureka.provider.controller.server;

import com.ga.com.eureka.eurekacenter.base.IUserService;
import com.ga.com.eureka.eurekacenter.entity.User;
import com.ga.com.eureka.provider.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

@Slf4j
@RestController
public class UserResourceController implements IUserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        // 获取登录信息
        Object pricipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (pricipal instanceof UserDetails) {
            UserDetails user = (UserDetails) pricipal;
            Collection<? extends GrantedAuthority> collections = user.getAuthorities();
            for (GrantedAuthority authority : collections) {
                log.info("当前的用户是{},角色是{}", user.getUsername(), authority.getAuthority());
            }
        } else {
            log.info("用户登录出问题了");
        }
        User user = this.userRepository.findById(id).get();
        return user;
    }
}
