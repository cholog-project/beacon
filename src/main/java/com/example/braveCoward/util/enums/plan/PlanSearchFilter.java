package com.example.braveCoward.util.enums.plan;

public enum PlanSearchFilter {
    TITLE("제목"),
    DESCRIPTION("설명"),
    TITLE_AND_DESCRIPTION("제목과 설명");

    private final String name;

    PlanSearchFilter(String name){
        this.name = name;
    }
}
