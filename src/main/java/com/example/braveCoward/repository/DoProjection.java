package com.example.braveCoward.repository;

import java.time.LocalDate;

public interface DoProjection {
    Long getId();
    LocalDate getDate();
    String getDescription();
    Long getPlanId();
}
