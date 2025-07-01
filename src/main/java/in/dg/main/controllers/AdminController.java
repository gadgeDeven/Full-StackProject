package in.dg.main.controllers;

import in.dg.main.repositories.ClientRepository;
import in.dg.main.repositories.ContactRepository;
import in.dg.main.repositories.NewsletterRepository;
import in.dg.main.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private NewsletterRepository newsletterRepository;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("contacts", contactRepository.findAll());
        model.addAttribute("newsletters", newsletterRepository.findAll());
        return "admin";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}