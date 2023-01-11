package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Comment;
import lombok.Data;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class CommentChildDto {
    private Long id;
    private String content;
    private WriterDto writer;
    private String receiver;
    private String updateAt;

    public CommentChildDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = new WriterDto(comment.getWriter());
        if(comment.getReceiver() != null){
            this.receiver = comment.getReceiver().getName();
        }
        this.updateAt = comment.getUpdateDate().atZone(ZoneId.of("Asia/Tokyo")).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
