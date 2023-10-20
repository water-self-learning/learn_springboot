package com.example.customizeauthz.controller;

import com.example.customizeauthz.entity.User;
import com.example.customizeauthz.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @Resource
    private UserService userService;
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/create_user")
    private String createUser(){
        userService.createUser();
        return "success";
    }

    @GetMapping("/import")
    private String imp(){
        List users = new ArrayList<>();
        users.add(new User(1, "zhangsan", "zhangsan", "张三", 1));
        users.add(new User(2, "lisi", "lisi", "李四", 1));
        users.add(new User(3, "wangwu", "wangwu", "王五", 1));
        userService.batchImport(users);
        return "success";
    }

    @GetMapping("/list")
    private List list(){
        List<User> users = userService.listUser();
        return users;
    }
}