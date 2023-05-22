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

	private final UserRepo repo;
	private final RedisRepo redisRepo;
	private final JwtUtil jwtUtil;

	@Autowired
	public UserService(UserRepo repo, RedisRepo redisRepo, JwtUtil jwtUtil) {
		this.repo = repo;
		this.redisRepo = redisRepo;
		this.jwtUtil = jwtUtil;
	}

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

		// orElse()用來設置當findById()找不到時的返回結果
		// findById()的Optional<T>類是用於處理對象可能為null容器類
		User user = repo.findById(id).orElse(null);

		if (user == null) {
			throw new UserException().NotFoundUser(new IllegalArgumentException());
		}

		return user;
	}

	/**
	 * 更新使用者名稱
	 */
	public void UpdateUserName(Long id, String name) throws Exception {
		User user = repo.findById(id).orElse(null);
		if (user == null) {
			throw new UserException().NotFoundUser(new IllegalArgumentException());
		}

		user.setName(name);

		try {
			repo.save(user);
		} catch (Exception e) {
			throw new UserException().UpdateFail(e);
		}
	}

	/**
	 * 更新密碼
	 */
	public void UpdateUserPassword(Long id, String password) throws Exception {
		User user = repo.findById(id).orElse(null);
		if (user == null) {
			throw new UserException().NotFoundUser(new IllegalArgumentException());
		}

		// 產生新的密碼Hash
		String newPwdHash = Hash.generateHash(user.getAccount(), password);

		user.setPwdHash(newPwdHash);

		try {
			repo.save(user);
		} catch (Exception e) {
			throw new UserException().UpdateFail(e);
		}
	}

	/**
	 * 登出
	 */
	public void Logout(Long id) throws Exception {
		redisRepo.del(id.toString());
	}

}
