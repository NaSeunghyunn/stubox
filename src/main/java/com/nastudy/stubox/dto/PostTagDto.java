package com.nastudy.stubox.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostTagDto {
    private Long postId;
    private String name;

    @QueryProjection
    public PostTagDto(Long postId, String name) {
        this.postId = postId;
        this.name = name;
    }
}
