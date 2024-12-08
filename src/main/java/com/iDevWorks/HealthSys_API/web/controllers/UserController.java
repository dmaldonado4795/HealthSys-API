package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.utils.MapperObjectUtil;
import com.iDevWorks.HealthSys_API.domain.entities.UserEntity;
import com.iDevWorks.HealthSys_API.domain.services.IUserService;
import com.iDevWorks.HealthSys_API.web.dtos.UserDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "user")
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getUsers() {
        Map<String, Object> resp = new HashMap<>();
        List<UserEntity> users = service.findAll();
        if (!users.isEmpty()) {
            resp.put(ResponseHelper.DATA_KEY, users);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("users"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable long id) {
        Map<String, Object> resp = new HashMap<>();
        Optional<UserEntity> userEntity = service.findById(id);
        if (userEntity.isPresent()) {
            resp.put(ResponseHelper.DATA_KEY, userEntity);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("User"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveUser(@Valid @RequestBody UserDto dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            UserEntity userEntity = service.save(MapperObjectUtil.toUserEntity(0, dto));
            resp.put(ResponseHelper.DATA_KEY, userEntity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable long id, @Valid @RequestBody UserDto dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            UserEntity userEntity = service.update(MapperObjectUtil.toUserEntity(id, dto));
            resp.put(ResponseHelper.DATA_KEY, userEntity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

}
