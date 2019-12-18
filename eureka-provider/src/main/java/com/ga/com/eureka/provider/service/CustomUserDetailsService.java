package com.ga.com.eureka.provider.service;

import com.ga.com.eureka.provider.beans.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * 模拟两个用户
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return new SecurityUser("user", "user_password", "user-role");
        } else if ("admin".equals(username)) {
            return new SecurityUser("admin", "admin_password", "admin-role");
        } else {
            return null;
        }
    }
}
