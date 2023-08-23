package com.mamoorjaankhan.first_project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mamoorjaankhan.first_project.entity.TodoItems;
import com.mamoorjaankhan.first_project.entity.TodoLists;
import com.mamoorjaankhan.first_project.entity.Users;
import com.mamoorjaankhan.first_project.repository.TodoItemsRepository;
import com.mamoorjaankhan.first_project.repository.TodoListsRepository;
import com.mamoorjaankhan.first_project.repository.UsersRepository;
import com.mamoorjaankhan.first_project.request.TodoItemCreateRequest;
import com.mamoorjaankhan.first_project.response.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoItemsController {
  private final UsersRepository users;
  private final TodoItemsRepository todoItems;
  private final TodoListsRepository todoLists;

  public TodoItemsController(UsersRepository users, TodoItemsRepository todoItems,
      TodoListsRepository todoLists) {
    this.users = users;
    this.todoItems = todoItems;
    this.todoLists = todoLists;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public MessageResponse createTodo(@Valid @RequestBody TodoItemCreateRequest req,
      @RequestHeader(value = "Authorization", required = true) String auth) {
    if (auth.equals(""))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty Authorization");
    // some auth parsing code
    Users loggedUser = users.findByUsername(auth)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist"));
    TodoLists list = todoLists.findById(req.listId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List does not exist"));
    TodoItems newTodoItem = new TodoItems();
    newTodoItem.setList(list);

    if (req.newList()) {
      TodoLists l = new TodoLists();
      l.setTitle(req.title());
      l.setUser(loggedUser);
      TodoLists newList = todoLists.save(l);
      newTodoItem.setNewList(newList);
    } else {
      newTodoItem.setItem(req.todoItem());
    }

    try {
      todoItems.save(newTodoItem);
      return new MessageResponse(true, "Saved");
    } catch (Exception e) {
      System.out.println(e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e);
    }
  }

  @PutMapping("/update/{id}")
  public MessageResponse updateTodo(@Valid @RequestBody TodoItems t, @PathVariable Long id) {
    todoItems.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    todoItems.save(t);
    return new MessageResponse(true, "Updated");
  }

  @DeleteMapping("/delete/{id}")
  public MessageResponse deleteTodo(@PathVariable Long id) {
    if (!todoItems.existsById(id))
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist");
    todoItems.deleteById(id);
    return new MessageResponse(true, "Deleted");
  }
}
