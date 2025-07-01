package in.dg.main.repositories;

import in.dg.main.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for Contact Form submissions
public interface ContactRepository extends JpaRepository<Contact, Long> {
}