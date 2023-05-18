package com.mask.todolist.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mask.todolist.controller.dto.Response;
import com.mask.todolist.controller.dto.UserDto;
import com.mask.todolist.controller.dto.UserLoginDto;
import com.mask.todolist.model.User;
import com.mask.todolist.service.UserService;
import com.mask.todolist.util.Hash;
import com.mask.todolist.util.JwtUtil;

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

	/**
	 * 
	 */
	@PostMapping("/login")
	public Response UserLogin(@Valid @RequestBody UserLoginDto data, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new Response().Error().ErrorMessage(bindingResult.getAllErrors());
		}

		try {

			// 使用者登入
			User user = userSvc.UserLogin(data.account, data.password);

			// 產生 Token
			String token = userSvc.GenerateToken(user);

			Map<String, String> res = new HashMap<>();
			res.put("token", token);

			return new Response().AddData(res);
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}

	}

	/**
	 *
	 */
	@GetMapping("/info")
	public Response UserInfo() {
		System.out.println("/info");

		JwtUtil jwtUtil = new JwtUtil();

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();

			User user = userSvc.GetUserInfoById(userId);
			return new Response().AddData(user);
		} catch (Exception e) {

			return new Response().Error().ErrorMessage(e);

		}

	}

}
