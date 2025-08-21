package com.raymondaheto.portfolio.service.impl;

import com.raymondaheto.portfolio.dto.LinksDto;
import com.raymondaheto.portfolio.dto.LinksUpdateDto;
import com.raymondaheto.portfolio.entity.Links;
import com.raymondaheto.portfolio.entity.LinksDisplay;
import com.raymondaheto.portfolio.entity.Profile;
import com.raymondaheto.portfolio.repositories.ProfileRepository;
import com.raymondaheto.portfolio.service.LinksService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinksServiceImpl implements LinksService {

  private final ProfileRepository profileRepo;

  @Override
  @Transactional(readOnly = true)
  public LinksDto get() {
    try {
      final Optional<Profile> opt = findProfile();
      if (opt.isEmpty() || opt.get().getLinks() == null) {
        return new LinksDto();
      }
      return toLinksDto(opt.get().getLinks());
    } catch (final RuntimeException ex) {
      log.error("Failed to read Links", ex);
      throw ex;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public LinksUpdateDto getBoth() {
    try {
      final Optional<Profile> opt = findProfile();
      if (opt.isEmpty()) {
        return LinksUpdateDto.builder()
            .links(new LinksDto())
            .linksDisplay(new LinksDto())
            .contactIntro(null)
            .footerText(null)
            .build();
      }
      final Profile p = opt.get();
      return LinksUpdateDto.builder()
          .links(p.getLinks() != null ? toLinksDto(p.getLinks()) : new LinksDto())
          .linksDisplay(
              p.getLinksDisplay() != null ? toLinksDisplayDto(p.getLinksDisplay()) : new LinksDto())
          .contactIntro(p.getContactIntro())
          .footerText(p.getFooterText())
          .build();
    } catch (final RuntimeException ex) {
      log.error("Failed to read Links + LinksDisplay + contact/footer", ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public LinksUpdateDto update(final LinksUpdateDto dto) {
    try {
      final Profile profile = ensureProfile();

      // Upsert Links + Display
      profile.setLinks(toLinks(dto.getLinks(), profile.getLinks()));
      profile.setLinksDisplay(toLinksDisplay(dto.getLinksDisplay(), profile.getLinksDisplay()));

      // contactIntro + footerText live on Profile
      profile.setContactIntro(dto.getContactIntro());
      profile.setFooterText(dto.getFooterText());

      final Profile saved = profileRepo.save(profile);

      return LinksUpdateDto.builder()
          .links(saved.getLinks() != null ? toLinksDto(saved.getLinks()) : new LinksDto())
          .linksDisplay(
              saved.getLinksDisplay() != null
                  ? toLinksDisplayDto(saved.getLinksDisplay())
                  : new LinksDto())
          .contactIntro(saved.getContactIntro())
          .footerText(saved.getFooterText())
          .build();
    } catch (final RuntimeException ex) {
      log.error("Failed to update Links/Display/contactIntro/footerText", ex);
      throw ex;
    }
  }

  private Optional<Profile> findProfile() {
    return profileRepo.findAll().stream().findFirst();
  }

  private Profile ensureProfile() {
    return findProfile().orElseGet(() -> profileRepo.save(new Profile()));
  }

  private static Links toLinks(final LinksDto d, final Links existing) {
    if (d == null) {
      return null;
    }
    final Links l = existing == null ? new Links() : existing;
    l.setEmail(d.getEmail());
    l.setPhone(d.getPhone());
    l.setLinkedin(d.getLinkedin());
    l.setGithub(d.getGithub());
    l.setFacebook(d.getFacebook());
    l.setX(d.getX());
    l.setInstagram(d.getInstagram());
    return l;
  }

  private static LinksDisplay toLinksDisplay(final LinksDto d, final LinksDisplay existing) {
    if (d == null) {
      return null;
    }
    final LinksDisplay ld = existing == null ? new LinksDisplay() : existing;
    ld.setLinkedin(d.getLinkedin());
    ld.setGithub(d.getGithub());
    ld.setFacebook(d.getFacebook());
    ld.setX(d.getX());
    ld.setInstagram(d.getInstagram());
    return ld;
  }

  private static LinksDto toLinksDto(final Links l) {
    if (l == null) {
      return new LinksDto();
    }
    return LinksDto.builder()
        .email(l.getEmail())
        .phone(l.getPhone())
        .linkedin(l.getLinkedin())
        .github(l.getGithub())
        .facebook(l.getFacebook())
        .x(l.getX())
        .instagram(l.getInstagram())
        .build();
  }

  private static LinksDto toLinksDisplayDto(final LinksDisplay ld) {
    if (ld == null) {
      return new LinksDto();
    }
    return LinksDto.builder()
        .linkedin(ld.getLinkedin())
        .github(ld.getGithub())
        .facebook(ld.getFacebook())
        .x(ld.getX())
        .instagram(ld.getInstagram())
        .build();
  }
}
