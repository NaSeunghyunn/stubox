package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.*;
import com.nastudy.stubox.dto.MyTeamDto;
import com.nastudy.stubox.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/team")
@RestController
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping()
    public Long save(@RequestBody @Validated TeamSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.save(form, principal.getMemberId());
    }

    @GetMapping("/my")
    public MyTeamDto myTeam(@AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.findMyTeam(principal.getMemberId());
    }

    @PutMapping("/auth")
    public Long auth(@RequestBody @Validated TeamAuthForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.auth(form.getMemberId(), principal.getMemberId());
    }

    @PutMapping("/expel")
    public Long expel(@RequestBody @Validated TeamExpelForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.expel(form.getMemberId(), principal.getMemberId());
    }

    @PutMapping("/approval")
    public Long approval(@RequestBody @Validated TeamApprovalForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.approval(form.getMemberId(), principal.getMemberId());
    }

    @PutMapping("/refuse")
    public Long refuse(@RequestBody @Validated TeamRefuseForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.refuse(form.getMemberId(), principal.getMemberId());
    }

    @PutMapping("/cancel")
    public Long cancel(@AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.cancel(principal.getMemberId(), principal.getMemberId());
    }

    @PutMapping("/withdrawal")
    public Long withdrawal(@AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.withdrawal(principal.getMemberId());
    }

    @PutMapping("/my")
    public Long update(@RequestBody @Validated TeamUpdateForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.update(form, principal.getMemberId());
    }

    @PostMapping("/req")
    public Long reqTeam(@RequestBody @Validated TeamReqForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.req(principal.getMemberId(), form.getTeamName());
    }

    @DeleteMapping()
    public Long remove(@AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.remove(principal.getMemberId());
    }
}
