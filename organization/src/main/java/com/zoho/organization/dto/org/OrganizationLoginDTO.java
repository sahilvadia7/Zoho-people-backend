package com.zoho.organization.dto.org;

import lombok.Data;

// OrganizationLoginDTO is use for organization login.
@Data
public class OrganizationLoginDTO {
    private String organizationName;
    private String password;
}
