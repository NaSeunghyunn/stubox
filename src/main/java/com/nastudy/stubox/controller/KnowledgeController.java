package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/knowledge")
@Controller
public class KnowledgeController {
    private final Auth2Service auth2Service;

    @GetMapping("/new")
    public String init() {
        return "knowledgeNew";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetail principal, Model model) {
        Member member = auth2Service.findMember(principal.getMemberId());
        model.addAttribute("postId", id);
        model.addAttribute("profile", member.getProfile());
        return "knowledgeDetail";
    }
}
