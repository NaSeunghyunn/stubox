package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.entity.Member;
import lombok.Data;

@Data
public class TeamMemberDto {
    private Long id;
    private String name;
    private String profile;

    public static TeamMemberDto of(Member member) {
        TeamMemberDto teamMemberDto = new TeamMemberDto();
        teamMemberDto.setId(member.getId());
        teamMemberDto.setName(member.getName());
        teamMemberDto.setProfile(member.getProfile());
        return teamMemberDto;
    }
}
