package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.PostsSaveForm;
import com.nastudy.stubox.controller.form.PostsForm;
import com.nastudy.stubox.dto.PostsDetailDto;
import com.nastudy.stubox.dto.PostsResponse;
import com.nastudy.stubox.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping()
    public Long save(@RequestBody @Validated PostsSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return postService.save(form, principal.getMemberId());
    }

    @GetMapping()
    public PostsResponse findPosts(PostsForm form) {
        return postService.findPosts(form.toCond());
    }

    @GetMapping("/{id}")
    public PostsDetailDto findPostDetail(@PathVariable("id") Long id) {
        return postService.findPostDetail(id);
    }
}
