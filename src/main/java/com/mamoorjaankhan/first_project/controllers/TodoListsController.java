package com.mamoorjaankhan.first_project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mamoorjaankhan.first_project.entity.FirstLists;
import com.mamoorjaankhan.first_project.entity.TodoLists;
import com.mamoorjaankhan.first_project.entity.Users;
import com.mamoorjaankhan.first_project.projection.TodoItemProjection;
import com.mamoorjaankhan.first_project.repository.FirstListsRepository;
import com.mamoorjaankhan.first_project.repository.TodoItemsRepository;
import com.mamoorjaankhan.first_project.repository.TodoListsRepository;
import com.mamoorjaankhan.first_project.repository.UsersRepository;
import com.mamoorjaankhan.first_project.request.TodoListCreateRequest;
import com.mamoorjaankhan.first_project.response.MessageResponse;
import com.mamoorjaankhan.first_project.response.TodoListHomeResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/list")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoListsController {
  private final UsersRepository users;
  private final FirstListsRepository firstLists;
  private final TodoListsRepository todoLists;
  private final TodoItemsRepository todoItems;

  public TodoListsController(UsersRepository users, FirstListsRepository firstLists, TodoListsRepository todoLists,
      TodoItemsRepository todoItems) {
    this.users = users;
    this.firstLists = firstLists;
    this.todoLists = todoLists;
    this.todoItems = todoItems;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public MessageResponse createList(@Valid @RequestBody TodoListCreateRequest req) {
    try {
      Users u = users.findByUsername(req.username())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
      TodoLists newList = new TodoLists();
      newList.setUser(u);
      newList.setTitle(req.title());
      todoLists.save(newList);
      return new MessageResponse(true, "Todo list created");
    } catch (Exception e) {
      System.out.println(e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e);
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/home")
  public TodoListHomeResponse getFirstList(
      @RequestHeader(value = "Authorization", required = true) String auth) {
    if (auth.equals(""))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty Authorization");
    // some auth parsing code
    Users loggedUser = users.findByUsername(auth)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist"));
    FirstLists firstList = firstLists.findByUserId(loggedUser.getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong"));
    List<TodoItemProjection> items = todoItems.findAllByListId(firstList.getTodoList().getId());
    return new TodoListHomeResponse(firstList.getTodoList(), items);
  }
}
