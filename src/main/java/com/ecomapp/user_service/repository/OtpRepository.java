package com.ecomapp.user_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomapp.user_service.model.OtpModel;

@Repository
public interface OtpRepository extends JpaRepository<OtpModel, UUID> {
  Optional<OtpModel> findByPhoneNumber(String phoneNumber);
}
