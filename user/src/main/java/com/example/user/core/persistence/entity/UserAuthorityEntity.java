package com.example.user.core.persistence.entity;

import com.example.user.core.model.UserAuthority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class UserAuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private UserAuthority userAuthority;

    @ManyToMany(mappedBy = "userAuthorityEntities")
    private Collection<UserRoleEntity> userRoleEntities;

    public UserAuthorityEntity(UserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }

    @Override
    public String toString() {
        return "{userAuthority=" + userAuthority + '}';
    }
}
