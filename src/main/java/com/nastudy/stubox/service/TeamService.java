package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.controller.form.TeamSaveForm;
import com.nastudy.stubox.controller.form.TeamUpdateForm;
import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Team;
import com.nastudy.stubox.domain.entity.TeamCategory;
import com.nastudy.stubox.dto.MyTeamDto;
import com.nastudy.stubox.repository.MemberRepository;
import com.nastudy.stubox.repository.PostsJpaRepository;
import com.nastudy.stubox.repository.TeamCategoryJpaRepository;
import com.nastudy.stubox.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamService {

    private final TeamJpaRepository teamJpaRepository;
    private final MemberRepository memberRepository;
    private final TeamCategoryJpaRepository teamCategoryJpaRepository;
    private final PostsJpaRepository postJpaRepository;
    private final Auth2Service auth2Service;

    public Long save(TeamSaveForm form, Long memberId) {
        Team team = form.toTeam();
        existsTeamName(team);
        teamJpaRepository.save(team);
        saveTeamCategories(form.getCategories(), team);

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("다시 로그인 해주세요."));
        member.joinTeam(team, TeamRole.MASTER);

        return team.getId();
    }

    public MyTeamDto findMyTeam(Long memberId) {
        Member member = auth2Service.findMember(memberId);
        auth2Service.hasTeam(member);
        List<Member> members = memberRepository.findByTeam(member.getTeam());
        return MyTeamDto.of(member.getTeam(), members);
    }

    public Long auth(Long groupMemberId, Long loginMemberId) {
        Member loginMember = getMasterMember(loginMemberId);
        Member member = groupMember(groupMemberId, auth2Service.getTeamId(loginMember));
        member.auth(TeamRole.MASTER);
        return member.getId();
    }

    public Long expel(Long groupMemberId, Long loginMemberId) {
        Member loginMember = getMasterMember(loginMemberId);
        Member member = groupMember(groupMemberId, auth2Service.getTeamId(loginMember));
        member.removeTeam();
        return member.getId();
    }

    public Long approval(Long groupMemberId, Long loginMemberId) {
        Member loginMember = getMasterMember(loginMemberId);
        Member member = groupMember(groupMemberId, auth2Service.getTeamId(loginMember));
        member.auth(TeamRole.MEMBER);
        return member.getId();
    }

    public Long refuse(Long groupMemberId, Long loginMemberId) {
        Member loginMember = getMasterMember(loginMemberId);
        Member member = groupMember(groupMemberId, auth2Service.getTeamId(loginMember));
        member.removeTeam();
        return member.getId();
    }

    public Long cancel(Long groupMemberId, Long loginMemberId) {
        Member loginMember = auth2Service.findMember(loginMemberId);
        auth2Service.hasTeam(loginMember);
        Member member = groupMember(groupMemberId, auth2Service.getTeamId(loginMember));
        member.removeTeam();
        return member.getId();
    }

    public Long req(Long memberId, String teamName) {
        Member member = notExistGroupMember(memberId);
        Team team = teamJpaRepository.findByName(teamName).orElseThrow(() -> new IllegalStateException("팀이 없습니다."));
        member.joinTeam(team, TeamRole.UNAPPROVED);
        return memberId;
    }

    public Long withdrawal(Long memberId) {
        Member member = auth2Service.findMember(memberId);
        auth2Service.hasTeam(member);
        if (member.getTeamRole() == TeamRole.MASTER) {
            int count = teamJpaRepository.countMaster(member.getTeam().getId());
            if (count < 2) {
                throw new IllegalStateException("관리자 탈퇴는 2명이상의 관리자가 있어야 탈퇴가 가능합니다.");
            }
        }
        member.removeTeam();
        return memberId;
    }

    public Long update(TeamUpdateForm form, Long memberId) {
        Member member = auth2Service.findMember(memberId);
        auth2Service.isMaster(member);
        modifyCategories(form.getCategories(), member.getTeam());
        return member.getTeam().getId();
    }

    public Long remove(Long memberId) {
        Member member = auth2Service.findMember(memberId);
        auth2Service.isMaster(member);
        int count = teamJpaRepository.countMaster(member.getTeam().getId());
        if (count > 1) {
            throw new IllegalStateException("임원이 2명 이상이면 그룹없애기를 할 수 없습니다.");
        }
        Team team = member.getTeam();
        member.removeTeam();
        memberRepository.withdrawTeam(team);
        postJpaRepository.deleteByTeam(team);
        teamJpaRepository.delete(team);
        return memberId;
    }

    private void saveTeamCategories(EnumSet<Category> categories, Team team) {
        List<TeamCategory> teamCategories = categories.stream()
                .map(category -> TeamCategory.builder().team(team).category(category).build())
                .collect(Collectors.toList());
        teamCategoryJpaRepository.saveAll(teamCategories);
    }

    private void modifyCategories(EnumSet<Category> categories, Team team) {
        teamCategoryJpaRepository.deleteByTeam(team);
        saveTeamCategories(categories, team);
    }

    private void existsTeamName(Team team) {
        boolean existsByName = teamJpaRepository.existsByName(team.getName());
        if (existsByName) {
            throw new IllegalArgumentException("이미 존재하는 팀 이름입니다.");
        }
    }

    private Member getMasterMember(Long memberId){
        Member member = auth2Service.findMember(memberId);
        auth2Service.isMaster(member);
        return member;
    }

    private Member groupMember(Long groupMemberId, Long teamId) {
        Member groupMember = auth2Service.findMember(groupMemberId);
        if (groupMember.getTeam() == null || !groupMember.getTeam().getId().equals(teamId)) {
            throw new IllegalArgumentException("그룹멤버가 아닙니다.");
        }
        return groupMember;
    }

    private Member notExistGroupMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디 입니다."));

        if (member.getTeamRole() == TeamRole.UNAPPROVED) {
            throw new IllegalArgumentException("그룹신청을 취소해 주세요.");
        }

        if (member.getTeam() != null) {
            throw new IllegalArgumentException("그룹을 탈퇴 해 주세요.");
        }
        return member;
    }
}
