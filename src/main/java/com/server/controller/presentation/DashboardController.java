package com.server.controller.presentation;

import com.server.domain.Project;
import com.server.domain.User;
import com.server.service.ProjectService;
import com.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model, RedirectAttributes redirectAttributes) {
        try {
            // current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() ||
                    "anonymousUser".equals(authentication.getPrincipal())) {
                log.warn("Unauthenticated user attempting to access dashboard");
                redirectAttributes.addFlashAttribute("error", "Please log in to access the dashboard");
                return "redirect:/login";
            }

            String userId = authentication.getName();

            var userOptional = userService.getUserById(userId);
            if (userOptional.isEmpty()) {
                log.warn("User not found in database: {}", userId);
                redirectAttributes.addFlashAttribute("error", "User account not found");
                return "redirect:/login";
            }

            User user = userOptional.get();
            model.addAttribute("name", user.getName());

            if (user.getProject() != null) {
                Project project = user.getProject();
                model.addAttribute("projectName", project.getProjectName());
                model.addAttribute("emailList", project.getEmailList());
                model.addAttribute("hasProject", true);
            } else {
                model.addAttribute("hasProject", false);
                log.info("User {} has no associated project", userId);
                return "redirect:/projects/new";
            }

            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userId", user.getId());

            log.debug("Dashboard loaded successfully for user: {}", userId);
            return "dashboard";

        } catch (Exception e) {
            log.error("Error loading dashboard", e);
            redirectAttributes.addFlashAttribute("error", "An error occurred while loading the dashboard");
            return "redirect:/error";
        }
    }
}