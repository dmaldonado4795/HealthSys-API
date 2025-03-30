package com.iDevWorks.HealthSys_API.infrastructure.filters;

import com.iDevWorks.HealthSys_API.domain.services.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Filter for handling JWT authentication
 * Extends OncePerRequestFilter to ensure a single execution per request dispatch
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService iJwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor for JwtAuthenticationFilter
     * @param iJwtService Service for JWT operations
     * @param userDetailsService Service to load user-specific data
     */
    public JwtAuthenticationFilter(IJwtService iJwtService, UserDetailsService userDetailsService) {
        this.iJwtService = iJwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Internal filter method that handles the JWT authentication process
     * @param request The HTTP request
     * @param response The HTTP response
     * @param filterChain The filter chain
     * @throws ServletException If a servlet error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (servletPath.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Optional<String> token = extractTokenFromRequest(request);

            if (token.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                String jwt = token.get();
                final String username = iJwtService.extractUsername(jwt);

                if (username != null && iJwtService.isTokenValid(jwt)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            logger.warn("JWT exception detected: ", ex);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the request's Authorization header
     * @param request The HTTP request
     * @return Optional containing the JWT token if present and valid, empty otherwise
     */
    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }

        return Optional.empty();
    }
}
