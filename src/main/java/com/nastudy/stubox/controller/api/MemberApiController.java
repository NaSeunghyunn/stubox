package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.MemberNameUpdateForm;
import com.nastudy.stubox.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PutMapping()
    public Long modName(@RequestBody @Validated MemberNameUpdateForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return memberService.modName(principal.getMemberId(), form.getMemberName());
    }
}
