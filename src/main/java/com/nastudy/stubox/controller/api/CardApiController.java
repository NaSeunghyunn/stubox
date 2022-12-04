package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.CardDeleteForm;
import com.nastudy.stubox.controller.form.CardSaveForm;
import com.nastudy.stubox.controller.form.CardUpdateForm;
import com.nastudy.stubox.dto.CardDto;
import com.nastudy.stubox.dto.CardsDto;
import com.nastudy.stubox.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/card")
@RestController
public class CardApiController {

    private final CardService cardService;

    @GetMapping("/{boxId}")
    public CardsDto init(@PathVariable("boxId") Long boxId) {
        return cardService.findCards(boxId);
    }

    @PostMapping()
    public CardDto addCard(@RequestBody @Validated CardSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return cardService.save(form, principal.getMemberId());
    }

    @PutMapping()
    public Long changeBoxName(@RequestBody @Validated CardUpdateForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        cardService.changeBoxName(form, principal.getMemberId());
        return form.getBoxId();
    }

    @DeleteMapping()
    public Long delete(@RequestBody @Validated CardDeleteForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        cardService.delete(form, principal.getMemberId());
        return form.getId();
    }
}
