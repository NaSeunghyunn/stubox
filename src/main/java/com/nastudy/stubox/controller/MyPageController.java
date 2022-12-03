package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
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
        principal = auth2Service.refresh(principal);
        model.addAttribute("profile", principal.getProfile());
        model.addAttribute("memberName", principal.getName());
        model.addAttribute("teamName", principal.getTeamName());
        model.addAttribute("teamRole", principal.getTeamRole());
        return "myPage";
    }
}
