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
    private PatientManagement patientManagement;
    
    public ConsultationManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    public void setDoctorManagement(DoctorManagement doctorManagement) {
        this.doctorManagement = doctorManagement;
    }
    
    public void setPatientManagement(PatientManagement patientManagement) {
        this.patientManagement = patientManagement;
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
        currentPatient.setStatus("In consultation");
        
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
        
        currentPatient.setStatus("Consulted");
        
        System.out.println("\nConsultation completed successfully!");
        System.out.println("Consultation ID: " + consultationId);
    }
    
    public void displayAllConsultationsSorted() {
        Object[] consultationsArray = consultationList.toArray();

        SetAndQueueInterface<Consultation> tempList = new SetAndQueue<>();
        for (Object obj : consultationsArray) {
            tempList.add((Consultation) obj);
        }
        tempList.sort();
        
        Object[] sortedConsultationsArray = tempList.toArray();
        Consultation[] consultationArray = new Consultation[sortedConsultationsArray.length];
        for (int i = 0; i < sortedConsultationsArray.length; i++) {
            consultationArray[i] = (Consultation) sortedConsultationsArray[i];
        }
        
        String[] headers = {"Consultation ID", "Patient Name", "Doctor Name", "Date", "Status"};
        Object[][] rows = new Object[consultationArray.length][headers.length];
        for (int i = 0; i < consultationArray.length; i++) {
            Consultation consultation = consultationArray[i];
            rows[i][0] = consultation.getConsultationId();
            rows[i][1] = getPatientName(consultation.getPatientId());
            rows[i][2] = getDoctorName(consultation.getDoctorId());
            rows[i][3] = consultation.getConsultationDate();
            rows[i][4] = consultation.getStatus();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "ALL CONSULTATIONS (SORTED BY DATE)",
            headers,
            rows
        ));
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
        String[] headers = {"Consultation ID", "Patient Name", "Date", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetAndQueue<>();
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getDoctorId().equals(doctorId)) {
                rowList.add(new Object[]{consultation.getConsultationId(), getPatientName(consultation.getPatientId()), consultation.getConsultationDate(), consultation.getStatus()});
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length];
        Object[] rowArray = rowList.toArray();
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CONSULTATIONS BY DOCTOR: " + getDoctorName(doctorId),
            headers,
            rows
        ));
        if (rowList.isEmpty()) {
            System.out.println("No consultations found for this doctor.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchConsultationsByPatient() {
        System.out.print("Enter patient ID to search consultations: ");
        String patientId = scanner.nextLine();
        
        Object[] consultationsArray = consultationList.toArray();
        String[] headers = {"Consultation ID", "Doctor Name", "Date", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetAndQueue<>();
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getPatientId().equals(patientId)) {
                rowList.add(new Object[]{consultation.getConsultationId(), getDoctorName(consultation.getDoctorId()), consultation.getConsultationDate(), consultation.getStatus()});
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length];
        Object[] rowArray = rowList.toArray();
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CONSULTATIONS BY PATIENT: " + getPatientName(patientId),
            headers,
            rows
        ));
        if (rowList.isEmpty()) {
            System.out.println("No consultations found for this patient.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayConsultationDetails(Consultation consultation) {
        System.out.println("\n" + StringUtility.repeatString("-", 60));
        System.out.println("CONSULTATION DETAILS");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Consultation ID: " + consultation.getConsultationId());
        System.out.println("Patient: " + getPatientName(consultation.getPatientId()) + " (ID: " + consultation.getPatientId() + ")");
        System.out.println("Doctor: " + getDoctorName(consultation.getDoctorId()) + " (ID: " + consultation.getDoctorId() + ")");
        System.out.println("Date: " + consultation.getConsultationDate());
        System.out.println("Status: " + consultation.getStatus());
        System.out.println("Notes: " + (consultation.getNotes().isEmpty() ? "None" : consultation.getNotes()));

        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateConsultationStatisticsReport() {
        System.out.println("\n" + utility.StringUtility.repeatString("=", 80));
        System.out.println("        CONSULTATION STATISTICS REPORT");
        System.out.println(utility.StringUtility.repeatString("=", 80));
        System.out.println("Generated at: " + utility.StringUtility.getCurrentDateTime());
        System.out.println(utility.StringUtility.repeatString("-", 80));

        Object[] consultationsArray = consultationList.toArray();
        int totalConsultations = consultationsArray.length;

        SetAndQueueInterface<String> uniqueDoctors = new SetAndQueue<>();
        int maxConsultations = 0;
        
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            String doctorId = consultation.getDoctorId();
            uniqueDoctors.add(doctorId);

            int consultationCount = countConsultationsForDoctor(doctorId, consultationsArray);
            if (consultationCount > maxConsultations) {
                maxConsultations = consultationCount;
            }
        }

        Object[] doctorsArray = uniqueDoctors.toArray();
        int[] consultationCounts = new int[doctorsArray.length];
        
        for (int i = 0; i < doctorsArray.length; i++) {
            String doctorId = (String) doctorsArray[i];
            consultationCounts[i] = countConsultationsForDoctor(doctorId, consultationsArray);
        }

        String[] headers = {"Consultation ID", "Doctor Name", "Patient Name", "Date", "Status"};
        Object[][] rows = new Object[consultationsArray.length][headers.length];
        for (int i = 0; i < consultationsArray.length; i++) {
            Consultation c = (Consultation) consultationsArray[i];
            rows[i][0] = c.getConsultationId();
            rows[i][1] = getDoctorName(c.getDoctorId());
            rows[i][2] = getPatientName(c.getPatientId());
            rows[i][3] = c.getConsultationDate();
            rows[i][4] = c.getStatus();
        }
        System.out.println("\nCONSULTATION LIST:");
        System.out.print(utility.StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nCONSULTATIONS PER DOCTOR:");
        int barWidth = 30;
        for (int i = 0; i < doctorsArray.length; i++) {
            String doctorId = (String) doctorsArray[i];
            int count = consultationCounts[i];
            System.out.printf("%-35s [%s] %d\n", getDoctorName(doctorId), utility.StringUtility.greenBarChart(count, maxConsultations, barWidth), count);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Consultations: " + totalConsultations);
        System.out.println(utility.StringUtility.repeatString("=", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    

    
    private int countConsultationsForDoctor(String doctorId, Object[] consultationsArray) {
        int count = 0;
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getDoctorId().equals(doctorId)) {
                count++;
            }
        }
        return count;
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

    private String getDoctorName(String doctorId) {
        if (doctorManagement != null) {
            Doctor doctor = doctorManagement.findDoctorById(doctorId);
            if (doctor != null) {
                return doctor.getName();
            }
        }
        return "Unknown Doctor";
    }
    
    private String getPatientName(String patientId) {
        if (patientManagement != null) {
            try {
                int patientIdInt = Integer.parseInt(patientId);
                Patient patient = patientManagement.findPatientById(patientIdInt);
                if (patient != null) {
                    return patient.getName();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid patient ID format");
            }
        }
        return "Unknown Patient";
    }
} 