package com.lbs.shared.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Eric Xue
 * @date 2021/06/09
 * @describe
 * 此类为配置类，主要作用是配置和自定义RedisTemplate。
 */
@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    // 注入Jedis连接工厂
    private final JedisConnectionFactory factory;

    // 定义一个Spring Bean，用于提供RedisTemplate实例
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        return getRedisTemplate(Object.class);
    }

    // 泛型方法，用于创建并配置RedisTemplate
    private <T> RedisTemplate<String, T> getRedisTemplate(Class<T> type) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 配置键（key）的序列化方式为字符串序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 配置哈希键（hash key）的序列化方式为字符串序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 获取自定义的值（value）序列化器
        RedisSerializer<T> customSerializer = getSerializer(type);
        // 配置值（value）的序列化方式
        redisTemplate.setValueSerializer(customSerializer);
        // 配置哈希值（hash value）的序列化方式
        redisTemplate.setHashValueSerializer(customSerializer);
        return redisTemplate;
    }

    // 创建并返回自定义的Redis序列化器
    private <T> RedisSerializer<T> getSerializer(Class<T> type) {
        // 创建ObjectMapper实例，用于JSON处理
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置ObjectMapper以序列化任何可见性的属性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 激活默认类型化，允许非最终类的类型信息包含在JSON中
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        // 创建并返回Jackson 2 JSON Redis序列化器
        Jackson2JsonRedisSerializer<T> serializer = new Jackson2JsonRedisSerializer<>(type);
        // 设置自定义的ObjectMapper到序列化器
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }
}
