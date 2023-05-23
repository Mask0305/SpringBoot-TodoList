package com.mask.todolist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mask.todolist.model.Event;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {

}
