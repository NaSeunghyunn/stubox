package com.nastudy.stubox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/box")
public class BoxController {
    @GetMapping("/init")
    public String init(){
        return "cardBox";
    }

    @GetMapping("/form")
    public String form(){
        return "cardBoxForm";
    }
}
