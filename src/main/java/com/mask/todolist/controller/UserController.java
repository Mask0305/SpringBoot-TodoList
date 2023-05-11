package com.mask.todolist.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mask.todolist.controller.dto.Response;
import com.mask.todolist.controller.dto.UserDto;
import com.mask.todolist.model.User;
import com.mask.todolist.service.UserService;
import com.mask.todolist.util.Hash;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	Map<String, String> message = new HashMap<>();

	@Autowired
	UserService userSvc;

	/**
	 * 註冊使用者
	 */
	@PostMapping("/register")
	public Response UserRegister(@Valid @RequestBody UserDto data, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new Response().Error().ErrorMessage(bindingResult.getAllErrors());
		}

		try {
			User user = new User();
			user.setName(data.name);
			user.setAccount(data.account);

			// Hash Password
			String pwdHash = Hash.generateHash(data.account, data.password);
			user.setPwdHash(pwdHash);

			userSvc.UserRegister(user);
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}

		return new Response();
	}

}
