package com.pict.fraudDetect.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private com.pict.fraudDetect.security.JwtProvider jwtProvider;

    public JwtFilter(com.pict.fraudDetect.security.JwtProvider jwtProvider) {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get JWT from Authorization header
        String token = request.getHeader("Authorization");

        if (token != null && jwtProvider.validateToken(token)) {

            // Extract email from token (JwtProvider handles "Bearer " automatically)
            String email = jwtProvider.getEmailFromJwtToken(token);

            // For now, no roles, just a basic user
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Create Authentication object
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            // Set it in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
