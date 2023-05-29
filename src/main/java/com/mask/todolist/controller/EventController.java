package com.mask.todolist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mask.todolist.controller.dto.EventDto;
import com.mask.todolist.controller.dto.EventSearchDto;
import com.mask.todolist.controller.dto.EventUpdateDto;
import com.mask.todolist.controller.dto.Response;
import com.mask.todolist.model.Event;
import com.mask.todolist.model.EventStatus;
import com.mask.todolist.service.EventService;

import io.jsonwebtoken.lang.Strings;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/event")
public class EventController {
	Map<String, String> message = new HashMap<>();

	@Autowired
	private EventService eventSvc;

	/**
	 * 新增待辦事項
	 */
	@PostMapping("/create")
	public Response CreateEvent(@Valid @RequestBody EventDto req, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new Response().Error().ErrorMessage(bindingResult.getAllErrors());
		}

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();

			eventSvc.CreateEvent(req.title, req.content, userId);

		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}

		return new Response();
	}

	/**
	 * 取得使用者所有待辦事項
	 */
	@GetMapping("")
	public Response GetAllEvent() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();

			List<Event> list = eventSvc.GetAllEvent(userId);

			return new Response().AddData(list);
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}

	}

	/**
	 * 編輯待辦事項
	 */
	@PutMapping("/{eventId}")
	public Response UpdateEvent(@PathVariable Long eventId, @Valid @RequestBody EventUpdateDto req,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new Response().Error().ErrorMessage(bindingResult.getAllErrors());
		}

		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();

			eventSvc.UpdateEvent(req.title, req.content, eventId, userId);

			return new Response();
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}

	}

	/*
	 * 刪除待辦事項
	 */
	@DeleteMapping("/{eventId}")
	public Response RemoveEvent(@PathVariable Long eventId) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();

			eventSvc.RemoveEvent(eventId, userId);

			return new Response();
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}

	}

	/**
	 * 標記已完成的事項
	 */
	@PutMapping("/complete/{eventId}")
	public Response Complete(@PathVariable Long eventId) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();

			eventSvc.CompleteEvent(eventId, userId);

			return new Response();
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}

	}

	/*
	 * 搜尋待辦事項
	 */
	@GetMapping("/search")
	public Response Search(@RequestBody EventSearchDto req) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();
			System.out.println(req.status);
			if (!Strings.hasText(req.status)) {
				req.status = EventStatus.NONE.getDesc();
			}

			List<Event> list = eventSvc.Search(userId, req.searchKey, EventStatus.valueOf(req.status),
					req.page, req.size);

			return new Response().AddData(list);
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}
	}

	/*
	 * 取得已完成的待辦事項
	 */
	@GetMapping("/complete")
	public Response GetCompleteEventList(@RequestBody EventSearchDto req) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Long userId = (Long) auth.getPrincipal();

			List<Event> list = eventSvc.Search(userId, "", EventStatus.COMPLETE, req.page, req.size);

			return new Response().AddData(list);
		} catch (Exception e) {
			return new Response().Error().ErrorMessage(e);
		}
	}

}
