package com.ontapauth.auth.security;

import com.ontapauth.auth.service.CustomUserDetailsService;
import com.ontapauth.auth.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtTokenUtil jwtTokenUtil;
  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String requestURI = request.getRequestURI();

    if (requestURI.startsWith("/api/auth/")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String header = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    if (header != null && header.startsWith("Bearer ")) {
      jwt = header.substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwt);
      } catch (Exception e) {
        logger.error("Error getting username from token", e);
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

      if (jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}