package boundary;

import control.TreatmentManagement;
import java.util.Scanner;

public class TreatmentManagementUI {
    private TreatmentManagement treatmentManagement;
    private Scanner scanner;
    
    public TreatmentManagementUI(TreatmentManagement treatmentManagement) {
        this.treatmentManagement = treatmentManagement;
        this.scanner = new Scanner(System.in);
    }
    
    public void manageTreatmentHistory() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + repeatString("=", 50));
            System.out.println("        TREATMENT HISTORY MANAGEMENT");
            System.out.println(repeatString("=", 50));
            System.out.println("1 . View All Prescriptions (Sorted by Date)");
            System.out.println("2 . Search Prescription by ID");
            System.out.println("3 . Search Prescriptions by Patient");
            System.out.println("4 . Search Prescriptions by Doctor");
            System.out.println("0 . Back to Main Menu");
            System.out.println(repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 4);
            
            switch (choice) {
                case 1:
                    treatmentManagement.displayAllPrescriptionsSorted();
                    break;
                case 2:
                    treatmentManagement.searchPrescriptionById();
                    break;
                case 3:
                    treatmentManagement.searchPrescriptionsByPatient();
                    break;
                case 4:
                    treatmentManagement.searchPrescriptionsByDoctor();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    private int getUserInputInt(int min, int max) {
        int input;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number between " + min + " and " + max + ": ");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine();
            
            if (input < min || input > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (input < min || input > max);
        
        return input;
    }

    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
} 