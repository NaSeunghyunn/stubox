package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.domain.TeamRole;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/post")
@Controller
public class PostController {
    @GetMapping()
    public String form() {
        return "post";
    }

    @GetMapping("/{id}/{teamName}")
    public String detailForm(@PathVariable("id") Long id, @PathVariable("teamName") String teamName, @AuthenticationPrincipal PrincipalDetail principal, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("teamName", teamName);
        model.addAttribute("disableReq", principal.getTeamRole() != TeamRole.NONE);
        return "postDetail";
    }
}
