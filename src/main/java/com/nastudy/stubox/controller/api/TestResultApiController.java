package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.TestResultForm;
import com.nastudy.stubox.dto.TestResultResponse;
import com.nastudy.stubox.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/test/result")
@RestController
public class TestResultApiController {
    private final TestResultService testResultService;

    @GetMapping()
    public TestResultResponse findTestResults(TestResultForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        return testResultService.findTestResults(form, principal.getId());
    }
}
