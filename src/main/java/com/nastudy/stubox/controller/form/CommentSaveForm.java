package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentSaveForm {
    @NotNull
    private Long postId;
    @NotBlank
    private String content;
}
