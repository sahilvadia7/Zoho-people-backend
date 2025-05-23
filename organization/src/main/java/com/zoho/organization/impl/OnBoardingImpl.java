package com.zoho.organization.impl;

import com.zoho.organization.feign.UserClient;
import com.zoho.organization.model.OnBoarding;
import com.zoho.organization.repository.OnBoardingRepository;
import com.zoho.organization.service.OnBoardingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.zoho.organization.constrain.GlobalConstrain.*;

@Service
@RequiredArgsConstructor
public class OnBoardingImpl implements OnBoardingService {

    private final OnBoardingRepository onBoardingRepository;
    private final UserClient userClient;

    @Override
    public ResponseEntity<?> onBoardingUser(OnBoarding onBoarding) {
        ResponseEntity<?> user = findByUserId(onBoarding.getUserId());
        if(user==null){
            return new ResponseEntity<>(USER_NOT_FOUND,HttpStatus.NOT_FOUND);
        }

        OnBoarding alreadyBoard = onBoardingRepository.findByUserId(onBoarding.getUserId());
        if(alreadyBoard!=null){
            return new ResponseEntity<>(ALREADY_ONBOARD,HttpStatus.ALREADY_REPORTED);
        }

        onBoarding.setDateOfJoining(new Date());
        return new ResponseEntity<>(onBoardingRepository.save(onBoarding),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByUserId(UUID userID) {
        return new ResponseEntity<>(userClient.findByUserId(userID),HttpStatus.OK);
    }
}
