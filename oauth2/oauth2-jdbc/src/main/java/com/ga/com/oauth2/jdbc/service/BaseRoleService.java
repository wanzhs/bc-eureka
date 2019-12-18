package com.ga.com.oauth2.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import mapper.entity.BaseRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
public interface BaseRoleService extends IService<BaseRole> {
    List<BaseRole> getRoleByUserId(String userId);
}
