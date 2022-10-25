package com.example.user.core;

import com.example.user.core.model.UserAuthority;
import com.example.user.core.persistence.entity.UserAuthorityEntity;

import java.util.Set;

public interface UserAuthorityManager {
    UserAuthorityEntity getOrCreateUserAuthorityEntity(UserAuthority userAuthority);
    Set<UserAuthorityEntity> getOrCreateUserAuthorityEntities(java.util.Set<UserAuthority> userAuthorities);
}
