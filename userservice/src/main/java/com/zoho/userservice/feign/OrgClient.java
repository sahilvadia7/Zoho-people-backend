package com.zoho.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ZOHOORGANIZATION", url = "http://localhost:8081")
public interface OrgClient {

    @GetMapping("/api/org/validate-key/{key}")
    ResponseEntity<Boolean> validateOrgAuthKey(@PathVariable("key") String key);

}
