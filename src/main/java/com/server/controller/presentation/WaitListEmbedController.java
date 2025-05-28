package com.server.controller.presentation;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/waitlist")
public class WaitListEmbedController {

    @GetMapping
    public String embedWaitlist(HttpServletResponse response,
                                @RequestParam(name = "projectName") String projectName,
                                Model model) {
        model.addAttribute("projectName", projectName);
        response.setHeader("X-Frame-Options", "ALLOWALL");
        response.setHeader("Content-Security-Policy", "frame-ancestors *;");
        return "waitlist-form-embed";
    }
}
