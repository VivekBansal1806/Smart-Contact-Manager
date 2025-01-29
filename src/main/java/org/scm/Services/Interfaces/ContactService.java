package org.scm.Services.Interfaces;

import org.scm.Forms.ContactForm;
import org.scm.entities.Contact;
import org.scm.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public interface ContactService {

    Optional<Contact> saveContact(ContactForm contactForm, User user);

    Optional<Contact> updateContact(ContactForm contactForm);

    List<Contact> getAllContacts();

    Optional<Contact> getContactById(String id);

    void deleteContact(String  id);

    List<Contact> searchContact(String name,String email,String phoneNumber);

    List<Contact> getByUserId(String userId);

    List<Contact> getByUser(User user);


}
