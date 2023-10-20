package com.example.customizeauthz.security;

import com.example.customizeauthz.service.UserService;
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
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    public boolean authorize(Authentication authentication, HttpServletRequest request) {
        //查询username对应的权限，如拥有权限返回true,否则返回false
        return true;
    }
    //基于基础认证模式进行测试
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.authorizeRequests().mvcMatchers("/he*").access("permitAll()");
        http.authorizeRequests().mvcMatchers("/hello").access("denyAll()");

        http.authorizeRequests().anyRequest()
//                .access("hasAuthority('CREATE_DOC') and hasAuthority('UPDATE_DOC')")
//                .access("hasAnyRole('USER','USER1') and !isRememberMe()")
                .access("@securityConfiguration.authorize(authentication,request)")
                .and().httpBasic(withDefaults());

        return http.build();
    }

}