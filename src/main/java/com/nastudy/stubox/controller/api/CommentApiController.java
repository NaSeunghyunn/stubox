package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.CommentChildSaveForm;
import com.nastudy.stubox.controller.form.CommentDeleteForm;
import com.nastudy.stubox.controller.form.CommentSaveForm;
import com.nastudy.stubox.controller.form.CommentUpdateForm;
import com.nastudy.stubox.dto.CommentDto;
import com.nastudy.stubox.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping()
    public Long save(@RequestBody @Validated CommentSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return commentService.save(form, principal.getMemberId());
    }

    @PostMapping("/child")
    public Long saveChild(@RequestBody @Validated CommentChildSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return commentService.saveChild(form, principal.getMemberId());
    }

    @GetMapping("/{postId}")
    public List<CommentDto> findAll(@PathVariable("postId") Long postId) {
        return commentService.findAll(postId);
    }

    @DeleteMapping()
    public Long delete(@RequestBody @Validated CommentDeleteForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return commentService.delete(form, principal.getMemberId());
    }

    @PutMapping()
    public Long update(@RequestBody @Validated CommentUpdateForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return commentService.update(form, principal.getMemberId());
    }
}
