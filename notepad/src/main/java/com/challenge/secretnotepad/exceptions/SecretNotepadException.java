package com.challenge.secretnotepad.exceptions;

/**
 * Superclass for the custom exceptions defined in the scope of this application.
 * <p>
 * @author Mohamed Amine Allani
 */
public class SecretNotepadException extends Exception {
    public SecretNotepadException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public SecretNotepadException(String errorMessage) {
        super(errorMessage);
    }
}
