package com.example.braveCoward.model;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@Where(clause = "is_deleted=0")
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 20)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TeamMember> teamMembers;
}
