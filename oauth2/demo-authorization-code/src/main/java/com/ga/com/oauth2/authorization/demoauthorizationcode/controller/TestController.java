package com.ga.com.oauth2.authorization.demoauthorizationcode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    /**
     * 获取用户列表
     *
     * @author wanzhongsu
     * @date 2019/12/12 13:00
     */
    @RequestMapping("/get/users/list")
    public Object getUsersList() {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "user" + i);
            map.put("tel", "1814868982" + i);
            map.put("sex", "man");
            list.add(map);
        }
        return list;
    }
}
