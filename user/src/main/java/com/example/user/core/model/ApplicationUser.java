package com.example.user.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class ApplicationUser implements UserDetails {
    private final String uudi;
    private final String username;
    private final String password;
    private final String email;

    @Setter
    private Set<? extends GrantedAuthority> grantedAuthorities;

    @Setter
    private boolean isAccountNonExpired;

    @Setter
    private boolean isAccountNonLocked;

    @Setter
    private boolean isCredentialsNonExpired;

    @Setter
    private boolean isEnabled;


    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setRole(UserRole userRole) {
        grantedAuthorities = userRole.getGrantedAuthorities();
    }
}
