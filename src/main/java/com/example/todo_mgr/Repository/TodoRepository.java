package com.example.todo_mgr.Repository;

import java.util.List;

import com.example.todo_mgr.Model.Todo;
import com.example.todo_mgr.Model.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Integer>{
    List<Todo> findTodosByUser (User user);
    List<Todo> findTodosByUserName (String name, Pageable pageable);

}