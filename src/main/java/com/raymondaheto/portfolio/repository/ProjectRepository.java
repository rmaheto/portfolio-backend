package com.raymondaheto.portfolio.repository;

import com.raymondaheto.portfolio.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findAllByOrderByDisplayOrderAsc();
}
