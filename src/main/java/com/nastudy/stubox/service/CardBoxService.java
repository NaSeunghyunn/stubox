package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.CardBox;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.dto.BoxDeleteDto;
import com.nastudy.stubox.dto.BoxDto;
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
    private final Auth2Service auth2Service;
    private final CardJpaRepository cardJpaRepository;
    private final CardTestJpaRepository cardTestJpaRepository;
    private final TestJpaRepository testJpaRepository;

    @Transactional(readOnly = true)
    public List<BoxDto> search(Long memberId) {
        Member member = auth2Service.findMember(memberId);

        if (member.getTeam() != null && member.getTeamRole() != TeamRole.UNAPPROVED) {
            return cardBoxRepository.searchByTeam(member.getTeam().getId());
        } else {
            return searchMyBox(memberId);
        }
    }

    public List<BoxDto> searchMyBox(Long memberId) {
        return cardBoxRepository.searchByMember(memberId);
    }

    public BoxDto save(String name, Long memberId) {
        Member member = auth2Service.findMember(memberId);
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
