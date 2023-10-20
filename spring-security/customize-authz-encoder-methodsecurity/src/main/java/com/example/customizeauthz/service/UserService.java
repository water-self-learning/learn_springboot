package com.example.customizeauthz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.customizeauthz.entity.User;
import com.example.customizeauthz.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
            log.error("Access denied: Username: " + username + " not found");
            throw new UsernameNotFoundException("Username:" + username + "not found");
        }else{
            return user;
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void  createUser(){
        System.out.println("user inserted");
    }

    @PreFilter(value = "filterObject.username == authentication.name",filterTarget = "users")
    public void batchImport(List<User> users){
        System.out.println(users);
    }

    @PostFilter(value = "filterObject.username == authentication.name")
    public List<User> listUser(){
        return userMapper.selectList(new QueryWrapper<>());
    }
}
