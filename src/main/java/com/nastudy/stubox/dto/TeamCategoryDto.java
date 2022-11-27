package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class TeamCategoryDto {
    private String teamName;

    @Enumerated(EnumType.STRING)
    private Category category;

    @QueryProjection
    public TeamCategoryDto(String teamName, Category category) {
        this.teamName = teamName;
        this.category = category;
    }
}
