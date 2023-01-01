package com.nastudy.stubox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostLikeDto {
    private int likeCount;
    private boolean receiveMyLike;
}
