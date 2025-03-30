package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helper.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.util.MapperObjectUtil;
import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;
import com.iDevWorks.HealthSys_API.domain.services.IUserService;
import com.iDevWorks.HealthSys_API.web.dtos.UserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.iDevWorks.HealthSys_API.common.api.ApiPath.PATH_V1;
import static com.iDevWorks.HealthSys_API.common.schema.Schema.BEARER_SCHEMA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = PATH_V1 + "/user")
@SecurityRequirement(name = BEARER_SCHEMA)
public class UserController {
    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getUsers() {
        Map<String, Object> response = new HashMap<>();
        List<UserEntity> users = service.findAll();
        if (!users.isEmpty()) {
            response.put(ResponseHelper.DATA_KEY, users);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.NoRegisteredItem("users"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<UserEntity> userEntity = service.findById(id);
        if (userEntity.isPresent()) {
            response.put(ResponseHelper.DATA_KEY, userEntity);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("User"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveUser(@Valid @RequestBody UserDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserEntity userEntity = service.save(MapperObjectUtil.toUserEntity(0, dto));
            response.put(ResponseHelper.DATA_KEY, userEntity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.MESSAGE_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable long id, @Valid @RequestBody UserDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserEntity userEntity = service.update(MapperObjectUtil.toUserEntity(id, dto));
            response.put(ResponseHelper.DATA_KEY, userEntity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.MESSAGE_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
