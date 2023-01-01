package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Posts;
import lombok.Data;

@Data
public class PostsDetailDto {
    private String title;
    private String content;

    public PostsDetailDto(Posts posts) {
        this.title = posts.getTitle();
        this.content = posts.getContent();
    }
}
