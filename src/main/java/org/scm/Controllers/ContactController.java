package org.scm.Controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.scm.Forms.ContactForm;
import org.scm.Helper.LoggedInUserHelper;
import org.scm.Helper.Message;
import org.scm.Helper.MessageType;
import org.scm.Services.Interfaces.ContactService;
import org.scm.Services.Interfaces.UserService;
import org.scm.entities.Contact;
import org.scm.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private final ContactForm contactForm;
    private final ContactService contactService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    public ContactController(ContactForm contactForm, ContactService contactService, UserService userService) {
        this.contactForm = contactForm;
        this.contactService = contactService;
        this.userService = userService;
    }

    @RequestMapping(value = "/addContact")
    public String addContact(Model model) {

//        contactForm.setName("vivek");
        model.addAttribute("contactForm", contactForm);
        return "user/addContact";
    }

    @RequestMapping(value = "/do-addContact", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, HttpSession session, Authentication authentication) {

        //  fetch the data
        logger.info( "ContactForm details submitted by user: {}",contactForm.toString());

        //checking
        if (result.hasErrors()) {
            logger.warn(result.getAllErrors().toString());
            Message message=Message.builder().msg("Try Again").type(MessageType.red).build();
            session.setAttribute("message",message);
            return "user/addContact";
        }

        //save the contact
        String username = LoggedInUserHelper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        logger.info(contactForm.toString());
        contactService.saveContact(contactForm, user);

        //message
        Message message = Message.builder().type(MessageType.green).msg("Contact is Added Successfully").build();
        session.setAttribute("message", message);

        //redirect
        return "redirect:/user/contacts/addContact";
    }


    @RequestMapping
    public String viewContacts(Authentication authentication, Model model) {

        String userName=LoggedInUserHelper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(userName);
        List<Contact> contactList= contactService.getByUser(user);

        model.addAttribute("contactList", contactList);

        return "user/Contacts";
    }

    @RequestMapping("/delete/{id}")
    public String deleteContact( @PathVariable("id") String contactId ,HttpSession session) {
        contactService.deleteContact(contactId);
        Message message = Message.builder().msg("Contact deleted successfully").type(MessageType.red).build();
        session.setAttribute("message", message);
        return "redirect:/user/contacts";

    }
}
