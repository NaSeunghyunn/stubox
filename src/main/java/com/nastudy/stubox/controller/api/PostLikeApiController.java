package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.PostLikeDeleteForm;
import com.nastudy.stubox.controller.form.PostLikeSaveForm;
import com.nastudy.stubox.dto.PostLikeDto;
import com.nastudy.stubox.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/like")
@RestController
public class PostLikeApiController {
    private final PostLikeService postLikeService;

    @GetMapping("/{postId}")
    public PostLikeDto findLike(@PathVariable("postId") Long postId, @AuthenticationPrincipal PrincipalDetail principal) {
        return postLikeService.findPostLike(postId, principal.getMemberId());
    }

    @PostMapping()
    public Long save(@RequestBody @Validated PostLikeSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return postLikeService.save(form.getPostId(), principal.getMemberId());
    }

    @DeleteMapping()
    public Long delete(@RequestBody @Validated PostLikeDeleteForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return postLikeService.delete(form.getPostId(), principal.getMemberId());
    }
}
