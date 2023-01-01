package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Comment;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class CommentChildDto {
    private String content;
    private WriterDto writer;
    private String receiver;
    private String updateAt;

    public CommentChildDto(Comment comment) {
        this.content = comment.getContent();
        this.writer = new WriterDto(comment.getWriter());
        if(comment.getReceiver() != null){
            this.receiver = comment.getReceiver().getName();
        }
        this.updateAt = comment.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
