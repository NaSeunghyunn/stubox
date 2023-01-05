package com.nastudy.stubox.controller;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.config.auth.PrincipalDetail;
import com.nastudy.stubox.controller.form.PostForm;
import com.nastudy.stubox.domain.PostSearchType;
import com.nastudy.stubox.domain.PostSortType;
import com.nastudy.stubox.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/knowledge")
@Controller
public class KnowledgeController {
    private final Auth2Service auth2Service;

    @GetMapping()
    public String init(PostForm form, Model model) {
        model.addAttribute("page", form.getPage());
        model.addAttribute("size", 10);
        PostSortType sortType = form.getSort() == null ? PostSortType.LATEST : form.getSort();
        model.addAttribute("sort", sortType);
        model.addAttribute("sortType", sortType.getTypeName());
        PostSearchType searchType = form.getSearchType() == null ? PostSearchType.TITLE : form.getSearchType();
        model.addAttribute("searchType", searchType.name());
        model.addAttribute("search", form.getSearch());
        model.addAttribute("totalCount", form.getTotalCount());
        return "knowledge";
    }

    @GetMapping("/new")
    public String knowledgeNew() {
        return "knowledgeNew";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetail principal, Model model) {
        Member member = auth2Service.findMember(principal.getMemberId());
        model.addAttribute("postId", id);
        model.addAttribute("myName", member.getName());
        model.addAttribute("profile", member.getProfile());
        return "knowledgeDetail";
    }
}
