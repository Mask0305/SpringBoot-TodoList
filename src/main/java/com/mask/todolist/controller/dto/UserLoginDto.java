package com.mask.todolist.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserLoginDto {
	/**
	 * 帳號
	 */

	@NotNull
	@NotBlank(message = "帳號不可為空")
	public String account;

	/**
	 * 密碼
	 */
	@NotNull
	@NotBlank(message = "密碼不可為空")
	public String password;

}
