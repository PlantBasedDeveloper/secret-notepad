package com.example.user.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserAuthority {
    NOTE_READ("note:read"),
    NOTE_WRITE("note:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String authority;
}
