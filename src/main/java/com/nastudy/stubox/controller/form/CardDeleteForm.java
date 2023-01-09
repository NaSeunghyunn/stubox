package com.nastudy.stubox.controller.form;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CardDeleteForm {
    @NotNull
    private Long id;
}
