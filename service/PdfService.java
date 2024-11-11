package service;

import database.DatabaseConnection;
import java.io.File;
import java.io.IOException;

public class PdfService {

    private final DatabaseConnection databaseConnection;
    private final EncryptionService encryptionService;
    //private final SignatureService signatureService;

    public PdfService() throws Exception {
        //this.signatureService = new SignatureService();
        this.encryptionService = new EncryptionService();
        this.databaseConnection = new DatabaseConnection();

    }

    public void processPdf(String pdfPath) throws Exception {
        File pdfFile = new File(pdfPath);
        if (!pdfFile.exists() || !pdfFile.isFile()) {
            throw new IOException("File not found: " + pdfPath);
        }

        // TODO Create signature service which gets path and returns byte[] of signed pdf

        // TODO Encrypt PDF with AES-256 using encryptionService
//        byte[] encryptedPdf = encryptionService.encrypt(signedPdf);
//
//        // Store encrypted file in a secure temporary location
//        File encryptedFile = encryptionService.saveEncryptedPdf(encryptedPdf);
//
//        // Store metadata in the database
//        PdfMetadata metadata = new PdfMetadata(pdfFile.getName(), encryptedFile.getAbsolutePath());
//        databaseConnection.storeMetadata(metadata);
  }
}
