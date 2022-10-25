package com.example.user.core.persistence;

import com.example.user.core.persistence.entity.ApplicationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, Long> {
    Optional<ApplicationUserEntity> findByUsername(String username);
    Optional<ApplicationUserEntity> findByEmail(String email);
}
