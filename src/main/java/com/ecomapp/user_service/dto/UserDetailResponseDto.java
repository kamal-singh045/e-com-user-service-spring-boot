package com.ecomapp.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailResponseDto {
  private String name;
  private String phoneNumber;
}
