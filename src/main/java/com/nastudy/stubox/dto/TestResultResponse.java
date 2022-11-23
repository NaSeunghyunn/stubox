package com.nastudy.stubox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestResultResponse {
    private List<TestResults> testResults;
}
