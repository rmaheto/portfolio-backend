package com.raymondaheto.portfolio.repositories;

import com.raymondaheto.portfolio.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  @EntityGraph(
      attributePaths = {
        "links",
        "linksDisplay",
        "projects",
        "projects.tags",
        "experiences",
        "experiences.bullets",
        "experiences.stack",
        "certifications",
        "education",
        "skills"
      })
  Optional<Profile> findFirstByOrderByIdAsc();
}
