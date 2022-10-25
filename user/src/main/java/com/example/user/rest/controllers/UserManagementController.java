package com.example.user.rest.controllers;


import com.example.user.core.ApplicationUserManager;
import com.example.user.core.ApplicationUserManagerImpl;
import com.example.user.core.model.ApplicationUser;
import com.example.user.rest.controllers.model.RegisterUserRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/users")
@PreAuthorize("hasAuthority('user:write')")
public class UserManagementController {

    private final ApplicationUserManager applicationUserService;

    @Autowired
    public UserManagementController(ApplicationUserManager applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public List<ApplicationUser> getAllUsers() {
        return applicationUserService.getAllUsers();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public void registerUser(RegisterUserRestModel restModel) {
        System.out.println(restModel);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteStudent(@PathVariable Integer userId) {
        System.out.println("Deleting user with id: " + userId);
    }

    @PutMapping("{userId}")
    public void updateUser(@PathVariable Integer userId, RegisterUserRestModel restModel) {
        System.out.println();
    }
}
