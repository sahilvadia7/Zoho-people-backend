package com.zoho.userservice.model;


import com.zoho.userservice.enums.STATUS;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserModel",description = "User Model")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id",nullable = false)
    private UUID userId;

    @Column(name = "user_name")
    @NotBlank(message = "username not be blank")
    @Size(min = 2,max = 20 ,message = "username must be between 2-20")
    private String userName;

    @Column(name = "email")
    @Email(message = "Invalid email formate")
    private String email;

    @Column(name = "phone_number")
    @Size(min = 10,max = 10, message = "phone number must be 10 digit")
    private BigInteger phoneNumber;

    @Column(name = "status")
    private STATUS status;

    @Column(name = "password",nullable = false)
    private String password;

}
