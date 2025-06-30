package in.dg.main.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// Entity to test MySQL connection
@Entity
public class TestEntity {
    @Id
    private Long id;
    private String name;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}