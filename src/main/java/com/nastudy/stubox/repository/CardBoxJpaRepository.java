package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.CardBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBoxJpaRepository extends JpaRepository<CardBox, Long> {
}
