package com.ga.com.oauth2.jwt.config.mybatis;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 中心库配置
 *
 * @author wanzhongsu
 * @date 2019/11/30 10:16
 */
@Data
@ConfigurationProperties(prefix = "mysql.datasource.oauth")
@SpringBootConfiguration
public class DBOauthConfig {
    private String url;
    private String username;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;
}
