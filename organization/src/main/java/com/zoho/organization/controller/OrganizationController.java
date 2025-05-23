package com.zoho.organization.controller;

import com.zoho.organization.dto.org.OrganizationLoginDTO;
import com.zoho.organization.dto.org.OrganizationRequestDTO;
import com.zoho.organization.model.OnBoarding;
import com.zoho.organization.service.OnBoardingService;
import com.zoho.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/org")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final OnBoardingService onBoardingService;

    @PostMapping("/reg")
    public ResponseEntity<?> registerOrganization(@RequestBody OrganizationRequestDTO org){
        return organizationService.registerOrganization(org);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginOrganization(@RequestBody OrganizationLoginDTO orgLogin){
        return organizationService.loginOrganization(orgLogin.getOrganizationName(),orgLogin.getPassword());
    }

    @PostMapping("/onboarding")
    public ResponseEntity<?> onBoardingUser(@RequestBody OnBoarding onBoarding){
        return onBoardingService.onBoardingUser(onBoarding);
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<?> logoutOrg(@PathVariable(name = "id") UUID orgId){
        return organizationService.logoutOrg(orgId);
    }

    @GetMapping("/validate-key/{key}")
    public ResponseEntity<Boolean> validateOrgAuthKey(@PathVariable String key) {
        ResponseEntity<?> org = organizationService.findByAuthKey(key);
        if (org != null) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

}
