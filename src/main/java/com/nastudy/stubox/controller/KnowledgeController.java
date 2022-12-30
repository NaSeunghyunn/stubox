package com.nastudy.stubox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/knowledge")
public class KnowledgeController {

    @GetMapping("/new")
    public String init(){
        return "knowledgeNew";
    }
}
