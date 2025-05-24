package com.server.controller.presentation;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/waitlist")
public class WaitListEmbedController {

    @GetMapping("/embed")
    public String embedWaitlist(HttpServletResponse response) {
        response.setHeader("X-Frame-Options", "ALLOWALL");
        response.setHeader("Content-Security-Policy", "frame-ancestors *;");
        return "WaitListFormEmbed";
    }
}
