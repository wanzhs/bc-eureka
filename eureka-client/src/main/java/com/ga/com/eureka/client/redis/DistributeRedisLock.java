package com.ga.com.eureka.client.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁 redis
 */
@Component
public class DistributeRedisLock {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 加锁
     *
     * @param lockName 锁的名称
     * @return 是否成功
     * @author wanzhongsu
     * @date 2019/12/30 16:14
     */
    public Boolean lock(String lockName) {
        try {
            if (redissonClient == null) {
                return false;
            }
            RLock lock = redissonClient.getLock(lockName);
            lock.lock(5, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param lockName 锁的名称
     * @return 是否成功
     * @author wanzhongsu
     * @date 2019/12/30 16:14
     */
    public Boolean unLock(String lockName) {
        try {
            if (redissonClient == null) {
                return false;
            }
            RLock lock = redissonClient.getLock(lockName);
            lock.unlock();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
