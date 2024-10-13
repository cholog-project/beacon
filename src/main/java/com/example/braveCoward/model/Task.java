package com.example.braveCoward.model;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Task extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project; // Assuming a Project entity exists

    @ManyToOne
    @JoinColumn(name = "team_member_id", nullable = false)
    private TeamMember teamMember; // Assuming a TeamMember entity exists

    @OneToOne(mappedBy = "task")
    private Plan plan;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Do> dos;
}
