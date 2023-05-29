package com.mask.todolist.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mask.todolist.model.Event;
import com.mask.todolist.model.EventStatus;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {

	public List<Event> findAllByUserId(Long userId);

	public Event findByIdAndUserId(Long id, Long userId);

	@Query("SELECT e FROM Event e WHERE (e.title Like %:searchKey% OR e.content Like %:searchKey%) And e.user.id = :userId And (:status = 0 OR e.status = :status)")
	public List<Event> search(@Param("userId") Long userId, @Param("searchKey") String searchKey,
			@Param("status") EventStatus status, Pageable pageable);

}
