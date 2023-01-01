package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Comment;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private WriterDto writer;
    private List<CommentChildDto> children;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = new WriterDto(comment.getWriter());
        this.children = comment.getChildren().stream().map(CommentChildDto::new).collect(Collectors.toList());
    }
}
