package com.raymondaheto.portfolio.repositories;


import com.raymondaheto.portfolio.entity.AppConfig;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppConfigRepository extends JpaRepository<AppConfig, String> {
  Optional<AppConfig> findByKey(String key);
}
