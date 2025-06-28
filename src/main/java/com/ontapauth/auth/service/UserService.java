package com.ontapauth.auth.service;

import com.ontapauth.auth.dto.LoginRequest;
import com.ontapauth.auth.dto.LoginResponse;
import com.ontapauth.auth.dto.RegisterRequest;
import com.ontapauth.auth.entity.User;

public interface UserService {
  LoginResponse login(LoginRequest request);

  User register(RegisterRequest request);

}
