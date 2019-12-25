package com.ga.com.eureka.client.sentinel;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用sentinel注解，需要手动注册一个SentinelResourceAspectBean
 *
 * @author wanzhongsu
 * @date 2019/12/25 10:43
 */
@Configuration
public class AopConfiguration {
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
