package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.controller.form.TestUpdateForm;
import com.nastudy.stubox.domain.entity.Card;
import com.nastudy.stubox.domain.entity.CardTest;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Test;
import com.nastudy.stubox.dto.*;
import com.nastudy.stubox.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TestService {
    private final CardTestRepository cardTestRepository;
    private final CardTestJpaRepository cardTestJpaRepository;
    private final TestJpaRepository testJpaRepository;
    private final Auth2Service auth2Service;
    private final CardJpaRepository cardJpaRepository;
    private final CardBoxRepository cardBoxRepository;

    @Transactional(readOnly = true)
    public TestMainDto findTestCount(Long boxId) {
        List<Integer> countLevels = cardTestJpaRepository.countLevelByBoxId(boxId);
        Map<Integer, List<Integer>> countLevelMap = countLevels.stream().collect(Collectors.groupingBy(Function.identity()));
        TestMainDto result = new TestMainDto();

        int countLevel1 = countLevelMap.get(1) == null ? 0 : countLevelMap.get(1).size();
        int unimplementedCardCnt = cardJpaRepository.findByCardBoxId(boxId).size() - countLevels.size();
        result.setCountLevel1(countLevel1 + unimplementedCardCnt);
        int countLevel2 = countLevelMap.get(2) == null ? 0 : countLevelMap.get(2).size();
        result.setCountLevel2(countLevel2);
        int countLevel3 = countLevelMap.get(3) == null ? 0 : countLevelMap.get(3).size();
        result.setCountLevel3(countLevel3);
        int countLevel4 = countLevelMap.get(4) == null ? 0 : countLevelMap.get(4).size();
        result.setCountLevel4(countLevel4);
        int countLevel5 = countLevelMap.get(5) == null ? 0 : countLevelMap.get(5).size();
        result.setCountLevel5(countLevel5);
        return result;
    }

    @Transactional(readOnly = true)
    public List<TestDto> findTestData(Long boxId, Long memberId, int level) {
        Member member = auth2Service.findMember(memberId);
        Long teamId = auth2Service.getTeamId(member);
        boolean isMyGroupBox = cardBoxRepository.isGroupBox(boxId, memberId, teamId);
        if (!isMyGroupBox) {
            throw new IllegalArgumentException("테스트 권한이 없는 암기박스입니다.");
        }
        TestCond cond = new TestCond(memberId, boxId, level);
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
        Member member = auth2Service.findMember(saveDto.getMemberId());

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
