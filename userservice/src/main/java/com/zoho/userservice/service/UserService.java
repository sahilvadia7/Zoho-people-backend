package com.zoho.userservice.service;

import com.zoho.userservice.dto.UserRequestDto;
import com.zoho.userservice.exception.EmptyValueException;
import com.zoho.userservice.dto.UserLoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {
     ResponseEntity<?> registerUser(UserRequestDto user) throws EmptyValueException;
    ResponseEntity<?> findByUserId(UUID id);
     ResponseEntity<?> loginUser(UserLoginRequest loginRequest);

    }
