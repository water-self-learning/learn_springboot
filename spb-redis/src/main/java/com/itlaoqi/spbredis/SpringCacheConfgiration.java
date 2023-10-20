package com.itlaoqi.spbredis;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class SpringCacheConfgiration {
    @Bean
    @Primary //设置默认的CacheManager
    public CacheManager cacheManager(LettuceConnectionFactory factory){
        //加载默认Spring Cache配置信息
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        //设置有效期为1小时
        config = config.entryTtl(Duration.ofHours(1));
        //说明缓存Key使用单冒号进行分割
        config = config.computePrefixWith(cacheName -> cacheName + ":");
        //Redis Key采用String直接存储
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        //Redis Value则将对象采用JSON形式存储
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        //不缓存Null值对象
        config = config.disableCachingNullValues();

        //实例化CacheManger缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder
                //绑定REDIS连接工厂
                .fromConnectionFactory(factory)
                //绑定配置对象
                .cacheDefaults(config)
                //与声明式事务注解@Transactional进行兼容
                .transactionAware()
                //完成对象构建
                .build();
        return cacheManager;

    }

    @Bean
    public CacheManager cacheManager1m(LettuceConnectionFactory factory){
        //加载默认Spring Cache配置信息
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        //设置有效期为1小时
        config = config.entryTtl(Duration.ofMinutes(1));
        //说明缓存Key使用单冒号进行分割
        config = config.computePrefixWith(cacheName -> cacheName + ":");
        //Redis Key采用String直接存储
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        //Redis Value则将对象采用JSON形式存储
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        //不缓存Null值对象
        config = config.disableCachingNullValues();

        //实例化CacheManger缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder
                //绑定REDIS连接工厂
                .fromConnectionFactory(factory)
                //绑定配置对象
                .cacheDefaults(config)
                //与声明式事务注解@Transactional进行兼容
                .transactionAware()
                //完成对象构建
                .build();
        return cacheManager;

    }
}
