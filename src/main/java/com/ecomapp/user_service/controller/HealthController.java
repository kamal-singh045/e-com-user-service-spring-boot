package com.ecomapp.user_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HealthController {
  @GetMapping("/health")
  public String UserServiceHealth() {
    String ans = "User service is healthy";
    return ans;
  }
}
