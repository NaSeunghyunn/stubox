package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.dto.PostTagDto;
import com.nastudy.stubox.service.PostTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/postTag")
@RestController
public class PostTagApiController {
    private final PostTagService postTagService;

    @GetMapping("/{postId}")
    public List<PostTagDto> findPostTags(@PathVariable("postId") Long postId) {
        return postTagService.findPostTags(postId);
    }
}
