Full Stack Project
A full-stack web application for managing projects and clients, with a landing page displaying project and client details, an admin panel for CRUD operations, contact form submissions, and newsletter subscriptions. The frontend is hosted on Netlify as static HTML/CSS/JS, and the backend is a Spring Boot application with MySQL, deployed on Render.
Table of Contents

Features
Tech Stack
Prerequisites
Project Setup
Backend Setup (Spring Boot)
Frontend Setup (Static HTML)


Deployment
Deploy Backend to Render
Deploy Frontend to Netlify


Accessing the Application
Troubleshooting
Documentation Links

Features

Landing Page: Displays projects and clients with images, fetched from the backend API.
Admin Panel: Secure interface for managing projects and clients, accessible at /admin with credentials admin/Admin@123.
CRUD Operations: Create, read, update, and delete projects and clients via forms and REST APIs.
Contact Form: Collects user submissions (name, email, phone, city).
Newsletter: Manages email subscriptions.
Image Uploads: Supports uploading images for projects and clients (max 10MB).

Tech Stack

Backend: Spring Boot, Spring Data JPA, Spring Security (JWT), MySQL
Frontend: Static HTML, Tailwind CSS, JavaScript
Deployment: Netlify (frontend), Render (backend)
Tools: Maven, Git, GitHub

Prerequisites

Java 17: For running the Spring Boot backend.
MySQL: Database for storing data (local or managed service like PlanetScale).
Node.js: Optional, for local frontend testing.
Git: For version control.
Accounts: Netlify, Render, GitHub.

Project Setup
Backend Setup (Spring Boot)

Clone the Repository:
git clone https://github.com/your-username/your-backend-repo.git
cd your-backend-repo


Configure MySQL:

Create a database (e.g., fullstack_db).
Update src/main/resources/application.properties:spring.datasource.url=jdbc:mysql://localhost:3306/fullstack_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false
spring.web.resources.static-locations=classpath:/static/,classpath:/public/
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
server.port=${PORT:8080}




Build and Run Locally:
mvn clean package
java -jar target/your-app-name.jar


Access at http://localhost:8080.



Frontend Setup (Static HTML)

Create Frontend Directory:

Create a frontend directory with the following structure:frontend/
├── public/
│   ├── index.html
│   ├── admin.html
│   ├── login.html
│   ├── images/
│   │   ├── lead-generation-landing-page-images/
│   │   │   ├── logo.svg
│   │   │   ├── Ellipse 11.svg
│   │   │   ├── pexels-brett-sayles-2881232.svg
│   ├── css/
│   ├── js/
├── netlify.toml




Convert Thymeleaf to Static:

Convert index.html, admin.html, and login.html from Thymeleaf to static HTML with JavaScript for API calls (see Description_Document.md for examples).
Ensure static assets (images/, css/, js/) are copied from src/main/resources/static/.


Test Locally:

Use a simple HTTP server (e.g., npx serve public).
Verify API calls to http://localhost:8080/api/*.



Deployment
Deploy Backend to Render

Create Dockerfile:

Add to the project root:FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]




Build JAR:
mvn clean package


Push to GitHub:
git add .
git commit -m "Prepare for Render deployment"
git push origin main


Deploy on Render:

Sign up/login at Render.
Click “New” > “Web Service” > Connect your GitHub repo.
Configure:
Environment: Docker
Instance Type: Free
Environment Variables:
SPRING_DATASOURCE_URL: jdbc:mysql://your-mysql-host:3306/fullstack_db?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME: Your MySQL username
SPRING_DATASOURCE_PASSWORD: Your MySQL password




Deploy to get a URL (e.g., https://your-backend.onrender.com).



Deploy Frontend to Netlify

Create netlify.toml:

In the frontend directory:[build]
  publish = "public"
  command = "echo 'No build command required for static site'"




Push to GitHub:
cd frontend
git init
git add .
git commit -m "Initial frontend commit"
git remote add origin https://github.com/your-username/your-frontend-repo.git
git push -u origin main


Deploy on Netlify:

Sign up/login at Netlify.
Click “Add new site” > “Import an existing project” > Select your-frontend-repo.
Configure:
Publish directory: public
Build command: Leave blank


Deploy to get a URL (e.g., https://your-site-name.netlify.app).


Update API URLs:

In index.html, admin.html, and login.html, replace https://your-backend-url with the Render URL (e.g., https://your-backend.onrender.com).
Redeploy the frontend on Netlify.



Accessing the Application

Landing Page: https://your-site-name.netlify.app
Displays projects and clients.


Admin Panel: https://your-site-name.netlify.app/admin.html
Login with username: admin, password: Admin@123.
Manage projects and clients (CRUD), view contact submissions, and newsletters.


Backend APIs: https://your-backend.onrender.com/api/*
Example: GET /api/projects, POST /api/clients.



Troubleshooting

CORS Issues: Add CorsConfig.java to allow the Netlify domain:package in.dg.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://your-site-name.netlify.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}


Database Errors: Verify MySQL credentials and connectivity in Render’s environment variables.
Image Uploads: Ensure images/lead-generation-landing-page-images/ is writable on Render.
Login Failures: Check JWT implementation and credentials in SecurityConfig.java.
404 Errors: Ensure static assets (logo.svg, Ellipse 11.svg, etc.) are in frontend/public/images/lead-generation-landing-page-images/.

Documentation Links

Spring Boot
Netlify
Render
MySQL
Tailwind CSS
Spring Security
JWT
