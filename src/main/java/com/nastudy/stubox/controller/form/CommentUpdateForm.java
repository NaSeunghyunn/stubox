package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentUpdateForm {
    @NotNull
    private Long id;
    @NotBlank
    private String content;
}
