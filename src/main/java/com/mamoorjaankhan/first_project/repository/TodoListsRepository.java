package com.mamoorjaankhan.first_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamoorjaankhan.first_project.entity.TodoLists;

public interface TodoListsRepository extends JpaRepository<TodoLists, Long> {
}
