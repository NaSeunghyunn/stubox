package com.nastudy.stubox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
@Controller
public class TestController {

    @GetMapping("/{boxId}")
    public String testMain(@PathVariable("boxId") Long boxId, Model model) {
        model.addAttribute("boxId", boxId);
        return "testMain";
    }

    @GetMapping("/{boxId}/{level}")
    public String test(@PathVariable("boxId") Long boxId, @PathVariable(name = "level") int level, Model model) {
        model.addAttribute("boxId", boxId);
        model.addAttribute("level", level);
        return "test";
    }
}
