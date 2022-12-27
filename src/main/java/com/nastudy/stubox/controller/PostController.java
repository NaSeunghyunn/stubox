package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {
    private final Auth2Service auth2Service;

    @GetMapping()
    public String form() {
        return "post";
    }

    @GetMapping("/{id}/{teamName}")
    public String detailForm(@PathVariable("id") Long id, @PathVariable("teamName") String teamName, @AuthenticationPrincipal PrincipalDetail principal, Model model) {
        Member member = auth2Service.findMember(principal.getMemberId());
        model.addAttribute("id", id);
        model.addAttribute("teamName", teamName);
        model.addAttribute("disableReq", member.getTeamRole() != TeamRole.NONE);
        return "postDetail";
    }
}
