package com.example.braveCoward.model;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Plan extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "team_member_id", nullable = false)
    private TeamMember teamMember;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Do> dos = new ArrayList<>();

    public enum Status {
        NOT_STARTED("not_started"),
        IN_PROGRESS("in_progress"),
        COMPLETED("completed");

        private final String jsonValue;

        Status(String jsonValue) {
            this.jsonValue = jsonValue;
        }

        public String getJsonValue() {
            return jsonValue;
        }

        @JsonCreator
        public static Status fromString(String value) {
            for (Status status : values()) {
                if (status.jsonValue.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("형식에 맞지 않는 Status 입니다! : " + value);
        }
    }

    @Builder
    public Plan(
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Status status,
        Project project,
        TeamMember teamMember
    ) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.project = project;
        this.teamMember = teamMember;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
