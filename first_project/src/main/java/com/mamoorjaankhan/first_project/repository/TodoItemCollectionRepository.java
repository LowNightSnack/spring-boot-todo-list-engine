package com.mamoorjaankhan.first_project.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mamoorjaankhan.first_project.models.TodoItem;
import com.mamoorjaankhan.first_project.models.Type;

import jakarta.annotation.PostConstruct;

@Repository
public class TodoItemCollectionRepository {
    private final List<TodoItem> todoList = new ArrayList<>();

    public TodoItemCollectionRepository() {
    }

    public List<TodoItem> findAll() {
        return todoList;
    }

    public Optional<TodoItem> findById(Integer id) {
        return todoList.stream().filter(t -> t.id().equals(id)).findFirst();
    }

    public void save(TodoItem t) {
        try {
            todoList.removeIf(t_ -> t_.id().equals(t.id()));
            todoList.add(t);
        } catch (Exception e) {
            throw e;
        }

    }

    public boolean existsById(Integer id) {
        return todoList.stream().filter(t -> t.id().equals(id)).count() == 1;
    }

    public void delete(Integer id) {
        todoList.removeIf(t -> t.id().equals(id));
    }

    @PostConstruct
    private void init() {
        TodoItem t = new TodoItem(1, "Hey", false, Type.OTHER);
        todoList.add(t);
    }

}
