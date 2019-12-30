package com.ga.com.eureka.client.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/redisson")
public class RedissonControlller {

    @Resource
    MRedisUtil mRedisUtil;

    @Resource
    DistributeRedisLock redisLock;

    @GetMapping("/add/product")
    public void addProduct() {
        try {
            String product_key = "USER-PRODUCT1";
            redisLock.lock("redisson");
            log.info("redis请求ok");
            Integer product = mRedisUtil.get(product_key);
            if (ObjectUtils.isEmpty(product)) {
                product = 1;
            } else {
                product = product + 1;
            }
            mRedisUtil.put(product_key, product);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisLock.unLock("redisson");
        }
    }

    @GetMapping("/add/product1")
    public void addProduct1() {
        String product_key = "USER-PRODUCT1";
        log.info("redis请求ok");
        Integer product = mRedisUtil.get(product_key);
        if (ObjectUtils.isEmpty(product)) {
            product = 1;
        } else {
            product = product + 1;
        }
        mRedisUtil.put(product_key, product);
    }
}
