package com.ga.eureka.jwt.service.impl;

import com.ga.eureka.jwt.service.ITestService;
import com.ga.eureka.jwt.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 测试接口实现类
 *
 * @author wanzhongsu
 * @date 2019/11/28 14:01
 */
@Slf4j
@Service
public class TestServiceImpl implements ITestService {
    @Resource
    JwtOperator jwtOperator;

    @Override
    public void testRequest1() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("X-Token");
        Integer userId = (Integer) request.getAttribute("userId");
        String userCode = (String) request.getAttribute("userCode");
        String userTel = (String) request.getAttribute("userTel");
        Claims claims = jwtOperator.getClaimsFromToken(token);
        log.info("userId:{},userCode:{},userTel:{}", userId, userCode, userTel);
        System.out.println("claims:" + claims);
    }
}
