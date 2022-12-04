package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeamAuthForm {
    @NotNull
    private Long memberId;
}
