package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CardSaveForm {
    @NotNull
    private Long boxId;
    @NotBlank
    private String keyword;
    @NotBlank
    private String concept;
}
