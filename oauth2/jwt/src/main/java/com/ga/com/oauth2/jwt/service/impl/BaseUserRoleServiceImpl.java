package com.ga.com.oauth2.jwt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ga.com.oauth2.jwt.service.BaseUserRoleService;
import mapper.entity.BaseUserRole;
import mapper.mapper.BaseUserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
@Service
public class BaseUserRoleServiceImpl extends ServiceImpl<BaseUserRoleMapper, BaseUserRole> implements BaseUserRoleService {

}
