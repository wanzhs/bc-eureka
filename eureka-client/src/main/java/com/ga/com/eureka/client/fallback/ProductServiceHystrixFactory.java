package com.ga.com.eureka.client.fallback;

import com.ga.com.eureka.client.fein.ProductService;
import com.ga.com.eureka.eurekacenter.entity.Product;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductServiceHystrixFactory implements FallbackFactory<ProductService> {
    @Override
    public ProductService create(Throwable throwable) {
        return new ProductService() {
            @Override
            public Product getProductById(Long id) {
                return new Product().setProductName("熔断商品").setPrice(BigDecimal.ZERO).setDesc("熔断描述");
            }
        };
    }
}
