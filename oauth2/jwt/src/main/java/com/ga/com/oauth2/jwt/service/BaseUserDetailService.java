package com.ga.com.oauth2.jwt.service;

import com.ga.com.oauth2.jwt.config.jwt.BaseUserDetail;
import mapper.entity.BaseRole;
import mapper.entity.BaseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseUserDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private BaseRoleService baseRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询用户
        BaseUser baseUser = baseUserService.getUserByUserName(username);
        if (ObjectUtils.isEmpty(baseUser)) {
            logger.error("找不到该用户，用户名：" + username);
            throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
        }

        // 查询角色
        List<BaseRole> baseRoleList = baseRoleService.getRoleByUserId(baseUser.getUserId());
        if (ObjectUtils.isEmpty(baseRoleList)) {
            logger.error("查询角色失败！");
            baseRoleList = new ArrayList<>();
        }

        // 获取用户权限列表
        List<GrantedAuthority> authorities = new ArrayList();
        baseRoleList.forEach(e -> {
            // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
            GrantedAuthority authority = new SimpleGrantedAuthority(e.getRoleCode());
            authorities.add(authority);

        });

        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(baseUser.getUserName(),
                        baseUser.getUserPwd(), isActive(baseUser.getUserActive().getValue()), true, true, true, authorities);
        return new BaseUserDetail(baseUser, user);
    }

    private boolean isActive(int active) {
        return active == 1 ? true : false;
    }
}
