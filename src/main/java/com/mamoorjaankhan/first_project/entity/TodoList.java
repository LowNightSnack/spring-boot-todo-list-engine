package com.mamoorjaankhan.first_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoList {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increment_1")
  private Integer id;
  private String title;
  private String username;
}
