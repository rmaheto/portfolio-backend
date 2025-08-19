# Personal Website Generator (Profile2Web)

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![SpringBoot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![Angular](https://img.shields.io/badge/Angular-18-red)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

A full-stack web application that allows users to instantly generate a professional personal website from profile data.

---

## Features
- **Profile-driven website generation** – users enter profile details, which are dynamically rendered into a personal website.
- **Social links integration** – automatically displays all included social media links.
- **Contact form with email delivery** – visitors can reach out directly, with **emails generated using Thymeleaf templates** for a personalized format.
- **Template-driven design** – currently includes one template, with plans to support multiple templates in future versions.
- **Scalable backend** – built on Spring Boot with REST APIs, enabling extensibility.
- **Modern frontend** – responsive UI built with Angular.
- **Secure key management** – sensitive keys are externalized, automatically encrypted on first run, and re-encrypted if new keys are added.

---

## Tech Stack
- **Frontend:** Angular
- **Backend:** Spring Boot, Spring Security
- **Template Engine:** Thymeleaf (for dynamic email generation)
- **Cloud/Deployment:** AWS (optional), CI/CD pipeline
- **Languages:** Java, TypeScript

---

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/your-username/profile2web.git
cd profile2web
```

### 2. Backend (Spring Boot)
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```
- Runs on **http://localhost:8090** by default.
- Update `application.yml` as needed for server configs.

### 3. Frontend (Angular)
```bash
cd frontend
npm install
ng serve
```
- Runs on **http://localhost:4200** by default.
- Update `environment.ts` to point to your backend API URL (e.g., `http://localhost:8090/api`).

### 4. Database Setup
- Use PostgreSQL or MySQL.
- Update backend `application.properties` with your DB connection.
- Run schema migrations (if provided in `/resources`).

### 5. Properties & Keys Setup
This project externalizes sensitive keys (email credentials, reCAPTCHA secrets, etc.) into a properties file.

1. Create a folder named **`keys`** in the **root directory** of your system.
    - On **Windows**: `C:/keys`
    - On **Linux/Mac**: `/keys`

2. Inside the `keys` folder, create a file named `application-keys.properties`.  
   Example contents:
   ```properties
   EMAIL_USERNAME=your_email@gmail.com
   EMAIL_PASSWORD.key=your_password_here
   GOOGLE_RECAPTCHA_SECRET.key=your_recaptcha_secret
   ```

3. On first run, the application will automatically **encrypt all sensitive keys** found in the file.
    - Encrypted keys are stored securely, and the application transparently uses the decrypted values at runtime.
    - Any new keys added later will also be encrypted when the application is re-run.

### 6. Email Configuration
Configure SMTP credentials in the `application-keys.properties` file as above. The Spring Boot backend will load and use these settings to send contact form emails using Thymeleaf templates.

---

## Goal
Enable users to quickly deploy professional personal websites with minimal setup while supporting future customization through multiple templates.

---

## 🗺 Roadmap
- Add multiple customizable templates.
- Provide user authentication and profile editing.
- Add analytics dashboard for site visits and form submissions.
- Expand cloud deployment options with Docker and Kubernetes.

---
