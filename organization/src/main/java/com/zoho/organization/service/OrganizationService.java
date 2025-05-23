package com.zoho.organization.service;

import com.zoho.organization.dto.org.OrganizationRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface OrganizationService {
     ResponseEntity<?> registerOrganization(OrganizationRequestDTO org);
     ResponseEntity<?> loginOrganization(String organization, String password);
     ResponseEntity<?> logoutOrg(UUID orgId);
    ResponseEntity<?> findByAuthKey(String key);
}
