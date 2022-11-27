package com.nastudy.stubox.dto;

import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Team;
import com.nastudy.stubox.domain.entity.TeamCategory;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MyTeamDto {
    private Long id;
    private String name;
    private List<Category> categories;
    private List<TeamMemberDto> teamMembers;
    private List<TeamMemberDto> teamManagers;

    public static MyTeamDto of(Team team, List<Member> members) {
        MyTeamDto myTeamDto = new MyTeamDto();
        myTeamDto.setId(team.getId());
        myTeamDto.setName(team.getName());
        List<Category> categories = team.getTeamCategories().stream().map(TeamCategory::getCategory).collect(Collectors.toList());
        myTeamDto.setCategories(categories);
        List<TeamMemberDto> teamMembers = members.stream()
                .filter(m -> m.getTeamRole() == TeamRole.MEMBER)
                .map(TeamMemberDto::of)
                .collect(Collectors.toList());
        myTeamDto.setTeamMembers(teamMembers);
        List<TeamMemberDto> teamManagers = members.stream()
                .filter(m -> m.getTeamRole() == TeamRole.MASTER)
                .map(TeamMemberDto::of)
                .collect(Collectors.toList());
        myTeamDto.setTeamManagers(teamManagers);
        return myTeamDto;
    }
}
