package com.challenge.secretnotepad.core;

import com.challenge.secretnotepad.core.cryptography.CryptographyService;
import com.challenge.secretnotepad.core.persistence.SecretNotesRepository;
import com.challenge.secretnotepad.core.persistence.entity.SecretNoteEntity;
import com.challenge.secretnotepad.exceptions.*;
import com.challenge.secretnotepad.rest.model.CreateSecretNoteRestModel;
import com.challenge.secretnotepad.rest.model.GetSecretNoteRestModel;
import com.challenge.secretnotepad.rest.model.SecretNoteSummaryRestModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Acts as intermediate layer between the controller and the {@link SecretNotesRepository}.
 * Manages all the CRUD operations and uses an instance of {@link CryptographyService} to encrypt/decrypt the
 * contents of {@link SecretNoteEntity SecretNoteEntity note field}.
 * <p>
 * This class is annotated as a component and will be added to the Spring context and injected wherever
 * it is needed.
 * <p>
 * @author Mohamed Amine Allani
 */
@Component
public class SecretNoteManagerImpl implements SecretNoteManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecretNoteManagerImpl.class);

    private final SecretNotesRepository secretNotesRepository;
    private final CryptographyService cryptographyService;

    @Autowired
    public SecretNoteManagerImpl(SecretNotesRepository secretNotesRepository, CryptographyService cryptographyService) {
        this.secretNotesRepository = secretNotesRepository;
        this.cryptographyService = cryptographyService;
    }

    /**
     * Creates a {@link SecretNoteEntity} according the values in the given restModel
     * and encrypts the {@link SecretNoteEntity SecretNoteEntity note field} before storing it in the database.
     *
     * @param restModel {@link CreateSecretNoteRestModel} contains the values that will be used to create the entity
     * @return id of the newly created SecretNode
     * @throws EncryptionException thrown when error occurs during encryption of the node
     * @throws TitleAlreadyExistsException thrown when the title of the new entity already exists
     */
    public Long createSecretNote(CreateSecretNoteRestModel restModel) throws EncryptionException, TitleAlreadyExistsException {
        Optional<SecretNoteEntity> entityOpt = secretNotesRepository
                .findByTitle(restModel.getTitle());

        if (entityOpt.isPresent()) {
            throw new TitleAlreadyExistsException(String.format("Title [%s] has already been taken.", restModel.getTitle()));
        }

        SecretNoteEntity entity = new SecretNoteEntity();
        BeanUtils.copyProperties(restModel, entity);
        String encryptedNote = cryptographyService.encrypt(restModel.getNote());
        entity.setNote(encryptedNote);
        secretNotesRepository.save(entity);

        LOGGER.info(String.format("Secret note was created successfully - %s", entity));

        return entity.getId();
    }


    /**
     * Updates the given {@link SecretNoteEntity}. The note field in the given {@link CreateSecretNoteRestModel} instance
     * is encrypted if it is not empty before storing the entity in the database.
     *
     * @param restModel {@link CreateSecretNoteRestModel} contains the values that will be used to update the entity
     * @param idStr id of the entity
     * @throws EncryptionException thrown when error occurs during encryption of the node
     * @throws TitleAlreadyExistsException thrown when the title of the new entity already exists
     * @throws MalformedIdException thrown when an error occurs while parsing idStr
     * @throws SecretNoteNotFoundException thrown when no note with the given id
     */
    public void updateSecretNote(String idStr, CreateSecretNoteRestModel restModel)
            throws MalformedIdException, SecretNoteNotFoundException, EncryptionException, TitleAlreadyExistsException {
        Long id = parseId(idStr)
                .orElseThrow(() -> new MalformedIdException(String.format("[%s] is not a valid secret note id", idStr)));

        SecretNoteEntity entity = secretNotesRepository
                .findById(id)
                .orElseThrow(() -> new SecretNoteNotFoundException(String.format("Secret note with id=%d was not found.", id)));

        Optional<SecretNoteEntity> entityOpt = secretNotesRepository
                .findByTitle(restModel.getTitle());

        if (entityOpt.isPresent() && !restModel.getTitle().equals(entity.getTitle())) {
            throw new TitleAlreadyExistsException(String.format("Title [%s] has already been taken.", restModel.getTitle()));
        }

        BeanUtils.copyProperties(restModel, entity);

        if (!restModel.getNote().isEmpty()) {
            String encryptedNote = cryptographyService.encrypt(restModel.getNote());
            entity.setNote(encryptedNote);
        }
        secretNotesRepository.save(entity);

        LOGGER.info(String.format("Secret note was updated successfully - %s", entity));
    }
    /**
     * Fetches the database entry of {@link SecretNoteEntity} with the given id. The result is used to create an instance
     * of {@link GetSecretNoteRestModel}. The {@link GetSecretNoteRestModel note field}
     * is decrypted before returning the model instance to the controller.
     *
     * @param idStr id of the entity
     * @return {@link GetSecretNoteRestModel}
     */
    public GetSecretNoteRestModel getSecretNote(String idStr) throws MalformedIdException, SecretNoteNotFoundException, DecryptionException {
        Long id = parseId(idStr)
                .orElseThrow(() -> new MalformedIdException(String.format("[%s] is not a valid secret note id", idStr)));

        SecretNoteEntity entity = secretNotesRepository
                .findById(id)
                .orElseThrow(() -> new SecretNoteNotFoundException(String.format("Secret note with id=%d was not found.", id)));

        String decryptedNote = cryptographyService.decrypt(entity.getNote());

        return GetSecretNoteRestModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .note(decryptedNote)
                .createdAt(entity.getCreatedAt())
                .lastModifiedAt(entity.getLastModifiedAt())
                .build();
    }

    /**
     * Fetches the {@link SecretNoteEntity} with the given id. The result is used to create an instance
     * of {@link GetSecretNoteRestModel}. The {@link GetSecretNoteRestModel note field}
     * is NOT decrypted before returning the model instance to the controller.
     *
     * @param idStr id of the entity
     * @return {@link GetSecretNoteRestModel}
     */
    public GetSecretNoteRestModel getEncryptedSecretNote(String idStr) throws SecretNoteNotFoundException, MalformedIdException {
        Long id = parseId(idStr)
                .orElseThrow(() -> new MalformedIdException(String.format("[%s] is not a valid secret note id", idStr)));

        SecretNoteEntity entity = secretNotesRepository
                .findById(id)
                .orElseThrow(() -> new SecretNoteNotFoundException(String.format("Secret note with id=%d was not found.", id)));

        return GetSecretNoteRestModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .note(entity.getNote())
                .createdAt(entity.getCreatedAt())
                .lastModifiedAt(entity.getLastModifiedAt())
                .build();
    }

    /**
     * Fetches all the {@link SecretNoteEntity SecretNoteEntities} in the database. The entities are used to create a list
     * of {@link SecretNoteSummaryRestModel} which are processed by the controller.
     *
     * @return List of {@link SecretNoteSummaryRestModel}
     */
    public List<SecretNoteSummaryRestModel> getAllSecretNoteSummaries() {
        List<SecretNoteEntity> entities = secretNotesRepository.findAll();
        List<SecretNoteSummaryRestModel> restModelEntities = new ArrayList<>();

        for (SecretNoteEntity entity: entities) {
            SecretNoteSummaryRestModel restModelEntity = SecretNoteSummaryRestModel.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .createdAt(entity.getCreatedAt())
                    .lastModifiedAt(entity.getLastModifiedAt())
                    .build();
            restModelEntities.add(restModelEntity);
        }

        LOGGER.info("Returning summaries of all secret notes");

        return restModelEntities;
    }

    /**
     * Deletes the database entry with the given id
     * @param idStr id of the to be deleted entry
     */
    public void deleteSecretNote(String idStr) throws SecretNoteNotFoundException, MalformedIdException {
        Long id = parseId(idStr)
                .orElseThrow(() -> new MalformedIdException(String.format("[%s] is not a valid secret note id", idStr)));

        SecretNoteEntity entity = secretNotesRepository
                .findById(id)
                .orElseThrow(() -> new SecretNoteNotFoundException(String.format("Unable to delete secret note. Secret note with id=%d not found.", id)));

        secretNotesRepository.delete(entity);

        LOGGER.info(String.format("Secret note was deleted successfully - %d", id));
    }

    /**
     * Transforms the given string to a Long.
     *
     * @param idStr string ig
     * @return optional of the result
     */
    private static Optional<Long> parseId(String idStr) {
        Optional<Long> result = Optional.empty();

        if (idStr != null && !idStr.isEmpty()) {
            try {
                result = Optional.of(Long.valueOf(idStr));
            } catch (NumberFormatException ex) {
                LOGGER.error("NumberFormatException was caught", ex);
            }
        }

        return result;
    }
}
