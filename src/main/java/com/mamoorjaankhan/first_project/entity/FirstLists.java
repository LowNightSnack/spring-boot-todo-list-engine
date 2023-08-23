package com.mamoorjaankhan.first_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FirstLists {
  @Id
  @GeneratedValue
  private Long id;
  @OneToOne
  private Users user;
  @OneToOne
  private TodoLists todoList;
}
