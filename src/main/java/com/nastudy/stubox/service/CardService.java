package com.nastudy.stubox.service;

import com.nastudy.stubox.controller.form.CardDeleteForm;
import com.nastudy.stubox.controller.form.CardSaveForm;
import com.nastudy.stubox.controller.form.CardUpdateForm;
import com.nastudy.stubox.domain.entity.Card;
import com.nastudy.stubox.domain.entity.CardBox;
import com.nastudy.stubox.dto.CardDto;
import com.nastudy.stubox.dto.CardsDto;
import com.nastudy.stubox.repository.CardBoxJpaRepository;
import com.nastudy.stubox.repository.CardJpaRepository;
import com.nastudy.stubox.repository.CardRepository;
import com.nastudy.stubox.repository.CardTestJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardJpaRepository cardJpaRepository;
    private final CardBoxJpaRepository cardBoxJpaRepository;
    private final CardTestJpaRepository cardTestJpaRepository;

    @Transactional(readOnly = true)
    public CardsDto findCards(Long boxId) {
        CardBox cardBox = cardBoxJpaRepository.getReferenceById(boxId);
        List<CardDto> cards = cardJpaRepository.findByCardBox(cardBox)
                .stream()
                .map(CardDto::of)
                .collect(Collectors.toList());
        return new CardsDto(cardBox.getName(), cards);
    }

    public CardDto save(CardSaveForm form) {
        if (cardRepository.exists(form)) {
            throw new IllegalStateException("키워드 중복입니다");
        }

        CardBox cardBox = cardBoxJpaRepository.getReferenceById(form.getBoxId());
        Card card = Card.builder()
                .keyword(form.getKeyword())
                .concept(form.getConcept())
                .cardBox(cardBox)
                .build();
        card = cardJpaRepository.save(card);
        return CardDto.of(card);
    }

    public void changeBoxName(CardUpdateForm form) {
        CardBox cardBox = cardBoxJpaRepository.getReferenceById(form.getBoxId());
        cardBox.changeName(form.getBoxName());
    }

    public void delete(CardDeleteForm form) {
        Card card = cardJpaRepository.findById(form.getId()).orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."));
        cardTestJpaRepository.deleteByCard(card);
        cardJpaRepository.deleteById(form.getId());
    }
}
