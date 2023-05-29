package com.mask.todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mask.todolist.exception.error.EventException;
import com.mask.todolist.model.Event;
import com.mask.todolist.model.EventStatus;
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

		Event event = GetEventByIdAndUserId(eventId, userId);

		event.setTitle(title);
		event.setContent(content);

		eventRepo.save(event);
	}

	/**
	 * 刪除待辦事項
	 */
	public void RemoveEvent(Long eventId, Long userId) throws Exception {
		GetEventByIdAndUserId(eventId, userId);

		eventRepo.deleteById(eventId);
	}

	/**
	 * 標記已完成的事項
	 */
	public void CompleteEvent(Long eventId, Long userId) throws Exception {
		Event event = GetEventByIdAndUserId(eventId, userId);

		event.setStatusComplete();

		eventRepo.save(event);
	}

	/**
	 * 透過eventId & userId取得待辦事項
	 */
	public Event GetEventByIdAndUserId(Long eventId, Long userId) throws Exception {

		Event event = eventRepo.findByIdAndUserId(eventId, userId);
		if (event == null) {
			throw new EventException().NotFoundEvent(new IllegalArgumentException());
		}

		return event;
	}

	/**
	 * 搜尋待辦事項
	 */
	public List<Event> Search(Long userId, String searchKey, EventStatus status, int page, int size) {

		// page-1符合頁面邏輯
		// > 0 判斷避免變成負數
		if (page - 1 >= 0) {
			page--;
		}

		// 預設size
		if (size == 0) {
			size = 10;
		}

		Pageable pageable = PageRequest.of(page, size);

		List<Event> list = eventRepo.search(userId, searchKey, status, pageable);

		return list;
	}

}
