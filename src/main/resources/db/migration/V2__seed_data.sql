-- Profile
INSERT INTO portfolio_profile (name, title, blurb, avatar_url, about_me, resume_url, location, contact_intro, footer_text,
                                email, phone, linkedin, linkedin_display, github, github_display,
                                facebook, facebook_display, x_handle, x_display, instagram, instagram_display)
VALUES ('Raymond Aheto',
        'SOFTWARE ENGINEER | JAVA | AWS CERTIFIED',
        'Java backend engineer specializing in Spring Boot microservices, event-driven architecture, and AWS cloud solutions. I build systems that scale — from healthcare data pipelines to fleet management platforms. AWS Certified Solutions Architect.',
        '/assets/raymond.jpg',
        'I''m a backend-focused engineer who''s spent 6+ years building systems that handle real load in high-stakes environments — healthcare data pipelines processing 100K+ daily transactions, fleet management platforms used by compliance teams, and enterprise integrations that replaced fragile SOAP services with clean REST APIs.

Before moving fully into software engineering, I led IT operations for healthcare networks in Ghana. That experience shaped how I think about reliability and resilience — I''ve seen firsthand what it costs when systems go down. It''s why I care about more than just shipping features: I care about uptime, observability, and building things that hold up under pressure.

I work primarily in Java and Spring Boot, with strong experience in event-driven architecture (Kafka, RabbitMQ), AWS infrastructure, and microservices design. I''m at my best in roles where engineering decisions have measurable business impact.',
        '/assets/Raymond_Aheto_Resume_V7.pdf',
        'West Des Moines, IA',
        'I''m always interested in discussing new opportunities, challenging projects, or connecting.',
        'Crafted with ❤️ by Raymond Aheto',
        'rmkaheto4java@gmail.com',
        'XXX-XXX-8276',
        'https://www.linkedin.com/in/raymond-aheto', 'linkedin.com/in/raymond-aheto',
        'https://github.com/rmaheto', 'github.com/rmaheto',
        'https://www.facebook.com/profile.php?id=100086921498712', 'facebook.com/raymond-aheto',
        'https://x.com/mawunyoaheto', 'x.com/mawunyoaheto',
        'https://www.instagram.com/mawunyoaheto/', 'instagram.com/mawunyoaheto/');

-- Skills: frontend
INSERT INTO skill (category, name, display_order) VALUES
('frontend', 'Angular', 1), ('frontend', 'TypeScript', 2), ('frontend', 'HTML5 / CSS3', 3),
('frontend', 'JavaScript', 4), ('frontend', 'Tailwind CSS', 5), ('frontend', 'Bootstrap', 6),
('frontend', 'jQuery', 7), ('frontend', 'AJAX', 8), ('frontend', 'JSP', 9);

-- Skills: backend
INSERT INTO skill (category, name, display_order) VALUES
('backend', 'Java', 1), ('backend', 'Spring (Boot, MVC, Data JPA, Security, Batch)', 2),
('backend', 'Hibernate', 3), ('backend', 'Microservices', 4), ('backend', 'RESTful APIs', 5),
('backend', 'SOAP', 6), ('backend', 'Kafka', 7), ('backend', 'RabbitMQ', 8),
('backend', 'Node.js', 9), ('backend', 'Struts 2 (legacy)', 10),
('backend', 'JUnit', 11), ('backend', 'Mockito', 12);

-- Skills: db
INSERT INTO skill (category, name, display_order) VALUES
('db', 'PostgreSQL', 1), ('db', 'Oracle', 2), ('db', 'MySQL', 3),
('db', 'MS SQL Server', 4), ('db', 'MongoDB', 5), ('db', 'Redis', 6);

-- Skills: devops
INSERT INTO skill (category, name, display_order) VALUES
('devops', 'AWS (Solutions Architect)', 1), ('devops', 'Terraform', 2),
('devops', 'Jenkins (CI/CD)', 3), ('devops', 'Docker', 4), ('devops', 'GitHub Actions', 5),
('devops', 'OpenAPI / Swagger', 6), ('devops', 'Postman', 7),
('devops', 'Git / GitHub / Bitbucket', 8), ('devops', 'IDEs: IntelliJ / Eclipse / VS Code', 9),
('devops', 'Observability: Splunk', 10), ('devops', 'Project: Jira', 11),
('devops', 'Server: Apache Tomcat', 12);

