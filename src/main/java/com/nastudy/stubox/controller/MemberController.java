package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.MemberNameUpdateForm;
import com.nastudy.stubox.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PutMapping()
    public Long modName(@RequestBody MemberNameUpdateForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return memberService.modName(principal.getId(), form.getMemberName());
    }
}
