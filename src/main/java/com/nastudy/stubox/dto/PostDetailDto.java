package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Post;
import lombok.Data;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class PostDetailDto {
    private String title;
    private String content;
    private WriterDto writer;
    private int viewCount;
    private String updateAt;

    public PostDetailDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = new WriterDto(post.getWriter());
        this.viewCount = post.getViewCount();
        this.updateAt = post.getCreateDate().atZone(ZoneId.of("Asia/Tokyo")).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
