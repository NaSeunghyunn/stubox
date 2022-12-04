package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.dto.TestResult;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TestSaveForm {
    @NotNull
    private Long cardId;
    @NotNull
    private TestResult testResult;
}
