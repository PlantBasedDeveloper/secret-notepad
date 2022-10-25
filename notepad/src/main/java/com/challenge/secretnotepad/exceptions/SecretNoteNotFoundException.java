package com.challenge.secretnotepad.exceptions;

import com.challenge.secretnotepad.rest.SecretNoteControllerAdvice;

/**
 * Exception class to be thrown when the secret note entry in database with a given ID is not found. It is used by an instance
 * of {@link com.challenge.secretnotepad.core.SecretNoteManager SecretNoteManager} and handled in {@link  SecretNoteControllerAdvice}.
 * <p>
 * @author Mohamed Amine Allani
 */
public class SecretNoteNotFoundException extends SecretNotepadException {
    public SecretNoteNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public SecretNoteNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
