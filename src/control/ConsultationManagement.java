package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import dao.DataInitializer;
import utility.StringUtility;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsultationManagement {
    private SetAndQueueInterface<Consultation> consultationList = new SetAndQueue<>();
    private Scanner scanner;
    private int consultationIdCounter = 2001;
    private DoctorManagement doctorManagement;
    
    public ConsultationManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    public void setDoctorManagement(DoctorManagement doctorManagement) {
        this.doctorManagement = doctorManagement;
    }
    
    private void loadSampleData() {
        Consultation[] sampleConsultations = DataInitializer.initializeSampleConsultations();
        for (Consultation consultation : sampleConsultations) {
            consultationList.add(consultation);
        }
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
        
        //select doctor
        System.out.println("Doctors on duty:");
        Doctor[] doctorsArray = doctorManagement.getDoctorsOnDuty();
        for (int i = 0; i < doctorsArray.length; i++) {
            Doctor doctor = doctorsArray[i];
            System.out.println((i + 1) + ". " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
        }
        
        System.out.print("Select doctor (1-" + doctorsArray.length + "): ");
        int doctorChoice = getUserInputInt(1, doctorsArray.length);
        Doctor selectedDoctor = doctorsArray[doctorChoice - 1];
        
        //get next patient from queue
        Patient currentPatient = patientManagement.getNextPatientFromQueue();
        if (currentPatient == null) {
            System.out.println("Error: No patient available in queue!");
            return;
        }
        currentPatient.setIsInWaiting(false);
        currentPatient.setCurrentStatus("in consultation");
        
        System.out.println("\nConsulting with patient: " + currentPatient.getName());
        System.out.println("Doctor: " + selectedDoctor.getName());
        
        //get diagnosis
        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine();
        
        //create consultation
        String consultationId = "CONS" + consultationIdCounter++;
        String consultationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        Consultation consultation = new Consultation(consultationId, String.valueOf(currentPatient.getId()), selectedDoctor.getDoctorId(), consultationDate, "Completed", diagnosis);
        
        consultationList.add(consultation);
        
        //create prescription through treatment management
        treatmentManagement.createPrescription(consultationId, currentPatient, selectedDoctor, diagnosis, consultationDate);
        
        currentPatient.setCurrentStatus("Consulted");
        
        System.out.println("\nConsultation completed successfully!");
        System.out.println("Consultation ID: " + consultationId);
    }
    
    public void displayAllConsultationsSorted() {
        System.out.println("\n" + StringUtility.repeatString("-", 80));
        System.out.println("ALL CONSULTATIONS (SORTED BY DATE)");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-15s %-10s %-10s %-20s %-15s\n", "Consultation ID", "Patient ID", "Doctor ID", "Date", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
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
        System.out.println(StringUtility.repeatString("-", 80));
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
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s\n", "Consultation ID", "Patient ID", "Date", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
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
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchConsultationsByPatient() {
        System.out.print("Enter patient ID to search consultations: ");
        String patientId = scanner.nextLine();
        
        Object[] consultationsArray = consultationList.toArray();
        System.out.println("\nConsultations by Patient ID: " + patientId);
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s\n", "Consultation ID", "Doctor ID", "Date", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
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
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayConsultationDetails(Consultation consultation) {
        System.out.println("\n" + StringUtility.repeatString("-", 60));
        System.out.println("CONSULTATION DETAILS");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Consultation ID: " + consultation.getConsultationId());
        System.out.println("Patient ID: " + consultation.getPatientId());
        System.out.println("Doctor ID: " + consultation.getDoctorId());
        System.out.println("Date: " + consultation.getConsultationDate());
        System.out.println("Status: " + consultation.getStatus());
        System.out.println("Notes: " + (consultation.getNotes().isEmpty() ? "None" : consultation.getNotes()));

        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateConsultationStatisticsReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("        CONSULTATION STATISTICS REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        Object[] consultationsArray = consultationList.toArray();
        int totalConsultations = consultationsArray.length;

        SetAndQueueInterface<String> doctorIds = new SetAndQueue<>();
        SetAndQueueInterface<String> patientIds = new SetAndQueue<>();
        SetAndQueueInterface<String> dates = new SetAndQueue<>();
        
        //count doctor consultations manually
        int[] doctorCounts = new int[100];
        String[] doctorIdArray = new String[100];
        int doctorIndex = 0;
        
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            
            //track unique doctors
            String doctorId = consultation.getDoctorId();
            doctorIds.add(doctorId);
            
            //count consultations per doctor
            boolean found = false;
            for (int i = 0; i < doctorIndex; i++) {
                if (doctorIdArray[i].equals(doctorId)) {
                    doctorCounts[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                doctorIdArray[doctorIndex] = doctorId;
                doctorCounts[doctorIndex] = 1;
                doctorIndex++;
            }
            
            //track unique dates
            String date = consultation.getConsultationDate();
            dates.add(date);
            
            //track unique patients
            String patientId = consultation.getPatientId();
            patientIds.add(patientId);
        }

        System.out.println("📊 CONSULTATION OVERVIEW:");
        System.out.println("• Total Consultations: " + totalConsultations);
        System.out.println("• Average Consultations per Day: " + String.format("%.1f", (double)totalConsultations/dates.size()));
        
        System.out.println("\n👨‍⚕️ DOCTOR PERFORMANCE ANALYSIS:");
        System.out.println("• Total Doctors Involved: " + doctorIds.size());
        
        //find top performing doctors
        System.out.println("• Top 3 Performing Doctors:");
        for (int i = 0; i < Math.min(3, doctorIndex); i++) {
            //find the doctor with highest count
            int maxIndex = 0;
            for (int j = 1; j < doctorIndex; j++) {
                if (doctorCounts[j] > doctorCounts[maxIndex]) {
                    maxIndex = j;
                }
            }
            
            String doctorName = getDoctorName(doctorIdArray[maxIndex]);
            System.out.printf("  %d. %s (%s): %d consultations\n", 
                i+1, doctorName, doctorIdArray[maxIndex], doctorCounts[maxIndex]);
            //mark as used by setting to -1
            doctorCounts[maxIndex] = -1;
        }
        
        System.out.println("\n📅 CONSULTATION TRENDS:");
        System.out.println("• Total Active Days: " + dates.size());

        System.out.println("• Daily Consultation Distribution:");
        Object[] datesArray = dates.toArray();
        for (Object dateObj : datesArray) {
            String date = (String) dateObj;
            int count = 0;
            for (Object obj : consultationsArray) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getConsultationDate().equals(date)) {
                    count++;
                }
            }
            int bars = (int) Math.round((double) count / getMaxConsultations(consultationsArray) * 20);
            System.out.printf("  %s: [%s] %d consultations\n", 
                date, 
                createColoredBar(BLUE_BG, bars, 20),
                count);
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private int getMaxConsultations(Object[] consultationsArray) {
        //count consultations per date
        SetAndQueueInterface<String> dates = new SetAndQueue<>();
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            dates.add(consultation.getConsultationDate());
        }
        
        int maxCount = 0;
        Object[] datesArray = dates.toArray();
        for (Object dateObj : datesArray) {
            String date = (String) dateObj;
            int count = 0;
            for (Object obj : consultationsArray) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getConsultationDate().equals(date)) {
                    count++;
                }
            }
            if (count > maxCount) maxCount = count;
        }
        return maxCount;
    }
    
    public void generateDoctorPerformanceReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        DOCTOR PERFORMANCE REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        
        Object[] consultationsArray = consultationList.toArray();
        SetAndQueueInterface<String> doctorIds = new SetAndQueue<>();

        int[] doctorCounts = new int[100];
        String[] doctorIdArray = new String[100];
        int doctorIndex = 0;
        
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            String doctorId = consultation.getDoctorId();
            doctorIds.add(doctorId);

            boolean found = false;
            for (int i = 0; i < doctorIndex; i++) {
                if (doctorIdArray[i].equals(doctorId)) {
                    doctorCounts[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                doctorIdArray[doctorIndex] = doctorId;
                doctorCounts[doctorIndex] = 1;
                doctorIndex++;
            }
        }
        
        System.out.println("📊 Doctor Performance (Consultations per Doctor):");
        for (int i = 0; i < doctorIndex; i++) {
            System.out.println("• Doctor ID " + doctorIdArray[i] + ": " + doctorCounts[i] + " consultations");
        }
        
        System.out.println("\n📈 Performance Chart:");
        if (doctorIndex > 0) {
            //find the maximum consultations for scaling
            int maxConsultations = 0;
            for (int i = 0; i < doctorIndex; i++) {
                if (doctorCounts[i] > maxConsultations) maxConsultations = doctorCounts[i];
            }
            
            for (int i = 0; i < doctorIndex; i++) {
                int bars = maxConsultations > 0 ? (int) Math.round((double) doctorCounts[i] / maxConsultations * 25) : 0;
                System.out.printf("%-12s [%s] %d consultations\n", 
                    "Doctor " + doctorIdArray[i] + ":", 
                    createColoredBar(BLUE_BG, bars, 25),
                    doctorCounts[i]);
                System.out.println("");
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public int getTotalConsultationCount() {
        return consultationList.size();
    }
    
    public Object[] getAllConsultations() {
        return consultationList.toArray();
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

    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BG = "\u001B[44m";
    
    private String createColoredBar(String color, int barCount, int totalWidth) {
        return color + StringUtility.repeatString(" ", barCount) + RESET + StringUtility.repeatString(" ", totalWidth - barCount);
    }

    private String getDoctorName(String doctorId) {
        if (doctorManagement != null) {
            Doctor doctor = doctorManagement.findDoctorById(doctorId);
            if (doctor != null) {
                return doctor.getName();
            }
        }
        return "Unknown Doctor";
    }
} 