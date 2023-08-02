package com.mamoorjaankhan.first_project.request;

import jakarta.validation.constraints.NotEmpty;

public record TodoItemCreate(
    @NotEmpty String todoItem,
    @NotEmpty Long listId,
    boolean newList,
    String username,
    String title) {
}
