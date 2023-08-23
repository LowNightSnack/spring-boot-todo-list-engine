package com.mamoorjaankhan.first_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamoorjaankhan.first_project.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
  boolean existsUserByUsername(String username);

  Optional<Users> findByUsername(String username);
}
