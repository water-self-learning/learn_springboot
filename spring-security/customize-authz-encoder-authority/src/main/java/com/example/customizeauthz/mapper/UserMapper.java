package com.example.customizeauthz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.customizeauthz.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
