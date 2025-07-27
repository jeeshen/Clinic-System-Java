package boundary;

import control.ConsultationManagement;
import entity.Consultation;
import utility.StringUtility;
import java.util.Scanner;
import utility.InputValidator;

public class ConsultationManagementUI {
    private Scanner scanner;
    private ConsultationManagement consultationManagement;
    
    public ConsultationManagementUI(ConsultationManagement consultationManagement) {
        this.scanner = new Scanner(System.in);
        this.consultationManagement = consultationManagement;
    }
    
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== CONSULTATION MANAGEMENT SYSTEM ===");
            System.out.println("1  . Schedule New Consultation");
            System.out.println("2  . Update Consultation Status");
            System.out.println("3  . Update Consultation Notes");
            System.out.println("4  . Schedule Follow-up Appointment");
            System.out.println("5  . List All Consultations");
            System.out.println("6  . View Consultations by Status");
            System.out.println("7  . View Consultations by Doctor");
            System.out.println("8  . View Consultations by Patient");
            System.out.println("9  . View Scheduled Consultations");
            System.out.println("10 . View Completed Consultations");
            System.out.println("11 . Generate Consultation Report");
            System.out.println("0  . Return to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = InputValidator.getValidInt(scanner, 0, 11, "");
            
            switch (choice) {
                case 1:
                    scheduleConsultationMenu();
                    break;
                case 2:
                    updateStatusMenu();
                    break;
                case 3:
                    updateNotesMenu();
                    break;
                case 4:
                    scheduleFollowUpMenu();
                    break;
                case 5:
                    listConsultationsMenu();
                    break;
                case 6:
                    viewByStatusMenu();
                    break;
                case 7:
                    viewByDoctorMenu();
                    break;
                case 8:
                    viewByPatientMenu();
                    break;
                case 9:
                    viewScheduledConsultationsMenu();
                    break;
                case 10:
                    viewCompletedConsultationsMenu();
                    break;
                case 11:
                    generateReportMenu();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void scheduleConsultationMenu() {
        System.out.println("\n=== SCHEDULE NEW CONSULTATION ===");
        
        Consultation consultation = new Consultation();
        
        System.out.print("Enter Consultation ID: ");
        consultation.setConsultationId(scanner.nextLine());
        
        //show available patient IDs
        showAvailablePatientIds();
        
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        consultation.setPatientId(patientId);
        
        //check if patient exists
        if (!consultationManagement.patientExists(patientId)) {
            System.out.println("Patient not found. Please register the patient first.");
            return;
        }
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        consultation.setDoctorId(doctorId);
        
        //check if doctor exists
        if (!consultationManagement.doctorExists(doctorId)) {
            System.out.println("Doctor not found. Please register the doctor first.");
            return;
        }
        
        System.out.print("Enter Consultation Date (dd-MM-yyyy): ");
        consultation.setConsultationDate(scanner.nextLine());
        
        System.out.print("Enter Status (Scheduled/In Progress/Completed/Cancelled): ");
        consultation.setStatus(scanner.nextLine());
        
        System.out.print("Enter Notes: ");
        consultation.setNotes(scanner.nextLine());
        
        System.out.print("Enter Next Appointment Date (dd-MM-yyyy) or leave empty: ");
        String nextAppointment = scanner.nextLine();
        consultation.setNextAppointmentDate(nextAppointment.isEmpty() ? null : nextAppointment);
        
        boolean success = consultationManagement.scheduleConsultation(consultation);
        
        if (success) {
            System.out.println("Consultation scheduled successfully!");
        } else {
            System.out.println("Failed to schedule consultation. Consultation ID may already exist.");
        }
    }
    
    private void updateStatusMenu() {
        System.out.println("\n=== UPDATE CONSULTATION STATUS ===");
        
        //show available consultation IDs
        showAvailableConsultationIds();
        
        System.out.print("Enter Consultation ID: ");
        String consultationId = scanner.nextLine();
        
        System.out.print("Enter new Status (Scheduled/In Progress/Completed/Cancelled): ");
        String status = scanner.nextLine();
        
        boolean success = consultationManagement.updateConsultationStatus(consultationId, status);
        
        if (success) {
            System.out.println("Consultation status updated successfully!");
        } else {
            System.out.println("Failed to update consultation status. Consultation not found.");
        }
    }
    
    private void updateNotesMenu() {
        System.out.println("\n=== UPDATE CONSULTATION NOTES ===");
        
        //show available consultation IDs
        showAvailableConsultationIds();
        
        System.out.print("Enter Consultation ID: ");
        String consultationId = scanner.nextLine();
        
        System.out.print("Enter new Notes: ");
        String notes = scanner.nextLine();
        
        boolean success = consultationManagement.updateConsultationNotes(consultationId, notes);
        
        if (success) {
            System.out.println("Consultation notes updated successfully!");
        } else {
            System.out.println("Failed to update consultation notes. Consultation not found.");
        }
    }
    
    private void scheduleFollowUpMenu() {
        System.out.println("\n=== SCHEDULE FOLLOW-UP APPOINTMENT ===");
        
        //show available consultation IDs
        showAvailableConsultationIds();
        
        System.out.print("Enter Consultation ID: ");
        String consultationId = scanner.nextLine();
        
        System.out.print("Enter Follow-up Date (dd-MM-yyyy): ");
        String followUpDate = scanner.nextLine();
        
        boolean success = consultationManagement.scheduleFollowUp(consultationId, followUpDate);
        
        if (success) {
            System.out.println("Follow-up appointment scheduled successfully!");
        } else {
            System.out.println("Failed to schedule follow-up appointment. Consultation not found.");
        }
    }
    
    private void listConsultationsMenu() {
        System.out.println("\n=== LIST ALL CONSULTATIONS ===");
        
        Consultation[] consultations = consultationManagement.getAllConsultations();
        
        if (consultations.length == 0) {
            System.out.println("No consultations found.");
        } else {
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tStatus\t\tNext Appointment");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Consultation consultation : consultations) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-15s\t%s\n",
                    consultation.getConsultationId(),
                    consultation.getPatientId(),
                    consultation.getDoctorId(),
                    consultation.getConsultationDate(),
                    consultation.getStatus(),
                    consultation.getNextAppointmentDate() != null ? consultation.getNextAppointmentDate() : "None");
            }
        }
    }
    
