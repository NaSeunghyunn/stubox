package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.controller.form.TagSaveForm;
import com.nastudy.stubox.dto.TagDto;
import com.nastudy.stubox.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/tag")
@RestController
public class TagApiController {
    private final TagService tagService;

    @GetMapping()
    public List<TagDto> tags(String name){
        return tagService.findTags(name);
    }

    @PostMapping()
    public Long saveTag(@RequestBody @Validated TagSaveForm form){
        return tagService.save(form);
    }
}
