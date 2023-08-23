package com.mamoorjaankhan.first_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mamoorjaankhan.first_project.entity.FirstLists;

public interface FirstListsRepository extends JpaRepository<FirstLists, Long> {
  Optional<FirstLists> findByUserId(Long id);
}
