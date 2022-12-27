package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/myPage")
@Controller
public class MyPageController {

    private final Auth2Service auth2Service;

    @GetMapping()
    public String init(@AuthenticationPrincipal PrincipalDetail principal, Model model) {
        Member member = auth2Service.findMember(principal.getMemberId());
        model.addAttribute("profile", member.getProfile());
        model.addAttribute("memberName", member.getName());
        model.addAttribute("teamName", auth2Service.getTeamName(member));
        model.addAttribute("teamRole", member.getTeamRole());
        return "myPage";
    }
}
