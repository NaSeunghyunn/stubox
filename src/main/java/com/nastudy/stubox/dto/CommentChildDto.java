package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Comment;
import lombok.Data;

@Data
public class CommentChildDto {
    private String content;
    private WriterDto writer;
    private String receiver;

    public CommentChildDto(Comment comment) {
        this.content = comment.getContent();
        this.writer = new WriterDto(comment.getWriter());
        if(comment.getReceiver() != null){
            this.receiver = comment.getReceiver().getName();
        }
    }
}
