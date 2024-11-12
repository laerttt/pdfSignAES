package com.example;

import com.example.controller.PdfController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Display menu options for PDF processing or decryption
        System.out.print("1. Process PDF\t\t2. Decrypt PDF\n[1/2]: ");
        int input = scanner.nextInt(); // Read user choice

        if (input == 1) {
            // Initialize PdfController and handle PDF processing
            PdfController controller = new PdfController();
            controller.handlePdfProcessing();
        } else if (input == 2) {
            // Initialize PdfController and handle PDF decrypting
            PdfController controller = new PdfController();
            controller.handleDecrypting();
        } else {
            // Inform user of invalid input
            System.out.println("Wrong Input!");
        }
    }
}