package com.ga.com.eureka.client.config;

import feign.Feign;
import feign.Retryer;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
/**
 * 结局HystrixFeignBuider 在zipkin和hystrix中bean重复冲突
 * @author wanzhongsu
 * @date 2019/12/19 16:15
 */
@Component
public class MyFeignHystrixBuilder {
    @Bean
    @Primary
    public Feign.Builder builder() {
        return HystrixFeign.builder().retryer(Retryer.NEVER_RETRY);
    }
}
