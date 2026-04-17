package com.raymondaheto.portfolio.repository;

import com.raymondaheto.portfolio.entity.Experience;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
  List<Experience> findAllByOrderByDisplayOrderAsc();
}
