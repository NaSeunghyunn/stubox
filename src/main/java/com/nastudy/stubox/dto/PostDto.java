package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Post;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class PostDto {
    private String title;
    private String content;
    private WriterDto writer;
    private int viewCount;
    private String updateAt;

    public PostDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = new WriterDto(post.getWriter());
        this.viewCount = post.getViewCount();
        this.updateAt = post.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
