package com.ontapauth.auth.service;

import com.ontapauth.auth.dto.LoginRequest;
import com.ontapauth.auth.dto.LoginResponse;

public interface UserService {
  LoginResponse login(LoginRequest request);

}
