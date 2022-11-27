package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.TeamAuthForm;
import com.nastudy.stubox.controller.form.TeamExpelForm;
import com.nastudy.stubox.controller.form.TeamSaveForm;
import com.nastudy.stubox.controller.form.TeamUpdateForm;
import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.dto.MyTeamDto;
import com.nastudy.stubox.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/team")
@RestController
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping()
    public Long save(@RequestBody TeamSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.save(form, principal.getId());
    }

    @GetMapping("/my")
    public MyTeamDto myTeam(@AuthenticationPrincipal PrincipalDetail principal) {
        hasTeam(principal.getTeamId());
        return teamService.findMyTeam(principal.getTeamId());
    }

    @PutMapping("/auth")
    public Long auth(@RequestBody TeamAuthForm form, @AuthenticationPrincipal PrincipalDetail principal){
        hasTeam(principal.getTeamId());
        checkMaster(principal.getTeamRole());
        return teamService.auth(form, principal.getTeamId());
    }

    @PutMapping("/expel")
    public Long expel(@RequestBody TeamExpelForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        hasTeam(principal.getTeamId());
        checkMaster(principal.getTeamRole());
        return teamService.expel(form, principal.getTeamId());
    }

    @PutMapping("/withdrawal")
    public Long withdrawal(@AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.withdrawal(principal.getId());
    }

    @PutMapping("/my")
    public Long update(@RequestBody TeamUpdateForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        hasTeam(principal.getTeamId());
        checkMaster(principal.getTeamRole());
        return teamService.update(form, principal.getTeamId());
    }

    @DeleteMapping()
    public Long remove(@AuthenticationPrincipal PrincipalDetail principal) {
        hasTeam(principal.getTeamId());
        checkMaster(principal.getTeamRole());
        return teamService.remove(principal.getId());
    }

    private void checkMaster(TeamRole teamRole) {
        if (teamRole != TeamRole.MASTER) throw new IllegalStateException("관리자가 아닙니다.");
    }

    private void hasTeam(Long teamId) {
        if (teamId == null) throw new IllegalStateException("팀이 없습니다.");
    }
}
