package boundary;

import control.TreatmentManagement;
import entity.Treatment;
import utility.StringUtility;
import java.util.Scanner;
import utility.InputValidator;

public class TreatmentManagementUI {
    private Scanner scanner;
    private TreatmentManagement treatmentManagement;
    
    public TreatmentManagementUI(TreatmentManagement treatmentManagement) {
        this.scanner = new Scanner(System.in);
        this.treatmentManagement = treatmentManagement;
    }
    
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== MEDICAL TREATMENT MANAGEMENT SYSTEM ===");
            System.out.println("1  . Add New Treatment");
            System.out.println("2  . Update Treatment Diagnosis");
            System.out.println("3  . Update Treatment Medications");
            System.out.println("4  . Schedule Follow-up Treatment");
            System.out.println("5  . List All Treatments");
            System.out.println("6  . View Treatments by Patient");
            System.out.println("7  . View Treatments by Doctor");
            System.out.println("8  . Search Treatments by Diagnosis");
            System.out.println("9  . View Treatments with Follow-ups");
            System.out.println("10 . View Recent Treatments");
            System.out.println("11 . Generate Treatment Report");
            System.out.println("12 . View Patient Treatment History");
            System.out.println("0  . Return to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = InputValidator.getValidInt(scanner, 0, 12, "");
            
            switch (choice) {
                case 1:
                    addTreatmentMenu();
                    break;
                case 2:
                    updateDiagnosisMenu();
                    break;
                case 3:
                    updateMedicationsMenu();
                    break;
                case 4:
                    scheduleFollowUpMenu();
                    break;
                case 5:
                    listTreatmentsMenu();
                    break;
                case 6:
                    viewByPatientMenu();
                    break;
                case 7:
                    viewByDoctorMenu();
                    break;
                case 8:
                    searchByDiagnosisMenu();
                    break;
                case 9:
                    viewWithFollowUpsMenu();
                    break;
                case 10:
                    viewRecentTreatmentsMenu();
                    break;
                case 11:
                    generateReportMenu();
                    break;
                case 12:
                    viewPatientHistoryMenu();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void addTreatmentMenu() {
        System.out.println("\n=== ADD NEW TREATMENT RECORD ===");
        
        Treatment treatment = new Treatment();
        
        System.out.print("Enter Treatment ID: ");
        treatment.setTreatmentId(scanner.nextLine());
        
        //show available patient IDs
        showAvailablePatientIds();
        
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        treatment.setPatientId(patientId);
        
        //check if patient exists
        if (!treatmentManagement.patientExists(patientId)) {
            System.out.println("Patient not found. Please register the patient first.");
            return;
        }
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        treatment.setDoctorId(doctorId);
        
        //check if doctor exists
        if (!treatmentManagement.doctorExists(doctorId)) {
            System.out.println("Doctor not found. Please register the doctor first.");
            return;
        }
        
        System.out.print("Enter Treatment Date (dd-MM-yyyy): ");
        treatment.setTreatmentDate(scanner.nextLine());
        
        System.out.print("Enter Diagnosis: ");
        treatment.setDiagnosis(scanner.nextLine());
        
        System.out.print("Enter Prescribed Medications: ");
        treatment.setPrescribedMedications(scanner.nextLine());
        
        System.out.print("Enter Follow-up Date (dd-MM-yyyy) or leave empty: ");
        String followUpDate = scanner.nextLine();
        treatment.setFollowUpDate(followUpDate.isEmpty() ? null : followUpDate);
        
        boolean success = treatmentManagement.addTreatment(treatment);
        
        if (success) {
            System.out.println("Treatment record added successfully!");
        } else {
            System.out.println("Failed to add treatment record. Treatment ID may already exist.");
        }
    }
    
    private void updateDiagnosisMenu() {
        System.out.println("\n=== UPDATE TREATMENT DIAGNOSIS ===");
        
        //show available treatment IDs
        showAvailableTreatmentIds();
        
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine();
        
        System.out.print("Enter new Diagnosis: ");
        String diagnosis = scanner.nextLine();
        
        boolean success = treatmentManagement.updateDiagnosis(treatmentId, diagnosis);
        
        if (success) {
            System.out.println("Treatment diagnosis updated successfully!");
        } else {
            System.out.println("Failed to update treatment diagnosis. Treatment not found.");
        }
    }
    
    private void updateMedicationsMenu() {
        System.out.println("\n=== UPDATE PRESCRIBED MEDICATIONS ===");
        
        //show available treatment IDs
        showAvailableTreatmentIds();
        
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine();
        
        System.out.print("Enter new Prescribed Medications: ");
        String medications = scanner.nextLine();
        
        boolean success = treatmentManagement.updatePrescribedMedications(treatmentId, medications);
        
        if (success) {
            System.out.println("Prescribed medications updated successfully!");
        } else {
            System.out.println("Failed to update prescribed medications. Treatment not found.");
        }
    }
    
    private void scheduleFollowUpMenu() {
        System.out.println("\n=== SCHEDULE FOLLOW-UP APPOINTMENT ===");
        
        //show available treatment IDs
        showAvailableTreatmentIds();
        
        System.out.print("Enter Treatment ID: ");
        String treatmentId = scanner.nextLine();
        
        System.out.print("Enter Follow-up Date (dd-MM-yyyy): ");
        String followUpDate = scanner.nextLine();
        
        boolean success = treatmentManagement.scheduleFollowUp(treatmentId, followUpDate);
        
        if (success) {
            System.out.println("Follow-up appointment scheduled successfully!");
        } else {
            System.out.println("Failed to schedule follow-up appointment. Treatment not found.");
        }
    }
    
    private void listTreatmentsMenu() {
        System.out.println("\n=== LIST ALL TREATMENTS ===");
        
        Treatment[] treatments = treatmentManagement.getAllTreatments();
        
        if (treatments.length == 0) {
            System.out.println("No treatments found.");
        } else {
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tDiagnosis\t\tMedications\t\tFollow-up");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Treatment treatment : treatments) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-20s\t%-20s\t%s\n",
                    treatment.getTreatmentId(),
                    treatment.getPatientId(),
                    treatment.getDoctorId(),
                    treatment.getTreatmentDate(),
                    treatment.getDiagnosis(),
                    treatment.getPrescribedMedications(),
                    treatment.getFollowUpDate() != null ? treatment.getFollowUpDate() : "None");
            }
        }
    }
    
