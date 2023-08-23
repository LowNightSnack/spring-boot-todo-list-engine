package com.mamoorjaankhan.first_project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mamoorjaankhan.first_project.response.GreetingResponse;

@RestController
public class GreetingsController {
    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public GreetingResponse greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new GreetingResponse(1, String.format(template, name));
    }
}
