package com.example.user.core.persistence.entity;

import com.example.user.core.model.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @Getter
    private UserRole userRole;

    @ManyToMany(mappedBy = "userRoleEntities")
    private Collection<ApplicationUserEntity> applicationUserEntities;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id", referencedColumnName = "id"))
    private Set<UserAuthorityEntity> userAuthorityEntities;

    public UserRoleEntity(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "{userRole=" + userRole + '}';
    }
}
