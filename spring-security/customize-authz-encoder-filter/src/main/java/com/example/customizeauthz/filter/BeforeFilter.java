package com.example.customizeauthz.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class BeforeFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException, IOException {
        System.out.println("This is a filter before UsernamePasswordAuthenticationFilter.");
        // 继续调用 Filter 链
        filterChain.doFilter(servletRequest, servletResponse);
    }
}