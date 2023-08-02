package com.mamoorjaankhan.first_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoItem {
  @Id
  @SequenceGenerator(name = "increment_1", sequenceName = "increment_1", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increment_1")
  private Integer id;
  private String item;
  @Default
  private boolean checked = false;
  @ManyToOne
  private TodoList inList;
  @OneToOne
  @Default
  private TodoList newList = null;
}
