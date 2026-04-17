package com.raymondaheto.portfolio.repository;

import com.raymondaheto.portfolio.entity.Education;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
  List<Education> findAllByOrderByDisplayOrderAsc();
}
