package com.example.user.rest.controllers.model;

import com.example.user.core.model.UserRole;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RegisterUserRestModel {
    private final Integer id;
    private final String name;
    private final String username;
    private final String email;
    private final String password;
    private final boolean isAdmin;
    private final boolean isAdminTrainee;

    public Set<UserRole> getUserRoles() {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.USER);

        if (isAdminTrainee) {
            userRoles.add(UserRole.ADMIN_TRAINEE);
        }

        if (isAdmin) {
            userRoles.add(UserRole.ADMIN);
        }

        return userRoles;
    }
}
