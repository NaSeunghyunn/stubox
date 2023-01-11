package com.nastudy.stubox.controller.form;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentDeleteForm {
    @NotNull
    private Long postId;
    @NotNull
    private Long commentId;
}
