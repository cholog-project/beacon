package com.example.braveCoward.repository;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.UserToken;

public interface UserTokenRepository extends Repository<UserToken, Long> {
    void save(UserToken build);
}
