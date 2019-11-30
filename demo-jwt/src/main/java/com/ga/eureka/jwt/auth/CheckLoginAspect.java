package com.ga.eureka.jwt.auth;

import com.ga.eureka.jwt.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 切面检测
 *
 * @author wanzhongsu
 * @date 2019/11/30 10:15
 */
@Aspect
@Component
public class CheckLoginAspect {
    @Resource
    private JwtOperator jwtOperator;

    @Around("@annotation(com.ga.eureka.jwt.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        try {
            /*从header中获取token*/
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("X-Token");
            if (StringUtils.isEmpty(token)) {
                throw new Exception("token为空");
            }
            /*校验token是否合法,是否过期*/
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new Exception("token 过期");
            }
            /*如果校验成功，就将用户的信息设置到request的attribute里面*/
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("userCode", claims.get("userCode"));
            request.setAttribute("userTel", claims.get("userTel"));
        } catch (Throwable throwable) {
            throw new Exception("验证异常");
        }
        return point.proceed();
    }

}
