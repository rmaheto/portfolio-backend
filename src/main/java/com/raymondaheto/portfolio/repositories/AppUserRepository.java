package com.raymondaheto.portfolio.repositories;

import com.raymondaheto.portfolio.entity.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppUserRepository
    extends JpaRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser> {
  Optional<AppUser> findByUsername(String username);
}
