package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.domain.Category;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/group")
@Controller
public class StudyGroupController {

    @GetMapping()
    public String init(Model model, @AuthenticationPrincipal PrincipalDetail principal){
        model.addAttribute("teamRole", principal.getTeamRole());
        model.addAttribute("categories", Category.values());
        return "studyGroup";
    }
}
