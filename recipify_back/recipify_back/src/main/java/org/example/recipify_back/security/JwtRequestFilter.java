package org.example.recipify_back.security;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j  // Cette annotation ajoute un logger (via Lombok)
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String path = request.getServletPath();
        final String authorizationHeader = request.getHeader("Authorization");

        log.info("Request path: {}", path);

        // Skip JWT filter for specific paths
        if (path.startsWith("/register") || path.startsWith("/authenticate")) {
            log.info("Skipping filter for path: {}", path);
            chain.doFilter(request, response);
            return;
        }

        // Extract JWT token if present
        String jwt = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            log.debug("JWT Token: {}", jwt);

            try {
                username = jwtUtil.extractUsername(jwt);  // Attempt to extract username
            } catch (Exception e) {
                log.error("Failed to extract username from JWT. JWT might be invalid. Token: {}", jwt, e);
                chain.doFilter(request, response);  // Proceed with the chain if token is invalid
                return;
            }
        } else {
            log.warn("JWT Token missing or does not start with Bearer. Request path: {}", path);
            chain.doFilter(request, response);  // Proceed without JWT
            return;
        }

        // Authenticate the user if JWT is valid and not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            log.info("User found: {}", username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                log.info("JWT is valid for user: {}", username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                log.warn("Invalid JWT for user: {}", username);
            }
        }

        // Proceed with the rest of the filter chain
        chain.doFilter(request, response);
    }

}
