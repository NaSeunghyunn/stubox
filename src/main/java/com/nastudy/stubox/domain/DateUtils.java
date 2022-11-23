package com.nastudy.stubox.domain;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public enum DateUtils {
    yyyyMMdd(DateTimeFormatter.ISO_DATE);

    private DateTimeFormatter formatter;

    DateUtils(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
}
