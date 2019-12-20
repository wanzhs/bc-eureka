package com.ga.com.eureka.client.controller;

import com.ga.com.eureka.client.config.UserConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 配置测试
 *
 * @author wanzhongsu
 * @date 2019/12/20 14:58
 */
@RefreshScope // 该注解用于自动刷新配置的值
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Value("${item_url}")
    private String itmeUrl;
    @Resource
    private UserConfig userConfig;

    @RequestMapping("/item_url")
    public String getItmeUrl() {
        return this.itmeUrl;
    }

    @RequestMapping("/get/userconfig")
    public UserConfig getUserConfig() {
        return userConfig;
    }
}
