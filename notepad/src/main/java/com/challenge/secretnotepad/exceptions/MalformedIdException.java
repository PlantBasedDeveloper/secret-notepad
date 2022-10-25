package com.challenge.secretnotepad.exceptions;

import com.challenge.secretnotepad.rest.SecretNoteControllerAdvice;

/**
 * Exception class to be thrown when an exception occurs while parsing a given string id to a Long. It is used by an instance
 * of {@link com.challenge.secretnotepad.core.SecretNoteManager SecretNoteManager} and handled in {@link  SecretNoteControllerAdvice}.
 * <p>
 * @author Mohamed Amine Allani
 */
public class MalformedIdException extends SecretNotepadException {
    public MalformedIdException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public MalformedIdException(String errorMessage) {
        super(errorMessage);
    }
}
