package com.nastudy.stubox.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.Set;

@Data
public class PostsDto {
    private Long postId;
    private String teamName;
    private Set<String> categories;
    private String title;

    @QueryProjection
    public PostsDto(Long postId, String teamName, String title) {
        this.postId = postId;
        this.teamName = teamName;
        this.title = title;
    }
}
