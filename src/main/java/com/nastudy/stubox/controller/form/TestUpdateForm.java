package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.dto.TestResult;
import lombok.Data;

@Data
public class TestUpdateForm {
    private Long id;
    private TestResult testResult;
}
