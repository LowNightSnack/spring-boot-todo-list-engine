package com.mamoorjaankhan.first_project.request;

import jakarta.validation.constraints.NotNull;

public record TodoItemCreateRequest(
    String todoItem,
    @NotNull Long listId,
    boolean newList,
    String title) {
}
