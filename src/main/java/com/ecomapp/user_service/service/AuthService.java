package com.ecomapp.user_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecomapp.user_service.config.JwtUtil;
import com.ecomapp.user_service.constant.RoleEnum;
import com.ecomapp.user_service.dto.ApiResponse;
import com.ecomapp.user_service.exception.CustomException;
import com.ecomapp.user_service.model.OtpModel;
import com.ecomapp.user_service.model.UserModel;
import com.ecomapp.user_service.repository.OtpRepository;
import com.ecomapp.user_service.repository.UserRepository;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final OtpRepository otpRepository;
  private final JwtUtil jwtUtil;

  private final String environment;

  public AuthService(
    UserRepository userRepository,
    OtpRepository otpRepository,
    JwtUtil jwtUtil,
    @Value("${app.environment:development}") String environment
  ) {
    this.userRepository = userRepository;
    this.otpRepository = otpRepository;
    this.jwtUtil = jwtUtil;
    this.environment = environment;
  }

  public ApiResponse<?> sendOtp(String phoneNumber) {
    String otpCode = String.valueOf((int) (Math.random() * 9000) + 1000);

    // Check if an OTP entry already exists for the given phone number
    Optional<OtpModel> existingOtp = otpRepository.findByPhoneNumber(phoneNumber);
    OtpModel otp;
    if(existingOtp.isPresent()) {
      // update the existing otp entry
      otp = existingOtp.get();
      otp.setOtp(otpCode);
      otp.setExpiryTime(java.time.LocalDateTime.now().plusMinutes(5));
      otpRepository.save(otp);
    } else {
      // create a new otp entry
      otp = OtpModel.builder()
              .phoneNumber(phoneNumber)
              .otp(otpCode)
              .expiryTime(java.time.LocalDateTime.now().plusMinutes(5))
              .build();
      otpRepository.save(otp);
      // send otp: TODO
    }
    // if environment is development then log the otp
    if(environment.equalsIgnoreCase("development")) {
      System.out.println("______________OTP:____________" + otpCode);
    }
    // if create user if not exist
    Optional<UserModel> existingUser = userRepository.findByPhoneNumber(phoneNumber);
    if(!existingUser.isPresent()) {
      UserModel userModel = UserModel.builder()
              .phoneNumber(phoneNumber)
              .role(RoleEnum.USER)
              .isVerified(false)
              .build();
      userRepository.save(userModel);
    }
    return new ApiResponse<>(true, "OTP sent successfully");
  }

  public ApiResponse<String> verifyOtp(String phoneNumber, String otp) {
    Optional<OtpModel> existingOtp = otpRepository.findByPhoneNumber(phoneNumber);
    if(existingOtp.isPresent()) {
      OtpModel otpModel = existingOtp.get();
      if(otpModel.getOtp().equals(otp)) {
        if(otpModel.getExpiryTime().isAfter(java.time.LocalDateTime.now())) {
          UserModel userModel = userRepository.findByPhoneNumber(phoneNumber).get();
          userModel.setIsVerified(true);
          userRepository.save(userModel);
          // generate jwt token
          String token = jwtUtil.generateToken(userModel.getId());
          return new ApiResponse<String>(true, "Logged in successfully", token);
        }
        //if invalid then delete the otp and throw error
        otpRepository.delete(otpModel);
        throw new CustomException("OTP expired", HttpStatus.BAD_REQUEST);
      } else {
        throw new CustomException("Invalid OTP", HttpStatus.BAD_REQUEST);
      }
    } else {
      throw new CustomException("OTP not found", HttpStatus.BAD_REQUEST);
    }
  }
}
