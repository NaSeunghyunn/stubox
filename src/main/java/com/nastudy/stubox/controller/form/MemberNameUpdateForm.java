package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberNameUpdateForm {
    @NotBlank
    private String memberName;
}
