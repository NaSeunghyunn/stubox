package com.nastudy.stubox.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class TestDto {
    private Long id;
    private Long cardId;
    private String keyword;
    private String concept;

    @Builder
    @QueryProjection
    public TestDto(Long id, Long cardId, String keyword, String concept) {
        this.id = id;
        this.cardId = cardId;
        this.keyword = keyword;
        this.concept = concept;
    }
}
