package com.mamoorjaankhan.first_project.request;

import jakarta.validation.constraints.NotEmpty;

public record TodoListCreateRequest(
        @NotEmpty String username,
        @NotEmpty String title) {
}
