package com.zoho.organization.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "USERSERVICE", url = "http://localhost:8080")
public interface UserClient {

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable UUID id);
}
