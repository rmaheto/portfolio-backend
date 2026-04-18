package com.raymondaheto.portfolio.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorService {

  private record CodeEntry(String code, Instant expiresAt) {}

  private final ConcurrentHashMap<String, CodeEntry> store = new ConcurrentHashMap<>();
  private final SecureRandom random = new SecureRandom();

  public String generateAndStore(final String username) {
    final String code = String.format("%06d", random.nextInt(1_000_000));
    store.put(username, new CodeEntry(code, Instant.now().plusSeconds(600)));
    return code;
  }

  public boolean verify(final String username, final String code) {
    final CodeEntry entry = store.get(username);
    if (entry == null || Instant.now().isAfter(entry.expiresAt())) {
      store.remove(username);
      return false;
    }
    if (!entry.code().equals(code)) return false;
    store.remove(username);
    return true;
  }
}
