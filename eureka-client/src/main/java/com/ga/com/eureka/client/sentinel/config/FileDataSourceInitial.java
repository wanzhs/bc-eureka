package com.ga.com.eureka.client.sentinel.config;

import com.alibaba.csp.sentinel.command.handler.ModifyParamFlowRulesCommandHandler;
import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
public class FileDataSourceInitial implements InitFunc {
    /**
     * 定义并实现各个规则对象的转换器，用于将json格式的数据转换为相应的Java对象
     */
    private Converter<String, List<FlowRule>> flowRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<FlowRule>>() {
            }
    );

    private Converter<String, List<DegradeRule>> degradeRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<DegradeRule>>() {
            }
    );

    private Converter<String, List<SystemRule>> systemRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<SystemRule>>() {
            }
    );

    private Converter<String, List<AuthorityRule>> authorityRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<AuthorityRule>>() {
            }
    );

    private Converter<String, List<ParamFlowRule>> paramFlowRuleListParser = source -> JSON.parseObject(
            source,
            new TypeReference<List<ParamFlowRule>>() {
            }
    );

    @Override
    public void init() throws Exception {
        // 规则持久化文件所存放的路径及文件名，可以按需求自行修改
        String ruleDir = System.getProperty("user.home") + "/sentinel/rules";
        String flowRulePath = ruleDir + "/flow-rule.json";
        String degradeRulePath = ruleDir + "/degrade-rule.json";
        String systemRulePath = ruleDir + "/system-rule.json";
        String authorityRulePath = ruleDir + "/authority-rule.json";
        String paramFlowRulePath = ruleDir + "/param-flow-rule.json";

        // 目录路径及文件若不存在则创建
        this.mkdirIfNotExits(ruleDir);
        this.createFileIfNotExits(flowRulePath);
        this.createFileIfNotExits(degradeRulePath);
        this.createFileIfNotExits(systemRulePath);
        this.createFileIfNotExits(authorityRulePath);
        this.createFileIfNotExits(paramFlowRulePath);

        // 注册各个规则的可读写数据源
        this.registerFlowRWDS(flowRulePath);
        this.registerDegradeRWDS(degradeRulePath);
        this.registerSystemRWDS(systemRulePath);
        this.registerAuthorityRWDS(authorityRulePath);
        this.registerParamRWDS(paramFlowRulePath);
    }

    /**
     * 注册流控规则的可读写数据源
     */
    private void registerFlowRWDS(String flowRulePath) throws FileNotFoundException {
        // 构建可读数据源，用于定时读取本地的json文件
        ReadableDataSource<String, List<FlowRule>> flowRuleRDS = new FileRefreshableDataSource<>(
                flowRulePath,
                flowRuleListParser
        );
        // 将可读数据源注册至FlowRuleManager，当文件里的规则内容发生变化时，就会更新到缓存里
        FlowRuleManager.register2Property(flowRuleRDS.getProperty());

        // 构建可写数据源
        WritableDataSource<List<FlowRule>> flowRuleWDS = new FileWritableDataSource<>(
                flowRulePath,
                this::toJson
        );
        // 将可写数据源注册至transport模块的WritableDataSourceRegistry中
        // 这样收到控制台推送的规则时，Sentinel会先更新到内存，然后将规则写入到文件中
        WritableDataSourceRegistry.registerFlowDataSource(flowRuleWDS);
    }

    /**
     * 注册降级规则的可读写数据源
     */
    private void registerDegradeRWDS(String degradeRulePath) throws FileNotFoundException {
        ReadableDataSource<String, List<DegradeRule>> degradeRuleRDS = new FileRefreshableDataSource<>(
                degradeRulePath,
                degradeRuleListParser
        );
        DegradeRuleManager.register2Property(degradeRuleRDS.getProperty());
        WritableDataSource<List<DegradeRule>> degradeRuleWDS = new FileWritableDataSource<>(
                degradeRulePath,
                this::toJson
        );
        WritableDataSourceRegistry.registerDegradeDataSource(degradeRuleWDS);
    }

    /**
     * 注册系统规则的可读写数据源
     */
    private void registerSystemRWDS(String systemRulePath) throws FileNotFoundException {
        ReadableDataSource<String, List<SystemRule>> systemRuleRDS = new FileRefreshableDataSource<>(
                systemRulePath,
                systemRuleListParser
        );
        SystemRuleManager.register2Property(systemRuleRDS.getProperty());
        WritableDataSource<List<SystemRule>> systemRuleWDS = new FileWritableDataSource<>(
                systemRulePath,
                this::toJson
        );
        WritableDataSourceRegistry.registerSystemDataSource(systemRuleWDS);
    }

    /**
     * 注册授权规则的可读写数据源
     */
    private void registerAuthorityRWDS(String authorityRulePath) throws FileNotFoundException {
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleRDS = new FileRefreshableDataSource<>(
                authorityRulePath,
                authorityRuleListParser
        );
        AuthorityRuleManager.register2Property(authorityRuleRDS.getProperty());
        WritableDataSource<List<AuthorityRule>> authorityRuleWDS = new FileWritableDataSource<>(
                authorityRulePath,
                this::toJson
        );
        WritableDataSourceRegistry.registerAuthorityDataSource(authorityRuleWDS);
    }

    /**
     * 注册热点参数规则的可读写数据源
     */
    private void registerParamRWDS(String paramFlowRulePath) throws FileNotFoundException {
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleRDS = new FileRefreshableDataSource<>(
                paramFlowRulePath,
                paramFlowRuleListParser
        );
        ParamFlowRuleManager.register2Property(paramFlowRuleRDS.getProperty());
        WritableDataSource<List<ParamFlowRule>> paramFlowRuleWDS = new FileWritableDataSource<>(
                paramFlowRulePath,
                this::toJson
        );
        ModifyParamFlowRulesCommandHandler.setWritableDataSource(paramFlowRuleWDS);
    }

    private void mkdirIfNotExits(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            log.info("创建目录: {} filePath: {}", result ? "成功" : "失败", filePath);
        }
    }

    private void createFileIfNotExits(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean result = file.createNewFile();
            log.info("创建文件: {} filePath: {}", result ? "成功" : "失败", filePath);
        }
    }

    private <T> String toJson(T t) {
        return JSON.toJSONString(t);
    }
}
