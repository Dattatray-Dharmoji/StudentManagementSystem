# Student Management System

A web application for managing students, courses, and course enrollments, with an administrative dashboard and authenticated access.

## Screenshot

The project screenshot will be added here when provided<img width="1917" height="1198" alt="Screenshot 2026-11-17 115653" src="https://github.com/user-attachments/assets/7b60ef07-d1f5-41e9-9d3c-20bb5a62de86" />
<img width="1917" height="1097" alt="Screenshot 2026-11-17 115924" src="https://github.com/user-attachments/assets/ccff609d-aea0-4e24-a67a-0c196f7b2eaa" />

<img width="1895" height="1081" alt="Screenshot 2026-11-17 115910" src="https://github.com/user-attachments/assets/860d2b41-5c1d-4f04-99e4-37db6dc43fb5" />
<img width="1906" height="1148" alt="Screenshot 2026-11-17 115855" src="https://github.com/user-attachments/assets/8b1fa74c-1722-46f7-af84-9c2abf9ed8f5" />
<img width="1917" height="1088" alt="Screenshot 2026-11-17 115748" src="https://github.com/user-attachments/assets/9df42ac8-d086-485d-bc42-55f9b2cf58e5" />
<img width="1917" height="1092" alt="Screenshot 2026-11-17 115739" src="https://github.com/user-attachments/assets/545f7102-8f01-424a-8177-ae4b011420be" />


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
