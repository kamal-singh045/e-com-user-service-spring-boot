package com.ecomapp.user_service.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecomapp.user_service.dto.ApiResponse;
import com.ecomapp.user_service.dto.UserDetailResponseDto;
import com.ecomapp.user_service.exception.CustomException;
import com.ecomapp.user_service.model.UserModel;
import com.ecomapp.user_service.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ApiResponse<UserDetailResponseDto> getUserDetails(UUID userId) {
    UserModel user = userRepository.findById(userId)
                        .orElseThrow(() -> 
                          new CustomException("User not found", HttpStatus.NOT_FOUND));
    return new ApiResponse<UserDetailResponseDto>(
      true,
      "User details",
      new UserDetailResponseDto(user.getName(), user.getPhoneNumber())
    );
  }
}
