package com.example.controller;

import com.example.service.PdfService;
import java.util.Scanner;


//TODO: Create DecryptionService, integrate keys for bot services by user.
public class PdfController {
    private  final PdfService pdfService;
    /// Constructor initializes the PdfService instance
    public PdfController() throws Exception {
        this.pdfService = new PdfService();
    }
    public PdfController(int i){

    }
    /// Method to handle PDF processing by prompting the user for input
    public void handlePdfProcessing() {
        // Read PDF file path from input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the PDF file: ");
        String pdfPath = scanner.nextLine();
        // Close the scanner to free up resources
        scanner.close();
        if (pdfPath.startsWith("\"") && pdfPath.endsWith("\"")) {
            pdfPath= pdfPath.substring(1, pdfPath.length() - 1);
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
}

