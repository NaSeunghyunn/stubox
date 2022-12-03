package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.PostSaveForm;
import com.nastudy.stubox.controller.form.PostsForm;
import com.nastudy.stubox.dto.PostDetailDto;
import com.nastudy.stubox.dto.PostsResponse;
import com.nastudy.stubox.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping()
    public Long save(@RequestBody PostSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        principal.checkMaster();
        principal.hasTeam();
        return postService.save(form, principal.getTeamId());
    }

    @GetMapping()
    public PostsResponse findPosts(PostsForm form) {
        return postService.findPosts(form.toCond());
    }

    @GetMapping("/{id}")
    public PostDetailDto findPostDetail(@PathVariable("id") Long id) {
        return postService.findPostDetail(id);
    }
}
