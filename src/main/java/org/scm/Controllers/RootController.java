package org.scm.Controllers;

import org.scm.Helper.LoggedInUserHelper;
import org.scm.Services.Interfaces.UserService;
import org.scm.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice // helps to work with every handler
public class RootController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    // addd to every handler present in this user handler        // we add this method for every handler when user is login so we create a controller for it
    public void addUserDetails(Model model, Authentication authentication) {

        if (authentication == null) { return; }
        String username = LoggedInUserHelper.getEmailOfLoggedInUser(authentication);
        logger.info("user logged in {}", username);
        User user = userService.getUserByEmail(username);
        model.addAttribute("user", user);
    }
}
