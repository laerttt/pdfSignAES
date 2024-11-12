package com.example.service;

import com.example.database.DatabaseConnection;
import com.example.model.PdfMetadata;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PdfService {
    //TODO: Create DecryptionService, recieve keys from pdfController give to both services.

    private final DatabaseConnection databaseConnection;
    private final EncryptionService encryptionService;
    private final SignatureService signatureService;
    private final DecryptionService decryptionService;  // Decryption service for handling decryption

//    public PdfService() throws Exception {
//        this.signatureService = new SignatureService();
//        this.encryptionService = new EncryptionService();  // Ensure EncryptionService uses the AES key
//        this.decryptionService = new DecryptionService();  // Use the same AES key for decryption
//        this.databaseConnection = new DatabaseConnection();
//    }
    public PdfService(byte[] aesKeyBytes) throws Exception {
        this.signatureService = new SignatureService();
        this.encryptionService = new EncryptionService();  // Ensure EncryptionService uses the AES key
        this.decryptionService = new DecryptionService(aesKeyBytes);  // Use the same AES key for decryption
        this.databaseConnection = new DatabaseConnection();
    }

    // Processes (signs and encrypts) a PDF file and stores metadata in the database
    public void processPdf(String pdfPath) throws Exception {
        File pdfFile = new File(pdfPath);

        if (!pdfFile.exists() || !pdfFile.isFile()) {
            throw new IOException("File not found: " + pdfPath);
        }

        // Step 1: Sign the PDF and get it as a byte array
        byte[] signedPdf = signatureService.signPdf(pdfPath);
        System.out.println("\n### signedPdf => " + signedPdf.length +"\n");

        // Step 2: Encrypt the signed PDF with AES-256
        byte[] encryptedPdf = encryptionService.encrypt(signedPdf);
        System.out.println("\n### encryptedPdf => " + encryptedPdf.length +"\n");

        // Step 3: Store the encrypted file in a secure temporary location
        File encryptedFile = encryptionService.saveEncryptedPdf(encryptedPdf);
        System.out.println("\n### encryptedFile => " + encryptedFile +"\n");

        // Step 4: Store metadata in the database
        PdfMetadata metadata = new PdfMetadata(pdfFile.getName(), encryptedFile.getAbsolutePath());
        System.out.println("\n### metadata => " + metadata.toString() +"\n");
        databaseConnection.storeMetadata(metadata);

        System.out.println("PDF processed, signed, encrypted, and metadata stored.");
    }

    // Decrypts a previously encrypted PDF file and saves it to the specified output path
    public void decryptPdf(String encryptedPdfPath, String outputPdfPath) throws Exception {
        File encryptedFile = new File(encryptedPdfPath);

        if (!encryptedFile.exists() || !encryptedFile.isFile()) {
            throw new IOException("Encrypted file not found: " + encryptedPdfPath);
        }

        // Step 1: Use PdfDecryptionService to decrypt the file
//        decryptionService.decryptPdf(encryptedPdfPath, outputPdfPath);
//
        System.out.println("PDF decrypted and saved to: " + outputPdfPath);
    }
}
