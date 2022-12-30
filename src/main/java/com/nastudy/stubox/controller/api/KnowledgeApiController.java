package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.PostSaveForm;
import com.nastudy.stubox.domain.FileUploader;
import com.nastudy.stubox.domain.S3Folder;
import com.nastudy.stubox.dto.ImageResponse;
import com.nastudy.stubox.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/knowledge")
@RestController
public class KnowledgeApiController {

    private final FileUploader fileUploader;
    private final KnowledgeService knowledgeService;

    @PostMapping("/image")
    public ImageResponse test(MultipartFile file){
        String url = fileUploader.uploadProfileS3(file, S3Folder.KNOWLEDGE);
        return new ImageResponse(url);
    }

    @PostMapping()
    public Long save(@RequestBody @Validated PostSaveForm form, @AuthenticationPrincipal PrincipalDetail principal){
        return knowledgeService.save(form, principal.getMemberId());
    }
}
