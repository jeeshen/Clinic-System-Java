package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsultationManagement {
    private SetAndQueueInterface<Consultation> consultationList = new SetAndQueue<>();
    private Scanner scanner;
    private int consultationIdCounter = 2001;
    
    public ConsultationManagement() {
        scanner = new Scanner(System.in);
    }
    
    public void conductConsultation(PatientManagement patientManagement, DoctorManagement doctorManagement, TreatmentManagement treatmentManagement) {
        if (doctorManagement.getDoctorsOnDutyCount() == 0) {
            System.out.println("No doctors on duty! Please add doctors to duty first.");
            return;
        }
        
        if (patientManagement.isQueueEmpty()) {
            System.out.println("No patients in waiting queue! Please add patients to queue first.");
            return;
        }
        
        System.out.println("\n=== CONDUCT CONSULTATION ===");
        
        // Select doctor
        System.out.println("Doctors on duty:");
        Doctor[] doctorsArray = doctorManagement.getDoctorsOnDuty();
        for (int i = 0; i < doctorsArray.length; i++) {
            Doctor doctor = doctorsArray[i];
            System.out.println((i + 1) + ". " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
        }
        
        System.out.print("Select doctor (1-" + doctorsArray.length + "): ");
        int doctorChoice = getUserInputInt(1, doctorsArray.length);
        Doctor selectedDoctor = doctorsArray[doctorChoice - 1];
        
        // Get next patient from queue
        Patient currentPatient = patientManagement.getNextPatientFromQueue();
        if (currentPatient == null) {
            System.out.println("Error: No patient available in queue!");
            return;
        }
        currentPatient.setIsInWaiting(false);
        currentPatient.setCurrentStatus("in consultation");
        
        System.out.println("\nConsulting with patient: " + currentPatient.getName());
        System.out.println("Doctor: " + selectedDoctor.getName());
        
        // Get diagnosis
        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine();
        
        // Create consultation
        String consultationId = "CONS" + consultationIdCounter++;
        String consultationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        Consultation consultation = new Consultation(consultationId, 
                                                 String.valueOf(currentPatient.getId()),
                                                 selectedDoctor.getDoctorId(),
                                                 consultationDate, "completed", diagnosis, "");
        
        consultationList.add(consultation);
        
        // Create prescription through treatment management
        treatmentManagement.createPrescription(consultationId, currentPatient, selectedDoctor, diagnosis, consultationDate);
        
        currentPatient.setCurrentStatus("consulted");
        
        System.out.println("\nConsultation completed successfully!");
        System.out.println("Consultation ID: " + consultationId);
    }
    
    public void displayAllConsultationsSorted() {
        System.out.println("\n" + repeatString("-", 80));
        System.out.println("ALL CONSULTATIONS (SORTED BY DATE)");
        System.out.println(repeatString("-", 80));
        System.out.printf("%-15s %-10s %-10s %-20s %-15s\n", "Consultation ID", "Patient ID", "Doctor ID", "Date", "Status");
        System.out.println(repeatString("-", 80));
        
        Object[] consultationsArray = consultationList.toArray();
        Consultation[] consultationArray = new Consultation[consultationsArray.length];
        for (int i = 0; i < consultationsArray.length; i++) {
            consultationArray[i] = (Consultation) consultationsArray[i];
        }
        
        utility.BubbleSort.sort(consultationArray);
        
        for (Consultation consultation : consultationArray) {
            System.out.printf("%-15s %-10s %-10s %-20s %-15s\n", 
                consultation.getConsultationId(), consultation.getPatientId(),
                consultation.getDoctorId(), consultation.getConsultationDate(),
                consultation.getStatus());
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchConsultationById() {
        System.out.print("Enter consultation ID to search: ");
        String consultationId = scanner.nextLine();
        
        Object[] consultationsArray = consultationList.toArray();
        Consultation foundConsultation = null;
        
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getConsultationId().equals(consultationId)) {
                foundConsultation = consultation;
                break;
            }
        }
        
        if (foundConsultation != null) {
            displayConsultationDetails(foundConsultation);
        } else {
            System.out.println("Consultation not found!");
        }
    }
    
    public void searchConsultationsByDoctor() {
        System.out.print("Enter doctor ID to search consultations: ");
        String doctorId = scanner.nextLine();
        
        Object[] consultationsArray = consultationList.toArray();
        System.out.println("\nConsultations by Doctor ID: " + doctorId);
        System.out.println(repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s\n", "Consultation ID", "Patient ID", "Date", "Status");
        System.out.println(repeatString("-", 80));
        
        boolean found = false;
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getDoctorId().equals(doctorId)) {
                System.out.printf("%-15s %-10s %-20s %-15s\n", 
                    consultation.getConsultationId(), consultation.getPatientId(),
                    consultation.getConsultationDate(), consultation.getStatus());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No consultations found for this doctor.");
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchConsultationsByPatient() {
        System.out.print("Enter patient ID to search consultations: ");
        String patientId = scanner.nextLine();
        
        Object[] consultationsArray = consultationList.toArray();
        System.out.println("\nConsultations by Patient ID: " + patientId);
        System.out.println(repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s\n", "Consultation ID", "Doctor ID", "Date", "Status");
        System.out.println(repeatString("-", 80));
        
        boolean found = false;
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getPatientId().equals(patientId)) {
                System.out.printf("%-15s %-10s %-20s %-15s\n", 
                    consultation.getConsultationId(), consultation.getDoctorId(),
                    consultation.getConsultationDate(), consultation.getStatus());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No consultations found for this patient.");
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayConsultationDetails(Consultation consultation) {
        System.out.println("\n" + repeatString("-", 60));
        System.out.println("CONSULTATION DETAILS");
        System.out.println(repeatString("-", 60));
        System.out.println("Consultation ID: " + consultation.getConsultationId());
        System.out.println("Patient ID: " + consultation.getPatientId());
        System.out.println("Doctor ID: " + consultation.getDoctorId());
        System.out.println("Date: " + consultation.getConsultationDate());
        System.out.println("Status: " + consultation.getStatus());
        System.out.println("Notes: " + (consultation.getNotes().isEmpty() ? "None" : consultation.getNotes()));
        System.out.println("Next Appointment: " + (consultation.getNextAppointmentDate().isEmpty() ? "None" : consultation.getNextAppointmentDate()));
        System.out.println(repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateConsultationStatisticsReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        CONSULTATION STATISTICS REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] consultationsArray = consultationList.toArray();
        int totalConsultations = consultationsArray.length;
        int completedCount = 0, scheduledCount = 0;
        
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getStatus().equals("completed")) completedCount++;
            else if (consultation.getStatus().equals("scheduled")) scheduledCount++;
        }
        
        System.out.println("📊 Consultation Statistics:");
        System.out.println("• Total Consultations: " + totalConsultations);
        System.out.println("• Completed Consultations: " + completedCount);
        System.out.println("• Scheduled Consultations: " + scheduledCount);
        System.out.println("• Completion Rate: " + String.format("%.1f", (double)completedCount/totalConsultations*100) + "%");
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateDoctorPerformanceReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        DOCTOR PERFORMANCE REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] consultationsArray = consultationList.toArray();
        java.util.Map<String, Integer> doctorConsultationCount = new java.util.HashMap<>();
        
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            String doctorId = consultation.getDoctorId();
            doctorConsultationCount.put(doctorId, doctorConsultationCount.getOrDefault(doctorId, 0) + 1);
        }
        
        System.out.println("📊 Doctor Performance (Consultations per Doctor):");
        for (java.util.Map.Entry<String, Integer> entry : doctorConsultationCount.entrySet()) {
            System.out.println("• Doctor ID " + entry.getKey() + ": " + entry.getValue() + " consultations");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public int getTotalConsultationCount() {
        return consultationList.size();
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