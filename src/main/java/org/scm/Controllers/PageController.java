package org.scm.Controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.scm.Forms.UserForm;
import org.scm.Helper.Message;
import org.scm.Helper.MessageType;
import org.scm.Services.Interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PageController {

   private final UserForm userForm;
   private final UserService userService;
   private final Logger logger= LoggerFactory.getLogger(this.getClass());

   //dependency injection through constructor
    @Autowired  // this annotation is not required when you're done dependency injection through constructor.
    PageController(UserForm userForm, UserService userService) {
        this.userForm = userForm;
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(){
        return "home";
    }

    @RequestMapping("/home")
    public String homePage(Model model) { // for sending dynamic data from controller.
        System.out.println("Home page handler.");

        //sending data to view.
        model.addAttribute("leetcodelink", "https://leetcode.com/u/VivekBansal1806/");
        return "home";  // this is connected to template named home.
    }

    @RequestMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @RequestMapping("/services")
    public String servicePage() {
        return "services";
    }

    @RequestMapping("/base")
    public String base() {
        return "base";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    //for register page
    @RequestMapping("/register")
    public String registerPage(Model model) {
//        userForm.setPhoneNumber("1234567890");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //process registration
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute UserForm userForm, BindingResult result, HttpSession session) {

        //fetch the data
        logger.info("User: {}",userForm.toString());

        //validate the data
        if(result.hasErrors()) {
            logger.error("Error: {}", result.getAllErrors().toString());
            Message message=Message.builder().msg("Try Again").type(MessageType.red).build();
            session.setAttribute("message",message);
            return "register";
        }

        //save the data
        userService.saveUser(userForm);

        //message="successful"
        Message message=Message.builder().msg("Registration successful").type(MessageType.green).build();
        session.setAttribute("message", message);

        //redirect login page
        return "redirect:/login";
    }

    //view of login page
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout" , method = RequestMethod.GET)
    public String logout() {
        return "redirect:/home";
    }
}
