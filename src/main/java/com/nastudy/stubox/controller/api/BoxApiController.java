package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.CreateBoxForm;
import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.dto.BoxDto;
import com.nastudy.stubox.dto.BoxSearchCond;
import com.nastudy.stubox.service.CardBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/box")
@RestController
public class BoxApiController {

    private final CardBoxService cardBoxService;

    @GetMapping()
    public List<BoxDto> search(@AuthenticationPrincipal PrincipalDetail principal) {
        if (principal.getTeamRole() == TeamRole.UNAPPROVED) {
            return searchMyBox(principal);
        }

        BoxSearchCond searchCond = BoxSearchCond
                .builder()
                .memberId(principal.getMemberId())
                .teamId(principal.getTeamId())
                .build();
        return cardBoxService.search(searchCond);
    }

    @GetMapping("/myBox")
    public List<BoxDto> searchMyBox(@AuthenticationPrincipal PrincipalDetail principal) {
        BoxSearchCond searchCond = BoxSearchCond
                .builder()
                .memberId(principal.getMemberId())
                .build();
        return cardBoxService.search(searchCond);
    }

    @PostMapping()
    public BoxDto createBox(@RequestBody @Validated CreateBoxForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return cardBoxService.save(form.getName(), principal.getMemberId());
    }

    @DeleteMapping("/{id}")
    public Long remove(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetail principal) {
        return cardBoxService.remove(id, principal.getMemberId());
    }
}
