package com.zoho.userservice.dto;

import com.zoho.userservice.enums.STATUS;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class UserRequestDto {
    private String userName;
    private String email;
    private BigInteger phoneNumber;
    private STATUS status;
    private String password;
}
