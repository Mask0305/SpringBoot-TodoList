package com.mask.todolist.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {

	/**
	 * 使用者名稱
	 */

	@NotNull
	@NotBlank(message = "名稱不可為空")
	public String name;

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
