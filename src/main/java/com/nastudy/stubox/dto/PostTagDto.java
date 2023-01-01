package com.nastudy.stubox.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostTagDto {
    private String name;

    @QueryProjection
    public PostTagDto(String name) {
        this.name = name;
    }
}
