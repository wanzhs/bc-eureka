package com.ga.com.oauth2.jwt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ga.com.oauth2.jwt.service.BaseRoleService;
import mapper.entity.BaseRole;
import mapper.mapper.BaseRoleMapper;
import mapper.mapper.BaseUserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
@Service
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, BaseRole> implements BaseRoleService {
    @Resource
    BaseUserRoleMapper baseUserRoleMapper;

    @Override
    public List<BaseRole> getRoleByUserId(String userId) {
        List<BaseRole> list = this.baseUserRoleMapper.getRoleByUserId(userId);
        return list;
    }
}
