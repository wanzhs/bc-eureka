package com.ga.com.eureka.provider.service.impl;

import com.ga.com.eureka.provider.service.TestService;


//https://blog.csdn.net/andy_zhang2007/article/details/86680622
public class TestServiceImpl implements TestService {
    @Override
    public String echo(String parameter) {
        return "peace you ! " + parameter;
    }
}
