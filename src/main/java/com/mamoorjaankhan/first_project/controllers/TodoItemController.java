package com.mamoorjaankhan.first_project.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.mamoorjaankhan.first_project.models.Message;
import com.mamoorjaankhan.first_project.models.TodoItem;
import com.mamoorjaankhan.first_project.repository.TodoItemCollectionRepository;

@RestController
@RequestMapping("/todo")
public class TodoItemController {
    private final TodoItemCollectionRepository repository;

    public TodoItemController(TodoItemCollectionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<TodoItem> todoList() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TodoItem findById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not present"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public Message createTodo(@RequestBody TodoItem t) {
        try {
            repository.save(t);
            return new Message(true, "Saved");
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e);
        }
    }

    @PutMapping("/update/{id}")
    public Message updateTodo(@RequestBody TodoItem t, @PathVariable Integer id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        repository.save(t);
        return new Message(true, "Updated");
    }

    @DeleteMapping("/delete/{id}")
    public Message deleteTodo(@PathVariable Integer id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist");
        repository.delete(id);
        return new Message(true, "Deleted");
    }
}
