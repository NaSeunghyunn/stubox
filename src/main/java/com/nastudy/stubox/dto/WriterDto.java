package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Member;
import lombok.Data;

@Data
public class WriterDto {
    private String name;
    private String profile;

    public WriterDto(Member member) {
        this.name = member.getName();
        this.profile = member.getProfile();
    }
}
