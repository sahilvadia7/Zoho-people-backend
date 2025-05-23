package com.zoho.userservice.controller;


import com.zoho.userservice.dto.UserRequestDto;
import com.zoho.userservice.exception.EmptyValueException;
import com.zoho.userservice.dto.UserLoginRequest;
import com.zoho.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User-API", description = "CRUD for all the type of users(admin, hr, employee).")
public class UserController {

    private final UserService userService;

    @PostMapping("/reg")
    @Operation(description = "Add User")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto user) throws EmptyValueException {
        return userService.registerUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable UUID id){
        return userService.findByUserId(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }


}
