package com.nastudy.stubox.dto;

import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
public class TestResultContent {
    private String time;
    private String keyword;
    private int level;
    private TestResult testResult;

    public TestResultContent(TestResultDto dto) {
        this.time = dto.getTestDate().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        this.keyword = dto.getKeyword();
        this.level = dto.getLevel();
        this.testResult = dto.getTestResult();
    }
}
