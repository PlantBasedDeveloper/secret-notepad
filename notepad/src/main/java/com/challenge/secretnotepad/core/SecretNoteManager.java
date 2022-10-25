package com.challenge.secretnotepad.core;

import com.challenge.secretnotepad.exceptions.*;
import com.challenge.secretnotepad.rest.model.CreateSecretNoteRestModel;
import com.challenge.secretnotepad.rest.model.GetSecretNoteRestModel;
import com.challenge.secretnotepad.rest.model.SecretNoteSummaryRestModel;


import java.util.List;

/**
 * Interface to be implemented by the Secret note manager of the app.
 * <p>
 * @author Mohamed Amine Allani
 */
public interface SecretNoteManager {

    Long createSecretNote(CreateSecretNoteRestModel restModel) throws EncryptionException, TitleAlreadyExistsException;
    void updateSecretNote(String idStr, CreateSecretNoteRestModel restModel)
            throws MalformedIdException, SecretNoteNotFoundException, EncryptionException, TitleAlreadyExistsException;
    GetSecretNoteRestModel getSecretNote(String idStr) throws MalformedIdException, SecretNoteNotFoundException, DecryptionException;
    GetSecretNoteRestModel getEncryptedSecretNote(String idStr) throws MalformedIdException, SecretNoteNotFoundException;
    List<SecretNoteSummaryRestModel> getAllSecretNoteSummaries();
    void deleteSecretNote(String idStr) throws SecretNoteNotFoundException, MalformedIdException;
}