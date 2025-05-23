package com.zoho.organization.service;

import com.zoho.organization.model.OnBoarding;
import com.zoho.userservice.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface OnBoardingService {

    ResponseEntity<?> onBoardingUser(OnBoarding onBoarding);
    ResponseEntity<?> findByUserId(UUID userID);

}
