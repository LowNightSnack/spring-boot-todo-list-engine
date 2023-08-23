package com.mamoorjaankhan.first_project.request;

import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
    @NotEmpty String username) {
}
