package com.mamoorjaankhan.first_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamoorjaankhan.first_project.entity.TodoItems;
import com.mamoorjaankhan.first_project.projection.TodoItemProjection;

public interface TodoItemsRepository extends JpaRepository<TodoItems, Long> {
  List<TodoItemProjection> findAllByListId(Long id);
}
