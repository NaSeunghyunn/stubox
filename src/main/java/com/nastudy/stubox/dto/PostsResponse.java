package com.nastudy.stubox.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostsResponse {
    private List<PostsDto> posts;
    private boolean isMore = false;

    public PostsResponse(List<PostsDto> posts) {
        if (posts.size() > 5) {
            posts.remove(posts.size() - 1);
            this.isMore = true;
        }
        this.posts = posts;
    }
}
