## 熔断说明
1. 通过```@EnableCircuitBreaker 和 @HystrixCommand(fallbackMethod = "getUserByIdFallback")```指令实现熔断
启动类上加上注解@EnableCircuitBreaker，熔断的方法上加上注解 @HystrixCommand。手动创建feign时用此方法

2. 通过feign熔断机制熔断。通过@FeignClient 的fallback或fallbackFactory指定熔断类。默认feign时用此方法。

