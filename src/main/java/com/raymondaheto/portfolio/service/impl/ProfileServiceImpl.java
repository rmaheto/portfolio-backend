package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.ProfileDto;
import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.mapper.ProfileMapper;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.service.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileQueryService {

  private final ProfileRepository profileRepo;

  @Override
  @Transactional(readOnly = true)
  public ProfileDto get() {
    try {
      final Profile p =
          profileRepo
              .findFirstByOrderByIdAsc()
              .orElseThrow(() -> new IllegalStateException("Profile not set up yet"));
      return ProfileMapper.toDto(p);
    } catch (final RuntimeException ex) {
      log.error("Failed to aggregate Profile", ex);
      throw ex;
    }
  }
}
