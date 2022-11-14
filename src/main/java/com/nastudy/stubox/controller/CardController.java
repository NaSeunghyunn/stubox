package com.nastudy.stubox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/card")
public class CardController {
    @GetMapping("/init/{boxId}")
    public String init(@PathVariable("boxId") Long boxId, Model model) {
        model.addAttribute("boxId", boxId);
        return "card";
    }
}
