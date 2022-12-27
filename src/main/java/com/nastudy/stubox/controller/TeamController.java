package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/team")
@Controller
public class TeamController {

    private final Auth2Service auth2Service;

    @GetMapping()
    public String init(Model model) {
        model.addAttribute("categories", Category.values());
        return "team";
    }

    @GetMapping("/my")
    public String myTeam(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
        Member member = auth2Service.findMember(principal.getMemberId());
        model.addAttribute("teamRole", member.getTeamRole());
        model.addAttribute("categories", Category.values());
        if (member.getTeamRole() == TeamRole.MASTER) {
            return "myTeamManagement";
        }
        return "myTeam";
    }
}
