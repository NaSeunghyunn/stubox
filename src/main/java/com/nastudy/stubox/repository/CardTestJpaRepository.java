package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Card;
import com.nastudy.stubox.domain.entity.CardTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardTestJpaRepository extends JpaRepository<CardTest, Long> {
    @Modifying
    @Query("delete from CardTest ct where ct.card = :card")
    int deleteByCard(@Param("card") Card card);
}
