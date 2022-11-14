package com.nastudy.stubox.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CardsDto {
    private String boxName;
    private List<CardDto> cards = new ArrayList<>();

    public CardsDto(String boxName, List<CardDto> cards) {
        this.boxName = boxName;
        this.cards = cards;
    }
}
