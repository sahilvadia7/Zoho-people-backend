package com.zoho.organization.repository;

import com.zoho.organization.model.OnBoarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OnBoardingRepository extends JpaRepository<OnBoarding, UUID> {
    OnBoarding findByUserId(UUID userId);
}
