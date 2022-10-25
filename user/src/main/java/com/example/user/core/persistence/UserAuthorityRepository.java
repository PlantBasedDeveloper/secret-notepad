package com.example.user.core.persistence;

import com.example.user.core.model.UserAuthority;
import com.example.user.core.persistence.entity.UserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthorityEntity, Short> {

    UserAuthorityEntity findByUserAuthority(UserAuthority userAuthority);
}
