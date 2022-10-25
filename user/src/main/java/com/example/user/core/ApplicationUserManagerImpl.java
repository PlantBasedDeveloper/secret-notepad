package com.example.user.core;

import com.example.user.core.model.ApplicationUser;
import com.example.user.core.persistence.ApplicationUserRepository;
import com.example.user.core.persistence.entity.ApplicationUserEntity;
import com.example.user.core.persistence.entity.UserRoleEntity;
import com.example.user.exceptions.UserAlreadyExistsException;
import com.example.user.rest.controllers.model.RegisterUserRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ApplicationUserManagerImpl implements ApplicationUserManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserManagerImpl.class);

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleManager userRoleManager;

    @Autowired
    public ApplicationUserManagerImpl(ApplicationUserRepository applicationUserRepository,
                                      PasswordEncoder passwordEncoder,
                                      UserRoleManager userRoleManager) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleManager = userRoleManager;
    }

    @Override
    public ApplicationUser loadUserByUsername(String username) throws UsernameNotFoundException {

        LOGGER.info(String.format("Loading user by username - %s", username));

        ApplicationUserEntity applicationUserEntity = applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        LOGGER.info(String.format("User loaded - %s", applicationUserEntity));

        return createApplicationUser(applicationUserEntity);
    }

    @Override
    public ApplicationUser loadUserByEmail(String email) throws UsernameNotFoundException {

        LOGGER.info(String.format("Loading user by email - %s", email));

        ApplicationUserEntity applicationUserEntity = applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", email)));

        LOGGER.info(String.format("User loaded - %s", applicationUserEntity));

        return createApplicationUser(applicationUserEntity);
    }

    @Override
    public List<ApplicationUser> getAllUsers() {
        LOGGER.info("Loading all users");

        List<ApplicationUserEntity> userEntities = applicationUserRepository.findAll();

        return userEntities.stream().map(this::createApplicationUser).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void signUpApplicationUser(RegisterUserRestModel registerUserRestModel) throws UserAlreadyExistsException {

        LOGGER.info(String.format("Signing up user - %s", registerUserRestModel));

        Optional<ApplicationUserEntity> applicationUserEntityOpr = applicationUserRepository.findByUsername(registerUserRestModel.getUsername());
        if (applicationUserEntityOpr.isPresent()) {
            throw new UserAlreadyExistsException(String.format("The username '%s' has already been taken", registerUserRestModel.getUsername()));
        }

        applicationUserEntityOpr = applicationUserRepository.findByEmail(registerUserRestModel.getEmail());
        if (applicationUserEntityOpr.isPresent()) {
            throw new UserAlreadyExistsException(String.format("The email '%s' has already been taken", registerUserRestModel.getEmail()));
        }

        String uuid = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(registerUserRestModel.getPassword());
        Set<UserRoleEntity> userRoleEntities = userRoleManager.getOrCreateUserRoleEntities(registerUserRestModel.getUserRoles());

        ApplicationUserEntity applicationUserEntity = new ApplicationUserEntity(
                uuid,
                registerUserRestModel.getUsername(),
                encodedPassword,
                registerUserRestModel.getEmail(),
                userRoleEntities);

        LOGGER.info(String.format("User was signed up - %s", applicationUserEntity));

        applicationUserRepository.save(applicationUserEntity);
    }

    private ApplicationUser createApplicationUser(ApplicationUserEntity applicationUserEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (UserRoleEntity userRole: applicationUserEntity.getUserRoleEntities()) {
            authorities.addAll(userRole.getUserRole().getGrantedAuthorities());
        }

        return ApplicationUser.builder()
                .uudi(applicationUserEntity.getUudi())
                .username(applicationUserEntity.getUsername())
                .email(applicationUserEntity.getEmail())
                .password(applicationUserEntity.getPassword())
                .grantedAuthorities(authorities)
                .isAccountNonExpired(applicationUserEntity.isAccountNonExpired())
                .isAccountNonLocked(applicationUserEntity.isAccountNonLocked())
                .isCredentialsNonExpired(applicationUserEntity.isCredentialsNonExpired())
                .isEnabled(applicationUserEntity.isEnabled())
                .build();
    }
}
