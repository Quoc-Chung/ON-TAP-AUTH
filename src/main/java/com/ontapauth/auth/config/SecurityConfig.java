package com.ontapauth.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()

            // --- USER AREA ---
            .requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("PERM_GET")
            .requestMatchers(HttpMethod.POST, "/api/users/**").hasAuthority("PERM_POST")
            .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("PERM_PUT")
            .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("PERM_DELETE")
            .requestMatchers("/api/users/**").hasRole("USER")  // đặt SAU các method

            // --- NHANVIEN AREA ---
            .requestMatchers(HttpMethod.GET, "/api/nhanvien/**").hasAuthority("PERM_GET")
            .requestMatchers(HttpMethod.POST, "/api/nhanvien/**").hasAuthority("PERM_POST")
            .requestMatchers(HttpMethod.PUT, "/api/nhanvien/**").hasAuthority("PERM_PUT")
            .requestMatchers(HttpMethod.DELETE, "/api/nhanvien/**").hasAuthority("PERM_DELETE")
            .requestMatchers("/api/nhanvien/**").hasRole("NHANVIEN")

            // --- ADMIN AREA ---
            .requestMatchers(HttpMethod.GET, "/api/admin/**").hasAuthority("PERM_GET")
            .requestMatchers(HttpMethod.POST, "/api/admin/**").hasAuthority("PERM_POST")
            .requestMatchers(HttpMethod.PUT, "/api/admin/**").hasAuthority("PERM_PUT")
            .requestMatchers(HttpMethod.DELETE, "/api/admin/**").hasAuthority("PERM_DELETE")
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            // Bất kỳ request nào khác đều cần xác thực
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
