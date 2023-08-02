package com.mamoorjaankhan.first_project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mamoorjaankhan.first_project.entity.TodoList;
import com.mamoorjaankhan.first_project.repository.TodoListRepository;
import com.mamoorjaankhan.first_project.response.Message;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/list")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoListController {
  private final TodoListRepository repository;

  public TodoListController(TodoListRepository repository) {
    this.repository = repository;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public Message createList(@Valid @RequestBody TodoList t) {
    try {
      repository.save(t);
      return new Message(true, "Todo list created");
    } catch (Exception e) {
      System.out.println(e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e);
    }
  }
}
