package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.braveCoward.model.User;

import jakarta.persistence.LockModeType;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    Optional<User> findByEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailWithPessimisticLock(@Param("email") String email);

    @Query(value = "SELECT GET_LOCK(:email, 10)", nativeQuery = true)
    void getLock(@Param("email") String email);

    @Query(value = "SELECT RELEASE_LOCK(:email)", nativeQuery = true)
    void releaseLock(@Param("email") String email);

    @Query(value = "SELECT email FROM user_lock WHERE email = :email FOR UPDATE", nativeQuery = true)
    void getLockOnUser(@Param("email") String email);
}
