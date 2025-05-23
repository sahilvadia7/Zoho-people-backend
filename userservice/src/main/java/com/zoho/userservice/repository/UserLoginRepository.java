package com.zoho.userservice.repository;

import com.zoho.userservice.dto.UserLoginRequest;
import com.zoho.userservice.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLoginRepository extends JpaRepository<UserLogin, UUID> {
    UserLogin findByOrgAuthKey(String key);
    UserLogin findByUserNameAndPasswordAndOrgAuthKey(String userName, String password, String orgAuthKey);
}
