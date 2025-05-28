package com.server.controller.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping({"/", "/home"})
public class HomePageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping({"", "/", "/home"})
    public String getHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());

        model.addAttribute("authOK", isAuthenticated);
        log.info("GET Home page");
        return "index";
    }
}
