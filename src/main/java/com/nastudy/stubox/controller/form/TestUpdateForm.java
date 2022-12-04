package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.dto.TestResult;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TestUpdateForm {
    @NotNull
    private Long id;
    @NotNull
    private TestResult testResult;
}
