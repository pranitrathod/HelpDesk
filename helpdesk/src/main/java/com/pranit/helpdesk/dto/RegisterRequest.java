package com.pranit.helpdesk.dto;
import jakarta.validation.constraints.*;
public record RegisterRequest(@NotBlank String username,@Email @NotBlank String email,@Size(min=8) String password){}
