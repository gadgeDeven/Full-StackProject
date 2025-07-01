package in.dg.main.repositories;

import in.dg.main.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for Client CRUD operations
public interface ClientRepository extends JpaRepository<Client, Long> {
}