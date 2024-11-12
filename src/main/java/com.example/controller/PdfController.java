package com.example.controller;

import com.example.service.DecryptionService;
import com.example.service.PdfService;
import java.util.Scanner;

public class PdfController {
    // Redundant
    public PdfController() throws Exception {
    }
    /// Method to handle PDF processing by prompting the user for input
    public void handlePdfProcessing() throws Exception {
        // Use normal (encrypting) pdf service constructor
        PdfService pdfService = new PdfService();
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

        // call pdfService (decrypting constructor)
        PdfService pdfService = new PdfService(aesKey);
        // Prompt for encrypted file path
        System.out.print("\nEnter encrypted file path: ");
        String inPath = input.nextLine();
        //Promt for output location
        System.out.print("\nEnter output file path: ");
        String outPath = input.nextLine();
        // Close the scanner to free up resources
        input.close();

        // Call the decrypt method in PdfService to decrypt the file
        pdfService.decrypt(inPath, outPath);
    }
}