package com.example.user.core;

import com.example.user.core.model.ApplicationUser;
import com.example.user.exceptions.UserAlreadyExistsException;
import com.example.user.rest.controllers.model.RegisterUserRestModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ApplicationUserManager extends UserDetailsService {
    ApplicationUser loadUserByEmail(String email);
    List<ApplicationUser> getAllUsers();
    void signUpApplicationUser(RegisterUserRestModel registerUserRestModel) throws UserAlreadyExistsException;
}
