package com.mask.todolist.controller.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.ObjectError;

public class Response {
	public String message;
	public Object data = new HashMap<>();
	public ArrayList<String> error = new ArrayList<>();

	public Response() {
		this.message = "ok";
	}

	public Response Error() {
		this.message = "error";
		return this;
	}

	public Response ErrorMessage(List<ObjectError> errs) {
		errs.forEach(e -> this.error.add(e.getDefaultMessage()));
		return this;
	}

	public Response ErrorMessage(Throwable e) {
		this.error.add(e.getMessage());

		return this;
	}

	public Response AddData(Map<String, String> data) {
		this.data = data;
		return this;
	}

	public Response AddData(Object obj) {
		this.data = obj;
		return this;
	}

}
