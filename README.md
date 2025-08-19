
# Personal Website Generator (Profile2Web)

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![SpringBoot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![Angular](https://img.shields.io/badge/Angular-18-red)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

A full-stack web application that allows users to instantly generate a professional personal website from profile data.

---

##  Features
- **Profile-driven website generation** – users enter profile details, which are dynamically rendered into a personal website.
- **Social links integration** – automatically displays all included social media links.
- **Contact form with email delivery** – visitors can reach out directly, with **emails generated using Thymeleaf templates** for a personalized format.
- **Template-driven design** – currently includes one template, with plans to support multiple templates in future versions.
- **Scalable backend** – built on Spring Boot with REST APIs, enabling extensibility.
- **Modern frontend** – responsive UI built with Angular.
- **Secure key management** – sensitive keys are externalized, automatically encrypted on first run, and re-encrypted if new keys are added.

---

##  Tech Stack
- **Frontend:** Angular
- **Backend:** Spring Boot, Spring Security, Thymeleaf (emails)
- **Cloud/Deployment:** AWS (optional), GitHub Actions CI/CD, Nginx
- **Languages:** Java, TypeScript

---

##  Setup Instructions

### 1) Clone the repository
```bash
git clone https://github.com/your-username/profile2web.git
cd profile2web
```

### 2) Backend (Spring Boot)
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```
- Runs on **http://localhost:8090** by default.
- Update `application.yml` as needed for server configs.

#### Backend `application.yml` (excerpt)
```yaml
server:
  port: 8090

management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: always

spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${'{'} EMAIL_USERNAME {'}'}
    password: ${'{'} EMAIL_PASSWORD.key {'}'}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
          connectiontimeout: 5000
          timeout: 5000
          writetimeout: 5000

contact:
  to: rmkaheto4java@gmail.com

recaptcha:
  secretKey: ${'{'} GOOGLE_RECAPTCHA_SECRET.key {'}'}
  verifyUrl: https://www.google.com/recaptcha/api/siteverify

app:
  backend-base-url: http://localhost:8090
```

### 3) Frontend (Angular)
```bash
cd frontend
npm install
ng serve
```
- Runs on **http://localhost:4200** by default.
- Update `environment.ts` to point to your backend API URL (e.g., `http://localhost:8090/api`).

---

##  Properties & Keys Setup

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

3. **First-run encryption:** When the backend starts, it will automatically **encrypt all sensitive keys** found in this file.
    - The app uses the encrypted values transparently at runtime.
    - **Any new keys** added later will also be encrypted on the next application run.

>  Keep `application-keys.properties` out of version control.

---

## Front-End Profile Data (current manual method)

Until the database-backed profile service is released, the Angular app reads profile data from a local file:

**Path:** `frontend/src/app/data/profile.ts`

Provide your profile, links, and lists (skills, projects, experience, certifications) in this file. See the repo sample for the exact schema and examples.

---

## CI/CD — GitHub Actions (Backend)

This repository includes a production-ready CI/CD pipeline for the Spring Boot backend. It:

- **Builds on every push/PR to `main`** (format check with Spotless, unit tests, packages a fat JAR).
- **Publishes the JAR as an artifact** between jobs.
- **Deploys to an EC2 server on successful pushes to `main`**, using SSH + rsync.
- **Restarts the systemd service** and performs a **post-deploy health check** against `/actuator/health` through Nginx.

<details>
<summary>Click to view <code>.github/workflows/backend-ci-cd.yml</code></summary>

```yaml

name: Backend CI/CD (Spring Boot)

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]
  workflow_dispatch:

concurrency:
  group: backend-${{ github.ref }}
  cancel-in-progress: true

permissions:
  contents: read

jobs:
  ci:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repo
        uses: actions/checkout@v4
      - name: Setup Temurin JDK 17 (with Maven cache)
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
          cache: maven
      - name: Install/Restore dependencies
        run: mvn -B -q dependency:resolve dependency:resolve-plugins
      - name: Check code format (Spotless)
        run: mvn -B spotless:check
      - name: Run unit tests
        run: mvn -B test
      - name: Package (repackage Spring Boot)
        run: mvn -B clean package spring-boot:repackage -DskipTests -Dspring.profiles.active=prod
      - name: Upload JAR artifact
        uses: actions/upload-artifact@v4
        with:
          name: backend-jar
          path: target/*.jar
          if-no-files-found: error
          retention-days: 3

  deploy:
    needs: [ci]
    if: github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    environment:
      name: production
    steps:
      - name: Download JAR artifact
        uses: actions/download-artifact@v4
        with:
          name: backend-jar
          path: target
      - name: Install SSH tools
        run: sudo apt-get update && sudo apt-get install -y openssh-client rsync
      - name: Setup SSH
        env:
          EC2_SSH_KEY: ${{ secrets.EC2_SSH_KEY }}
          EC2_HOST:    ${{ secrets.EC2_HOST }}
        run: |
          mkdir -p ~/.ssh
          chmod 700 ~/.ssh
          printf "%s
" "$EC2_SSH_KEY" > ~/.ssh/id_rsa
          chmod 600 ~/.ssh/id_rsa
          ssh-keyscan -H "$EC2_HOST" >> ~/.ssh/known_hosts
      - name: Deploy to server
        env:
          EC2_HOST:      ${{ secrets.EC2_HOST }}
          EC2_USER:      ${{ secrets.EC2_USER }}
          BACKEND_PATH:  ${{ secrets.BACKEND_DEPLOY_PATH }}
          SERVICE_NAME:  ${{ secrets.SERVICE_NAME }}
        run: |
          set -e
          JAR_PATH="$(ls -1 target/*.jar | head -n1)"
          echo "📦 Rsync $JAR_PATH to $EC2_USER@$EC2_HOST:$BACKEND_PATH/app.jar"
          rsync -avz --delete "$JAR_PATH" "$EC2_USER@$EC2_HOST:$BACKEND_PATH/app.jar"

          echo "Restart $SERVICE_NAME"
          ssh -o StrictHostKeyChecking=no "$EC2_USER@$EC2_HOST"             "sudo systemctl restart $SERVICE_NAME && sudo systemctl is-active --quiet $SERVICE_NAME || (sudo journalctl -u $SERVICE_NAME -n 200 --no-pager; exit 1)"
      - name: Health check (through nginx /api)
        env:
          PUBLIC_HOST: raymond-aheto.com
        run: |
          echo " Checking https://$PUBLIC_HOST/actuator/health ..."
          curl -fsS --retry 5 --retry-delay 3 "https://$PUBLIC_HOST/actuator/health"             | tee /dev/stderr | grep -q '"status":"UP"'

```
</details>

### Required GitHub Secrets
- `EC2_SSH_KEY` — private key used to SSH into the server (multi-line).
- `EC2_HOST` — public hostname or IP of your server.
- `EC2_USER` — SSH user, e.g. `ubuntu`.
- `BACKEND_DEPLOY_PATH` — remote path where the app JAR lives, e.g. `/opt/portfolio-backend`.
- `SERVICE_NAME` — systemd service name, e.g. `portfolio-backend.service`.

>  **Security tips**
> - Store secrets in **GitHub Secrets** (never in the repo).
> - Lock down SSH with key-only auth and restricted users.
> - Ensure the service user has only the minimum permissions required.
> - Consider rotating keys regularly.

---

## Planned: DB‑backed Profile Storage
Next iteration will move profile data to the backend with a secure CRUD API, PostgreSQL storage, admin UI, JWT auth, and DB migrations (Flyway/Liquibase).

---

## Project Goal
Enable users to quickly deploy professional personal websites with minimal setup while supporting future customization through multiple templates.

---

## Roadmap
- Multiple customizable templates & theme switcher
- Admin dashboard for editing profile/content (DB-backed)
- Analytics for visits and contact submissions
- Docker & Kubernetes deployment

---
