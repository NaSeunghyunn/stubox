package com.nastudy.stubox.controller;

import com.nastudy.stubox.domain.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/team")
@Controller
public class TeamController {

    @GetMapping()
    public String init(Model model){
        model.addAttribute("categories", Category.values());
        return "team";
    }
}
