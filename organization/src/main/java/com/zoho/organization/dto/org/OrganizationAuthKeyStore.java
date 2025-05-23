package com.zoho.organization.dto.org;

import com.zoho.organization.enums.STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//OrganizationAuthKeyStore is used to store organization with auth_key in table called org_login
// it used for we know organization already login or not?,
        // to reducing the auth_key creation every time.

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "org_login")
public class OrganizationAuthKeyStore {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Column(name = "organization_id", nullable = false)
        private UUID organizationId;

        @Column(name = "auth_key", unique = true)
        private String authKey;

        @Column(name = "org_status")
        private STATUS status;
}