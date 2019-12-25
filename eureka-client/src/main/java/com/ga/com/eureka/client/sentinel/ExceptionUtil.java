package com.ga.com.eureka.client.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {
    public static void testAspectj1BlockHandler(BlockException ex) {
        System.out.println("testAspectj1BlockHandler blocked ........");
    }

    public static void testSystemRuleBlocker(BlockException ex) {
        System.out.println("testSystemRuleBlocker blocked......");
    }

    public static void testParamFlowRuleBlocker(int userId, String userTel, String userName, BlockException ex) {
        System.out.println("testParamFlowRuleBlocker blocked......");
    }
}
