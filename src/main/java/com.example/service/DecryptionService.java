package com.example.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

public class DecryptionService {
    private final SecretKey aesKey;

    /**
     * Constructor to initialize the DecryptionService with an AES key.
     * This constructor allows initializing with a byte array representing the AES key.
     *
     * @param aesKeyBytes byte array representing the AES encryption key
     */
    public DecryptionService(byte[] aesKeyBytes) throws Exception {
        // Convert the byte array into a SecretKey object for AES decryption
        this.aesKey = new SecretKeySpec(aesKeyBytes, "AES");
    }

    /**
     * Decrypts an encrypted byte array using AES decryption.
     *
     * @param encryptedContent byte array containing the encrypted data
     * @return byte array containing the decrypted data
     * @throws Exception if there are any issues with decryption
     */
    public byte[] decrypt(byte[] encryptedContent) throws Exception {
        // Create a Cipher instance for AES decryption
        Cipher cipher = Cipher.getInstance("AES");

        // Initialize the cipher in decryption mode, using the stored AES key
        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        // Decrypt the encrypted byte array and return the decrypted data
        return cipher.doFinal(encryptedContent);
    }

    /**
     * Reads encrypted content from a file, decrypts it, and saves it to a specified output file.
     *
     * @param encryptedFile File object representing the encrypted file to decrypt
     * @param outputFile File object representing where to save the decrypted content
     * @return File object containing the decrypted content
     * @throws Exception if there are any issues with reading, decrypting, or saving the data
     */
    public File decryptPdfFile(File encryptedFile, File outputFile) throws Exception {
        // Read encrypted content from the input file
        byte[] encryptedContent;
        try (FileInputStream fis = new FileInputStream(encryptedFile)) {
            encryptedContent = fis.readAllBytes();
        }

        // Decrypt the content
        byte[] decryptedContent = decrypt(encryptedContent);

        // Save decrypted content to the output file
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(decryptedContent);
        }

        // Return the file containing the decrypted PDF content
        return outputFile;
    }
}