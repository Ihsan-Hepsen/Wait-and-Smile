package com.server.controller.presentation;

import com.server.domain.Project;
import com.server.domain.User;
import com.server.service.ProjectService;
import com.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/new")
    public String showProjectForm() {
        return "new-project-form";
    }

    @PostMapping
    public String createProject(@RequestParam("name") String name,
                                @AuthenticationPrincipal OAuth2User oauthUser) {

        String email = oauthUser.getAttribute("email");
        var u = userService.getUserByEmail(email);
        if (u.isEmpty()) {
            log.error("Can't create project — user not found");
            return "redirect:/login";
        }

        User user = u.get();

        Project project = new Project();
        project.setProjectName(name);
        project.setOwner(user);

        user.setProject(project); // bi-directional

        userService.updateUser(user); // enough due to cascade
        return "redirect:/dashboard";
    }


}
