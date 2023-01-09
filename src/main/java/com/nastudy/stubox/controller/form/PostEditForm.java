package com.nastudy.stubox.controller.form;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class PostEditForm {
    @NotNull
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String previewURL;
    private List<Long> tagIds;
}
