package com.mamoorjaankhan.first_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mamoorjaankhan.first_project.entity.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
