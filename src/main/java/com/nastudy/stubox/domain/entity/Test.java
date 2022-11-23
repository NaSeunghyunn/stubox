package com.nastudy.stubox.domain.entity;

import com.nastudy.stubox.dto.TestResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Test extends BaseEntity {
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_test_id")
    private CardTest cardTest;

    @LastModifiedDate
    private LocalDateTime testDate;

    @Enumerated(EnumType.STRING)
    private TestResult testResult = TestResult.NG;

    private int level;

    @Builder
    public Test(Long id, CardTest cardTest, LocalDateTime testDate, TestResult testResult, int level) {
        this.id = id;
        this.cardTest = cardTest;
        this.testDate = testDate;
        this.testResult = testResult;
        this.level = level;
    }
}
