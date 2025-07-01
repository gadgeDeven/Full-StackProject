package in.dg.main.repositories;

import in.dg.main.models.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for Newsletter subscriptions
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
}