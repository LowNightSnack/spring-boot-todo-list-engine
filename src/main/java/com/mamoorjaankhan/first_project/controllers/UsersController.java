package com.mamoorjaankhan.first_project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mamoorjaankhan.first_project.entity.FirstLists;
import com.mamoorjaankhan.first_project.entity.TodoLists;
import com.mamoorjaankhan.first_project.entity.Users;
import com.mamoorjaankhan.first_project.repository.FirstListsRepository;
import com.mamoorjaankhan.first_project.repository.TodoListsRepository;
import com.mamoorjaankhan.first_project.repository.UsersRepository;
import com.mamoorjaankhan.first_project.request.UserCreateRequest;
import com.mamoorjaankhan.first_project.request.UserLoginRequest;
import com.mamoorjaankhan.first_project.response.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {
  private final UsersRepository users;
  private final TodoListsRepository todoLists;
  private final FirstListsRepository firstLists;

  public UsersController(UsersRepository users, TodoListsRepository todoLists, FirstListsRepository firstLists) {
    this.users = users;
    this.todoLists = todoLists;
    this.firstLists = firstLists;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public MessageResponse createUser(@Valid @RequestBody UserCreateRequest user) {
    if (users.existsUserByUsername(user.username())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
    } else {
      Users newUser = new Users();
      newUser.setUsername(user.username());
      Users userSaved = users.save(newUser);

      TodoLists createNewList = new TodoLists();
      createNewList.setTitle(String.format("Hello %s", userSaved.getUsername()));
      createNewList.setUser(userSaved);
      TodoLists listSaved = todoLists.save(createNewList);

      FirstLists createFirstList = new FirstLists();
      createFirstList.setUser(userSaved);
      createFirstList.setTodoList(listSaved);
      firstLists.save(createFirstList);

      return new MessageResponse(true, "User created");
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/login")
  public MessageResponse loginUser(@Valid @RequestBody UserLoginRequest user) {
    users.findByUsername(user.username())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exists"));
    return new MessageResponse(true, "User logged in");
  }
}
