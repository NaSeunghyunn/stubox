package com.nastudy.stubox.domain;

import lombok.Getter;

@Getter
public enum Category {
    LANGUAGE("언어")
    , ENGLISH("영어")
    , JAPANESE("일본어")
    , KOREAN("한국어")
    , IT("IT")
    , JAVA("자바")
    , AWS("AWS");

    private String title;

    Category(String title) {
        this.title = title;
    }
}
