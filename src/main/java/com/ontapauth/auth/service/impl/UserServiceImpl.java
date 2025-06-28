package com.ontapauth.auth.service.impl;

import com.ontapauth.auth.dto.LoginRequest;
import com.ontapauth.auth.dto.LoginResponse;
import com.ontapauth.auth.dto.RegisterRequest;
import com.ontapauth.auth.entity.Permission;
import com.ontapauth.auth.entity.Role;
import com.ontapauth.auth.entity.User;
import com.ontapauth.auth.exception.ResErrorCode;
import com.ontapauth.auth.exception.ResException;
import com.ontapauth.auth.repository.PermissionRepository;
import com.ontapauth.auth.repository.RoleRepository;
import com.ontapauth.auth.repository.UserRepository;
import com.ontapauth.auth.service.UserService;
import com.ontapauth.auth.utils.JwtUtils;
import com.ontapauth.auth.utils.contains.AuthContains;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final AuthenticationManager authenticationManager;
  private  final UserRepository userRepository;
  private final JwtUtils jwtUtils;
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Override
  public LoginResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new ResException(ResErrorCode.NOT_FOUND_USER));

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

  @Override
  public User register(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw  new ResException(ResErrorCode.EXIST_USERNAME);
    }


    /* Kiểm tra hợp lệ */
    if (!AuthContains.VALID_ROLES.containsAll(request.getRole())) {
      throw  new ResException(ResErrorCode.INVALID_ROLE);
    }

    if (!AuthContains.VALID_PERMISSIONS.containsAll(request.getPermission())) {
      throw  new ResException(ResErrorCode.INVALID_PERMISSION);
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));


    Set<Role> roles = roleRepository.findByNameIn(request.getRole());

    Set<Permission> permissions = permissionRepository.findByNameIn(request.getPermission());


    for (Role role : roles) {
      role.getPermissions().addAll(permissions);
    }
    user.setRoles(roles);

    return  userRepository.save(user);
  }
}


