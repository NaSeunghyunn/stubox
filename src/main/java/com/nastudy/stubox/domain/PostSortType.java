package com.nastudy.stubox.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostSortType {
    LATEST("최신순"), RECOMMEND("추천순"), COMMENT("댓글순"), VIEW("조회순");

    private String typeName;
}
