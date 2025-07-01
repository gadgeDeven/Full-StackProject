package in.dg.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"in.dg.main.controllers", "in.dg.main.repositories", "in.dg.main.models"})
public class FullStackProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FullStackProjectApplication.class, args);
    }
}