package com.example.customizeauthz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/user")
    public void userInfo() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();
        System.out.println("username = " + username);
        System.out.println("authorities = " + authorities);
    }
}