package com.mamoorjaankhan.first_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class TodoItems {
  @Id
  @GeneratedValue
  private Long id;
  private String item;
  @Default
  private boolean checked = false;
  @ManyToOne
  private TodoLists list;
  @OneToOne
  @Default
  private TodoLists newList = null;
}
