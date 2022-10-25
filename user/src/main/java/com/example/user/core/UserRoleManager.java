package com.example.user.core;

import com.example.user.core.model.UserRole;
import com.example.user.core.persistence.entity.UserRoleEntity;

import java.util.Set;

public interface UserRoleManager {
    UserRoleEntity getOrCreateUserRoleEntity(UserRole userRole);
    Set<UserRoleEntity> getOrCreateUserRoleEntities(Set<UserRole> userRoles);
}
