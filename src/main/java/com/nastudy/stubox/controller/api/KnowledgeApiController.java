package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.PostForm;
import com.nastudy.stubox.controller.form.PostSaveForm;
import com.nastudy.stubox.domain.FileUploader;
import com.nastudy.stubox.domain.S3Folder;
import com.nastudy.stubox.dto.ImageResponse;
import com.nastudy.stubox.dto.PostDetailDto;
import com.nastudy.stubox.dto.PostDto;
import com.nastudy.stubox.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/knowledge")
@RestController
public class KnowledgeApiController {

    private final FileUploader fileUploader;
    private final KnowledgeService knowledgeService;

    @GetMapping()
    public Page<PostDto> findPosts(PostForm form) {
        return knowledgeService.findPosts(form);
    }

    @GetMapping("/count")
    public Page<PostDto> countPosts(PostForm form) {
        return knowledgeService.countPosts(form);
    }

    @GetMapping("/{id}")
    public PostDetailDto findPost(@PathVariable("id") Long id) {
        return knowledgeService.findPost(id);
    }

    @PostMapping("/image")
    public ImageResponse test(MultipartFile file) {
        String url = fileUploader.uploadProfileS3(file, S3Folder.KNOWLEDGE);
        return new ImageResponse(url);
    }

    @PostMapping()
    public Long save(@RequestBody @Validated PostSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return knowledgeService.save(form, principal.getMemberId());
    }
}
