package com.server.controller.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/home"})
public class HomePageController {

    @GetMapping({"", "/", "/home"})
    public String getHomePage() {
        return "index";
    }
}
