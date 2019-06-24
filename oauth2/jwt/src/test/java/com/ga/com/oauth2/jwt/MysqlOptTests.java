package com.ga.com.oauth2.jwt;

import com.ga.com.oauth2.jwt.service.BaseUserService;
import mapper.entity.BaseUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlOptTests {
    @Resource
    BaseUserService baseUserService;

    @Test
    public void testMysql() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUserName("lihaitao").setUserPhone("13532123562")
                .setUserAge(23)
                .setUserGender(BaseUser.UserGender.MALE)
                .setUserActive(BaseUser.UserActive.ENABLE)
                .setUserPwd("123456");
        baseUserService.saveBaseUser(baseUser);

        List<BaseUser> baseUserList = baseUserService.getBaseUserList();
        System.out.println(baseUserList);
    }

    @Test
    public void updatePwd() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<BaseUser> baseUserList = baseUserService.getBaseUserList();
        for (BaseUser user : baseUserList) {
            //加密
            String encodedPassword = passwordEncoder.encode(user.getUserPwd());
            System.out.println("==========================="+encodedPassword);
            user.setUserPwd(encodedPassword);
            baseUserService.saveBaseUser(user);
        }
    }
}