-- Experience 1: AEG
INSERT INTO experience (role, company, period, stack, display_order) VALUES
('Software Engineer II', 'AEG, Inc (Employer) | Client: Enterprise Mobility, St. Louis, MO',
 'Jun 2022 – Mar 2026',
 'Spring Boot, Spring Web, Spring Data JPA, Spring Batch, Spring Security, Hibernate, Struts2, JSP, Angular, AWS, Microservices, JUnit, Mockito, Oracle, IntelliJ, Swagger, Splunk, Bitbucket, Jenkins, Jira, Apache Tomcat',
 1);

INSERT INTO experience_bullet (experience_id, text, display_order)
SELECT e.id, b.text, b.ord FROM experience e
CROSS JOIN (VALUES
  (1, 'Modernized legacy SOAP services into secure, standards-based RESTful APIs using Spring Boot, improving interoperability'),
  (2, 'Migrated Spring Batch workloads to AWS S3, reducing on-prem storage costs by 40% and boosting scalability'),
  (3, 'Upgraded Java (8 to 17), Spring Boot (2.5 to 3.1), Spring MVC (4 to 5.3), enhancing reliability and reducing downtime by 30%'),
  (4, 'Developed event-driven microservices with RabbitMQ and OAuth 2.0, enabling real-time processing and improving throughput by 30%'),
  (5, 'Delivered new feature development, adding Vehicle Recall Audit functionality that improved compliance tracking and streamlined vehicle repair workflows in a fleet management application'),
  (6, 'Collaborated with product teams to align sprint goals and user needs, reducing feature delivery time by 15%'),
  (7, 'Resolved production incidents with Splunk log analysis and code fixes, ensuring SLA compliance')
) AS b(ord, text)
WHERE e.display_order = 1;

-- Experience 2: Infotech
INSERT INTO experience (role, company, period, stack, display_order) VALUES
('Software Engineer', 'Infotech Dot Net Systems Ltd – Accra, Ghana',
 'Sep 2018 – Jul 2021',
 'Spring Boot, Spring Web, Spring Data JPA, Spring Security, Kafka, Redis, Microservices, JUnit, Mockito, PostgreSQL, IntelliJ, Swagger, Slack, GitHub, Apache Tomcat',
 2);

INSERT INTO experience_bullet (experience_id, text, display_order)
SELECT e.id, b.text, b.ord FROM experience e
CROSS JOIN (VALUES
  (1, 'Designed and implemented RESTful APIs in Spring Boot to integrate EHR systems with third-party healthcare services'),
  (2, 'Transformed healthcare monolithic applications into Spring Boot microservices with API Gateway and Eureka, improving scalability and system availability for patient services'),
  (3, 'Developed batch jobs with Spring Batch to automate daily data synchronization of lab results and billing information, reducing manual intervention'),
  (4, 'Implemented Kafka messaging to securely process 100K+ daily healthcare transactions, improving reliability and throughput'),
  (5, 'Introduced caching (Spring Cache + Redis) for frequently accessed medical codes and reference data, improving application response time by 40%'),
  (6, 'Acted as primary liaison with healthcare facility management to gather requirements, demonstrate solutions and ensure successful adoption of the Health Information Management System (HIMS)'),
  (7, 'Coordinated with vendors, stakeholders and healthcare administrators to deliver compliant, scalable solutions')
) AS b(ord, text)
WHERE e.display_order = 2;

-- Experience 3: New Crystal
INSERT INTO experience (role, company, period, display_order) VALUES
('IT Manager', 'New Crystal Health Services Ltd – Accra, Ghana', 'Jan 2015 – Aug 2018', 3);

INSERT INTO experience_bullet (experience_id, text, display_order)
SELECT e.id, b.text, b.ord FROM experience e
CROSS JOIN (VALUES
  (1, 'Directed IT operations for a healthcare network, improving system uptime to 99.5% through proactive monitoring'),
  (2, 'Led the development of a telemedicine platform and modernization of health information systems'),
  (3, 'Oversaw vendor management, contract negotiations and performance reviews'),
  (4, 'Directed a team of IT staff, setting goals, conducting evaluations and ensuring project delivery')
) AS b(ord, text)
WHERE e.display_order = 3;

-- Experience 4: St. John
INSERT INTO experience (role, company, period, display_order) VALUES
('IT Manager', 'St. John of God Hospital – Duayaw Nkwanta, Ghana', 'Nov 2009 – Dec 2014', 4);

