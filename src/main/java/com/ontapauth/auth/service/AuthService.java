package com.ontapauth.auth.service;

import com.ontapauth.auth.dto.JwtResponse;
import com.ontapauth.auth.dto.LoginRequest;

public interface AuthService {
     JwtResponse authenticateAndGenerateToken(LoginRequest loginRequest);
}
