package com.project.foodie.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter
public class JWTAuthFilter extends OncePerRequestFilter {
    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");  // send token in the Authorization header

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // remove "Bearer" prefix

            try {
                Long userId = Long.valueOf(jwtUtils.extractUserId(token));
                // set user id in the request for further use (authentication)
                request.setAttribute("userId", userId);
            } catch (Exception e) {
                // handle invalid or expired token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);  // pass request along the filter chain
    }

    @Override
    public void destroy() {}
}
