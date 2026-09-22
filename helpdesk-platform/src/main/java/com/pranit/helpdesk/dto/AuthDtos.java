package com.pranit.helpdesk.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public final class AuthDtos {

  private AuthDtos() {}

  @Getter
  @Setter
  @NoArgsConstructor
  public static class RegisterRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @Size(min = 8, max = 100) private String password;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class LoginRequest {
    @Email @NotBlank private String email;
    @NotBlank private String password;
  }

  @Getter
  @AllArgsConstructor
  public static class AuthResponse {
    private final String token;
    private final String email;
    private final String role;
  }
}
