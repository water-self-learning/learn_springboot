package com.example.customizeauthz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.customizeauthz.entity.User;
import com.example.customizeauthz.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Resource
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            log.error("Authenticate denied: Username: " + username + " not found");
            throw new UsernameNotFoundException("Username:" + username + "not found");
        }else{
            return user;
        }

    }
}
