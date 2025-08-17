package boundary;

import control.PatientManagement;
import java.util.Scanner;
import utility.StringUtility;

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
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        PATIENT QUEUE MANAGEMENT");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . Register New Patient");
            System.out.println("2 . Select Existing Patient");
            System.out.println("3 . View Waiting Queue");
            System.out.println("4 . Remove Patient from Queue");
            System.out.println("5 . View Next Patient in Queue");
            System.out.println("6 . Clear Patient Queue");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 6);
            
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
                case 5:
                    patientManagement.viewNextPatientInQueue();
                    break;
                case 6:
                    patientManagement.clearPatientQueue();
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
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        PATIENT RECORDS MANAGEMENT");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . View All Patients (Sorted by ID)");
            System.out.println("2 . Search Patient by ID");
            System.out.println("3 . Search Patient by Name");
            System.out.println("4 . Search Patient Consultations");
            System.out.println("5 . Search Patient Treatments");
            System.out.println("6 . Search Patient Prescriptions");
            System.out.println("7 . Update Patient Information");
            System.out.println("8 . Remove Patient");
            System.out.println("9 . Clear All Patient Records");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 9);
            
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
                    patientManagement.searchPatientConsultations();
                    break;
                case 5:
                    patientManagement.searchPatientTreatments();
                    break;
                case 6:
                    patientManagement.searchPatientPrescriptions();
                    break;
                case 7:
                    patientManagement.updatePatientInformation();
                    break;
                case 8:
                    patientManagement.removePatient();
                    break;
                case 9:
                    patientManagement.clearSet();
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

