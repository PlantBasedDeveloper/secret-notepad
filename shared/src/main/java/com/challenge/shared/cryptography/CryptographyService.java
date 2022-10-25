package com.challenge.shared.cryptography;

import com.challenge.shared.cryptography.exceptions.DecryptionException;
import com.challenge.shared.cryptography.exceptions.EncryptionException;

/**
 * Interface to be implemented by the Cryptography service(s) of the app.
 * <p>
 * @author Mohamed Amine Allani
 */

public interface CryptographyService {
    String encrypt(String message) throws EncryptionException;
    String decrypt(String message) throws DecryptionException;
}
