package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.CardBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardBoxJpaRepository extends JpaRepository<CardBox, Long> {
    @Query("select cb from CardBox cb join fetch cb.member where cb.id = :id")
    CardBox findCardBox(@Param("id") Long id);
}
