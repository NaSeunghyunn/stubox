package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostLikeDeleteForm {
    @NotNull
    private Long postId;
}
