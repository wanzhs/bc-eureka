## 熔断说明
1. 通过```@EnableCircuitBreaker 和 @HystrixCommand(fallbackMethod = "getUserByIdFallback")```指令实现熔断
启动类上加上注解@EnableCircuitBreaker，熔断的方法上加上注解 @HystrixCommand。手动创建feign时用此方法

2. 通过feign熔断机制熔断。通过@FeignClient 的fallback或fallbackFactory指定熔断类。默认feign时用此方法。

## ZipKin链路追踪
1. 在官网下载服务端并运行，springboot2 不再支持通过注解方式
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
### 注意
1. 如果出现bean重复，需要定义一个bean，并通过```@Primary```注解定义一个主bean解决冲突。