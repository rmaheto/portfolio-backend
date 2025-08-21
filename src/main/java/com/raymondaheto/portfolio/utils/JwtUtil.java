package com.raymondaheto.portfolio.utils;

import com.raymondaheto.portfolio.dto.UserResponseDto;
import com.raymondaheto.portfolio.model.CustomUserDetails;
import com.raymondaheto.portfolio.model.JwtTokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(final JwtTokenData data) {
    return Jwts.builder()
        .setSubject(data.getUsername())
        .claim("firstName", data.getFirstName())
        .claim("lastName", data.getLastName())
        .claim("roles", data.getRoles())
        .claim("schoolId", data.getSchoolId())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public boolean validateToken(final String token, final String username) {
    final String tokenUsername = extractUsername(token);
    return (username.equals(tokenUsername) && !isTokenExpired(token));
  }

  public String extractUsername(final String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Set<String> extractRoles(final String token) {
    final Claims claims = extractAllClaims(token);
    return new HashSet<>(claims.get("roles", List.class)); // Extract roles
  }

  public Date extractExpiration(final String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public JwtTokenData buildTokenData(final CustomUserDetails userDetails) {

    return JwtTokenData.builder()
        .username(userDetails.getUsername())
        .firstName(userDetails.getFirstName())
        .lastName(userDetails.getLastName())
        .build();
  }

  public JwtTokenData buildTokenData(final UserResponseDto user) {
    return JwtTokenData.builder()
        .username(user.getUsername())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .build();
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(final String token) {
    return extractExpiration(token).before(new Date());
  }
}
