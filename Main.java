import com.example.controller.PdfController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter AES key: ");
        //PLACEHOLDER for convert AES Key to byte[]
        String secretKey = scanner.nextLine();
        PdfController controller = new PdfController();

        controller.handlePdfProcessing();
    }
}
