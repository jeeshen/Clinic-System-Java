package boundary;

import java.util.Scanner;
import entity.Patient;

public class PatientManagementUI {
    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Add new product");
        System.out.println("2. List all products");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }
}
