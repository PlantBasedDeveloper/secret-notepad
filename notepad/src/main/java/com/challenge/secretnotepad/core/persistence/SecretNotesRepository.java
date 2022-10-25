package com.challenge.secretnotepad.core.persistence;

import com.challenge.secretnotepad.core.persistence.entity.SecretNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Exposes the CRUD operations related to the database table of {@link SecretNoteEntity}
 * <p>
 * @author Mohamed Amine Allani
 */
public interface SecretNotesRepository extends JpaRepository<SecretNoteEntity, Long> {
    Optional<SecretNoteEntity> findById(Long id);
    Optional<SecretNoteEntity> findByTitle(String title);

}
