package com.example.braveCoward.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Builder
    public Do(LocalDate date,  String description, Plan plan) {
        this.date = date;
        this.description = description;
        this.plan = plan;
        this.project = plan.getProject();
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCompleted(boolean iscompleted){
        this.isCompleted = iscompleted;
    }
}
