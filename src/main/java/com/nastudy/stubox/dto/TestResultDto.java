package com.nastudy.stubox.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestResultDto {
    private String keyword;
    private int level;
    private TestResult testResult;
    private LocalDateTime testDate;

    @Builder
    @QueryProjection
    public TestResultDto(String keyword, int level, TestResult testResult, LocalDateTime testDate) {
        this.keyword = keyword;
        this.level = level;
        this.testResult = testResult;
        this.testDate = testDate;
    }
}
