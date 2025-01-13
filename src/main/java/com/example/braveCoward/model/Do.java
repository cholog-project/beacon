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

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Builder
    public Do(LocalDate date,  String description, Plan plan) {
        this.date = date;
        this.description = description;
        this.plan = plan;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
