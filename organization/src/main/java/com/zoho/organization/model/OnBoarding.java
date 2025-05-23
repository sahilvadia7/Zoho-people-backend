package com.zoho.organization.model;


import com.zoho.organization.enums.ROLES;
import com.zoho.organization.enums.STATUS;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "OnBoarding",description = "OnBoarding Model")
@Table(name = "employees_of_org")
public class OnBoarding {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id",nullable = false)
    private UUID empID;

    @Column(name = "organization_id",nullable = false)
    private UUID organizationID;

    @Column(name = "user_id",nullable = false)
    private UUID userId;

    @Column(name = "date_of_joining")
    private Date dateOfJoining;

    @Column(name ="role",nullable = false)
    private ROLES role;

    @Column(name = "status")
    private STATUS user_status;

}
