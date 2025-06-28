package com.ontapauth.auth.service.impl;

import com.ontapauth.auth.dto.LoginRequest;
import com.ontapauth.auth.dto.LoginResponse;
import com.ontapauth.auth.entity.Role;
import com.ontapauth.auth.entity.User;
import com.ontapauth.auth.repository.UserRepository;
import com.ontapauth.auth.service.UserService;
import com.ontapauth.auth.utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public LoginResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));

    String token = jwtUtils.generateToken(user);

    return new LoginResponse(
        token,
        true,
        jwtUtils.getIssuedAt(token),
        jwtUtils.getExpiration(token),
        user.getUsername(),
        jwtUtils.getRoles(token),
        jwtUtils.getPermissions(token)
    );
  }
}
