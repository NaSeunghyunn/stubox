package com.nastudy.stubox.service;

import com.nastudy.stubox.dto.BoxDto;
import com.nastudy.stubox.dto.BoxSearchCond;
import com.nastudy.stubox.domain.CardBox;
import com.nastudy.stubox.domain.Member;
import com.nastudy.stubox.repository.CardBoxJpaRepository;
import com.nastudy.stubox.repository.CardBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CardBoxService {
    private final CardBoxRepository cardBoxRepository;
    public final CardBoxJpaRepository jpaRepository;

    @Transactional(readOnly = true)
    public List<BoxDto> search(BoxSearchCond searchCond) {
        if (searchCond.getTeamId() != null && searchCond.getTeamId() > 0) {
            return cardBoxRepository.searchByTeam(searchCond.getTeamId());
        } else {
            return cardBoxRepository.searchByMember(searchCond.getMemberId());
        }
    }

    public BoxDto save(String name, Member member) {
        CardBox cardBox = CardBox.builder().name(name).member(member).build();
        cardBox = jpaRepository.save(cardBox);
        return BoxDto.of(cardBox);
    }
}
