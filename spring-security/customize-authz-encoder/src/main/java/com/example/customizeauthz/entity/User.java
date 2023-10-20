package com.example.customizeauthz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Data
@TableName("cust_user")
public class User implements UserDetails {
     @TableId(type = IdType.NONE)
     private Integer id;
     private String username;
     private String password;
     private String nickname;
     private Integer enabled;

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
     }

     @Override
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return enabled==1?true:false;
     }

}