package com.mask.todolist.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.validation.ObjectError;

import com.mask.todolist.model.Event;
import com.mask.todolist.model.User;

public class Response {
	public String message;
	// public Map<String, String> data = new HashMap<>();
	public Object data;
	public ArrayList<String> error;

	public Response() {
		this.message = "ok";
		this.error = new ArrayList<>();
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

	public Response AddData(User user) {
		this.data = user;
		return this;
	}

	public Response AddData(List<Event> eventList) {
		this.data = eventList;

		return this;
	}

}
