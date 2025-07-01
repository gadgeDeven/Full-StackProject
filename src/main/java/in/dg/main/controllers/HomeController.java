package in.dg.main.controllers;

import in.dg.main.repositories.ClientRepository;
import in.dg.main.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controller for landing page
@Controller
public class HomeController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        return "index"; // Loads index.html from templates/
    }
}