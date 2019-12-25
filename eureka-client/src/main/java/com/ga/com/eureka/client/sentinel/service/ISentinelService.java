package com.ga.com.eureka.client.sentinel.service;

public interface ISentinelService {
    void testLoop(Long requests,Integer qpslimits);

    void testAspectj1();

    String testAspectj2(long s);

    void testSystemRule();

    void testAuthorityRule();
}
