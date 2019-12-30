package com.ga.com.eureka.client.redis;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis工具类
 *
 * @author wanzhongsu
 * @date 2019/12/30 16:31
 */
@Slf4j
public class MRedisUtil {

    @Resource
    protected RedisTemplate redisTemplate;


    public <T> void put(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void put(String key, T value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }


    public <T> void putHash(String key, String hashKey, T hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    public <T> void putHash(String key, String hashKey, T hashValue, long time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
        redisTemplate.expire(key, time, timeUnit);
    }


    public boolean hasHash(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public <T> T getHash(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    public <K, T> void pushHashAll(String key, Map<K, T> hashValue) {
        redisTemplate.opsForHash().putAll(key, hashValue);
    }

    public <K, T> void pushHashAll(String key, Map<K, T> hashValue, long time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, hashValue);
        redisTemplate.expire(key, time, timeUnit);
    }

    public <T> void put(String key, T value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    public boolean remove(String key) {
        try {
            redisTemplate.opsForValue().getOperations().delete(key);
            return true;
        } catch (Throwable t) {
            log.error("redis remove " + key + " has error,detail----" + t);
        }
        return false;
    }

    public <T> boolean removeHash(String key, T... hashKeys) {
        try {
            redisTemplate.opsForHash().delete(key, hashKeys);
            return true;
        } catch (Throwable t) {
            log.error("redis hash remove " + key + " has error,detail----" + t);
        }
        return false;
    }

    public boolean removeMatch(String match) {
        Set<String> set = redisTemplate.keys(match);
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String keyStr = it.next();
            try {
                redisTemplate.delete(keyStr);
            } catch (Throwable t) {
                log.error("redis remove " + keyStr + " has error,detail----" + t);
                return false;
            }
        }
        return true;
    }

    public int removeMatchInt(String match) {
        Set<String> set = redisTemplate.keys(match);
        Iterator<String> it = set.iterator();
        int count = 0;
        while (it.hasNext()) {
            String keyStr = it.next();
            try {
                redisTemplate.delete(keyStr);
                count = count + 1;
            } catch (Throwable t) {
                log.error("redis remove " + keyStr + " has error,detail----" + t);
            }
        }
        return count;
    }

    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable t) {
            log.error("redis has key exec was error " + key + ", detail----" + t);
        }
        return false;
    }


    public <T> T get(String key) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Throwable t) {
            log.error("redis get key exec was error " + key + ", detail----" + t);
        }
        return null;
    }


    public <T> T getHash(String key) {
        try {
            return (T) redisTemplate.opsForHash().entries(key);
        } catch (Throwable t) {
            log.error("redis get hash key exec was error " + key + ", detail----" + t);
        }
        return null;
    }

    public <T> List<T> getSecondList(String match) {
        Set<String> set = redisTemplate.keys(match);
        Iterator<String> it = set.iterator();
        List<T> list = Lists.newArrayList();
        while (it.hasNext()) {
            String keyStr = it.next();
            try {
                list.add((T) redisTemplate.opsForValue().get(keyStr));
            } catch (Throwable t) {
                log.error("redis remove " + it + " has error,detail----" + t);
            }
        }
        return list;
    }


    public <T> T getHashOfList(String key) {
        try {
            return (T) redisTemplate.opsForHash().values(key);
        } catch (Throwable t) {
            log.error("redis get hash list key exec was error " + key + ", detail----" + t);
        }
        return null;
    }

    public <T> Long leftPush(String key, T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public <T> T rightPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    public Long queueSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public Long increment(String key, long base, long delta) {
        Object v = redisTemplate.opsForValue().get(key);
        if (ObjectUtils.isEmpty(v)) {
            redisTemplate.opsForValue().set(key, base);
        }

        return redisTemplate.opsForValue().increment(key, delta);
    }

}
