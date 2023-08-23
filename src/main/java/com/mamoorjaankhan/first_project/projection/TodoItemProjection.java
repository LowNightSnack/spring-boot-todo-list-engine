package com.mamoorjaankhan.first_project.projection;

import com.mamoorjaankhan.first_project.entity.TodoLists;

public interface TodoItemProjection {
  Long getId();

  String getItem();

  boolean isChecked();

  TodoLists getNewList();
}
