package com.ga.com.oauth2.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import mapper.entity.BaseUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
public interface BaseUserService extends IService<BaseUser> {
    void saveBaseUser(BaseUser baseUser);

    List<BaseUser> getBaseUserList();

    BaseUser getUserByUserName(String username);
}
