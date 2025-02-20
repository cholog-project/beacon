package com.example.braveCoward.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_tokens")
@NoArgsConstructor
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "access_token", length = 512, nullable = false)
    private String accessToken;

    @NotNull
    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;

    @Builder
    public UserToken(User user, String accessToken, LocalDateTime expirationTime) {
        this.user = user;
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
    }
}
