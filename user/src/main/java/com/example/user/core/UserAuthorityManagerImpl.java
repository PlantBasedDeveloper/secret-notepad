package com.example.user.core;

import com.example.user.core.model.UserAuthority;
import com.example.user.core.persistence.UserAuthorityRepository;
import com.example.user.core.persistence.entity.UserAuthorityEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserAuthorityManagerImpl implements UserAuthorityManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthorityManagerImpl.class);

    private final UserAuthorityRepository userAuthorityRepository;

    @Autowired
    public UserAuthorityManagerImpl(UserAuthorityRepository userAuthorityRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Override
    public UserAuthorityEntity getOrCreateUserAuthorityEntity(UserAuthority userAuthority) {
        UserAuthorityEntity authorityEntity = userAuthorityRepository.findByUserAuthority(userAuthority);
        if (authorityEntity == null) {
            authorityEntity = new UserAuthorityEntity(userAuthority);
            userAuthorityRepository.save(authorityEntity);
            LOGGER.info(String.format("UserAuthority created - %s", authorityEntity));
        }

        LOGGER.info(String.format("Returning UserAuthority - %s", authorityEntity));

        return authorityEntity;
    }

    @Override
    public Set<UserAuthorityEntity> getOrCreateUserAuthorityEntities(Set<UserAuthority> userAuthorities) {
        Set<UserAuthorityEntity> userAuthorityEntities = new HashSet<>();

        for (UserAuthority userAuthority: userAuthorities) {
            UserAuthorityEntity userAuthorityEntity = getOrCreateUserAuthorityEntity(userAuthority);
            userAuthorityEntities.add(userAuthorityEntity);
        }

        return userAuthorityEntities;
    }
}
