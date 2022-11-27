package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/group")
@Controller
public class StudyGroupController {

    private final Auth2Service auth2Service;

    @GetMapping()
    public String init(Model model, @AuthenticationPrincipal PrincipalDetail principal){
        principal = auth2Service.refresh(principal);
        model.addAttribute("teamRole", principal.getTeamRole());
        model.addAttribute("categories", Category.values());
        return "studyGroup";
    }
}
