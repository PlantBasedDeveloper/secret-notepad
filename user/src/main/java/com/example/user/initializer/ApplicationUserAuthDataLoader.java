package com.example.user.initializer;

import com.example.user.core.UserRoleManager;
import com.example.user.core.model.UserRole;
import com.example.user.core.persistence.ApplicationUserRepository;
import com.example.user.core.persistence.entity.ApplicationUserEntity;
import com.example.user.core.persistence.entity.UserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class ApplicationUserAuthDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserAuthDataLoader.class);

    private final ApplicationUserRepository applicationUserRepository;

    private final UserRoleManager userRoleManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserAuthDataLoader(ApplicationUserRepository applicationUserRepository,
                                         UserRoleManager userRoleManager,
                                         PasswordEncoder passwordEncoder) {
        this.userRoleManager = userRoleManager;
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        LOGGER.info("Creating users");

        createApplicationUser("amine", "amine@gmail.com", "password", new HashSet<>(
                Collections.singletonList(UserRole.ADMIN)
        ));
        createApplicationUser("lilia", "lilia@gmail.com", "password", new HashSet<>(
                Collections.singletonList(UserRole.USER)
        ));
        createApplicationUser("mahmoud", "mahmoud@gmail.com", "password", new HashSet<>(
                Collections.singletonList(UserRole.USER)
        ));

        LOGGER.info("Users created");
    }

    @Transactional
    protected ApplicationUserEntity createApplicationUser(String username, String email, String password, Set<UserRole> roles) {

        Optional<ApplicationUserEntity> applicationUserEntityOpr = applicationUserRepository.findByUsername(username);

        if (applicationUserEntityOpr.isPresent()) {
            return applicationUserEntityOpr.get();
        } else {
            applicationUserEntityOpr = applicationUserRepository.findByEmail(email);
        }

        if (applicationUserEntityOpr.isPresent()) {
            return applicationUserEntityOpr.get();
        }

        String uuid = UUID.randomUUID().toString();
        Set<UserRoleEntity> userRoleEntities = userRoleManager.getOrCreateUserRoleEntities(roles);
        String encodedPassword = passwordEncoder.encode(password);
        ApplicationUserEntity applicationUserEntity = new ApplicationUserEntity(uuid, username, encodedPassword, email, userRoleEntities);
        applicationUserRepository.save(applicationUserEntity);

        LOGGER.info(String.format("User created - %s", applicationUserEntity));

        return applicationUserEntity;
    }
}
