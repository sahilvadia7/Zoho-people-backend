package com.zoho.organization.repository;

import com.zoho.organization.dto.org.OrganizationAuthKeyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationLoginRepo extends JpaRepository<OrganizationAuthKeyStore, UUID> {
    OrganizationAuthKeyStore findByOrganizationId(UUID organizationId);
    OrganizationAuthKeyStore findByAuthKey(String key);

}
