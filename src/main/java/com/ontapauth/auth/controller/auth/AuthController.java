package com.ontapauth.auth.controller.auth;

import com.ontapauth.auth.dto.JwtResponse;
import com.ontapauth.auth.dto.LoginRequest;
import com.ontapauth.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
         JwtResponse tokenResponse =  authService.authenticateAndGenerateToken(loginRequest);
         return ResponseEntity.ok(tokenResponse);
    }

}
