package com.raymondaheto.portfolio.repository;

import com.raymondaheto.portfolio.entity.Certification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
  List<Certification> findAllByOrderByDisplayOrderAsc();
}
