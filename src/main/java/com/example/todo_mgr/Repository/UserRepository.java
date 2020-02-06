package com.example.todo_mgr.Repository;

import com.example.todo_mgr.Model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
    User findUserByName(String name);

}