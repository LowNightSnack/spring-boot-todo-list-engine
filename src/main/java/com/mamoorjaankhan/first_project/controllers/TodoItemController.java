package com.mamoorjaankhan.first_project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mamoorjaankhan.first_project.entity.TodoItem;
import com.mamoorjaankhan.first_project.entity.TodoList;
import com.mamoorjaankhan.first_project.repository.TodoItemRepository;
import com.mamoorjaankhan.first_project.repository.TodoListRepository;
import com.mamoorjaankhan.first_project.request.TodoItemCreate;
import com.mamoorjaankhan.first_project.response.Message;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoItemController {
  private final TodoItemRepository todoItemRepository;
  private final TodoListRepository todoListRepository;

  public TodoItemController(TodoItemRepository todoItemRepository, TodoListRepository todoListRepository) {
    this.todoItemRepository = todoItemRepository;
    this.todoListRepository = todoListRepository;
  }

  @GetMapping("/all")
  public List<TodoItem> todoList() {
    return todoItemRepository.findAll();
  }

  @GetMapping("/{id}")
  public TodoItem findById(@PathVariable Long id) {
    return todoItemRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not present"));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public Message createTodo(@Valid @RequestBody TodoItemCreate t) {
    TodoList l = todoListRepository.findById(t.listId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List does not exist"));
    TodoItem newTodoItem = new TodoItem();
    newTodoItem.setInList(l);
    newTodoItem.setItem(t.todoItem());

    if (t.newList()) {
      if (!t.username().isBlank()) {
        TodoList newList = new TodoList();
        newList.setTitle(t.title());
        newList.setUsername(t.username());
        newTodoItem.setNewList(newList);
      } else {
        // Missing username
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters");
      }
    }

    try {
      todoItemRepository.save(newTodoItem);
      return new Message(true, "Saved");
    } catch (Exception e) {
      System.out.println(e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e);
    }
  }

  @PutMapping("/update/{id}")
  public Message updateTodo(@Valid @RequestBody TodoItem t, @PathVariable Long id) {
    todoItemRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    todoItemRepository.save(t);
    return new Message(true, "Updated");
  }

  @DeleteMapping("/delete/{id}")
  public Message deleteTodo(@PathVariable Long id) {
    if (!todoItemRepository.existsById(id))
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist");
    todoItemRepository.deleteById(id);
    return new Message(true, "Deleted");
  }
}
