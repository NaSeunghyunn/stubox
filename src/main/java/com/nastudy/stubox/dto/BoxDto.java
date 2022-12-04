package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.CardBox;
import com.nastudy.stubox.domain.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class BoxDto {
    private Long id;
    private String name;
    private String profile;
    private String author;

    @Builder
    @QueryProjection
    public BoxDto(Long id, String name, String profile, String author) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.author = author;
    }

    public static BoxDto of(CardBox cardBox, Member member) {
        return BoxDto.builder()
                .id(cardBox.getId())
                .name(cardBox.getName())
                .profile(member.getProfile())
                .author(member.getName())
                .build();
    }
}
