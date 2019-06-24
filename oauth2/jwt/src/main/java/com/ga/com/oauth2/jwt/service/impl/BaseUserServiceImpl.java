package com.ga.com.oauth2.jwt.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ga.com.oauth2.jwt.service.BaseUserService;
import mapper.entity.BaseUser;
import mapper.mapper.BaseUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanzhs
 * @since 2019-06-21
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements BaseUserService {

    @Override
    public void saveBaseUser(BaseUser baseUser) {
        if (ObjectUtils.isEmpty(baseUser.getUserId())) {
            baseUser.setUserId(UUID.randomUUID().toString());
        }
        this.baseMapper.insertOrUpdateBaseUser(baseUser);
    }

    @Override
    public List<BaseUser> getBaseUserList() {
        List<BaseUser> userList = this.baseMapper.selectList(null);
        return userList;
    }

    @Override
    public BaseUser getUserByUserName(String username) {
        BaseUser baseUser = this.baseMapper.selectOne(new QueryWrapper<BaseUser>().lambda()
                .eq(BaseUser::getUserName, username));
        return baseUser;
    }
}
