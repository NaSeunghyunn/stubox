package com.nastudy.stubox.service;

import com.nastudy.stubox.controller.form.TestResultForm;
import com.nastudy.stubox.dto.*;
import com.nastudy.stubox.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TestResultService {
    private final TestResultRepository testResultRepository;

    public TestResultResponse findTestResults(TestResultForm form, Long memberId) {
        TestResultCond cond = form.toCond(memberId);
        List<TestResultDto> testResults = testResultRepository.findTestResults(cond);
        Map<String, List<TestResultContent>> testResultsMap = testResults
                .stream()
                .collect(Collectors.groupingBy(v -> v.getTestDate().format(DateTimeFormatter.ISO_DATE)
                        , Collectors.mapping(TestResultContent::new, Collectors.toList())));
        return new TestResultResponse(TestResults.of(testResultsMap));
    }
}