    private void viewByPatientMenu() {
        System.out.println("\n=== VIEW TREATMENTS BY PATIENT ===");
        
        //show available patient IDs
        showAvailablePatientIds();
        
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        
        Treatment[] results = treatmentManagement.getTreatmentsByPatient(patientId);
        
        if (results.length == 0) {
            System.out.println("No treatments found for this patient.");
        } else {
            System.out.println("Treatments for Patient ID: " + patientId);
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tDiagnosis\t\tMedications\t\tFollow-up");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Treatment treatment : results) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-20s\t%-20s\t%s\n",
                    treatment.getTreatmentId(),
                    treatment.getPatientId(),
                    treatment.getDoctorId(),
                    treatment.getTreatmentDate(),
                    treatment.getDiagnosis(),
                    treatment.getPrescribedMedications(),
                    treatment.getFollowUpDate() != null ? treatment.getFollowUpDate() : "None");
            }
        }
    }
    
    private void viewByDoctorMenu() {
        System.out.println("\n=== VIEW TREATMENTS BY DOCTOR ===");
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        
        Treatment[] results = treatmentManagement.getTreatmentsByDoctor(doctorId);
        
        if (results.length == 0) {
            System.out.println("No treatments found for this doctor.");
        } else {
            System.out.println("Treatments for Doctor ID: " + doctorId);
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tDiagnosis\t\tMedications\t\tFollow-up");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Treatment treatment : results) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-20s\t%-20s\t%s\n",
                    treatment.getTreatmentId(),
                    treatment.getPatientId(),
                    treatment.getDoctorId(),
                    treatment.getTreatmentDate(),
                    treatment.getDiagnosis(),
                    treatment.getPrescribedMedications(),
                    treatment.getFollowUpDate() != null ? treatment.getFollowUpDate() : "None");
            }
        }
    }
    
    private void searchByDiagnosisMenu() {
        System.out.println("\n=== SEARCH TREATMENTS BY DIAGNOSIS ===");
        System.out.print("Enter diagnosis to search: ");
        String diagnosis = scanner.nextLine();
        
        Treatment[] results = treatmentManagement.searchTreatmentsByDiagnosis(diagnosis);
        
        if (results.length == 0) {
            System.out.println("No treatments found with this diagnosis.");
        } else {
            System.out.println("Treatments with Diagnosis: " + diagnosis);
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tDiagnosis\t\tMedications\t\tFollow-up");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Treatment treatment : results) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-20s\t%-20s\t%s\n",
                    treatment.getTreatmentId(),
                    treatment.getPatientId(),
                    treatment.getDoctorId(),
                    treatment.getTreatmentDate(),
                    treatment.getDiagnosis(),
                    treatment.getPrescribedMedications(),
                    treatment.getFollowUpDate() != null ? treatment.getFollowUpDate() : "None");
            }
        }
    }
    
    private void viewWithFollowUpsMenu() {
        System.out.println("\n=== TREATMENTS WITH FOLLOW-UP APPOINTMENTS ===");
        
        Treatment[] treatmentsWithFollowUp = treatmentManagement.getTreatmentsWithFollowUp();
        
        if (treatmentsWithFollowUp.length == 0) {
            System.out.println("No treatments with follow-up appointments found.");
        } else {
            System.out.println("Treatments with Follow-up Appointments:");
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tDiagnosis\t\tFollow-up Date");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Treatment treatment : treatmentsWithFollowUp) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-20s\t%s\n",
                    treatment.getTreatmentId(),
                    treatment.getPatientId(),
                    treatment.getDoctorId(),
                    treatment.getTreatmentDate(),
                    treatment.getDiagnosis(),
                    treatment.getFollowUpDate());
            }
        }
    }
    
    private void viewRecentTreatmentsMenu() {
        System.out.println("\n=== RECENT TREATMENTS ===");
        
        Treatment[] recentTreatments = treatmentManagement.getRecentTreatments();
        
        if (recentTreatments.length == 0) {
            System.out.println("No recent treatments found.");
        } else {
            System.out.println("Recent Treatments:");
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tDiagnosis\t\tMedications");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Treatment treatment : recentTreatments) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-20s\t%s\n",
                    treatment.getTreatmentId(),
                    treatment.getPatientId(),
                    treatment.getDoctorId(),
                    treatment.getTreatmentDate(),
                    treatment.getDiagnosis(),
                    treatment.getPrescribedMedications());
            }
        }
    }
    
    private void generateReportMenu() {
        System.out.println("\n=== GENERATE TREATMENT REPORT ===");
        
        String report = treatmentManagement.generateTreatmentReport();
        System.out.println(report);
    }
    
    private void viewPatientHistoryMenu() {
        System.out.println("\n=== GENERATE PATIENT TREATMENT HISTORY ===");
        
        //show available patient IDs
        showAvailablePatientIds();
        
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        
        String history = treatmentManagement.generatePatientTreatmentHistory(patientId);
        System.out.println(history);
    }
    
    // Helper method to display available treatment IDs
    private void showAvailableTreatmentIds() {
        Treatment[] treatments = treatmentManagement.getAllTreatments();
        
        if (treatments.length == 0) {
            System.out.println("No treatments available in the system.");
            return;
        }
        
        System.out.println("Available Treatment IDs:");
        System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tDiagnosis");
        System.out.println(StringUtility.repeatString("-", 80));
        
        for (Treatment treatment : treatments) {
            System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%s\n",
                treatment.getTreatmentId(),
                treatment.getPatientId(),
                treatment.getDoctorId(),
                treatment.getTreatmentDate(),
                treatment.getDiagnosis());
        }
        System.out.println();
    }
    
    // Helper method to display available patient IDs
    private void showAvailablePatientIds() {
        // This would need to be implemented in TreatmentManagement or we need to pass PatientManagement
        // For now, we'll show a message
        System.out.println("Note: Please ensure the patient ID exists in the patient management system.");
    }
    
    // Helper method to display available doctor IDs
    private void showAvailableDoctorIds() {
        // This would need to be implemented in TreatmentManagement or we need to pass DoctorManagement
        // For now, we'll show a message
        System.out.println("Note: Please ensure the doctor ID exists in the doctor management system.");
    }
} 