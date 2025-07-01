package in.dg.main.controllers;

import in.dg.main.models.Contact;
import in.dg.main.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for Contact Form submissions
@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    // Create a new contact submission
    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // Get all contact submissions
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}