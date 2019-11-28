package com.ga.eureka.jwt.controller;

import com.ga.eureka.jwt.auth.CheckLogin;
import com.ga.eureka.jwt.service.ITestService;
import com.ga.eureka.jwt.utils.JwtOperator;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 测试接口控制层
 *
 * @author wanzhongsu
 * @date 2019/11/28 14:03
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    JwtOperator jwtOperator;
    @Resource
    ITestService testService;

    /**
     * 测试请求1 测试对请求接口的解析
     *
     * @author wanzhongsu
     * @date 2019/11/28 14:02
     */
    @CheckLogin
    @GetMapping("/request1")
    public void testRequest1() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("X-Token");
        Claims claims = jwtOperator.getClaimsFromToken(token);
        testService.testRequest1();
    }

    /**
     * 登录接口
     *
     * @author wanzhongsu
     * @date 2019/11/28 14:03
     */
    @GetMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        if ("username".equals(username) &&
                "password".equals(password)) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("userId", 1);
            map.put("userCode", "1000");
            map.put("userTel", "13506559252");
            String token = jwtOperator.generateToken(map);
            return token;
        } else {
            return "用户名密码错误";
        }
    }
}
