package com.ptit.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String roles; // e.g., "ROLE_USER", "ROLE_ADMIN"
}
