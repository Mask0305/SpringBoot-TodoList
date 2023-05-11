package com.mask.todolist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mask.todolist.model.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

}
