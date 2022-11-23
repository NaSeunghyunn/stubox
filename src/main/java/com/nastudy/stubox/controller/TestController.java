package com.nastudy.stubox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
@Controller
public class TestController {

    @GetMapping()
    public String testMain(){
        return "testMain";
    }

    @GetMapping("/{level}")
    public String test(@PathVariable(name = "level") int level, Model model){
        model.addAttribute("level", level);
        return "test";
    }
}
