package com.athosfs.todosimple.security;



import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  private JWTUtil jwtUtil;
  private UserDetailsService userDetailsService;

  public JWTAuthorizationFilter(
      AuthenticationManager authenticationManager,
      JWTUtil jwtUtil,
      UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    String header = request.getHeader("Authorization");
    if (Objects.nonNull(header) && header.startsWith("Bearer ")) {
      UsernamePasswordAuthenticationToken autheticationUser =
          getAuthentication(header.substring(7));
      if (Objects.nonNull(autheticationUser)) {
        SecurityContextHolder.getContext().setAuthentication(autheticationUser);
      }
    }
    filterChain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    if (this.jwtUtil.isValidToken(token)) {
      String username = jwtUtil.getUsername(token);
      UserDetails user = this.userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken autheticationUser =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      return autheticationUser;
    }
    return null;
  }
}
