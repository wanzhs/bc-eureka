package com.ga.com.oauth2.jwt.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ga.com.oauth2.jwt.service.IMBaseUserService;
import mapper.entity.BaseUser;
import mapper.mapper.BaseUserMapper;
import org.springframework.stereotype.Service;

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
public class MBaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IMBaseUserService {

    @Override
    public void addBaseUser(BaseUser baseUser) {
        baseUser.setUserId(UUID.randomUUID().toString());
        this.baseMapper.insert(baseUser);
    }

    @Override
    public List<BaseUser> getBaseUserList() {
        List<BaseUser> userList = this.baseMapper.selectList(null);
        return userList;
    }
}
