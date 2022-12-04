package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBoxForm {
    @NotBlank
    private String name;
}
