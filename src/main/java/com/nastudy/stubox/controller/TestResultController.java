package com.nastudy.stubox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestMapping("/test/result")
@Controller
public class TestResultController {
    @GetMapping("/{boxId}")
    public String init(@PathVariable("boxId") Long boxId, Model model) {
        String from = LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ISO_DATE);
        String to = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("boxId", boxId);
        return "testResult";
    }
}
