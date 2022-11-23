package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Card;
import lombok.Data;

@Data
public class CardDto {
    private Long id;
    private String keyword;
    private String concept;

    public static CardDto of(Card card) {
        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setKeyword(card.getKeyword());
        cardDto.setConcept(card.getConcept());
        return cardDto;
    }
}
