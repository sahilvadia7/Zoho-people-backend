package com.zoho.organization.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "OrganizationModel",description = "Organization Model")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "organization_id")
    private UUID organizationID;

    @Column(name = "organization_name")
    @NotBlank(message = "Organization cannot be blank")
    @Size(min = 3,message = "Organization name must grater than 3")
    private String organizationName;

    @Column(name = "org_password",nullable = false)
    private String password;

    @Column(name ="created_at",nullable = false)
    private Date createdAt;

    @Email(message = "Invalid Email Formate")
    @Column(name = "email")
    private String email;

    @Column(name ="location")
    private String location;

    @Column(name = "contact_no")
    private BigInteger contactNO;

    @Column(name = "web_link")
    private String webLink;

    @Column(name = "auth_key")
    private String auth_key;
}
