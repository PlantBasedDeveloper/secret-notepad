package com.challenge.secretnotepad.exceptions;

import com.challenge.secretnotepad.core.cryptography.CryptographyService;

/**
 * Exception class to be thrown when an exception occurs during decryption. It is used by the implementations
 * of {@link CryptographyService CryptographyService}
 * <p>
 * @author Mohamed Amine Allani
 */
public class DecryptionException extends SecretNotepadException {
    public DecryptionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public DecryptionException(String errorMessage) {
        super(errorMessage);
    }
}
