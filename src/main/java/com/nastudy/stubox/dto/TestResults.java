package com.nastudy.stubox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TestResults {
    private String testDate;
    private List<TestResultContent> contents;

    public static List<TestResults> of(Map<String, List<TestResultContent>> testResultsMap) {
        List<TestResults> results = new ArrayList<>();
        for(String testDate : testResultsMap.keySet()){
            TestResults testResults = new TestResults(testDate, testResultsMap.get(testDate));
            results.add(testResults);
        }
        return results;
    }
}
