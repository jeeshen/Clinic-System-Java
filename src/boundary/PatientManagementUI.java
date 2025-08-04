package boundary;

import control.PatientManagement;
import java.util.Scanner;

public class PatientManagementUI {
    private PatientManagement patientManagement;
    private Scanner scanner;
    
    public PatientManagementUI(PatientManagement patientManagement) {
        this.patientManagement = patientManagement;
        this.scanner = new Scanner(System.in);
    }
    
    public void managePatientQueue() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + repeatString("=", 50));
            System.out.println("        PATIENT QUEUE MANAGEMENT");
            System.out.println(repeatString("=", 50));
            System.out.println("1 . Register New Patient");
            System.out.println("2 . Select Existing Patient");
            System.out.println("3 . View Waiting Queue");
            System.out.println("4 . Remove Patient from Queue");
            System.out.println("0 . Back to Main Menu");
            System.out.println(repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 4);
            
            switch (choice) {
                case 1:
                    patientManagement.registerNewPatient();
                    break;
                case 2:
                    patientManagement.selectExistingPatient();
                    break;
                case 3:
                    patientManagement.displayWaitingQueue();
                    break;
                case 4:
                    patientManagement.removePatientFromQueue();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    public void managePatientRecords() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + repeatString("=", 50));
            System.out.println("        PATIENT RECORDS MANAGEMENT");
            System.out.println(repeatString("=", 50));
            System.out.println("1 . View All Patients (Sorted by ID)");
            System.out.println("2 . Search Patient by ID");
            System.out.println("3 . Search Patient by Name");
            System.out.println("4 . Update Patient Information");
            System.out.println("0 . Back to Main Menu");
            System.out.println(repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 4);
            
            switch (choice) {
                case 1:
                    patientManagement.displayAllPatientsSorted();
                    break;
                case 2:
                    patientManagement.searchPatientById();
                    break;
                case 3:
                    patientManagement.searchPatientByName();
                    break;
                case 4:
                    patientManagement.updatePatientInformation();
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

