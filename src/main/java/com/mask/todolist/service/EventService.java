package com.mask.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mask.todolist.repository.EventRepo;

@Service
public class EventService {
	private final EventRepo eventRepo;

	@Autowired
	public EventService(EventRepo eventRepo) {
		this.eventRepo = eventRepo;
	}

}
