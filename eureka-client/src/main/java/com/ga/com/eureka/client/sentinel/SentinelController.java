package com.ga.com.eureka.client.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.ga.com.eureka.client.sentinel.service.ISentinelService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    @Resource
    ISentinelService sentinelService;

    /**
     * 常规测试
     *
     * @param requests
     * @param qpslimits
     */
    @GetMapping("/test/loop")
    public void test1(@RequestParam("requests") Long requests,
                      @RequestParam("qpslimits") Integer qpslimits) {
        sentinelService.testLoop(requests, qpslimits);
    }

    /**
     * 注解 流控规则 限流
     */
    @GetMapping("/test/aspectj1")
    public void testAspectj1() {
        SentinelUtil.initFlowRules(ResourcesConstant.RESOURCE2, 50);
        int i = 100;
        while (i-- > 0) {
            sentinelService.testAspectj1();
        }
    }

    /**
     * 注解 降级规则 熔断降级
     *
     * @param s
     */
    @GetMapping("/test/aspectj2")
    public void testAspectj2(@RequestParam(value = "s", required = false) Long s) {
        if (ObjectUtils.isEmpty(s)) {
            s = System.currentTimeMillis();
        }
        SentinelUtil.initDegradeRule(ResourcesConstant.RESOURCE3, 1, 10);
        int i = 100;
        while (i-- > 0) {
            String result = sentinelService.testAspectj2(s);
            System.out.println("/test/aspectj2=========" + result);
        }
    }

    @RequestMapping("/test/system/rule")
    public void testSystemRules() {
        SentinelUtil.initSystemRule(ResourcesConstant.RESOURCE4);
        int i = 100000;
        while (i-- > 0) {
            sentinelService.testSystemRule();
        }
    }

    @RequestMapping("/test/authority/rule")
    public void testAuthorityRules() {
//        SentinelUtil.initAuthorityRule(ResourcesConstant.RESOURCE5);
        int i = 100;
        while (i-- > 0) {
            sentinelService.testAuthorityRule();
        }
    }

    @RequestMapping("/test/param/flow/rule")
    public void testParamFlowRules(@RequestParam("userId") int userId) {
        SentinelUtil.initParamFlowRule(ResourcesConstant.RESOURCE6);
        int i = 100;
        while (i-- > 0) {
            Entry entry = null;
            try {
                entry = SphU.entry(ResourcesConstant.RESOURCE6, EntryType.IN, 1, userId);
                System.out.println("passed........" + userId);
            } catch (BlockException ex) {
                System.out.println("blocked.........." + userId);
            } finally {
                if (entry != null) {
                    entry.exit(1, userId);
                }
            }
        }
    }
}
