package com.ga.com.eureka.client.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test1")
public class Test1Controller {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "thymeleaf");
        return "hello";
    }
    @ResponseBody
    @GetMapping("/user")
    public String userinfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user = "";
        if (principal instanceof UserDetails) {
            user = ((UserDetails) principal).getUsername();
            System.out.println("userdetails:=====");
        } else {
            user = principal.toString();
        }
        return "some user info user:" + user;
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admininfo() {
        return "all admin info";
    }
}
