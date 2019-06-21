package com.ga.com.oauth2.jwt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import mapper.entity.BaseUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
public interface IMBaseUserService extends IService<BaseUser> {
    void addBaseUser(BaseUser baseUser);

    List<BaseUser> getBaseUserList();
}
