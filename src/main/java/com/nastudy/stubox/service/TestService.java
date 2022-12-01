package com.nastudy.stubox.service;

import com.nastudy.stubox.controller.form.TestUpdateForm;
import com.nastudy.stubox.domain.entity.Card;
import com.nastudy.stubox.domain.entity.CardTest;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Test;
import com.nastudy.stubox.dto.TestCond;
import com.nastudy.stubox.dto.TestDto;
import com.nastudy.stubox.dto.TestResult;
import com.nastudy.stubox.dto.TestSaveDto;
import com.nastudy.stubox.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class TestService {
    private final CardTestRepository cardTestRepository;
    private final CardTestJpaRepository cardTestJpaRepository;
    private final TestJpaRepository testJpaRepository;
    private final MemberRepository memberRepository;
    private final CardJpaRepository cardJpaRepository;

    @Transactional(readOnly = true)
    public List<TestDto> findTestData(Long memberId, int level) {
        Long selectedBoxId = memberRepository.findSelectedBoxId(memberId);
        if(selectedBoxId == null){
            throw new IllegalArgumentException("암기박스를 선택해주세요");
        }
        TestCond cond = new TestCond(memberId, selectedBoxId, level);
        return cardTestRepository.selectTestData(cond);
    }

    public Long save(TestSaveDto saveDto) {
        existCardTest(saveDto);
        CardTest cardTest = saveCardTest(saveDto);
        saveTestResult(cardTest, LocalDateTime.now(), saveDto.getTestResult());
        cardTest.reLeveling(saveDto.getTestResult());
        return cardTest.getId();
    }

    public Long update(TestUpdateForm form) {
        CardTest cardTest = cardTestJpaRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("테스트 정보를 찾을 수 없습니다."));
        saveTestResult(cardTest, LocalDateTime.now(), form.getTestResult());
        cardTest.reLeveling(form.getTestResult());
        return form.getId();
    }

    private void existCardTest(TestSaveDto saveDto) {
        boolean existCardTest = cardTestRepository.exists(saveDto.getMemberId(), saveDto.getCardId());
        if (existCardTest) {
            throw new IllegalArgumentException("이미 존재하는 카드테스트");
        }
    }

    private CardTest saveCardTest(TestSaveDto saveDto) {
        Member member = memberRepository.findById(saveDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("다시 로그인해주세요"));

        Card card = cardJpaRepository.findById(saveDto.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재 하지 않습니다."));

        CardTest cardTest = CardTest
                .builder()
                .card(card)
                .member(member)
                .level(saveDto.getLevel())
                .build();

        cardTestJpaRepository.save(cardTest);
        return cardTest;
    }

    private void saveTestResult(CardTest cardTest, LocalDateTime testDate, TestResult testResult) {
        Test test = Test.builder()
                .cardTest(cardTest)
                .testDate(testDate)
                .testResult(testResult)
                .level(cardTest.getLevel())
                .build();
        testJpaRepository.save(test);
    }
}
