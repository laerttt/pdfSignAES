package controller;

import service.PdfService;
import java.util.Scanner;

public class PdfController {
    private final PdfService pdfService;
    /// Constructor initializes the PdfService instance
    public PdfController() throws Exception {
        this.pdfService = new PdfService();
    }
    /// Method to handle PDF processing by prompting the user for input
    public void handlePdfProcessing() {
        // Read PDF file path from input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the PDF file: ");
        String pdfPath = scanner.nextLine();
        // Close the scanner to free up resources
        scanner.close();
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

