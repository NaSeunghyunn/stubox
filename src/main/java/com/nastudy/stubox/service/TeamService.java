package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.controller.form.TeamSaveForm;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Team;
import com.nastudy.stubox.repository.MemberRepository;
import com.nastudy.stubox.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamService {

    private final TeamJpaRepository teamJpaRepository;
    private final MemberRepository memberRepository;
    private final Auth2Service auth2Service;

    public Long save(TeamSaveForm form, Long memberId){
        Team team = form.toEntity();
        teamJpaRepository.save(team);

        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalStateException("다시 로그인 해주세요."));
        member.createTeam(team);
        auth2Service.modifyMember(member);

        return team.getId();
    }
}
