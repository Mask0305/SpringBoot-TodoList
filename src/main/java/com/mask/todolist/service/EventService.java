package com.mask.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mask.todolist.exception.error.EventException;
import com.mask.todolist.model.Event;
import com.mask.todolist.model.User;
import com.mask.todolist.repository.EventRepo;
import com.mask.todolist.repository.UserRepo;

@Service
public class EventService {
	private final EventRepo eventRepo;
	private final UserRepo userRepo;

	@Autowired
	public EventService(EventRepo eventRepo, UserRepo userRepo) {
		this.eventRepo = eventRepo;
		this.userRepo = userRepo;
	}

	/**
	 * 新增待辦事項
	 */
	public void CreateEvent(String title, String content, Long userId) throws EventException {
		try {
			User user = userRepo.findById(userId).orElseThrow();

			Event event = new Event(title, content, user);
			Event savedEvent = eventRepo.save(event);
			if (savedEvent.getId() == null) {
				throw new EventException().CreateFail(null);
			}
		} catch (EventException e) {
			throw new EventException().CreateFail(e);
		}
	}

}
