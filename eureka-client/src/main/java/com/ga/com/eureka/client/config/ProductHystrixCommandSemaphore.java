package com.ga.com.eureka.client.config;

import com.ga.com.eureka.eurekacenter.entity.Product;
import com.netflix.hystrix.*;

import java.math.BigDecimal;

public class ProductHystrixCommandSemaphore extends HystrixCommand<Product> {
    public ProductHystrixCommandSemaphore() {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("g1"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("k1"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(5).withKeepAliveTimeMinutes(60).withMaxQueueSize(5).withQueueSizeRejectionThreshold(2))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true).withCircuitBreakerSleepWindowInMilliseconds(3000).withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
    }

    @Override
    protected Product run() throws Exception {
        return new Product().setProductName("信号量隔离策略商品").setDesc("信号量隔离商品描述").setPrice(BigDecimal.ZERO);
    }
}
