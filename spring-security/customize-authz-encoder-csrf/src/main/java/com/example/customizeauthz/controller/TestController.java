package com.example.customizeauthz.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {
    @GetMapping("/csrf-token")
    public String getCsrfToken(HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken)request.getAttribute("_csrf");
        if(csrfToken != null) {
            return csrfToken.getToken();
        }else{
            return "";
        }
    }
    @PostMapping("/hello")
    public String phello(){
        return "hello";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}