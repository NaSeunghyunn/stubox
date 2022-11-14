package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.CreateBoxForm;
import com.nastudy.stubox.dto.BoxDto;
import com.nastudy.stubox.dto.BoxSearchCond;
import com.nastudy.stubox.service.CardBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/box")
public class BoxApiController {

    private final CardBoxService cardBoxService;

    @GetMapping()
    public List<BoxDto> search(@AuthenticationPrincipal PrincipalDetail principal) {
        BoxSearchCond searchCond = BoxSearchCond.of(principal);
        return cardBoxService.search(searchCond);
    }

    @PostMapping()
    public BoxDto createBox(@RequestBody CreateBoxForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return cardBoxService.save(form.getName(), principal.getMember());
    }
}
