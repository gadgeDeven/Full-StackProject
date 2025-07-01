package in.dg.main.controllers;

import in.dg.main.models.Client;
import in.dg.main.models.Project;
import in.dg.main.repositories.ClientRepository;
import in.dg.main.repositories.ProjectRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Controller for Project CRUD operations
@Controller
@RequestMapping("/api/projects")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Create a new project with image upload
    @PostMapping(consumes = {"multipart/form-data"})
    public String createProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("client.id") Long clientId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        logger.info("Received POST /api/projects with name: {}, clientId: {}", name, clientId);
        try {
            // Fetch client by ID
            Optional<Client> clientOpt = clientRepository.findById(clientId);
            if (!clientOpt.isPresent()) {
                logger.error("Client with ID {} not found", clientId);
                redirectAttributes.addFlashAttribute("errorMessage", "Client with ID " + clientId + " not found.");
                return "redirect:/admin";
            }

            Project project = new Project();
            project.setName(name);
            project.setDescription(description);
            project.setStartDate(LocalDate.parse(startDate));
            project.setEndDate(LocalDate.parse(endDate));
            project.setClient(clientOpt.get());

            // Save image to static folder
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/images/lead-generation-landing-page-images/" + fileName);
                logger.info("Saving image to: {}", path);
                try {
                    Files.createDirectories(path.getParent()); // Ensure directory exists
                    Files.write(path, imageFile.getBytes());
                    project.setImage("/images/lead-generation-landing-page-images/" + fileName);
                } catch (IOException e) {
                    logger.error("Failed to save image: {}", e.getMessage());
                    redirectAttributes.addFlashAttribute("errorMessage", "Failed to save image: " + e.getMessage());
                    return "redirect:/admin";
                }
            } else {
                logger.warn("No image file provided, using default image");
                project.setImage("/images/lead-generation-landing-page-images/pexels-brett-sayles-2881232.svg");
            }

            Project savedProject = projectRepository.save(project);
            logger.info("Project saved with ID: {}", savedProject.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Project '" + name + "' added successfully!");
            return "redirect:/admin";
        } catch (MaxUploadSizeExceededException e) {
            logger.error("File size exceeds limit: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Image size exceeds 10MB limit. Please upload a smaller file.");
            return "redirect:/admin";
        } catch (Exception e) {
            logger.error("Error creating project: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating project: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    // Get all projects (for API)
    @GetMapping
    @ResponseBody
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get a project by ID (for API)
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a project (for API)
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Optional<Project> existingProject = projectRepository.findById(id);
        if (existingProject.isPresent()) {
            Project project = existingProject.get();
            project.setName(updatedProject.getName());
            project.setDescription(updatedProject.getDescription());
            project.setStartDate(updatedProject.getStartDate());
            project.setEndDate(updatedProject.getEndDate());
            project.setImage(updatedProject.getImage());
            project.setClient(updatedProject.getClient());
            return ResponseEntity.ok(projectRepository.save(project));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a project (for form and API)
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE, RequestMethod.POST})
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Received DELETE /api/projects/{} request", id);
        try {
            if (projectRepository.existsById(id)) {
                projectRepository.deleteById(id);
                logger.info("Project with ID {} deleted successfully", id);
                redirectAttributes.addFlashAttribute("successMessage", "Project with ID " + id + " deleted successfully!");
                return "redirect:/admin";
            }
            logger.warn("Project with ID {} not found", id);
            redirectAttributes.addFlashAttribute("errorMessage", "Project with ID " + id + " not found.");
            return "redirect:/admin";
        } catch (Exception e) {
            logger.error("Error deleting project with ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting project: " + e.getMessage());
            return "redirect:/admin";
        }
    }
}