package com.ecomapp.user_service.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomapp.user_service.constant.AppConstants;
import com.ecomapp.user_service.dto.ApiResponse;
import com.ecomapp.user_service.dto.UserDetailResponseDto;
import com.ecomapp.user_service.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<UserDetailResponseDto>> getUserDetails(@RequestHeader(AppConstants.X_USER_ID) String userId) {
    System.out.println("______________X-User-Id______________" + userId);
    ApiResponse<UserDetailResponseDto> response = userService.getUserDetails(UUID.fromString(userId));
    return ResponseEntity.ok(response);
  }
}
