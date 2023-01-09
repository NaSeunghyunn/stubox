package com.nastudy.stubox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostEditDto {
    private String title;
    private String content;
    private List<TagDto> tags;
}
