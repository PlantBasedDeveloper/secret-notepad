package com.example.user.rest.controllers;

import com.example.user.core.model.ApplicationUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @GetMapping(path = "{userId}")
    public ApplicationUser getUser(@PathVariable Integer userId) {
        return null;
    }
}
