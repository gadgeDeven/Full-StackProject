package in.dg.main.controllers;

import in.dg.main.models.Newsletter;
import in.dg.main.repositories.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for Newsletter subscriptions
@RestController
@RequestMapping("/api/newsletters")
public class NewsletterController {
    @Autowired
    private NewsletterRepository newsletterRepository;

    // Create a new newsletter subscription
    @PostMapping
    public Newsletter createNewsletter(@RequestBody Newsletter newsletter) {
        return newsletterRepository.save(newsletter);
    }

    // Get all newsletter subscriptions
    @GetMapping
    public List<Newsletter> getAllNewsletters() {
        return newsletterRepository.findAll();
    }
}