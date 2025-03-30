package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helper.ResponseHelper;
import com.iDevWorks.HealthSys_API.domain.entities.RefreshTokenEntity;
import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;
import com.iDevWorks.HealthSys_API.domain.services.IJwtService;
import com.iDevWorks.HealthSys_API.domain.services.IRefreshTokenService;
import com.iDevWorks.HealthSys_API.domain.services.IUserService;
import com.iDevWorks.HealthSys_API.web.dtos.AuthenticationDto;
import com.iDevWorks.HealthSys_API.web.dtos.RefreshTokenDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.iDevWorks.HealthSys_API.common.api.ApiPath.PATH_V1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = PATH_V1 + "/auth")
public class AuthenticationController {
    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final IRefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationController(
            IUserService userService,
            IJwtService jwtService,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            IRefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping(path = "login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthenticationDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserEntity> userEntity = userService.findByUsername(dto.getUsername());
            if (userEntity.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, "Invalid username or password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            UserEntity entity = userEntity.get();
            if (!entity.isActive() || !passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
                response.put(ResponseHelper.MESSAGE_KEY, "Invalid username or password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());

            String jwt = jwtService.generateToken(userDetails);
            RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefresthToken(entity);

            response.put("token", jwt);
            response.put("refreshToken", refreshTokenEntity.getToken());
            response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(path = "refresh-token")
    public ResponseEntity<Map<String, Object>> refreshToken(@Valid @RequestBody RefreshTokenDto dto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<RefreshTokenEntity> optionalRefreshToken = refreshTokenService.findByToken(dto.getRefreshToken());

            if (optionalRefreshToken.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, "Refresh token not found. Please login again.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            RefreshTokenEntity refreshToken = optionalRefreshToken.get();

            if (refreshTokenService.isExpired(refreshToken)) {
                refreshTokenService.deleteByUserId(refreshToken.getUser().getUserId());
                response.put(ResponseHelper.MESSAGE_KEY, "Refresh token expired. Please login again.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getUser().getUsername());
            String jwt = jwtService.generateToken(userDetails);
            RefreshTokenEntity newRefreshToken = refreshTokenService.createRefresthToken(refreshToken.getUser());

            response.put("token", jwt);
            response.put("refreshToken", newRefreshToken.getToken());
            response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
