package com.nastudy.stubox.dto;

import com.nastudy.stubox.controller.form.TestSaveForm;
import lombok.Data;

@Data
public class TestSaveDto {
    private Long cardId;
    private TestResult testResult;
    private int level;
    private Long memberId;

    public TestSaveDto(TestSaveForm form, Long memberId) {
        this.cardId = form.getCardId();
        this.testResult = form.getTestResult();
        this.level = 1;
        this.memberId = memberId;
    }
}
