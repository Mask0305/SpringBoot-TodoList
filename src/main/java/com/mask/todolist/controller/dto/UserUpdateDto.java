package com.mask.todolist.controller.dto;

/**
 * 接收用於更新資料的Dto
 * 預計用於兩個不同的API，欄位檢查於Controller
 */
public class UserUpdateDto {

	/**
	 * 使用者名稱
	 */
	public String name;

	/**
	 * 密碼
	 */
	public String password;

}
