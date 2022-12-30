package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostsSaveForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
