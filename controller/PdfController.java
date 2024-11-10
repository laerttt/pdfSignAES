package controller;

import service.PdfService;
import java.util.Scanner;

public class PdfController {
    private final PdfService pdfService;

    public PdfController() {
        this.pdfService = new PdfService();
    }

    public void handlePdfProcessing() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the PDF file: ");
        String pdfPath = scanner.nextLine();

        try {
            pdfService.processPdf(pdfPath);
            System.out.println("PDF file processed successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}

