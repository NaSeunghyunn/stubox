package com.nastudy.stubox.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BoxDeleteDto {
    private Set<Long> boxIds = new HashSet<>();
    private Set<Long> cardIds = new HashSet<>();
    private Set<Long> cardTestIds = new HashSet<>();
    private Set<Long> testIds = new HashSet<>();

    public void put(BoxIdDto boxIdDto) {
        boxIds.add(boxIdDto.getBoxId());
        cardIds.add(boxIdDto.getCardId());
        cardTestIds.add(boxIdDto.getCardTestId());
        testIds.add(boxIdDto.getTestId());
    }
}
