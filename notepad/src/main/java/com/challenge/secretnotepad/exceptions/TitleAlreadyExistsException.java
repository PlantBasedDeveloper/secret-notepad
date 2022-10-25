package com.challenge.secretnotepad.exceptions;

import com.challenge.secretnotepad.rest.SecretNoteControllerAdvice;

/**
 * Exception class to be thrown when a new secret note database entry is being added while the title field already exists.
 * It is used by an instance of {@link com.challenge.secretnotepad.core.SecretNoteManager SecretNoteManager}
 * and handled in {@link  SecretNoteControllerAdvice}.
 * <p>
 * @author Mohamed Amine Allani
 */
public class TitleAlreadyExistsException extends SecretNotepadException {
    public TitleAlreadyExistsException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public TitleAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
