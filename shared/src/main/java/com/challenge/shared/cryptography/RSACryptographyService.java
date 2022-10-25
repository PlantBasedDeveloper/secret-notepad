package com.challenge.shared.cryptography;

import com.challenge.shared.cryptography.exceptions.DecryptionException;
import com.challenge.shared.cryptography.exceptions.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

/**
 * Implementation of the {@link CryptographyService CryptographyService interface} which
 * Uses RSA as encryption/decryption algorithm.
 * <p>
 * This class is annotated as a component and will be added to the Spring context and injected wherever
 * it is needed.
 * <p>
 * @author Mohamed Amine Allani
 */
@Component
@Qualifier("RSA")
public class RSACryptographyService implements CryptographyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSACryptographyService.class);

    public static final int KEY_SIZE = 2048;
    public static final String ALGORITHM_RSA = "RSA";
    public static final int ENCRYPT_BLOCK_LENGTH = 200;
    public static final int DECRYPT_BLOCK_LENGTH = KEY_SIZE/8;

    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private final Cipher cipher;

    public RSACryptographyService() {
        KeyPair keyPair = generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

        try {
            cipher = Cipher.getInstance(ALGORITHM_RSA);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            LOGGER.error(String.format("%s was caught", ex.getClass().getName()), ex);

            throw new IllegalStateException(ex);
        }
    }

    /**
     * Generates a KeyPair which will be used for the lifetime of the application.
     *
     * @return keyPair
     */
    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            generator.initialize(KEY_SIZE);

            return generator.generateKeyPair();

        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException was caught", ex);

            throw new IllegalStateException(ex);
        }
    }

    /**
     * Encrypts the given string using the previously generates public key.
     *
     * @param message message to be encrypted
     * @return encrypted message
     * @throws EncryptionException thrown when decryption fails
     */
    @Override
    public String encrypt(String message) throws EncryptionException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = cypherBlocks(messageBytes, Cipher.ENCRYPT_MODE);

            String result = Base64.getEncoder().encodeToString(encryptedMessageBytes);

            LOGGER.info(String.format("Message was successfully encrypted - message: %s", result));

            return result;

        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            LOGGER.error(String.format("%s was caught", ex.getClass().getName()), ex);

            throw new EncryptionException("An error occurred during encryption", ex);
        }
    }

    /**
     * Decrypts the given string using the previously generates private key.
     * @param encryptedMessage encryptedMessage
     * @return decrypted message
     * @throws DecryptionException thrown when decryption fails
     */
    @Override
    public String decrypt(String encryptedMessage) throws DecryptionException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);
            byte[] decryptedMessageBytes = cypherBlocks(encryptedMessageBytes, Cipher.DECRYPT_MODE);

            String result = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

            LOGGER.info(String.format("Message was successfully decrypted - message: %s", result));

            return result;

        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            LOGGER.error(String.format("%s was caught", ex.getClass().getName()), ex);

            throw new DecryptionException("An error occurred during decryption", ex);
        }
    }

    /**
     * The given byte array is encrypted or decrypted according the value defined by the mode parameter. The byte array
     * is divided into blocks of size {@link RSACryptographyService#ENCRYPT_BLOCK_LENGTH}
     * or {@link RSACryptographyService#DECRYPT_BLOCK_LENGTH} or smaller according to the mode. The blocks are then
     * concatenated and returned.
     *
     * @param bytes to be encrypted/decrypted byte array
     * @param mode {@link Cipher#ENCRYPT_MODE} or {@link Cipher#DECRYPT_MODE}
     * @return encrypted/decrypted byte array
     * @throws IllegalBlockSizeException thrown when the bytes block is too big
     * @throws BadPaddingException thrown when the padding is malformed during decryption
     */
    private byte[] cypherBlocks(byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException{
        int blockLen = (mode == Cipher.ENCRYPT_MODE)? ENCRYPT_BLOCK_LENGTH : DECRYPT_BLOCK_LENGTH;
        int currBlockStartIdx=0;
        int currBlockEndIdx=Math.min(blockLen, bytes.length);
        byte[] result = new byte[0];
        byte[] buffer;
        byte[] cypherBuffer;

        while (currBlockStartIdx<bytes.length) {
            buffer = Arrays.copyOfRange(bytes, currBlockStartIdx, currBlockEndIdx);
            cypherBuffer = cipher.doFinal(buffer);
            result = concat(result,cypherBuffer);
            currBlockStartIdx = currBlockStartIdx+blockLen;
            currBlockEndIdx = Math.min(currBlockStartIdx+blockLen, bytes.length);
        }

        return result;
    }

    /**
     * Contacts two byte arrays
     * @param first first array
     * @param second second array
     * @return concatenated array
     */
    private static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }
}
