package com.raymondaheto.portfolio.model;

import com.raymondaheto.portfolio.dto.CustomUserDetailsDto;
import com.raymondaheto.portfolio.entity.AppUser;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  @Getter private final Long id;
  @Getter private final String username;
  @Getter private final String firstName;
  @Getter private final String lastName;
  @Getter private final String password;
  @Getter private final Boolean mustChangePassword;

  public CustomUserDetails(final AppUser user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.password = user.getPassword();
    this.mustChangePassword = user.isMustChangePassword();
  }

  public CustomUserDetails(final CustomUserDetailsDto dto) {
    this.id = dto.getId();
    this.username = dto.getUsername();
    this.firstName = dto.getFirstName();
    this.lastName = dto.getLastName();
    this.password = dto.getPassword();
    this.mustChangePassword = dto.getMustChangePassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
