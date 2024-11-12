package com.example.service;

import com.example.database.DatabaseConnection;
import com.example.model.PdfMetadata;

import java.io.File;
import java.io.IOException;

public class PdfService {

    private DatabaseConnection databaseConnection;
    private EncryptionService encryptionService;
    private SignatureService signatureService;
    private DecryptionService decryptionService;

    public PdfService() throws Exception {
        this.signatureService = new SignatureService();
        this.encryptionService = new EncryptionService();  // Ensure EncryptionService uses the AES key
        this.databaseConnection = new DatabaseConnection();
    }
    public PdfService(String key) throws Exception{
         this.decryptionService = new DecryptionService(key);
    }

    // Processes (signs and encrypts) a PDF file and stores metadata in the database
    public void processPdf(String pdfPath) throws Exception {
        File pdfFile = new File(pdfPath);

        if (!pdfFile.exists() || !pdfFile.isFile()) {
            throw new IOException("File not found: " + pdfPath);
        }

        // Step 1: Sign the PDF and get it as a byte array
        byte[] signedPdf = signatureService.signPdf(pdfPath);

        // Step 2: Encrypt the signed PDF with AES-256
        byte[] encryptedPdf = encryptionService.encrypt(signedPdf);

        // Step 3: Store the encrypted file in a secure temporary location
        File encryptedFile = encryptionService.saveEncryptedPdf(encryptedPdf);

        // Step 4: Store metadata in the database
        PdfMetadata metadata = new PdfMetadata(pdfFile.getName(), encryptedFile.getAbsolutePath());
        databaseConnection.storeMetadata(metadata);

        System.out.println("PDF processed, signed, encrypted, and metadata stored.");
    }

    // Decrypts a previously encrypted PDF file and saves it to the specified output path
    // Decrypts a previously encrypted PDF file and saves it to the specified output path
    public void decrypt(String encryptedFilePath, String outputFilePath) throws Exception {
        // Create a File object for the encrypted file
        File encryptedFile = new File(encryptedFilePath);

        // Check if the encrypted file exists
        if (!encryptedFile.exists() || !encryptedFile.isFile()) {
            throw new IOException("Encrypted file not found: " + encryptedFilePath);
        }
        // Create a File object for the output file
        File outputFile = new File(outputFilePath);

        // Decrypt the PDF file and save it to the output location
        decryptionService.decryptPdfFile(encryptedFile, outputFile);

        System.out.println("PDF decrypted and saved to: " + outputFilePath);
    }
}
