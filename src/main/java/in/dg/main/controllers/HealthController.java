package in.dg.main.controllers;

import in.dg.main.models.TestEntity;
import in.dg.main.repositories.TestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// REST controller for health check endpoint
@RestController
public class HealthController {
    @Autowired
    private TestEntityRepository repository;

    @GetMapping("/health")
    public String healthCheck() {
        long count = repository.count();
        return "Application is running with " + count + " test records in the database";
    }
}