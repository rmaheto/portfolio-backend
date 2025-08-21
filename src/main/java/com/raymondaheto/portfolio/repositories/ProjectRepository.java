package com.raymondaheto.portfolio.repositories;

import com.raymondaheto.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
