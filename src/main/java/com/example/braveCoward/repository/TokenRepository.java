package com.example.braveCoward.repository;

import com.example.braveCoward.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<RefreshToken, Long> {
//    Optional<Token> findByAccessToken(String accessToken);
}