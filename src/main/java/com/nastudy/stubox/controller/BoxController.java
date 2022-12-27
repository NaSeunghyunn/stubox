package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/box")
@Controller
public class BoxController {
    private final Auth2Service auth2Service;

    @GetMapping("/init")
    public String init(@AuthenticationPrincipal PrincipalDetail principal) {
        return "cardBox";
    }

    @GetMapping("/form")
    public String form() {
        return "cardBoxForm";
    }
}
