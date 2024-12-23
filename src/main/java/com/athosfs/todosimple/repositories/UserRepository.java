package com.athosfs.todosimple.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.athosfs.todosimple.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
  
}
