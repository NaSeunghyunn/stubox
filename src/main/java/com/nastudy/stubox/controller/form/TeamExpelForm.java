package com.nastudy.stubox.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeamExpelForm {
    @NotNull
    private Long memberId;
}
