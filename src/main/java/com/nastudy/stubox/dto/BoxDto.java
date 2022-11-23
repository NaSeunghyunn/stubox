package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.CardBox;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class BoxDto {
    private Long id;
    private String name;

    @Builder
    @QueryProjection
    public BoxDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BoxDto of(CardBox cardBox) {
        return BoxDto.builder().id(cardBox.getId()).name(cardBox.getName()).build();
    }

    public static List<BoxDto> of(List<CardBox> cardBoxes) {
        return cardBoxes.stream().map(BoxDto::of).collect(Collectors.toList());
    }
}
