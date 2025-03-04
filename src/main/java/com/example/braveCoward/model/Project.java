package com.example.braveCoward.model;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "project")
@NoArgsConstructor(access = PROTECTED)
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    //    @Column(name = "progress", nullable = false)
    @Column(name = "progress")
    private Double progress = 0.0; // 초기값은 0.0 (0%)

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Plan> plans;

    @Builder
    public Project(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Double progress,
        Team team
    ) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progress = progress;
        this.team = team;
    }

    public void updateProgress(double progress) {
        this.progress = progress;
    }
}
