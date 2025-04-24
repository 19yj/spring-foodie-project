package com.project.foodie.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    public JWTAuthFilter(JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  // Continue without applying filter if no token
            return;
        }

        String token = authHeader.substring(7);  // Extract the token part from "Bearer <token>"

        try {
            // Ensure token is valid before creating the authentication token
            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.extractUsername(token);  // Extract the username from the token
                List<String> roles = jwtUtils.getRolesFromToken(token);  // Extract roles from the token

                // Convert roles to authorities (required for Spring Security)
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)  // Don't add ROLE_ prefix here
                        .collect(Collectors.toList());

                // Create authentication object with username, null credentials (JWT handles authentication), and authorities
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                // Set authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);


                logger.info("Token validation result: " + jwtUtils.validateToken(token));
                logger.info("Username from token: " + username);
                logger.info("Roles from token: " + roles);
                logger.info("Authorities created: " + authorities);
                

            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication", e);
        }

        filterChain.doFilter(request, response);  // Continue with the filter chain
    }

    @Override
    public void destroy() {}
}
