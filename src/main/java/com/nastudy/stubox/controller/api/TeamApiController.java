package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.TeamSaveForm;
import com.nastudy.stubox.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/team")
@RestController
public class TeamApiController {

    private final TeamService teamService;

    @PostMapping()
    public Long save(@RequestBody TeamSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return teamService.save(form, principal.getId());
    }
}
