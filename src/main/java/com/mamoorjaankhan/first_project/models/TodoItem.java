package com.mamoorjaankhan.first_project.models;

public record TodoItem(
        Integer id,
        String item,
        boolean checked,
        Type type) {
}
