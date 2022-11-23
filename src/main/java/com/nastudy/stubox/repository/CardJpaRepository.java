package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Card;
import com.nastudy.stubox.domain.entity.CardBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardJpaRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardBox(CardBox cardBox);
}
