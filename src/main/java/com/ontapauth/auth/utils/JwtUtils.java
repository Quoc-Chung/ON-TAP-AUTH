package com.ontapauth.auth.utils;

import com.ontapauth.auth.entity.Role;
import com.ontapauth.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtExpirationMs;

  public String generateToken(User user) {
    List<String> roles = user.getRoles()
        .stream()
        .map(Role::getName)
        .collect(Collectors.toList());

    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim("email", user.getEmail())
        .claim("roles", roles)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
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
  public String getEmailFromJwt(String token) {
    return (String) getClaimsFromToken(token).get("email");
  }


  public List<String> getRolesFromJwt(String token) {
    return (List<String>) getClaimsFromToken(token).get("roles");
  }

  public long getIssuedAt(String token) {
    return getClaimsFromToken(token).getIssuedAt().getTime();
  }

  public long getExpiration(String token) {
    return getClaimsFromToken(token).getExpiration().getTime();
  }
}
