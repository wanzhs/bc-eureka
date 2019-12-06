package com.ga.com.eureka.client.fein;


//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Component
//@FeignClient(name = "EUREKA-PROVIDER", path = "/test1", url = "http://192.168.2.221:8034")  //3种写法均可，推荐第三种
//@FeignClient(name = "EUREKA-PROVIDER", path = "/test1") //大写
//@FeignClient(name = "eureka-provider", path = "/test1") //与项目里的名称一致
public interface TestService {
    @RequestMapping("/echo")
    public String echo(@RequestParam("param") String parameter);
}
