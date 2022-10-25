package com.example.user.security.jwt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernamePasswordAuthenticationRequest {
    public String username;
    public String password;
}
