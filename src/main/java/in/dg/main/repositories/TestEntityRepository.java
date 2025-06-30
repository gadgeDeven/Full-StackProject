package in.dg.main.repositories;

import in.dg.main.models.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for TestEntity CRUD operations
public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}