package com.pranit.helpdesk.dto;
import jakarta.validation.constraints.*;
public final class AuthDtos {
  private AuthDtos() {}
  public static class RegisterRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @Size(min = 8, max = 100) private String password;
    public String getName() {
      return name;
    }
    public void setName(String v) {
      name = v;
    }
    public String getEmail() {
      return email;
    }
    public void setEmail(String v) {
      email = v;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String v) {
      password = v;
    }
  }
  public static class LoginRequest {
    @Email @NotBlank private String email;
    @NotBlank private String password;
    public String getEmail() {
      return email;
    }
    public void setEmail(String v) {
      email = v;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String v) {
      password = v;
    }
  }
  public static class AuthResponse {
    private final String token, email, role;
    public AuthResponse(String token, String email, String role) {
      this.token = token;
      this.email = email;
      this.role = role;
    }
    public String getToken() {
      return token;
    }
    public String getEmail() {
      return email;
    }
    public String getRole() {
      return role;
    }
  }
}
