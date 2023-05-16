package com.mask.todolist.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mask.todolist.exception.error.UserException;
import com.mask.todolist.model.User;
import com.mask.todolist.repository.RedisRepo;
import com.mask.todolist.repository.UserRepo;
import com.mask.todolist.util.Hash;
import com.mask.todolist.util.JwtUtil;

@Service
public class UserService {

	@Autowired
	UserRepo repo;
	@Autowired
	RedisRepo redisRepo;

	/**
	 * 註冊使用者
	 */
	public void UserRegister(User user) throws Exception {
		try {
			repo.save(user);
		} catch (Exception e) {
			throw new UserException().CreateFail(e);
		}
	}

	/**
	 * 登入
	 */
	public User UserLogin(String account, String password) throws Exception {

		String pwdhash = Hash.generateHash(account, password);
		User user = repo.findByAccountAndPwdHash(account, pwdhash);

		if (user == null) {
			throw new UserException().NotFoundUser(new IllegalArgumentException());
		}

		return user;
	}

	/**
	 * 產生token
	 */
	public String GenerateToken(User user) {

		JwtUtil jwtUtil = new JwtUtil();
		// 產生 Token
		String token = jwtUtil.generateToken(user);

		// 存到Redis
		redisRepo.save(user.getId().toString(), token, Duration.ofDays(jwtUtil.expDay));

		return token;
	}

	/**
	 * 取得使用者資訊
	 */
	public User GetUserInfoById(Long id) throws Exception {

		User user = repo.findById(id);

		if (user == null) {
			throw new UserException().NotFoundUser(new IllegalArgumentException());
		}

		return user;
	}
}
