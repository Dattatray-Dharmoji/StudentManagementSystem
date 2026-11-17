# Student Management System

A web application for managing students, courses, and course enrollments, with an administrative dashboard and authenticated access.

## Screenshot

The project screenshot will be added here when provided.

## Features

- View dashboard statistics
- Create, edit, and view student records
- Create and manage courses
- Enroll students in courses and review enrollment details
- Sign in with an administrator account

## Tech stack

- Java 17 and Spring Boot
- Spring MVC, Thymeleaf, Spring Security, and Spring Data JPA
- MySQL
- Maven

## Run locally

1. Install Java 17 and make a MySQL database named `studentmanagement`.
2. Copy the example configuration to a local, ignored configuration file:

   ```powershell
   Copy-Item src\main\resources\application.example.properties src\main\resources\application.properties
   ```

   Set your own database username and password in `application.properties`. That file and the local admin initializer are excluded from Git.

3. Ensure your database has an administrator account, then start the application:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. Open [http://localhost:8080](http://localhost:8080) and sign in with an existing administrator account.

## Configuration

| Environment variable | Purpose |
| --- | --- |
The checked-in example uses environment-variable placeholders for database credentials. Keep real credentials and local account setup out of Git.
