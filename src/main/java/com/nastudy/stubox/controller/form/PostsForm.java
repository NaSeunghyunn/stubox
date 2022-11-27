package com.nastudy.stubox.controller.form;

import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.dto.PostsCond;
import lombok.Data;

@Data
public class PostsForm {
    private Long postId;
    private String searchContents;
    private Category category;

    public PostsCond toCond() {
        PostsCond cond = new PostsCond();
        cond.setPostId(postId);
        cond.setCategory(category);
        cond.setSearchContents(searchContents);
        return cond;
    }
}
