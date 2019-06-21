package com.ga.com.oauth2.jwt;

//import com.ga.com.oauth2.jwt.service.IMBaseUserService;

import com.ga.com.oauth2.jwt.service.IMBaseUserService;
import mapper.entity.BaseUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {
    @Resource
    IMBaseUserService baseUserService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testMysql() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUserName("万忠苏").setUserPhone("13532123562")
                .setUserAge(23)
                .setUserPwd("123456");
        baseUserService.addBaseUser(baseUser);

        List<BaseUser> baseUserList = baseUserService.getBaseUserList();
        System.out.println(baseUserList);
    }

}
