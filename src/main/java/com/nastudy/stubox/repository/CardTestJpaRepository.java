package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Card;
import com.nastudy.stubox.domain.entity.CardTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardTestJpaRepository extends JpaRepository<CardTest, Long> {
    @Modifying
    @Query("delete from CardTest ct where ct.card = :card")
    int deleteByCard(@Param("card") Card card);

    @Query("select ct.level from CardBox cb join Card c on c.cardBox = cb join CardTest ct on ct.card = c where cb.id = :boxId")
    List<Integer> countLevelByBoxId(@Param("boxId") Long boxId);
}
