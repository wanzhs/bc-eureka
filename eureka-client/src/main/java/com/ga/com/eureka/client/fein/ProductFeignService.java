package com.ga.com.eureka.client.fein;

import com.ga.com.eureka.client.fallback.ProductServiceHystrixFactory;
import com.ga.com.eureka.eurekacenter.base.IProductService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "eureka-provider", contextId = "eureka-server-resource-product",
        fallbackFactory = ProductServiceHystrixFactory.class)
public interface ProductFeignService extends IProductService {

}
