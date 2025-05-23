package com.zoho.userservice.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String userName;
    private String password;
    private String orgAuthKey;
}