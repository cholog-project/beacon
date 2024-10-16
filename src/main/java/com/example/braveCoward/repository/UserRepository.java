package com.example.braveCoward.repository;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.User;

public interface UserRepository extends Repository<User, Long> {
    User save(User user);

}
