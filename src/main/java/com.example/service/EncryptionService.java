package com.example.service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;

public class EncryptionService {
    private final SecretKey aesKey;

    public EncryptionService(byte[] aesKeyBytes) throws Exception {
        this.aesKey = new SecretKeySpec(aesKeyBytes, "AES");
    }

    /// Return the encrypted data
    public byte[] encrypt(byte[] content) throws Exception {
        // Create a Cipher instance for AES encryption
        Cipher cipher = Cipher.getInstance("AES");
        // Initialize the cipher in encryption mode, using the generated AES key
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        // Encrypt the input byte array and return the encrypted data
        return cipher.doFinal(content);
    }

    /// Return the temporary file containing the encrypted PDF content
    public File saveEncryptedPdf(byte[] encryptedContent) throws Exception {
        // Create a temporary file with a prefix "encrypted_pdf_" and ".pdf" extension
        File tempFile = File.createTempFile("encrypted_pdf_", ".pdf");
        // Use a FileOutputStream in a try block to ensure it is closed automatically
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            // Write the encrypted content to the temporary file
            fos.write(encryptedContent);
        }
        // Return the temporary file containing the encrypted PDF content
        return tempFile;
    }
}