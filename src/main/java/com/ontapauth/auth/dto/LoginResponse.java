package com.ontapauth.auth.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  private String token;
  private boolean valid;
  private long issuedAt;
  private long expiresAt;
  private String username;
  private List<String> roles;
}
