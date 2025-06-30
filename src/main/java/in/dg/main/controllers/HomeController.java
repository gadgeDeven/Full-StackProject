package in.dg.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller for landing page
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // Loads index.html from templates/
    }
}