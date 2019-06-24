package com.ga.com.eureka.oauth2.memory.service;

import com.ga.com.eureka.oauth2.memory.constant.AuthoritiesEnum;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author:wanzhongsu
 * @description: 用户信息获取
 * @date:2019/6/24 14:43
 */
@Service
public class BaseUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    /**
     * 通过 Username 加载用户详情
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("wanzhongsu")) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode("123456");
            UserDetails userDetails = new User("wanzhongsu",
                    password,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(AuthoritiesEnum.USER.getRole()));
            return userDetails;
        }
        return null;
    }
}
