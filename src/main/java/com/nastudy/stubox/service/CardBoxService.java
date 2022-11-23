package com.nastudy.stubox.service;

import com.nastudy.stubox.dto.BoxDto;
import com.nastudy.stubox.dto.BoxResponse;
import com.nastudy.stubox.dto.BoxSearchCond;
import com.nastudy.stubox.domain.entity.CardBox;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.repository.CardBoxJpaRepository;
import com.nastudy.stubox.repository.CardBoxRepository;
import com.nastudy.stubox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CardBoxService {
    private final CardBoxRepository cardBoxRepository;
    private final CardBoxJpaRepository jpaRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public BoxResponse search(BoxSearchCond searchCond) {
        List<BoxDto> boxes = getBoxes(searchCond);
        Long selectedBoxId = memberRepository.findSelectedBoxId(searchCond.getMemberId());
        return new BoxResponse(boxes, selectedBoxId);
    }

    private List<BoxDto> getBoxes(BoxSearchCond searchCond) {
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

    public Long select(Long memberId, Long boxId) {
        Member member = memberRepository.getReferenceById(memberId);
        CardBox cardBox = jpaRepository.getReferenceById(boxId);
        member.selectBox(cardBox);
        return boxId;
    }
}
