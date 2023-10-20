package com.example.customizeauthz.security;

import com.example.customizeauthz.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class SecurityConfiguration {
    @Resource
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request,response,exception)->{
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            // 写出去
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "401");
            map.put("msg", exception.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    //认证成功处理器
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (request, response, authentication)->{
            response.setContentType("application/json;charset=utf-8");
            // 写出去
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "0");
            map.put("msg", "LOGIN SUCCESS");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    //认证失败处理器
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            // 写出去
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "401");
            map.put("msg", exception.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    //注销处理器
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication)->{
            response.setContentType("application/json;charset=utf-8");
            // 写出去
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", "0");
            map.put("msg", "LOGOUT SUCCESS");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(map);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                //从Authentication对象中获取用户名和身份凭证信息
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();
                UserDetails user = userService.loadUserByUsername(username);
                //if (password.equals(user.getPassword())) {
                if (passwordEncoder().matches(password, user.getPassword())) {
                    log.info("Access success:" + user.toString());
                    //密码匹配成功则构建一个UsernamePasswordAuthenticationToken对象并返回
                    return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
                } else {
                    //密码匹配失败则抛出异常
                    log.error("Access denied:The username or password is wrong!");
                    throw new BadCredentialsException("The username or password is wrong!");
                }
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }

    //基于基础认证模式进行测试
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests((authz) -> {
            authz.anyRequest().authenticated();
        }).httpBasic(withDefaults());
        return http.build();
        */
        //启用会话存储
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                //任何请求必须要经过认证才可以放行
                .anyRequest().authenticated()
                .and()
                //启用表单认证模式
                .formLogin()
                // 登录成功执行的
                .successHandler(authenticationSuccessHandler())
                // 登录失败执行的
                .failureHandler(authenticationFailureHandler())
                //默认登录页面
                //.loginPage("/login.html")
                //默认请求提交地址
                .loginProcessingUrl("/check_login")
                //放行上面loginPage与loginProcessingUrl不做认证
                .permitAll()
                //设置提交的参数名
                .usernameParameter("u")
                .passwordParameter("p")
                .and()
                //开始设置注销功能
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                //注销功能的URL地址
                .logoutUrl("/logout")
                //Session直接过期
                .invalidateHttpSession(true)
                //清除认证信息
                .clearAuthentication(true)
                //注销后跳转地址
                //.logoutSuccessUrl("/login.html")
                .and()
                //禁用csrf安全防护
                .csrf().disable();
        return http.build();
    }

}