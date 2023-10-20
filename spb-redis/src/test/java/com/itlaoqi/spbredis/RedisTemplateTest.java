package com.itlaoqi.spbredis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        redisTemplate.opsForValue().set("name" , "itlaoqi");
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        redisTemplate.opsForValue().set("age" , map);
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
        Map age = (Map)redisTemplate.opsForValue().get("age");
        System.out.println(age);
    }

    @Test
    public void testHash(){
        redisTemplate.opsForHash().put("website" , "name" , "IT老齐");
        Map map = new HashMap<>();
        map.put("name", "张三");
        map.put("hiredate", "2021-05-06");
        redisTemplate.opsForHash().putAll("employee",map);
        Map<Object, Object> employee = redisTemplate.opsForHash().entries("employee");
        System.out.println(employee);
    }

    @Test
    public void testBasic(){
        redisTemplate.expire("employee", 30, TimeUnit.MINUTES);
        redisTemplate.delete("employee");
    }

    @Test
    public void  testList(){
        redisTemplate.opsForList();
    }
    @Test
    public void testSet(){
        redisTemplate.opsForSet();
    }
    @Test
    public void testZset(){
        redisTemplate.opsForZSet();
    }

}
