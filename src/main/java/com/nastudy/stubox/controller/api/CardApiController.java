package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.controller.form.CardDeleteForm;
import com.nastudy.stubox.controller.form.CardSaveForm;
import com.nastudy.stubox.controller.form.CardUpdateForm;
import com.nastudy.stubox.dto.CardDto;
import com.nastudy.stubox.dto.CardsDto;
import com.nastudy.stubox.service.CardService;
import lombok.RequiredArgsConstructor;
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
    public CardDto addCard(@RequestBody CardSaveForm form) {
        return cardService.save(form);
    }

    @PutMapping()
    public Long changeBoxName(@RequestBody CardUpdateForm form) {
        cardService.changeBoxName(form);
        return form.getBoxId();
    }

    @DeleteMapping()
    public Long delete(@RequestBody CardDeleteForm form) {
        cardService.delete(form);
        return form.getId();
    }
}