    private void viewByStatusMenu() {
        System.out.println("\n=== VIEW CONSULTATIONS BY STATUS ===");
        System.out.print("Enter Status to search (Scheduled/In Progress/Completed/Cancelled): ");
        String status = scanner.nextLine();
        
        Consultation[] results = consultationManagement.getConsultationsByStatus(status);
        
        if (results.length == 0) {
            System.out.println("No consultations found with this status.");
        } else {
            System.out.println("Consultations with Status: " + status);
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tStatus\t\tNext Appointment");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Consultation consultation : results) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-15s\t%s\n",
                    consultation.getConsultationId(),
                    consultation.getPatientId(),
                    consultation.getDoctorId(),
                    consultation.getConsultationDate(),
                    consultation.getStatus(),
                    consultation.getNextAppointmentDate() != null ? consultation.getNextAppointmentDate() : "None");
            }
        }
    }
    
    private void viewByDoctorMenu() {
        System.out.println("\n=== VIEW CONSULTATIONS BY DOCTOR ===");
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        
        Consultation[] results = consultationManagement.getConsultationsByDoctor(doctorId);
        
        if (results.length == 0) {
            System.out.println("No consultations found for this doctor.");
        } else {
            System.out.println("Consultations for Doctor ID: " + doctorId);
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tStatus\t\tNext Appointment");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Consultation consultation : results) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-15s\t%s\n",
                    consultation.getConsultationId(),
                    consultation.getPatientId(),
                    consultation.getDoctorId(),
                    consultation.getConsultationDate(),
                    consultation.getStatus(),
                    consultation.getNextAppointmentDate() != null ? consultation.getNextAppointmentDate() : "None");
            }
        }
    }
    
    private void viewByPatientMenu() {
        System.out.println("\n=== VIEW CONSULTATIONS BY PATIENT ===");
        
        //show available patient IDs
        showAvailablePatientIds();
        
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        
        Consultation[] results = consultationManagement.getConsultationsByPatient(patientId);
        
        if (results.length == 0) {
            System.out.println("No consultations found for this patient.");
        } else {
            System.out.println("Consultations for Patient ID: " + patientId);
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tStatus\t\tNext Appointment");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Consultation consultation : results) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-15s\t%s\n",
                    consultation.getConsultationId(),
                    consultation.getPatientId(),
                    consultation.getDoctorId(),
                    consultation.getConsultationDate(),
                    consultation.getStatus(),
                    consultation.getNextAppointmentDate() != null ? consultation.getNextAppointmentDate() : "None");
            }
        }
    }
    
    private void viewScheduledConsultationsMenu() {
        System.out.println("\n=== SCHEDULED CONSULTATIONS ===");
        
        Consultation[] scheduledConsultations = consultationManagement.getScheduledConsultations();
        
        if (scheduledConsultations.length == 0) {
            System.out.println("No scheduled consultations found.");
        } else {
            System.out.println("Scheduled Consultations:");
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tStatus\t\tNext Appointment");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Consultation consultation : scheduledConsultations) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-15s\t%s\n",
                    consultation.getConsultationId(),
                    consultation.getPatientId(),
                    consultation.getDoctorId(),
                    consultation.getConsultationDate(),
                    consultation.getStatus(),
                    consultation.getNextAppointmentDate() != null ? consultation.getNextAppointmentDate() : "None");
            }
        }
    }
    
    private void viewCompletedConsultationsMenu() {
        System.out.println("\n=== COMPLETED CONSULTATIONS ===");
        
        Consultation[] completedConsultations = consultationManagement.getCompletedConsultations();
        
        if (completedConsultations.length == 0) {
            System.out.println("No completed consultations found.");
        } else {
            System.out.println("Completed Consultations:");
            System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tStatus\t\tNext Appointment");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Consultation consultation : completedConsultations) {
                System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-15s\t%s\n",
                    consultation.getConsultationId(),
                    consultation.getPatientId(),
                    consultation.getDoctorId(),
                    consultation.getConsultationDate(),
                    consultation.getStatus(),
                    consultation.getNextAppointmentDate() != null ? consultation.getNextAppointmentDate() : "None");
            }
        }
    }
    
    private void generateReportMenu() {
        System.out.println("\n=== GENERATE CONSULTATION REPORT ===");
        
        String report = consultationManagement.generateConsultationReport();
        System.out.println(report);
    }
    
    // Helper method to display available consultation IDs
    private void showAvailableConsultationIds() {
        Consultation[] consultations = consultationManagement.getAllConsultations();
        
        if (consultations.length == 0) {
            System.out.println("No consultations available in the system.");
            return;
        }
        
        System.out.println("Available Consultation IDs:");
        System.out.println("ID\t\tPatient ID\tDoctor ID\tDate\t\tStatus");
        System.out.println(StringUtility.repeatString("-", 80));
        
        for (Consultation consultation : consultations) {
            System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%s\n",
                consultation.getConsultationId(),
                consultation.getPatientId(),
                consultation.getDoctorId(),
                consultation.getConsultationDate(),
                consultation.getStatus());
        }
        System.out.println();
    }
    
    // Helper method to display available patient IDs
    private void showAvailablePatientIds() {
        // This would need to be implemented in ConsultationManagement or we need to pass PatientManagement
        // For now, we'll show a message
        System.out.println("Note: Please ensure the patient ID exists in the patient management system.");
    }
    
    // Helper method to display available doctor IDs
    private void showAvailableDoctorIds() {
        // This would need to be implemented in ConsultationManagement or we need to pass DoctorManagement
        // For now, we'll show a message
        System.out.println("Note: Please ensure the doctor ID exists in the doctor management system.");
    }
} 