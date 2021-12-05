package com.cos.jwt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}