package in.dg.main.controllers;

import in.dg.main.models.Client;
import in.dg.main.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

// Controller for Client CRUD operations
@Controller
@RequestMapping("/api/clients")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientRepository clientRepository;

    // Create a new client with image upload
    @PostMapping(consumes = {"multipart/form-data"})
    public String createClient(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("description") String description,
            @RequestParam("designation") String designation,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        logger.info("Received POST /api/clients with name: {}, email: {}", name, email);
        try {
            Client client = new Client();
            client.setName(name);
            client.setEmail(email);
            client.setPhone(phone);
            client.setDescription(description);
            client.setDesignation(designation);

            // Save image to static folder
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/images/lead-generation-landing-page-images/" + fileName);
                logger.info("Saving image to: {}", path);
                try {
                    Files.createDirectories(path.getParent()); // Ensure directory exists
                    Files.write(path, imageFile.getBytes());
                    client.setImage("/images/lead-generation-landing-page-images/" + fileName);
                } catch (IOException e) {
                    logger.error("Failed to save image: {}", e.getMessage());
                    redirectAttributes.addFlashAttribute("errorMessage", "Failed to save image: " + e.getMessage());
                    return "redirect:/admin";
                }
            } else {
                logger.warn("No image file provided, using default image");
                client.setImage("/images/lead-generation-landing-page-images/Ellipse 11.svg");
            }

            Client savedClient = clientRepository.save(client);
            logger.info("Client saved with ID: {}", savedClient.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Client '" + name + "' added successfully!");
            return "redirect:/admin";
        } catch (MaxUploadSizeExceededException e) {
            logger.error("File size exceeds limit: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Image size exceeds 10MB limit. Please upload a smaller file.");
            return "redirect:/admin";
        } catch (Exception e) {
            logger.error("Error creating client: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating client: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    // Get all clients (for API)
    @GetMapping
    @ResponseBody
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get a client by ID (for API)
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a client (for API)
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Optional<Client> existingClientOpt = clientRepository.findById(id);
        if (existingClientOpt.isPresent()) {
            Client existingClient = existingClientOpt.get();
            existingClient.setName(updatedClient.getName());
            existingClient.setEmail(updatedClient.getEmail());
            existingClient.setPhone(updatedClient.getPhone());
            existingClient.setDescription(updatedClient.getDescription());
            existingClient.setDesignation(updatedClient.getDesignation());
            existingClient.setImage(updatedClient.getImage());
            return ResponseEntity.ok(clientRepository.save(existingClient));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a client (for form and API)
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Received DELETE /api/clients/{} request", id);
        try {
            if (clientRepository.existsById(id)) {
                clientRepository.deleteById(id);
                logger.info("Client with ID {} deleted successfully", id);
                redirectAttributes.addFlashAttribute("successMessage", "Client with ID " + id + " deleted successfully!");
                return "redirect:/admin";
            }
            logger.warn("Client with ID {} not found", id);
            redirectAttributes.addFlashAttribute("errorMessage", "Client with ID " + id + " not found.");
            return "redirect:/admin";
        } catch (Exception e) {
            logger.error("Error deleting client with ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting client: " + e.getMessage());
            return "redirect:/admin";
        }
    }
}