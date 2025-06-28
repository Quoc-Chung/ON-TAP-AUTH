package com.ontapauth.auth.utils;

import com.ontapauth.auth.entity.Role;
import com.ontapauth.auth.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtExpirationMs;

  public String generateToken(User user) {
    List<String> roles = user.getRoles().stream()
        .map(Role::getName)
        .map(roleName -> "ROLE_" + roleName)
        .toList();

    List<String> permissions = user.getRoles().stream()
        .flatMap(role -> role.getPermissions().stream())
        .map(p -> "PERM_" + p.getName())
        .distinct()
        .toList();

    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim("roles", roles)
        .claim("permissions", permissions)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public List<String> getRoles(String token) {
    return getClaimsFromToken(token).get("roles", List.class);
  }

  public List<String> getPermissions(String token) {
    return getClaimsFromToken(token).get("permissions", List.class);
  }


  public Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean isTokenValid(String token) {
    try {
      getClaimsFromToken(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  public String getUsernameFromJwt(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  public long getIssuedAt(String token) {
    return getClaimsFromToken(token).getIssuedAt().getTime();
  }

  public long getExpiration(String token) {
    return getClaimsFromToken(token).getExpiration().getTime();
  }
}
