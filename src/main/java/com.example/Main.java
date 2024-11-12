package com.example;

import com.example.controller.PdfController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1. Process PDF\t\t2. Decrypt PDF\n[1/2]: ");
        int input = scanner.nextInt();
        if (input == 1){
            PdfController controller = new PdfController();
            controller.handlePdfProcessing();
        } else if (input == 2) {
            //PLACEHOLDER for convert AES Key to byte[]

        } else {
            System.out.println("Wrong Input!");

        }

    }
}
