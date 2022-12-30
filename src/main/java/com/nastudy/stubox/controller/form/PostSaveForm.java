package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PostSaveForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String previewURL;
    private List<Long> tagIds;
}
