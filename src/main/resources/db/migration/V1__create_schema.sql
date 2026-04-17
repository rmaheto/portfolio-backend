CREATE TABLE portfolio_profile (
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255),
    title            VARCHAR(255),
    blurb            TEXT,
    avatar_url       VARCHAR(500),
    about_me         TEXT,
    resume_url       VARCHAR(500),
    location         VARCHAR(255),
    contact_intro    TEXT,
    footer_text      VARCHAR(255),
    email            VARCHAR(255),
    phone            VARCHAR(50),
    linkedin         VARCHAR(500),
    linkedin_display VARCHAR(255),
    github           VARCHAR(500),
    github_display   VARCHAR(255),
    facebook         VARCHAR(500),
    facebook_display VARCHAR(255),
    x_handle         VARCHAR(500),
    x_display        VARCHAR(255),
    instagram        VARCHAR(500),
    instagram_display VARCHAR(255)
);

CREATE TABLE skill (
    id            BIGSERIAL PRIMARY KEY,
    category      VARCHAR(50)  NOT NULL,
    name          VARCHAR(255) NOT NULL,
    display_order INT          NOT NULL DEFAULT 0
);

CREATE TABLE experience (
    id            BIGSERIAL PRIMARY KEY,
    role          VARCHAR(255),
    company       VARCHAR(255),
    period        VARCHAR(100),
    stack         TEXT,
    display_order INT NOT NULL DEFAULT 0
);

CREATE TABLE experience_bullet (
    id            BIGSERIAL PRIMARY KEY,
    experience_id BIGINT NOT NULL REFERENCES experience (id) ON DELETE CASCADE,
    text          TEXT,
    display_order INT NOT NULL DEFAULT 0
);

CREATE TABLE project (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255),
    description   TEXT,
    display_order INT NOT NULL DEFAULT 0
);

CREATE TABLE project_tag (
    id            BIGSERIAL PRIMARY KEY,
    project_id    BIGINT NOT NULL REFERENCES project (id) ON DELETE CASCADE,
    tag           VARCHAR(100),
    display_order INT NOT NULL DEFAULT 0
);

CREATE TABLE certification (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(500),
    badge_url     VARCHAR(500),
    link          VARCHAR(500),
    display_order INT NOT NULL DEFAULT 0
);

CREATE TABLE education (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(500),
    school        VARCHAR(500),
    years         VARCHAR(100),
    display_order INT NOT NULL DEFAULT 0
);

CREATE TABLE admin_user (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);
