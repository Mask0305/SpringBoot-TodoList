package com.mask.todolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mask.todolist.model.Event;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {

	public List<Event> findAllByUserId(Long userId);

	public Event findByIdAndUserId(Long id, Long userId);

}
