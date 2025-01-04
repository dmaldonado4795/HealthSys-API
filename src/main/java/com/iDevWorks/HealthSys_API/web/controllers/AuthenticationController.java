package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;
import com.iDevWorks.HealthSys_API.domain.services.IJwtService;
import com.iDevWorks.HealthSys_API.domain.services.IUserService;
import com.iDevWorks.HealthSys_API.web.dtos.AuthenticationDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "auth")
public class AuthenticationController {
    private final IUserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(
            IUserService userService,
            IJwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping(path = "login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody AuthenticationDto dto) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<UserEntity> userEntity = userService.findByUsername(dto.getUsername());
            userEntity.ifPresent(entity -> {
                if (entity.isActive() && passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    dto.getUsername(),
                                    dto.getPassword()
                            )
                    );

                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String jwt = jwtService.generateToken(userDetails);
                    response.put("Token", jwt);
                } else {
                    response.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound(entity.getUsername()));
                }
            });
            return ResponseEntity.status(userEntity.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
