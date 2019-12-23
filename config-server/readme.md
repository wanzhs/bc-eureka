## 配置服务中心
1.  建库建表
```
本项目中为了避免和mysql数据库字段相冲突，自定义查询字段
CREATE TABLE `config_server` (
`id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`akey`  varchar(50) NULL COMMENT '键' ,
`avalue`  varchar(100) NULL COMMENT '值' ,
`application`  varchar(100) NULL COMMENT '应用名称' ,
`aprofile`  varchar(50) NULL COMMENT '所属轮廓' ,
`label`  varchar(20) NULL COMMENT '标签' ,
PRIMARY KEY (`id`)
)
;

INSERT INTO `zipkin`.`config_server` (`id`, `akey`, `avalue`, `application`, `aprofile`, `label`) VALUES ('1', 'item_url', 'www.master.com', 'eureka-client', 'master', 'master');
INSERT INTO `zipkin`.`config_server` (`id`, `akey`, `avalue`, `application`, `aprofile`, `label`) VALUES ('2', 'item_url', 'www.dev.com', 'eureka-client', 'dev', 'dev');
INSERT INTO `zipkin`.`config_server` (`id`, `akey`, `avalue`, `application`, `aprofile`, `label`) VALUES ('3', 'item_url', 'www.dev.com', 'eureka-provider', 'dev', 'dev');

```
2. 项目中指定相关参数

## 自动刷新
1. 配置服务端，引入bus总线（通讯方式可以选kafka、rabbitmq）
2. 配置客户端，需要自动刷新的配置文件上加```@RefreshScope```注解
3. 配置更新后触发重新拉取配置文件操作,命令：
```
客户或服务端触发更新配置：
http://localhost:8033/actuator/bus-refresh(POST)
http://localhost:8035/actuator/bus-refresh(POST)
```

读取配置方法：
```
请求：http://localhost:8035/eureka-client/dev/dev
返回：
{
    "name": "eureka-client",
    "profiles": [
        "dev"
    ],
    "label": "dev",
    "version": null,
    "state": null,
    "propertySources": [
        {
            "name": "eureka-client-dev",
            "source": {
                "item_url": "www.dev.com1",
                "custom.username": "john",
                "custom.password": "passwd"
            }
        }
    ]
}
```

### 注意
如果注册到了监控，自动刷新接口会被覆盖掉，不存在改接口，造成自动刷新的接口无法使用。