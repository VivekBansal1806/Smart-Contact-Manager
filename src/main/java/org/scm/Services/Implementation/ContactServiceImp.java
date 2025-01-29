package org.scm.Services.Implementation;

import org.scm.Forms.ContactForm;
import org.scm.Helper.NoResourceFoundException;
import org.scm.Repository.ContactRepo;
import org.scm.Services.Interfaces.ContactService;
import org.scm.Services.Interfaces.ImageService;
import org.scm.entities.Contact;
import org.scm.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactServiceImp implements ContactService {

    private final ContactRepo contactRepo;
    private final Contact contact;
    private final ImageService imageService;

    @Autowired
    ContactServiceImp( ContactRepo contactRepo, Contact contact , ImageService imageService ) {
        this.contactRepo = contactRepo;
        this.contact = contact;
        this.imageService = imageService;
    }

    @Override
    public Optional<Contact> saveContact(ContactForm contactForm, User user) {

        BeanUtils.copyProperties(contactForm, contact);
        contact.setId(UUID.randomUUID().toString());
        contact.setUser(user);
        String url=null;

        if(contact.getPicture()!=null) {
            contact.setPictureId(UUID.randomUUID().toString());
            url = imageService.uploadImage(contactForm.getPicture(), contact.getPictureId());
        }
        else
            url=imageService.getUrlById(contact.getPictureId());
        contact.setPicture(url);
        contactRepo.save(contact);
        return Optional.of(contact);
    }

    @Override
    public Optional<Contact> updateContact(ContactForm contactForm) {
        return Optional.empty();
    }

    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }

    @Override
    public Optional<Contact> getContactById(String id) {
        Contact contact = contactRepo.findById(id).orElseThrow(()->new NoResourceFoundException("Contact not found"));
        return Optional.ofNullable(contact);
    }

    @Override
    public void deleteContact(String id) {
        contactRepo.deleteById(id);
    }

    @Override
    public List<Contact> searchContact(String name, String email, String phoneNumber) {
        return List.of();
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public List<Contact> getByUser(User user) {
        return contactRepo.findByUser(user);
    }

}
