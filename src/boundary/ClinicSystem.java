package boundary;

import control.ConsultationManagement;
import control.DoctorManagement;
import control.PatientManagement;
import control.PharmacyManagement;
import control.ReportManagement;
import control.TreatmentManagement;
import java.util.Scanner;

public class ClinicSystem {
    private PatientManagementUI patientUI;
    private DoctorManagementUI doctorUI;
    private ConsultationManagementUI consultationUI;
    private TreatmentManagementUI treatmentUI;
    private ReportManagementUI reportUI;
    private PharmacyManagementUI pharmacyUI;
    private Scanner scanner;
    
    public ClinicSystem() {
        scanner = new Scanner(System.in);
        initializeManagers();
    }
    
    private void initializeManagers() {
        PatientManagement patientManagement = new PatientManagement();
        DoctorManagement doctorManagement = new DoctorManagement();
        ConsultationManagement consultationManagement = new ConsultationManagement();
        TreatmentManagement treatmentManagement = new TreatmentManagement();
        ReportManagement reportManagement = new ReportManagement();
        PharmacyManagement pharmacyManagement = new PharmacyManagement();
        
        patientUI = new PatientManagementUI(patientManagement);
        doctorUI = new DoctorManagementUI(doctorManagement);
        consultationUI = new ConsultationManagementUI(consultationManagement);
        treatmentUI = new TreatmentManagementUI(treatmentManagement);
        reportUI = new ReportManagementUI(reportManagement);
        pharmacyUI = new PharmacyManagementUI(pharmacyManagement);

        //connect to other controllers
        consultationUI.setDependencies(patientManagement, doctorManagement, treatmentManagement);
        consultationManagement.setDoctorManagement(doctorManagement);
        consultationManagement.setPatientManagement(patientManagement);
        patientManagement.setConsultationManagement(consultationManagement);
        doctorManagement.setConsultationManagement(consultationManagement);
        reportUI.setDependencies(patientManagement, doctorManagement, consultationManagement, treatmentManagement);
        pharmacyUI.setDependencies(treatmentManagement);
        pharmacyManagement.setTreatmentManagement(treatmentManagement);
        treatmentManagement.setPatientManagement(patientManagement);
        treatmentManagement.setDoctorManagement(doctorManagement);
        
        //initialize entity relationships for sample data
        consultationManagement.initializeEntityRelationships();
        treatmentManagement.initializeEntityRelationships();
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getUserInputInt(0, 11);
            
            switch (choice) {
                case 1:
                    patientUI.managePatientQueue();
                    break;
                case 2:
                    patientUI.managePatientRecords();
                    break;
                case 3:
                    doctorUI.manageDoctorsOnDuty();
                    break;
                case 4:
                    doctorUI.manageDoctorAvailability();
                    break;
                case 5:
                    consultationUI.conductConsultation();
                    break;
                case 6:
                    consultationUI.manageConsultationHistory();
                    break;
                case 7:
                    treatmentUI.manageTreatmentHistory();
                    break;
                case 8:
                    reportUI.generateMedicalReports();
                    break;
                case 9:
                    pharmacyUI.managePharmacyOperations();
                    break;
                case 10:
                    pharmacyUI.generatePharmacyReports();
                    break;
                case 11:
                    pharmacyUI.processPayment();
                    break;
                case 0:
                    System.out.println("Thank you for using Integrated Clinic Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            System.out.println();
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + utility.StringUtility.repeatString("=", 60));
        System.out.println("        CHUBBY CLINIC SYSTEM");
        System.out.println(utility.StringUtility.repeatString("=", 60));
        System.out.println("1 . Patient Registration & Queue Management");
        System.out.println("2 . Patient Records & Search");
        System.out.println("3 . Doctor Information & Duty Management");
        System.out.println("4 . Doctor Availability & Schedule");
        System.out.println("5 . Conduct Consultation (Diagnose & Prescribe)");
        System.out.println("6 . Consultation History & Appointments");
        System.out.println("7 . Treatment History & Diagnosis Records");
        System.out.println("8 . Medical Reports & Analytics");
        System.out.println("9 . Medicine Dispensing & Stock Control");
        System.out.println("10. Pharmacy Reports & Inventory");
        System.out.println("11. Process Payment");
        System.out.println("0 . Exit System");
        System.out.println(utility.StringUtility.repeatString("-", 60));
        System.out.print("Enter your choice: ");
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
    
    public static void main(String[] args) {
        ClinicSystem clinicSystem = new ClinicSystem();
        clinicSystem.run();
    }
} 