package com.ecomapp.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomapp.user_service.dto.ApiResponse;
import com.ecomapp.user_service.dto.SendOtpDto;
import com.ecomapp.user_service.dto.VerifyOtpDto;
import com.ecomapp.user_service.exception.CustomException;
import com.ecomapp.user_service.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/send-otp")
  public ResponseEntity<ApiResponse<?>> sendOtp(@RequestBody SendOtpDto body) {
    try {
      ApiResponse<?> response = authService.sendOtp(body.getPhoneNumber());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody VerifyOtpDto body) {
    try {
      ApiResponse<String> response = authService.verifyOtp(body.getPhoneNumber(), body.getOtp());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
