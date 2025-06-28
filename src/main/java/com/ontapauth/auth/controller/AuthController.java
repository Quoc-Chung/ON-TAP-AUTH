package com.ontapauth.auth.controller;

import com.ontapauth.auth.dto.LoginRequest;
import com.ontapauth.auth.dto.LoginResponse;
import com.ontapauth.auth.dto.RegisterRequest;
import com.ontapauth.auth.entity.User;
import com.ontapauth.auth.utils.response.GeneralResponse;
import com.ontapauth.auth.utils.response.ResponseFactory;
import com.ontapauth.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<GeneralResponse<User>> register(@RequestBody RegisterRequest request) {
    return ResponseFactory.success(userService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<GeneralResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
    return ResponseFactory.success(userService.login(request));
  }
}

