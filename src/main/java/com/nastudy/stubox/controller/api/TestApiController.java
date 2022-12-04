package com.nastudy.stubox.controller.api;

import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.TestSaveForm;
import com.nastudy.stubox.controller.form.TestUpdateForm;
import com.nastudy.stubox.dto.TestDto;
import com.nastudy.stubox.dto.TestMainDto;
import com.nastudy.stubox.dto.TestSaveDto;
import com.nastudy.stubox.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/test")
@RestController
public class TestApiController {
    private final TestService testService;

    @GetMapping("/{boxId}")
    public TestMainDto testMain(@PathVariable("boxId") Long boxId) {
        return testService.findTestCount(boxId);
    }

    @GetMapping("/{boxId}/{level}")
    public List<TestDto> testData(@PathVariable("boxId") Long boxId, @PathVariable("level") int level, @AuthenticationPrincipal PrincipalDetail principal) {
        return testService.findTestData(boxId, principal.getMemberId(), principal.getTeamId(), level);
    }

    @PostMapping()
    public Long save(@RequestBody @Validated TestSaveForm form, @AuthenticationPrincipal PrincipalDetail principal) {
        TestSaveDto saveDto = new TestSaveDto(form, principal.getMemberId());
        return testService.save(saveDto);
    }

    @PutMapping()
    public Long update(@RequestBody @Validated TestUpdateForm form) {
        return testService.update(form);
    }
}
