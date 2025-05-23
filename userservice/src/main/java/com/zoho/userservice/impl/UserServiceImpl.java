package com.zoho.userservice.impl;

import com.zoho.userservice.dto.UserLoginResponse;
import com.zoho.userservice.dto.UserRequestDto;
import com.zoho.userservice.enums.STATUS;
import com.zoho.userservice.exception.EmptyValueException;
import com.zoho.userservice.dto.UserLoginRequest;
import com.zoho.userservice.feign.OrgClient;
import com.zoho.userservice.model.UserLogin;
import com.zoho.userservice.model.Users;
import com.zoho.userservice.repository.UserLoginRepository;
import com.zoho.userservice.repository.UserRepository;
import com.zoho.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static com.zoho.userservice.constrain.GlobalConstrain.USER_ALREADY_REGISTER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserLoginRepository userLoginRepo;
    private final OrgClient orgClient;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<?> registerUser(UserRequestDto user) throws EmptyValueException {
        if(user.getUserName().trim().isEmpty() || user.getPassword().trim().isEmpty()){
            throw new EmptyValueException("Value cannot be null or empty.");
        }else{
            Users existingUser = userRepo.findByUserName(user.getUserName());

            if(existingUser==null){
                String encodePass = passwordEncoder().encode(user.getPassword());
                user.setPassword(encodePass);
                user.setStatus(STATUS.PENDING_APPROVAL);

                Users askUser = Users.builder()
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .status(user.getStatus())
                        .phoneNumber(user.getPhoneNumber())
                        .password(user.getPassword())
                        .build();

                return new ResponseEntity<>(userRepo.save(askUser),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(USER_ALREADY_REGISTER,HttpStatus.CONFLICT);
    }


    @Override
    public ResponseEntity<?> findByUserId(UUID id) {
        return new ResponseEntity<>(userRepo.findById(id).orElse(null),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> loginUser(UserLoginRequest loginRequest) {
        ResponseEntity<Boolean> response = orgClient.validateOrgAuthKey(loginRequest.getOrgAuthKey());

        if (response == null || !Boolean.TRUE.equals(response.getBody())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid org auth key", "authKey", null));
        }

        UserLogin user = userLoginRepo.findByUserNameAndPasswordAndOrgAuthKey(
                loginRequest.getUserName(), loginRequest.getPassword(), loginRequest.getOrgAuthKey());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials", "authKey", null));
        }

        return ResponseEntity.ok(Map.of("message", "Login successful", "authKey", user.getOrgAuthKey()));
    }




}
