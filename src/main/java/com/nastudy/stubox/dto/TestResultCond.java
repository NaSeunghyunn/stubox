package com.nastudy.stubox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TestResultCond {
    private Long memberId;
    private LocalDateTime from;
    private LocalDateTime to;
    private TestResult testResult;
    private Long boxId;
}
