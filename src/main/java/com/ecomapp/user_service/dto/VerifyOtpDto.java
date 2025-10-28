package com.ecomapp.user_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDto {
  private String phoneNumber;
  private String otp;
}
