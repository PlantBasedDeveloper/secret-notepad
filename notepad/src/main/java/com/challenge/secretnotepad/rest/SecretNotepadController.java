package com.challenge.secretnotepad.rest;

import com.challenge.secretnotepad.core.SecretNoteManager;
import com.challenge.secretnotepad.exceptions.*;
import com.challenge.secretnotepad.rest.model.CreateSecretNoteRestModel;
import com.challenge.secretnotepad.rest.model.GetSecretNoteRestModel;
import com.challenge.secretnotepad.rest.model.SecretNoteSummaryRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class of the application. Handles the requests sent by the user.
 * <p>
 * @author Mohamed Amine allani
 */
@RestController
@RequestMapping("/secret-notepad")
public class SecretNotepadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecretNotepadController.class);

    @Autowired
    private SecretNoteManager secretNoteManager;

    @PostMapping("/create")
    public String createSecretNote(@Valid @RequestBody CreateSecretNoteRestModel createSecretNoteRestModel) throws EncryptionException, TitleAlreadyExistsException {
        LOGGER.info(String.format("Received request to create a secret note - %s", createSecretNoteRestModel));

        Long id = secretNoteManager.createSecretNote(createSecretNoteRestModel);

        return String.format("Secret note was created successfully - id=%d", id);
    }

    @PostMapping("/update/{id}")
    public String updateSecretNote(@PathVariable String id, @Valid @RequestBody CreateSecretNoteRestModel createSecretNoteRestModel) throws MalformedIdException, SecretNoteNotFoundException, EncryptionException, TitleAlreadyExistsException {
        LOGGER.info(String.format("Received request to update a secret note - id=%s -  %s", id, createSecretNoteRestModel));

        secretNoteManager.updateSecretNote(id, createSecretNoteRestModel);

        return String.format("Secret note was updated successfully - id=%s", id);
    }

    @GetMapping("/notes")
    public List<SecretNoteSummaryRestModel> getSecretNotes() {
        LOGGER.info("Received request to display all secret notes summaries");

        return secretNoteManager.getAllSecretNoteSummaries();
    }

    @GetMapping("/note/{id}")
    public GetSecretNoteRestModel getSecretNote(@PathVariable String id) throws MalformedIdException, SecretNoteNotFoundException, DecryptionException {
        LOGGER.info(String.format("Received request to display the secret note with id=%s", id));

        return secretNoteManager.getSecretNote(id);
    }

    @GetMapping("/enc-note/{id}")
    public GetSecretNoteRestModel getEncryptedSecretNote(@PathVariable String id) throws MalformedIdException, SecretNoteNotFoundException {
        LOGGER.info(String.format("Received request to display the encrypted secret note with id=%s", id));

        return secretNoteManager.getEncryptedSecretNote(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSecretNote(@PathVariable String id) throws MalformedIdException, SecretNoteNotFoundException {
        LOGGER.info(String.format("Received request to delete the secret note with id=%s", id));
        secretNoteManager.deleteSecretNote(id);

        return String.format("Secret note was deleted successfully - id=%s", id);
    }
}
