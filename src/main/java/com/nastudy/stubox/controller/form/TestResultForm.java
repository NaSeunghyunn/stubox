package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.dto.TestResult;
import com.nastudy.stubox.dto.TestResultCond;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class TestResultForm {
    @NotNull
    private Long boxId;
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    private TestResult testResult;

    public TestResultCond toCond(Long memberId) {
        LocalDateTime from = LocalDate.parse(this.from, DateTimeFormatter.ISO_DATE).atStartOfDay();
        LocalDateTime to = LocalDate.parse(this.to, DateTimeFormatter.ISO_DATE).plusDays(1).atStartOfDay();
        return new TestResultCond(memberId, from, to, testResult, boxId);
    }
}
