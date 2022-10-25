package com.example.user.core.model;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("admin", Sets.newHashSet(UserAuthority.NOTE_READ, UserAuthority.NOTE_WRITE, UserAuthority.USER_READ, UserAuthority.USER_WRITE)),
    ADMIN_TRAINEE("adminTrainee", Sets.newHashSet(UserAuthority.NOTE_READ, UserAuthority.USER_READ)),
    USER("user", Sets.newHashSet());

    private final String name;
    private final Set<UserAuthority> authorities;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = this.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