INSERT INTO experience_bullet (experience_id, text, display_order)
SELECT e.id, b.text, b.ord FROM experience e
CROSS JOIN (VALUES
  (1, 'Digitized health records, cutting data retrieval time by 40%, and led IT team to improve delivery by 35%'),
  (2, 'Oversaw hospital-wide IT strategy, system upgrades, and rollout of health information systems'),
  (3, 'Coordinated with software vendors for implementation and support')
) AS b(ord, text)
WHERE e.display_order = 4;

-- Projects
INSERT INTO project (name, description, display_order) VALUES
('Job Search Management System (MIU)', 'Designed and built an app to track job listings, applications, interviews, and resumes. Implemented REST services and data modeling; documented APIs with Swagger.', 1),
('Schomemoire', 'A modern school yearbook and management platform built with Spring Boot (Java) and Angular, designed to help schools create digital and printable yearbooks with ease.', 2),
('Authentication Service', 'Microservice for user authentication and authorization, built with Spring Boot and integrated with Spring Security and JWT. Supports role-based access control, secure token management, and service discovery through Spring Cloud Eureka. Designed to be a core component in a microservices architecture.', 3),
('Movie Management System (MIU)', 'Full‑stack app to manage movies, actors, and awards with CRUD workflows and Angular UI.', 4),
('Dictionary Application', 'Online dictionary with search, definitions, and responsive UI.', 5),
('Library Management System (MIU)', 'Team project for member/author registrations, book reservations, and fines.', 6);

INSERT INTO project_tag (project_id, tag, display_order)
SELECT p.id, t.tag, t.ord FROM project p
CROSS JOIN (VALUES (1,'Java'),(2,'Spring'),(3,'Spring Data JPA'),(4,'Hibernate'),(5,'Spring Security'),(6,'MySQL'),(7,'Artemis'),(8,'Swagger'),(9,'Git')) AS t(ord,tag)
WHERE p.display_order = 1;

INSERT INTO project_tag (project_id, tag, display_order)
SELECT p.id, t.tag, t.ord FROM project p
CROSS JOIN (VALUES (1,'Java'),(2,'SpringBoot'),(3,'Spring Data JPA'),(4,'Spring Security'),(5,'Spring AI'),(6,'Aws S3'),(7,'AWS CloudFront'),(8,'Hibernate'),(9,'Postgres'),(10,'Swagger'),(11,'GitLab')) AS t(ord,tag)
WHERE p.display_order = 2;

INSERT INTO project_tag (project_id, tag, display_order)
SELECT p.id, t.tag, t.ord FROM project p
CROSS JOIN (VALUES (1,'Java'),(2,'SpringBoot'),(3,'Spring Data JPA'),(4,'Spring Security'),(5,'JWT'),(6,'Spring Cloud'),(7,'Eureka Server'),(8,'Eureka Client'),(9,'Hibernate'),(10,'Postgres'),(11,'Swagger'),(12,'Git')) AS t(ord,tag)
WHERE p.display_order = 3;

INSERT INTO project_tag (project_id, tag, display_order)
SELECT p.id, t.tag, t.ord FROM project p
CROSS JOIN (VALUES (1,'Node.js'),(2,'Express.js'),(3,'Typescript'),(4,'MongoDB'),(5,'Angular')) AS t(ord,tag)
WHERE p.display_order = 4;

INSERT INTO project_tag (project_id, tag, display_order)
SELECT p.id, t.tag, t.ord FROM project p
CROSS JOIN (VALUES (1,'HTML5'),(2,'CSS3'),(3,'Node.js/Express'),(4,'JavaScript'),(5,'jQuery'),(6,'Ajax'),(7,'MySQL')) AS t(ord,tag)
WHERE p.display_order = 5;

INSERT INTO project_tag (project_id, tag, display_order)
SELECT p.id, t.tag, t.ord FROM project p
CROSS JOIN (VALUES (1,'Java'),(2,'J2EE'),(3,'Java Swing'),(4,'Git')) AS t(ord,tag)
WHERE p.display_order = 6;

-- Certifications
INSERT INTO certification (name, badge_url, link, display_order) VALUES
('AWS Certified Solutions Architect – Professional (2023)', 'assets/aws-badge.png',
 'https://www.credly.com/badges/f79ef9cb-a06f-4692-a149-4bf286d37c51/public_url', 1);

-- Education
INSERT INTO education (name, school, years, display_order) VALUES
('Doctor of Business Administration – Applied Computer Science (In Progress)', 'Westcliff University, Dallas, TX', 'Expected 2028', 1),
('Master of Science in Computer Science', 'Maharishi International University (MIU), Fairfield, IA, USA', '2023', 2),
('Bachelor of Education in Computer Science', 'University of Cape Coast, Ghana', '2008', 3);
