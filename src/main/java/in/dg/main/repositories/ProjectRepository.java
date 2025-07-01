package in.dg.main.repositories;

import in.dg.main.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for Project CRUD operations
public interface ProjectRepository extends JpaRepository<Project, Long> {
}