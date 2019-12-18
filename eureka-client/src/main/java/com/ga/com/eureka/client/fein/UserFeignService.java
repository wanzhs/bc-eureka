package com.ga.com.eureka.client.fein;


import com.ga.com.eureka.client.fallback.UserFeignServiceHystrix;
import com.ga.com.eureka.eurekacenter.base.IUserService;
import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(value = "eureka-provider", contextId = "eureka-server-resource-user", fallback = UserFeignServiceHystrix.class)
public interface UserFeignService extends IUserService {
}
