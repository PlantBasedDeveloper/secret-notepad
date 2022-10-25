package com.challenge.secretnotepad.core.persistence.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class that represents the secret_note table in the database.
 * <p>
 * @author Mohamed Amine Allani
 */

@Entity
@Getter
@ToString
public class SecretNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private @Setter String title;

    @Lob
    @Column(length=512)
    private @Setter String note;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;
}
