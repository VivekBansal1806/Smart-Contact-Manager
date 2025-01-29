package org.scm.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserController {

//    @ModelAttribute // addd to every handler present in this user handler        // we add this method for every handler when user is login so we create a controller for it
//    public void addUserDetails(Model model, Authentication authentication) {
//        String username = LoggedInUserHelper.getEmailOfLoggedInUser(authentication);
//        logger.info("user logged in{}", username);
//
//        User user = userService.getUserByEmail(username).orElseThrow(() -> new NoResourceFoundException("User not found"));
//        model.addAttribute("user", user);
//    }

    @RequestMapping(value = "/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    @RequestMapping(value = "/contact")
    public String contact() {
        return "user/contact";
    }

    // add profile page
    @RequestMapping(value = "/profile")
    public String profile() {
        return "user/profile";
    }


}
