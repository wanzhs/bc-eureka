package com.ga.com.eureka.provider.controller.server;

import com.ga.com.eureka.eurekacenter.base.IProductService;
import com.ga.com.eureka.eurekacenter.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
public class ProductResourceController implements IProductService {

    @Override
    public Product getProductById(Long id) {
        return new Product().setProductName("商品" + id).setDesc("描述" + id).setPrice(BigDecimal.valueOf(id));
    }
}
