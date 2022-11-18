package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.CreateBoxForm;
import com.nastudy.stubox.dto.BoxDto;
import com.nastudy.stubox.dto.BoxResponse;
import com.nastudy.stubox.dto.BoxSearchCond;
import com.nastudy.stubox.service.CardBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/box")
@RestController
public class BoxApiController {

    private final CardBoxService cardBoxService;

    @GetMapping()
    public BoxResponse search(@AuthenticationPrincipal PrincipalDetail principal) {
        BoxSearchCond searchCond = BoxSearchCond
                .builder()
                .memberId(principal.getId())
                .teamId(principal.getTeamId())
                .build();
        return cardBoxService.search(searchCond);
    }

    @GetMapping("/myBox")
    public BoxResponse searchMyBox(@AuthenticationPrincipal PrincipalDetail principal) {
        BoxSearchCond searchCond = BoxSearchCond
                .builder()
                .memberId(principal.getId())
                .build();
        return cardBoxService.search(searchCond);
    }

    @PostMapping()
    public BoxDto createBox(@RequestBody CreateBoxForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return cardBoxService.save(form.getName(), principal.getMember());
    }

    @PutMapping("/{id}")
    public Long select(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetail principal) {
        return cardBoxService.select(principal.getId(), id);
    }
}
