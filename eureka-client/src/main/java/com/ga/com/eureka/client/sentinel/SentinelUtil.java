package com.ga.com.eureka.client.sentinel;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 工具类
 *
 * @author wanzhongsu
 * @date 2019/12/25 13:46
 */
public class SentinelUtil {
    public static void initFlowRules(String resourceName, Integer qpslimits) {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(resourceName);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(qpslimits);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    public static void initDegradeRule(String resourceName, Integer count, Integer timeWindow) {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(resourceName);
        // 超过4+count值后抛出DegradeException异常触发熔断降级
        rule.setCount(count);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(timeWindow);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    public static void initParamFlowRule(String resourceName) {
        List<ParamFlowRule> rules = new ArrayList<>();
        ParamFlowRule rule = new ParamFlowRule(resourceName)
                .setParamIdx(0)
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setCount(5);
        // 针对int类型的参数param_b，单独设置QPS，阈值为10，而不是全局的阈值5
        ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf(1))
                .setClassType(int.class.getName())
                .setCount(10);
        rule.setParamFlowItemList(Collections.singletonList(item));
        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }


    public static void initSystemRule(String resourceName) {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setResource(resourceName);
        rule.setQps(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }

    public static void initAuthorityRule(String resourceName) {
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(resourceName);
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("appA,appB");
        rules.add(rule);
        AuthorityRuleManager.loadRules(rules);
    }
}
