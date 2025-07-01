Full Stack Project
A Spring Boot application with a Thymeleaf frontend (converted to static HTML for Netlify) and MySQL backend, featuring project and client management, contact form submissions, and newsletter subscriptions. The frontend is deployed on Netlify, and the backend on Render.
Prerequisites

Java 17 (for Spring Boot backend)
Node.js (optional, for local frontend development)
MySQL (database)
Git (version control)
Netlify account (for frontend deployment)
Render account (for backend deployment)
GitHub account (for repository hosting)

Project Structure

Backend: Spring Boot with JPA, MySQL, and REST APIs (/api/projects, /api/clients, etc.).
Frontend: Static HTML/CSS/JS (converted from Thymeleaf templates) with Tailwind CSS, hosted on Netlify.
Database: MySQL for storing projects, clients, contacts, and newsletters.

Setup Instructions
Backend (Spring Boot)

Clone the Repository:
git clone https://github.com/your-username/your-backend-repo.git
cd your-backend-repo


Configure MySQL:

Create a MySQL database (e.g., fullstack_db).
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


Deploy to Render:

Create a Dockerfile in the project root:FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


Push to GitHub:git add .
git commit -m "Prepare for Render deployment"
git push origin main


In Render (https://render.com):
Create a new Web Service, connect to your GitHub repo.
Set Environment: Docker, Instance Type: Free.
Add environment variables:
SPRING_DATASOURCE_URL: jdbc:mysql://your-mysql-host:3306/fullstack_db?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME: Your MySQL username
SPRING_DATASOURCE_PASSWORD: Your MySQL password


Deploy to get a URL (e.g., https://your-backend.onrender.com).





Frontend (Static HTML)

Create Static Frontend:

Create a frontend directory with public/index.html, admin.html, login.html, and static assets (images/, css/, js/).
Convert Thymeleaf templates to static HTML with JavaScript for API calls (see Description Document for details).


Configure netlify.toml:
[build]
  publish = "public"
  command = "echo 'No build command required for static site'"


Deploy to Netlify:

Push the frontend directory to a GitHub repo:cd frontend
git init
git add .
git commit -m "Initial frontend commit"
git remote add origin https://github.com/your-username/your-frontend-repo.git
git push -u origin main


In Netlify (https://app.netlify.com):
Add new site > Import from Git > Select your-frontend-repo.
Set Publish directory: public.
Deploy to get a URL (e.g., https://your-site-name.netlify.app).




Update API URLs:

In index.html, admin.html, and login.html, replace https://your-backend-url with your Render URL (e.g., https://your-backend.onrender.com).



Access

Landing Page: https://your-site-name.netlify.app
Admin Panel: https://your-site-name.netlify.app/admin.html
Login with username: admin, `


