package com.example.controller;

import com.example.service.PdfService;
import java.util.Scanner;

public class PdfController {
    private PdfService pdfService;

    /// Constructor initializes the PdfService instance
    public PdfController() throws Exception {
        this.pdfService = new PdfService();
    }

    public PdfController(String key) throws Exception {
        this.pdfService = new PdfService(key);
    }

    /// Method to handle PDF processing by prompting the user for input
    public void handlePdfProcessing() {
        // Read PDF file path from input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the PDF file: ");
        String pdfPath = scanner.nextLine();
        // Close the scanner to free up resources
        scanner.close();

        // Remove quotes if the path is surrounded by them
        if (pdfPath.startsWith("\"") && pdfPath.endsWith("\"")) {
            pdfPath = pdfPath.substring(1, pdfPath.length() - 1);
        }

        try {
            // Process the PDF file using the PdfService
            pdfService.processPdf(pdfPath);
            // Inform the user of successful processing
            System.out.println("PDF file processed successfully.");
        } catch (Exception e) {
            // Print an error message if an exception occurs during processing
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    /// Method to handle decrypting an encrypted PDF using AES key input
    public void handleDecrypting() throws Exception {
        Scanner input = new Scanner(System.in);

        // Prompt for AES decryption key
        System.out.print("Enter AES key (Base 64): ");
        String aesKey = input.nextLine();

        // Prompt for encrypted file path
        System.out.print("\nEnter encrypted file path: ");
        String path = input.nextLine();

        // Close the scanner to free up resources
        input.close();

        // Call the decrypt method in PdfService to decrypt the file
        pdfService.decrypt();
    }
}