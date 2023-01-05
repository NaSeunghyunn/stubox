package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.domain.PostSearchType;
import com.nastudy.stubox.domain.PostSortType;
import lombok.Data;

@Data
public class PostForm {
    private PostSearchType searchType;
    private String search;
    private int page;
    private PostSortType sort;
    private int size;
    private int totalCount;
}
