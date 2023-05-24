package com.mask.todolist.controller.dto;

import jakarta.validation.constraints.NotNull;

public class EventUpdateDto {

	@NotNull(message = "標題不可為空")
	public String title;

	public String content;

}
