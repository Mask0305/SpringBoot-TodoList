package com.mask.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mask.todolist.exception.error.UserException;
import com.mask.todolist.model.User;
import com.mask.todolist.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo repo;

	/**
	 * 註冊使用者
	 */
	public void UserRegister(User user) throws Exception {
		try {
			User savedUser = repo.save(user);
		} catch (Exception e) {
			throw new UserException().CreateFail(e);
		}

	}

}
