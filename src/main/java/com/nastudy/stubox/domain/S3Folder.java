package com.nastudy.stubox.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum S3Folder {
    PROFILE("/profile"),
    KNOWLEDGE("/knowledge");

    private String dir;
}
