package com.mask.todolist.repository;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepo {

	private final RedisTemplate<String, String> redisTemplate;

	@Autowired
	public RedisRepo(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public void save(String key, String value, Duration time) {
		redisTemplate.opsForValue().set(key, value, time);
	}

	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void del(String key) {
		redisTemplate.delete(key);
	}

}
