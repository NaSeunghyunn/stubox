package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.Category;
import lombok.Data;

@Data
public class PostsCond {
    private Long postId;
    private String searchContents;
    private Category category;
}
