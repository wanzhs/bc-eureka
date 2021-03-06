## 熔断说明
1. 通过```@EnableCircuitBreaker 和 @HystrixCommand(fallbackMethod = "getUserByIdFallback")```指令实现熔断
启动类上加上注解@EnableCircuitBreaker，熔断的方法上加上注解 @HystrixCommand。手动创建feign时用此方法

2. 通过feign熔断机制熔断。通过@FeignClient 的fallback或fallbackFactory指定熔断类。默认feign时用此方法。

## ZipKin链路追踪
1. 在官网下载服务端并运行，springboot2 不再支持通过注解方式，默认存储内存中，启动时可以指定存储地址和参数
```
java -jar zipkin-server-2.12.9-exec.jar --STORAGE_TYPE=mysql --MYSQL_HOST=192.168.2.132  --MYSQL_TCP_PORT=3306  --MYSQL_USER=root --MYSQL_PASS=root --MYSQL_DB=zipkin  --MYSQL_JDBC_URL=jdbc:mysql://192.168.2.132:3306/zipkin
``` 
2. 客戶端引入依赖
``` 
    <dependency>
           <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>
```
3. 配置文件配置中配置zipkin的访问信息
```
spring:
  application:
    name: eureka-client
  zipkin:
    base-url: http://localhost:9411/
  sleuth:
    sampler:
      probability: 1
``` 

### zipkin服务端配合mysql
参考GitHub官网作者源码：
``` 
https://github.com/openzipkin/zipkin
```
* 创建相关库和表，表结构如下：
```
CREATE TABLE IF NOT EXISTS zipkin_spans (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL,
  `id` BIGINT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `parent_id` BIGINT,
  `debug` BIT(1),
  `start_ts` BIGINT COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` BIGINT COMMENT 'Span.duration(): micros used for minDuration and maxDuration query'
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;
 
ALTER TABLE zipkin_spans ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `id`) COMMENT 'ignore insert on duplicate';
ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`, `id`) COMMENT 'for joining with zipkin_annotations';
ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTracesByIds';
ALTER TABLE zipkin_spans ADD INDEX(`name`) COMMENT 'for getTraces and getSpanNames';
ALTER TABLE zipkin_spans ADD INDEX(`start_ts`) COMMENT 'for getTraces ordering and range';
 
CREATE TABLE IF NOT EXISTS zipkin_annotations (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
  `span_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.id',
  `a_key` VARCHAR(255) NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` BLOB COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` INT NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` BIGINT COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` INT COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` BINARY(16) COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` SMALLINT COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` VARCHAR(255) COMMENT 'Null when Binary/Annotation.endpoint is null'
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;
 
ALTER TABLE zipkin_annotations ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `span_id`, `a_key`, `a_timestamp`) COMMENT 'Ignore insert on duplicate';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`, `span_id`) COMMENT 'for joining with zipkin_spans';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTraces/ByIds';
ALTER TABLE zipkin_annotations ADD INDEX(`endpoint_service_name`) COMMENT 'for getTraces and getServiceNames';
ALTER TABLE zipkin_annotations ADD INDEX(`a_type`) COMMENT 'for getTraces';
ALTER TABLE zipkin_annotations ADD INDEX(`a_key`) COMMENT 'for getTraces';
 
CREATE TABLE IF NOT EXISTS zipkin_dependencies (
  `day` DATE NOT NULL,
  `parent` VARCHAR(255) NOT NULL,
  `child` VARCHAR(255) NOT NULL,
  `call_count` BIGINT,
  `error_count` BIGINT
  ) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_dependencies ADD UNIQUE KEY(`day`, `parent`, `child`);
```
* 启动zipkin，并制定存储类型以及地连接参数
```
java -jar zipkin-server-2.12.9-exec.jar --STORAGE_TYPE=mysql --MYSQL_HOST=192.168.2.132  --MYSQL_TCP_PORT=3306  --MYSQL_USER=root --MYSQL_PASS=root --MYSQL_DB=zipkin  --MYSQL_JDBC_URL=jdbc:mysql://192.168.2.132:3306/zipkin
```
* 启动客户端连接即可

---

### 注意
1. 如果出现bean重复，需要定义一个bean，并通过```@Primary```注解定义一个主bean解决冲突。

## sentinel
###### 1. git clone github上下载源码，打包
```
拉取源码：git clone https://github.com/alibaba/sentinel
打包：mvn clean package 
```
###### 2. 将sentinel-dashboard 的jar包拷贝到服务端目录，启动
```
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
```
###### 3. 客户端接入控制台
引入依赖：
```
 <dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-core</artifactId>
    <version>1.7.0</version>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-annotation-aspectj</artifactId>
    <version>1.7.0</version>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-transport-simple-http</artifactId>
    <version>1.7.0</version>
</dependency>
```
配置启动参数：

```
-Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=eureka-client
```
客户端访问限流控制的接口：
```
http://localhost:8033/sentinel/test/loop?requests=1000&qpslimits=20
    @Override
    public void testLoop(Long requests, Integer qpslimits) {
        // 配置规则.
        initFlowRules(qpslimits, ResourcesConstant.RESOURCE1);
        while (requests-- > 0) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性，自动 exit entry
            try (Entry entry = SphU.entry(ResourcesConstant.RESOURCE1)) {
                // 被保护的逻辑
                System.out.println("testAspectj2 world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }
    
    private static void initFlowRules(Integer qpslimits, String resourceName) {
            List<FlowRule> rules = new ArrayList<>();
            FlowRule rule = new FlowRule();
            rule.setResource(resourceName);
            rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
            // Set limit QPS to 20.
            rule.setCount(qpslimits);
            rules.add(rule);
            FlowRuleManager.loadRules(rules);
        }
        
注解测试：
http://localhost:8033/sentinel/test/aspectj1
http://localhost:8033/sentinel/test/aspectj2

    @GetMapping("/test/aspectj2")
    public void testAspectj2(@RequestParam(value = "s", required = false) Long s) {
        if (ObjectUtils.isEmpty(s)) {
            s = System.currentTimeMillis();
        }
        int i=1000;
        while (i-->0){
            sentinelService.testAspectj2(s);
        }
    }
    
        @Override
        // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
        @SentinelResource(value = ResourcesConstant.RESOURCE2, blockHandler = "testAspectj1BlockHandler", blockHandlerClass = ExceptionUtil.class)
        public void testAspectj1() {
            System.out.println("testAspectj1");
        }
    
        @Override
        // 原函数
        @SentinelResource(value = ResourcesConstant.RESOURCE3, blockHandler = "testAspectj2FallbackBlockExceptionHandler"
                , fallback = "testAspectj2Fallback")
        public String testAspectj2(long s) {
            return String.format("Hello at %d", s);
        }
    
        // Fallback函数，函数起那么与原函数一致或加一个Throwable类型的函数
        public String testAspectj2Fallback(long s) {
            return String.format("halloFallback %d", s);
        }
    
        // Block异常处理函数，参数最后多一个BlockException，其余与原函数一致。
        public String testAspectj2FallbackBlockExceptionHandler(long s, BlockException ex) {
            // Do some log here
            ex.printStackTrace();
            return "Oops, Error occurred at " + s;
        }
    
    

```

###### 4. 控制台操作查看
![控制台功能介绍](./src/main/resources/static/sentinel_home.png)