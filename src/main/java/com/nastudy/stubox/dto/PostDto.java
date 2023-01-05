package com.nastudy.stubox.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String preView;
    private WriterDto writer;
    private String updateAt;
    private List<String> tags;
    private int viewCount;
    private int commentCount;
    private int likeCount;

    @QueryProjection
    public PostDto(Long id, String title, String preView, String writerName, String writerProfile, LocalDateTime updateDate, int viewCount, int commentCount, int likeCount) {
        this.id = id;
        this.title = title;
        this.preView = preView;
        this.writer = new WriterDto(writerName, writerProfile);
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.updateAt = updateDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
