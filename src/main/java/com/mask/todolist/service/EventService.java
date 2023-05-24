package com.mask.todolist.service;

import java.util.List;

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
		} catch (Exception e) {
			throw new EventException().CreateFail(e);
		}
	}

	/**
	 * 取得使用者所有待辦事項
	 */
	public List<Event> GetAllEvent(Long userId) throws Exception {
		try {
			var result = eventRepo.findAllByUserId(userId);

			return result;
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
	}

	/**
	 * 編輯待辦事項
	 */
	public void UpdateEvent(String title, String content, Long eventId, Long userId) throws Exception {

		Event event = eventRepo.findByIdAndUserId(eventId, userId);

		event.setTitle(title);
		event.setContent(content);

		eventRepo.save(event);
	}

}
