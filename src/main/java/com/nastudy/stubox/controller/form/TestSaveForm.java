package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.dto.TestResult;
import lombok.Data;

@Data
public class TestSaveForm {
    private Long cardId;
    private TestResult testResult;
}
