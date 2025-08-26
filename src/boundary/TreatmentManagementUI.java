package boundary;

import control.TreatmentManagement;
import java.util.Scanner;
import utility.StringUtility;

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
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        TREATMENT MANAGEMENT");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . View All Prescriptions (Sorted by Date)");
            System.out.println("2 . Search Prescription by ID");
            System.out.println("3 . Search Prescriptions by Patient");
            System.out.println("4 . Search Prescriptions by Doctor");
            System.out.println("5 . Update Prescription");
            System.out.println("6 . Remove Prescription");
            System.out.println("7 . View All Treatments");
            System.out.println("8 . Update Treatment");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 8);

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
                case 5:
                    treatmentManagement.updatePrescription();
                    break;
                case 6:
                    treatmentManagement.removePrescription();
                    break;
                case 7:
                    treatmentManagement.displayAllTreatments();
                    break;
                case 8:
                    treatmentManagement.updateTreatment();
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
} 