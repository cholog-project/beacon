package com.example.braveCoward.model;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Do extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    public enum Status {
        NOT_STARTED, IN_PROGRESS, COMPLETED;

        @JsonCreator
        public static Status fromString(String value) {
            switch (value.toLowerCase()) {
                case "not started":
                    return NOT_STARTED;
                case "in progress":
                    return IN_PROGRESS;
                case "completed":
                    return COMPLETED;
                default:
                    throw new IllegalArgumentException("Unknown status: " + value);
            }
        }
    }

    @Builder
    public Do(LocalDate date, Status status, String description, Plan plan) {
        this.date = date;
        this.status = status;
        this.description = description;
        this.plan = plan;
    }
}
