package boundary;

import control.DoctorManagement;
import java.util.Scanner;
import utility.StringUtility;

public class DoctorManagementUI {
    private DoctorManagement doctorManagement;
    private Scanner scanner;
    
    public DoctorManagementUI(DoctorManagement doctorManagement) {
        this.doctorManagement = doctorManagement;
        this.scanner = new Scanner(System.in);
    }
    
    public void manageDoctorsOnDuty() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        DOCTORS ON DUTY MANAGEMENT");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . View All Doctors");
            System.out.println("2 . View Doctors on Duty");
            System.out.println("3 . Add Doctor to Duty");
            System.out.println("4 . Remove Doctor from Duty");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 4);
            
            switch (choice) {
                case 1:
                    doctorManagement.displayAllDoctors();
                    break;
                case 2:
                    doctorManagement.displayDoctorsOnDuty();
                    break;
                case 3:
                    doctorManagement.addDoctorToDuty();
                    break;
                case 4:
                    doctorManagement.removeDoctorFromDuty();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    public void manageDoctorAvailability() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        DOCTOR AVAILABILITY MANAGEMENT");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . View All Doctors (Sorted by ID)");
            System.out.println("2 . Search Doctor by ID");
            System.out.println("3 . Search Doctor by Specialization");
            System.out.println("4 . Update Doctor Details");
            System.out.println("5 . Update Doctor Schedule");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 5);
            
            switch (choice) {
                case 1:
                    doctorManagement.displayAllDoctorsSorted();
                    break;
                case 2:
                    doctorManagement.searchDoctorById();
                    break;
                case 3:
                    doctorManagement.searchDoctorBySpecialization();
                    break;
                case 4:
                    doctorManagement.updateDoctorDetails();
                    break;
                case 5:
                    doctorManagement.updateDoctorSchedule();
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