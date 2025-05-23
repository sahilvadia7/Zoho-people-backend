package com.zoho.organization.impl;

import com.zoho.organization.dto.org.OrganizationAuthKeyStore;
import com.zoho.organization.dto.org.OrganizationRequestDTO;
import com.zoho.organization.enums.STATUS;
import com.zoho.organization.exception.EmptyValueException;
import com.zoho.organization.model.Organization;
import com.zoho.organization.repository.OrganizationLoginRepo;
import com.zoho.organization.repository.OrganizationRepository;
import com.zoho.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.UUID;

import static com.zoho.organization.constrain.GlobalConstrain.*;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationLoginRepo organizationLoginRepo;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public ResponseEntity<?> registerOrganization(@RequestBody OrganizationRequestDTO orgDto) {
        if (orgDto.getOrganizationName().trim().isEmpty()) {
            throw new EmptyValueException("exception");
        }
        Organization checkIsPresent = organizationRepository.findByOrganizationName(orgDto.getOrganizationName());
        if (checkIsPresent == null) {
            String encodePass = passwordEncoder().encode(orgDto.getPassword());

            Organization org = Organization.builder()
                    .organizationName(orgDto.getOrganizationName())
                    .email(orgDto.getEmail())
                    .password(encodePass)
                    .contactNO(orgDto.getContactNO())
                    .createdAt(new Date())
                    .location(orgDto.getLocation())
                    .webLink(orgDto.getWebLink())
                    .build();

            return new ResponseEntity<>(organizationRepository.save(org), HttpStatus.OK);
        }

        return new ResponseEntity<>(ORGANIZATION_ALREADY_REGISTER, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> loginOrganization(String organizationName, String password) {
        Organization org = organizationRepository.findByOrganizationName(organizationName);

        if (org == null) {
            return new ResponseEntity<>(BAD_CREDENTIAL, HttpStatus.BAD_REQUEST);
        }

        boolean matches = passwordEncoder().matches(password, org.getPassword());

        if (matches) {
            // Check auth key already present.
            OrganizationAuthKeyStore authEntry = organizationLoginRepo.findByOrganizationId(org.getOrganizationID());

            if (authEntry == null || authEntry.getAuthKey() == null || authEntry.getAuthKey().trim().isEmpty()) {
                String newAuthKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
                OrganizationAuthKeyStore newAuthEntry = OrganizationAuthKeyStore.builder()
                        .organizationId(org.getOrganizationID())
                        .authKey(newAuthKey)
                        .status(STATUS.ACTIVE)
                        .build();
//                CURRENT_LOGIN_ORG = org.getOrganizationName();
                organizationLoginRepo.save(newAuthEntry);

                return new ResponseEntity<>(LOGIN_SUCCESSFULLY, HttpStatus.OK);
            }
            else if(authEntry.getStatus().equals(STATUS.INACTIVE)){
                authEntry.setStatus(STATUS.ACTIVE);
                organizationLoginRepo.save(authEntry);

                return new ResponseEntity<>(STATUS_ACTIVATED,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Already logged in. Key exists.", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(BAD_CREDENTIAL, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> logoutOrg(UUID orgId) {
        OrganizationAuthKeyStore logoutOrg = organizationLoginRepo.findByOrganizationId(orgId);
        if(logoutOrg == null){
            return new ResponseEntity<>(USER_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
        logoutOrg.setStatus(STATUS.INACTIVE);
        organizationLoginRepo.save(logoutOrg);
        return new ResponseEntity<>(LOGOUT_SUCCESSFULLY,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByAuthKey(String key) {
        return new ResponseEntity<>(organizationLoginRepo.findByAuthKey(key),HttpStatus.OK);
    }


}
