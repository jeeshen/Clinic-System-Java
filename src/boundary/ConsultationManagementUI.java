package boundary;

import control.ConsultationManagement;
import control.PatientManagement;
import control.DoctorManagement;
import control.TreatmentManagement;
import java.util.Scanner;

public class ConsultationManagementUI {
    private ConsultationManagement consultationManagement;
    private PatientManagement patientManagement;
    private DoctorManagement doctorManagement;
    private TreatmentManagement treatmentManagement;
    private Scanner scanner;
    
    public ConsultationManagementUI(ConsultationManagement consultationManagement) {
        this.consultationManagement = consultationManagement;
        this.scanner = new Scanner(System.in);
    }
    
    public void setDependencies(PatientManagement patientManagement, DoctorManagement doctorManagement, TreatmentManagement treatmentManagement) {
        this.patientManagement = patientManagement;
        this.doctorManagement = doctorManagement;
        this.treatmentManagement = treatmentManagement;
    }
    
    public void conductConsultation() {
        if (patientManagement == null || doctorManagement == null || treatmentManagement == null) {
            System.out.println("Error: Dependencies not set!");
            return;
        }
        consultationManagement.conductConsultation(patientManagement, doctorManagement, treatmentManagement);
    }
    
    public void manageConsultationHistory() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + repeatString("=", 50));
            System.out.println("        CONSULTATION HISTORY MANAGEMENT");
            System.out.println(repeatString("=", 50));
            System.out.println("1 . View All Consultations (Sorted by Date)");
            System.out.println("2 . Search Consultation by ID");
            System.out.println("3 . Search Consultations by Doctor");
            System.out.println("4 . Search Consultations by Patient");
            System.out.println("5 . Consultation Statistics Report");
            System.out.println("6 . Doctor Performance Report");
            System.out.println("0 . Back to Main Menu");
            System.out.println(repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 6);
            
            switch (choice) {
                case 1:
                    consultationManagement.displayAllConsultationsSorted();
                    break;
                case 2:
                    consultationManagement.searchConsultationById();
                    break;
                case 3:
                    consultationManagement.searchConsultationsByDoctor();
                    break;
                case 4:
                    consultationManagement.searchConsultationsByPatient();
                    break;
                case 5:
                    consultationManagement.generateConsultationStatisticsReport();
                    break;
                case 6:
                    consultationManagement.generateDoctorPerformanceReport();
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