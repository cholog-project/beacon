package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.braveCoward.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    Optional<User> findByEmail(String email);
}
