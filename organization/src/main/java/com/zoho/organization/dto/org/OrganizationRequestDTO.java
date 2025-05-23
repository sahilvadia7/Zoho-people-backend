package com.zoho.organization.dto.org;

import lombok.Data;
import java.math.BigInteger;
import java.util.Date;

// OrganizationRequestDTO for sending the Organization data
// without exposing the actual Entity(organization).
@Data
public class OrganizationRequestDTO {

    private String organizationName;
    private String password;
    private Date createdAt;
    private String email;
    private String location;
    private BigInteger contactNO;
    private String webLink;
    private String auth_key;

}
