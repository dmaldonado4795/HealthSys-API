package com.iDevWorks.HealthSys_API.infrastructure.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iDevWorks.HealthSys_API.domain.services.IJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService iJwtService;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(IJwtService iJwtService, UserDetailsService userDetailsService) {
        this.iJwtService = iJwtService;
        this.objectMapper = new ObjectMapper();
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<String> token = extractTokenFromRequest(request);
                token.ifPresent(jwt -> processToken(jwt, response));
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleFilterException(e, response);
        }
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }

        return Optional.empty();
    }

    private void processToken(String jwt, HttpServletResponse response) {
        try {
            final String username = iJwtService.extractUsername(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (username != null && username.equals(userDetails.getUsername()) && iJwtService.isTokenValid(jwt)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            handleExpiredToken(response);
        } catch (JwtException e) {
            handleInvalidToken(response);
        }
    }

    private void handleFilterException(Exception e, HttpServletResponse response) {
        SecurityContextHolder.clearContext();

        if (e instanceof ExpiredJwtException) {
            handleExpiredToken(response);
        } else if (e instanceof JwtException) {
            handleInvalidToken(response);
        } else {
            logger.error("Security filter error: ", e);
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal security error");
        }
    }

    private void handleExpiredToken(HttpServletResponse response) {
        logger.warn("Expired JWT token detected");
        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token has expired");
    }

    private void handleInvalidToken(HttpServletResponse response) {
        logger.warn("Invalid JWT token detected");
        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token");
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) {
        try {
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getOutputStream(), Map.of("error", status.getReasonPhrase(), "message", message, "status", status.value()));
        } catch (IOException e) {
            logger.error("Error sending error response", e);
        }
    }
}
