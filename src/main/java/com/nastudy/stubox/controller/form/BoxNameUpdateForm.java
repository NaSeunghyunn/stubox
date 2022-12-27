package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BoxNameUpdateForm {
    @NotNull
    private Long boxId;
    @NotBlank
    private String boxName;
}
