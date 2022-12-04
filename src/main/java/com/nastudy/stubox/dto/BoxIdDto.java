package com.nastudy.stubox.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BoxIdDto {
    private Long boxId;
    private Long cardId;
    private Long cardTestId;
    private Long testId;

    @QueryProjection
    public BoxIdDto(Long boxId, Long cardId, Long cardTestId, Long testId) {
        this.boxId = boxId;
        this.cardId = cardId;
        this.cardTestId = cardTestId;
        this.testId = testId;
    }
}
