package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Card;
import com.nastudy.stubox.domain.entity.CardBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardJpaRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardBox(CardBox cardBox);

    @Query("select c from CardBox cb join Card c on c.cardBox = cb where cb.id = :boxId")
    List<Card> findByCardBoxId(@Param("boxId") Long boxId);

    @Modifying
    @Query("delete from Card c where c.cardBox = :cardBox")
    int deleteByBox(@Param("cardBox") CardBox cardBox);
}
