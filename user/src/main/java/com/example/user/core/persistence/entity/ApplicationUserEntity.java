package com.example.user.core.persistence.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class ApplicationUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @Column(unique=true)
    private String uudi;

    @Setter
    @Column(unique=true)
    private String username;

    @Setter
    @Column
    private String password;

    @Setter
    @Column(unique=true)
    private String email;

    @Setter
    @Column
    private boolean isAccountNonExpired = true;

    @Setter
    @Column
    private boolean isAccountNonLocked = true;

    @Setter
    @Column
    private boolean isCredentialsNonExpired = true;

    @Setter
    @Column
    private boolean isEnabled = true;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<UserRoleEntity> userRoleEntities;

    public ApplicationUserEntity(String uudi, String username, String password, String email, Set<UserRoleEntity> userRoleEntities) {
        this.uudi = uudi;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRoleEntities = userRoleEntities;
    }
}
