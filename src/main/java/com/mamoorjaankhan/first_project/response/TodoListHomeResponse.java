package com.mamoorjaankhan.first_project.response;

import java.util.List;

import com.mamoorjaankhan.first_project.entity.TodoLists;
import com.mamoorjaankhan.first_project.projection.TodoItemProjection;

public record TodoListHomeResponse(
                TodoLists list,
                List<TodoItemProjection> items) {
}
