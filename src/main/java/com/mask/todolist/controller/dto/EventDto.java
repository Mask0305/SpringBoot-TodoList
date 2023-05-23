package com.mask.todolist.controller.dto;

import jakarta.validation.constraints.NotNull;

public class EventDto {
	@NotNull(message = "標題不可為空")
	public String title;

	@NotNull(message = "內容不可為空")
	public String content;
}
