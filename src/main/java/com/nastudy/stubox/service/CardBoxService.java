package com.nastudy.stubox.service;

import com.nastudy.stubox.domain.entity.CardBox;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.dto.BoxDeleteDto;
import com.nastudy.stubox.dto.BoxDto;
import com.nastudy.stubox.dto.BoxSearchCond;
import com.nastudy.stubox.repository.*;
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
    private final CardJpaRepository cardJpaRepository;
    private final CardTestJpaRepository cardTestJpaRepository;
    private final TestJpaRepository testJpaRepository;

    @Transactional(readOnly = true)
    public List<BoxDto> search(BoxSearchCond searchCond) {
        if (searchCond.getTeamId() != null && searchCond.getTeamId() > 0) {
            return cardBoxRepository.searchByTeam(searchCond.getTeamId());
        } else {
            return cardBoxRepository.searchByMember(searchCond.getMemberId());
        }
    }

    public BoxDto save(String name, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("다시 로그인 해 주세요."));
        existsBoxName(name, memberId);
        CardBox cardBox = CardBox.builder().name(name).member(member).build();
        cardBox = jpaRepository.save(cardBox);
        return BoxDto.of(cardBox, member);
    }

    private void existsBoxName(String name, Long memberId) {
        boolean existsByName = cardBoxRepository.existsByName(memberId, name);
        if (existsByName) {
            throw new IllegalArgumentException("이미 작성한 박스이름 입니다.");
        }
    }

    public Long remove(Long boxId, Long memberId) {
        BoxDeleteDto boxDeleteDto = cardBoxRepository.selectBoxDeleteDto(boxId, memberId);
        if (boxDeleteDto == null) {
            throw new IllegalArgumentException("수정 권한이 없는 박스입니다.");
        }
        testJpaRepository.deleteAllByIdInBatch(boxDeleteDto.getTestIds());
        cardTestJpaRepository.deleteAllByIdInBatch(boxDeleteDto.getCardTestIds());
        cardJpaRepository.deleteAllByIdInBatch(boxDeleteDto.getCardIds());
        jpaRepository.deleteById(boxId);
        return boxId;
    }
}
