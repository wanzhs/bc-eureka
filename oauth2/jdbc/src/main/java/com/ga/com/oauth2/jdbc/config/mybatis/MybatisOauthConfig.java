package com.ga.com.oauth2.jdbc.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootConfiguration
@MapperScan(basePackages = "mapper.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisOauthConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setDialectType("mysql");
    }

    // 配置数据源
    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(DBOauthConfig config) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(config.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(config.getPassword());
        mysqlXaDataSource.setUser(config.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("dataSource");
        xaDataSource.setMinPoolSize(config.getMinPoolSize());
        xaDataSource.setMaxPoolSize(config.getMaxPoolSize());
        xaDataSource.setMaxLifetime(config.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(config.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(config.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(config.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(config.getMaxIdleTime());
        xaDataSource.setTestQuery(config.getTestQuery());
        return xaDataSource;
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeEnumsPackage("mapper.enums,mapper.entity");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/oauthMapper/**Mapper.xml"));
        //***导入Mybatis配置***
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        //配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId）
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        bean.setConfiguration(configuration);
        bean.setPlugins(new Interceptor[]{paginationInterceptor()});
        //***导入plus全局配置***
        GlobalConfig globalConfig = globalConfiguration();
        bean.setGlobalConfig(globalConfig);
        return bean.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 在代码中配置MybatisPlus替换掉application.yml中的配置
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig.DbConfig conf = new GlobalConfig.DbConfig();
        //数据库自增
        conf.setIdType(IdType.NONE);
        //字段策略 忽略判断
        conf.setFieldStrategy(FieldStrategy.IGNORED);
        //驼峰下划线转换含查询column及返回column(column下划线命名create_time，返回java实体是驼峰命名createTime，开启后自动转换否则保留原样)
        conf.setTablePrefix("t_");
        //数据库类型
        conf.setDbType(DbType.MYSQL);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(conf);
        return globalConfig;
    }

}
