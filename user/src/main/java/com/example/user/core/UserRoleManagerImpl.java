package com.example.user.core;

import com.example.user.core.model.UserRole;
import com.example.user.core.persistence.UserRoleRepository;
import com.example.user.core.persistence.entity.UserAuthorityEntity;
import com.example.user.core.persistence.entity.UserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserRoleManagerImpl implements UserRoleManager{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleManagerImpl.class);

    private final UserRoleRepository userRoleRepository;

    private final UserAuthorityManager userAuthorityManager;

    @Autowired
    public UserRoleManagerImpl(UserRoleRepository userRoleRepository, UserAuthorityManager userAuthorityManager) {
        this.userRoleRepository = userRoleRepository;
        this.userAuthorityManager = userAuthorityManager;
    }

    @Override
    @Transactional
    public UserRoleEntity getOrCreateUserRoleEntity(UserRole userRole) {

        UserRoleEntity userRoleEntity = userRoleRepository.findByUserRole(userRole);
        if (userRoleEntity == null) {
            userRoleEntity = new UserRoleEntity(userRole);
            Set<UserAuthorityEntity> userAuthorityEntities = userAuthorityManager.getOrCreateUserAuthorityEntities(userRole.getAuthorities());
            userRoleEntity.setUserAuthorityEntities(userAuthorityEntities);
            userRoleRepository.save(userRoleEntity);

            LOGGER.info(String.format("UserRole created - %s", userRoleEntity));
        }

        LOGGER.info(String.format("Returning UserRole - %s", userRoleEntity));

        return userRoleEntity;
    }

    @Override
    @Transactional
    public Set<UserRoleEntity> getOrCreateUserRoleEntities(Set<UserRole> userRoles) {
        Set<UserRoleEntity> userRoleEntities = new HashSet<>();

        for (UserRole userRole: userRoles) {
            UserRoleEntity userRoleEntity = getOrCreateUserRoleEntity(userRole);
            userRoleEntities.add(userRoleEntity);
        }

        return userRoleEntities;
    }
}
