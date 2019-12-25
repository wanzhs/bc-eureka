package com.ga.com.eureka.client.sentinel.service.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.ga.com.eureka.client.sentinel.ExceptionUtil;
import com.ga.com.eureka.client.sentinel.ResourcesConstant;
import com.ga.com.eureka.client.sentinel.SentinelUtil;
import com.ga.com.eureka.client.sentinel.service.ISentinelService;
import org.springframework.stereotype.Service;

@Service
public class SentinelServiceImpl implements ISentinelService {
    @Override
    public void testLoop(Long requests, Integer qpslimits) {
        // 配置规则.
        SentinelUtil.initFlowRules(ResourcesConstant.RESOURCE1, qpslimits);
        while (requests-- > 0) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性，自动 exit entry
            try (Entry entry = SphU.entry(ResourcesConstant.RESOURCE1)) {
                // 被保护的逻辑
                System.out.println("testLoop...................");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }

    @Override
    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
    @SentinelResource(value = ResourcesConstant.RESOURCE2, blockHandler = "testAspectj1BlockHandler", blockHandlerClass = ExceptionUtil.class)
    public void testAspectj1() {
        System.out.println("testAspectj1");
    }

    @Override
    // 原函数  fallback 只针对熔断降级有效，并且blockHandler和fallback只有一个生效，blockHandler会覆盖fallback
    @SentinelResource(value = ResourcesConstant.RESOURCE3
            , blockHandler = "testAspectj2FallbackBlockExceptionHandler"
            , fallback = "testAspectj2Fallback")
    public String testAspectj2(long s) {
        if (s % 2 == 0) {
            throw new RuntimeException("业务逻辑异常");
        }
        return String.format("Hello at %d", s);
    }

    @Override
    @SentinelResource(value = ResourcesConstant.RESOURCE4, blockHandler = "testSystemRuleBlocker", blockHandlerClass = ExceptionUtil.class)
    public void testSystemRule() {
        System.out.println("===========系统保护规则");
    }

    @Override
    public void testAuthorityRule() {
        ContextUtil.enter(ResourcesConstant.RESOURCE5, "appA");
        Entry entry = null;
        try {
            entry = SphU.entry(ResourcesConstant.RESOURCE5);
            System.out.println("授权passeded......");
        } catch (BlockException ex) {
            System.out.println("授权blocked......");
        } finally {
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }
    }

    // Fallback函数，函数起那么与原函数一致或加一个Throwable类型的函数
    public String testAspectj2Fallback(long s) {
        return String.format("helloFallback %d", s);
    }

    // Block异常处理函数，参数最后多一个BlockException，其余与原函数一致。
    public String testAspectj2FallbackBlockExceptionHandler(long s, BlockException ex) {
        // Do some log here
        ex.printStackTrace();
        return "Oops, Error occurred at " + s;
    }
}
