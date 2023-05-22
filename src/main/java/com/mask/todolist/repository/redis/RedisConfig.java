package com.mask.todolist.repository.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	/**
	 * Redis工具類
	 * RedisTemplate<String,String> 表示儲存的KV都是Stirng
	 */
	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory conn) {

		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

		redisTemplate.setConnectionFactory(conn);
		// 將Key序列化為String
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// 將Value序列化為String
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		return redisTemplate;
	}

}
