package com.ga.com.eureka.client.fein;


import com.ga.com.eureka.client.fallback.UserServiceHystrix;
import com.ga.com.eureka.eurekacenter.service.IUserService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "eureka-provider", contextId = "eureka-server-resource-user", fallback = UserServiceHystrix.class)
public interface UserService extends IUserService {
}
