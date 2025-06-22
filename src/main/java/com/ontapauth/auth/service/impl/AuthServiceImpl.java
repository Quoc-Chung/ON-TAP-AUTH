package com.ontapauth.auth.service.impl;

import com.ontapauth.auth.dto.JwtResponse;
import com.ontapauth.auth.dto.LoginRequest;
import com.ontapauth.auth.entity.UserEntity;
import com.ontapauth.auth.repository.UserRepository;
import com.ontapauth.auth.service.AuthService;
import com.ontapauth.auth.utils.JwtTokenUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final JwtTokenUtil jwtTokenUtil;

  @Override
  public JwtResponse authenticateAndGenerateToken(LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(),
              loginRequest.getPassword()
          )
      );
      UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));

      Map<String, Object> claims = new HashMap<>();
      claims.put("role", user.getRole().getCode());

      String token = jwtTokenUtil.generateToken(user.getUsername(), claims);
      return new JwtResponse(token);
    } catch (BadCredentialsException | UsernameNotFoundException ex) {
      throw new RuntimeException("Đăng nhập thất bại: " + ex.getMessage());
    } catch (Exception e) {
      throw new RuntimeException("Lỗi hệ thống khi đăng nhập: " + e.getMessage());
    }
  }

}
