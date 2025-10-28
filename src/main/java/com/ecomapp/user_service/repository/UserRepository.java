package com.ecomapp.user_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ecomapp.user_service.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
  Optional<UserModel> findByPhoneNumber(String phoneNumber);
}
