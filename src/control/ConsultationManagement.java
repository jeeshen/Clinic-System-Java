package control;

import adt.SetAndQueueInterface;
import adt.SetQueueArray;
import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import dao.DataInitializer;
import utility.StringUtility;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import utility.InputValidator;

public class ConsultationManagement {
    private SetAndQueueInterface<Consultation> consultationList = new SetQueueArray<>();
    private Scanner scanner;
    private int consultationIdCounter = 2001;
    private DoctorManagement doctorManagement;
    private PatientManagement patientManagement;
    private TreatmentManagement treatmentManagement;

    //shared diagnosis data between reports
    private int[] diagnosisCounts = new int[100];
    private String[] diagnosisArray = new String[100];
    private int diagnosisIndex = 0;
    
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

    public void setTreatmentManagement(TreatmentManagement treatmentManagement) {
        this.treatmentManagement = treatmentManagement;
    }
    
    private void loadSampleData() {
        SetAndQueueInterface<Consultation> sampleConsultations = DataInitializer.initializeSampleConsultations();
        Object[] sampleConsultationsArray = sampleConsultations.toArray();
        for (Object obj : sampleConsultationsArray) {
            consultationList.add((Consultation) obj); //adt method
        }
        
        if (sampleConsultations.size() > 0) {
            int maxId = 0;
            for (Object obj : sampleConsultationsArray) {
                Consultation consultation = (Consultation) obj;
                String consultationId = consultation.getConsultationId();
                if (consultationId.startsWith("CON")) {
                    try {
                        int idNumber = Integer.parseInt(consultationId.substring(3));
                        if (idNumber > maxId) {
                            maxId = idNumber;
                        }
                    } catch (NumberFormatException e) {
                        //skip invalid IDs
                    }
                }
            }
            consultationIdCounter = maxId + 1;
        }
    }
    
    public void initializeEntityRelationships() {
        if (patientManagement == null || doctorManagement == null) {
            return;
        }
        
        Object[] consultationsArray = consultationList.toArray();
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            
            try {
                int patientId = Integer.parseInt(consultation.getPatientId());
                Patient patient = patientManagement.findPatientById(patientId);
                if (patient != null) {
                    patient.getConsultations().add(consultation);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing patient ID: " + consultation.getPatientId());
            }
            
            Doctor doctor = doctorManagement.findDoctorById(consultation.getDoctorId());
            if (doctor != null) {
                doctor.getConsultations().add(consultation);
            }
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

        System.out.print("Select doctor (1-" + doctorsArray.length + " or 0 to cancel): ");
        int doctorChoice = getUserInputIntWithCancel(1, doctorsArray.length);

        if (doctorChoice == 0) {
            System.out.println("Consultation cancelled.");
            return;
        }

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
        System.out.print("Enter diagnosis (or press Enter to cancel): ");
        String diagnosis = scanner.nextLine().trim();

        if (diagnosis.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        //create consultation
        String consultationId = "CON" + consultationIdCounter++;
        String consultationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        Consultation consultation = new Consultation(consultationId, String.valueOf(currentPatient.getId()), selectedDoctor.getDoctorId(), consultationDate, "Completed", diagnosis);
        
        consultationList.add(consultation); //adt method
        
        Patient patient = patientManagement.findPatientById(currentPatient.getId());
        if (patient != null) {
            patient.getConsultations().add(consultation);
        }
        
        Doctor doctor = doctorManagement.findDoctorById(selectedDoctor.getDoctorId());
        if (doctor != null) {
            doctor.getConsultations().add(consultation);
        }
        
        //create prescription through treatment management
        treatmentManagement.createPrescription(consultationId, currentPatient, selectedDoctor, diagnosis, consultationDate);

        //create treatment record
        treatmentManagement.createTreatment(String.valueOf(currentPatient.getId()), selectedDoctor.getDoctorId(), diagnosis, consultationDate);

        currentPatient.setStatus("Consulted");

        System.out.println("\nConsultation completed successfully!");
        System.out.println("Consultation ID: " + consultationId);
        System.out.println("Treatment record created automatically.");
    }
    
    public void displayAllConsultationsSorted() {
        Object[] consultationsArray = consultationList.toArray(); //adt method

        SetAndQueueInterface<Consultation> tempList = new SetQueueArray<>();
        for (Object obj : consultationsArray) {
            tempList.add((Consultation) obj); //adt method
        }
        tempList.sort(); //adt method

        Object[] sortedConsultationsArray = tempList.toArray(); //adt method
        Consultation[] consultationArray = new Consultation[sortedConsultationsArray.length];
        for (int i = 0; i < sortedConsultationsArray.length; i++) {
            consultationArray[i] = (Consultation) sortedConsultationsArray[i];
        }
        
        String[] headers = {"Consultation ID", "Patient Name", "Doctor Name", "Date", "Status", "Notes"};
        Object[][] rows = new Object[consultationArray.length][headers.length];
        for (int i = 0; i < consultationArray.length; i++) {
            Consultation consultation = consultationArray[i];
            rows[i][0] = consultation.getConsultationId();
            rows[i][1] = getPatientName(consultation.getPatientId());
            rows[i][2] = getDoctorName(consultation.getDoctorId());
            rows[i][3] = consultation.getConsultationDate();
            rows[i][4] = consultation.getStatus();
            rows[i][5] = consultation.getNotes();
        }

        System.out.println("\n" + StringUtility.repeatString("=", 140));
        System.out.println("                                    ALL CONSULTATIONS (SORTED BY DATE)");
        System.out.println(StringUtility.repeatString("=", 140));
        System.out.printf("%-15s %-25s %-25s %-12s %-12s %-45s\n", 
            "Consultation ID", "Patient Name", "Doctor Name", "Date", "Status", "Notes");
        System.out.println(StringUtility.repeatString("-", 140));
        
        for (int i = 0; i < consultationArray.length; i++) {
            Consultation consultation = consultationArray[i];
            String notes = consultation.getNotes();
            
            //truncate long fields to fit in table
            if (notes.length() > 42) notes = notes.substring(0, 39) + "...";
            
            System.out.printf("%-15s %-25s %-25s %-12s %-12s %-45s\n",
                consultation.getConsultationId(),
                getPatientName(consultation.getPatientId()).length() > 23 ? getPatientName(consultation.getPatientId()).substring(0, 22) + "..." : getPatientName(consultation.getPatientId()),
                getDoctorName(consultation.getDoctorId()).length() > 23 ? getDoctorName(consultation.getDoctorId()).substring(0, 22) + "..." : getDoctorName(consultation.getDoctorId()),
                consultation.getConsultationDate(),
                consultation.getStatus(),
                notes);
        }
        System.out.println(StringUtility.repeatString("-", 140));
        System.out.println("Total Consultations: " + consultationArray.length);
        System.out.println(StringUtility.repeatString("=", 140));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchConsultationById() {
        System.out.print("Enter consultation ID to search (or press Enter to cancel): ");
        String consultationId = scanner.nextLine().trim();

        if (consultationId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Object[] consultationsArray = consultationList.toArray(); //adt method
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
        System.out.print("Enter doctor ID to search consultations (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Object[] consultationsArray = consultationList.toArray(); //adt method
        String[] headers = {"Consultation ID", "Patient Name", "Date", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetQueueArray<>();
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getDoctorId().equals(doctorId)) {
                rowList.add(new Object[]{consultation.getConsultationId(), getPatientName(consultation.getPatientId()), consultation.getConsultationDate(), consultation.getStatus()}); //adt method
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length]; //adt method
        Object[] rowArray = rowList.toArray(); //adt method
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CONSULTATIONS BY DOCTOR: " + getDoctorName(doctorId),
            headers,
            rows
        ));
        if (rowList.isEmpty()) { //adt method
            System.out.println("No consultations found for this doctor.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchConsultationsByPatient() {
        System.out.print("Enter patient ID to search consultations (or press Enter to cancel): ");
        String patientId = scanner.nextLine().trim();
        
        if (patientId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Object[] consultationsArray = consultationList.toArray(); //adt method
        String[] headers = {"Consultation ID", "Doctor Name", "Date", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetQueueArray<>();
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getPatientId().equals(patientId)) {
                rowList.add(new Object[]{consultation.getConsultationId(), getDoctorName(consultation.getDoctorId()), consultation.getConsultationDate(), consultation.getStatus()}); //adt method
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length]; //adt method
        Object[] rowArray = rowList.toArray(); //adt method
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CONSULTATIONS BY PATIENT: " + getPatientName(patientId),
            headers,
            rows
        ));
        if (rowList.isEmpty()) { //adt method
            System.out.println("No consultations found for this patient.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updateConsultation() {
        System.out.println("\n" + StringUtility.repeatString("=", 90));
        System.out.println("        UPDATE CONSULTATION");
        System.out.println(StringUtility.repeatString("=", 90));

        System.out.println("CURRENT CONSULTATION LIST:");
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.printf("%-15s %-30s %-40s %-15s %-15s\n", "Consultation ID", "Patient Name", "Doctor Name", "Date", "Status");
        System.out.println(StringUtility.repeatString("-", 90));

        Object[] consultationsArray = consultationList.toArray(); //adt method
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            System.out.printf("%-15s %-30s %-40s %-15s %-15s\n",
                consultation.getConsultationId(),
                getPatientName(consultation.getPatientId()),
                getDoctorName(consultation.getDoctorId()),
                consultation.getConsultationDate(),
                consultation.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.println("Total Consultations: " + consultationsArray.length);
        System.out.println(StringUtility.repeatString("=", 90));

        System.out.print("Enter consultation ID to update (or press Enter to cancel): ");
        String consultationId = scanner.nextLine().trim();

        if (consultationId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Consultation consultation = findConsultationById(consultationId);
        if (consultation != null) {
            System.out.println("Current consultation information:");
            displayConsultationDetails(consultation);

            System.out.println("\nEnter new information (press Enter to keep current value):");

            //update status
            System.out.print("Status [" + consultation.getStatus() + "] (Completed/In Progress/Cancelled): ");
            String status = scanner.nextLine().trim();
            if (!status.isEmpty()) {
                if (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("In Progress") || status.equalsIgnoreCase("Cancelled")) {
                    consultation.setStatus(status);
                    System.out.println("[OK] Status updated successfully!");
                } else {
                    System.out.println("[WARNING] Invalid status. Status not updated.");
                }
            }

            //update notes (diagnosis)
            System.out.print("Notes/Diagnosis [" + consultation.getNotes() + "]: ");
            String notes = scanner.nextLine().trim();
            if (!notes.isEmpty()) {
                consultation.setNotes(notes);
                System.out.println("[OK] Notes updated successfully!");
            }

            System.out.println("\n[OK] Consultation information updated successfully!");
            System.out.println("\nUpdated consultation information:");
            displayConsultationDetails(consultation);
        } else {
            System.out.println("[ERROR] Consultation not found!");
        }
    }

    public void removeConsultation() {
        System.out.println("\n" + StringUtility.repeatString("=", 90));
        System.out.println("        REMOVE CONSULTATION");
        System.out.println(StringUtility.repeatString("=", 90));
        
        System.out.println("CURRENT CONSULTATION LIST:");
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.printf("%-15s %-30s %-40s %-15s %-15s\n", "Consultation ID", "Patient Name", "Doctor Name", "Date", "Status");
        System.out.println(StringUtility.repeatString("-", 90));
        
        Object[] consultationsArray = consultationList.toArray(); //adt method
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            System.out.printf("%-15s %-30s %-40s %-15s %-15s\n", 
                consultation.getConsultationId(), 
                getPatientName(consultation.getPatientId()), 
                getDoctorName(consultation.getDoctorId()), 
                consultation.getConsultationDate(), 
                consultation.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Total Consultations: " + consultationsArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter consultation ID to remove (or press Enter to cancel): ");
        String consultationId = scanner.nextLine().trim();
        
        if (consultationId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Consultation consultation = findConsultationById(consultationId);
        if (consultation != null) {
            System.out.println("Consultation to be removed:");
            displayConsultationDetails(consultation);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this consultation? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removed = consultationList.remove(consultation); //adt method
                if (removed) {
                    System.out.println("[OK] Consultation removed successfully!");
                } else {
                    System.out.println("[ERROR] Failed to remove consultation from system!");
                }
            } else {
                System.out.println("[ERROR] Consultation removal cancelled.");
            }
        } else {
            System.out.println("[ERROR] Consultation not found!");
        }
    }
    
    private Consultation findConsultationById(String consultationId) {
        Object[] consultationsArray = consultationList.toArray(); //adt method
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getConsultationId().equals(consultationId)) {
                return consultation;
            }
        }
        return null;
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
        System.out.println("Notes: " + (consultation.getNotes().isEmpty() ? "None" : consultation.getNotes())); //adt method

        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public void generateConsultationVolumeAndTrendsReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                         CONSULTATION MANAGEMENT SUBSYSTEM");
        System.out.println("                       CONSULTATION PRESCRIPTION ANALYSIS REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        Object[] consultationsArray = consultationList.toArray();
        int totalConsultations = consultationsArray.length;

        String reset = "\u001B[0m";

        //diagnosis prescription analysis
        SetAndQueueInterface<String> uniqueDiagnoses = new SetQueueArray<>();
        int[] diagnosisPrescriptionCounts = new int[100];
        String[] diagnosisArray = new String[100];
        int diagnosisIndex = 0;

        //analyze prescriptions per diagnosis
        if (treatmentManagement != null) {
            Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
            for (Object prescObj : prescriptionsArray) {
                entity.Prescription prescription = (entity.Prescription) prescObj;
                String diagnosis = prescription.getDiagnosis();
                
                if (diagnosis != null && !diagnosis.trim().isEmpty()) {
                    uniqueDiagnoses.add(diagnosis);

                    //count prescriptions per diagnosis
                    boolean diagnosisFound = false;
                    for (int i = 0; i < diagnosisIndex; i++) {
                        if (diagnosisArray[i].equals(diagnosis)) {
                            diagnosisPrescriptionCounts[i]++;
                            diagnosisFound = true;
                            break;
                        }
                    }
                    if (!diagnosisFound && diagnosisIndex < 100) {
                        diagnosisArray[diagnosisIndex] = diagnosis;
                        diagnosisPrescriptionCounts[diagnosisIndex] = 1;
                        diagnosisIndex++;
                    }
                }
            }
        }

        //sort diagnoses by prescription count (bubble sort)
        for (int i = 0; i < diagnosisIndex - 1; i++) {
            for (int j = 0; j < diagnosisIndex - i - 1; j++) {
                if (diagnosisPrescriptionCounts[j] < diagnosisPrescriptionCounts[j + 1]) {
                    //swap diagnoses
                    String tempDiagnosis = diagnosisArray[j];
                    diagnosisArray[j] = diagnosisArray[j + 1];
                    diagnosisArray[j + 1] = tempDiagnosis;

                    //swap counts
                    int tempCount = diagnosisPrescriptionCounts[j];
                    diagnosisPrescriptionCounts[j] = diagnosisPrescriptionCounts[j + 1];
                    diagnosisPrescriptionCounts[j + 1] = tempCount;
                }
            }
        }

        //display diagnosis vs prescriptions table
        System.out.println("DIAGNOSIS VS PRESCRIPTIONS COUNT TABLE");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.print("| Diagnosis                    | Prescriptions Count                                |");
        System.out.println();
        System.out.println(StringUtility.repeatString("-", 95));

        for (int i = 0; i < Math.min(diagnosisIndex, 10); i++) {
            String diagnosis = diagnosisArray[i];
            int prescriptions = diagnosisPrescriptionCounts[i];

            System.out.printf("| %-28s | %-50d |%n",
                diagnosis.length() > 28 ? diagnosis.substring(0, 28) : diagnosis,
                prescriptions);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Total Number of Consultations: " + totalConsultations);
        System.out.println("Total Number of Diagnoses: " + diagnosisIndex);
        System.out.println();

        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                DIAGNOSIS VS PRESCRIPTIONS CHART");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Diagnosis vs Prescriptions Chart:");
        System.out.println("   ^");

        //show top 5 diagnoses by prescription count
        int maxPrescriptions = 0;
        int numDiagnoses = Math.min(diagnosisIndex, 5);
        for (int i = 0; i < numDiagnoses; i++) {
            if (diagnosisPrescriptionCounts[i] > maxPrescriptions) maxPrescriptions = diagnosisPrescriptionCounts[i];
        }

        String barColor = "\u001B[42m"; //green background

        for (int level = maxPrescriptions; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numDiagnoses; i++) {
                if (diagnosisPrescriptionCounts[i] >= level) {
                    System.out.print(" " + barColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.print("   +");
        for (int i = 0; i < numDiagnoses; i++) {
            System.out.print("------");
        }
        System.out.println("> Diagnoses");

        System.out.print("     ");
        for (int i = 0; i < numDiagnoses; i++) {
            String shortDiag = diagnosisArray[i].length() > 4 ? diagnosisArray[i].substring(0, 4) : diagnosisArray[i];
            System.out.printf("%-4s  ", shortDiag);
        }
        System.out.println();
        System.out.println();

        if (diagnosisIndex > 0) {
            System.out.printf("Diagnosis with most prescriptions: < %s with %d prescriptions >%n",
                diagnosisArray[0], diagnosisPrescriptionCounts[0]);
        }

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public void generateConsultationOutcomesAndDiagnosisReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                         CONSULTATION MANAGEMENT SUBSYSTEM");
        System.out.println("                     CONSULTATION DIAGNOSIS ANALYSIS REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        String reset = "\u001B[0m";

        //disease patient analysis table
        System.out.println("Disease Patient Analysis - Unique vs Repeat Patients:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("| %-20s | %-12s | %-15s | %-15s | %-17s |%n", "Disease", "Total Cases", "Unique Patients", "Repeat Patients", "Repeat Rate");
        System.out.println(StringUtility.repeatString("-", 95));

        //get diagnosis data - if not available from volume report, analyze here
        if (diagnosisIndex == 0 && treatmentManagement != null) {
            //analyze diagnosis data from TreatmentManagement
            SetAndQueueInterface<String> uniqueDiagnoses = new SetQueueArray<>();

            Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
            for (Object prescObj : prescriptionsArray) {
                entity.Prescription prescription = (entity.Prescription) prescObj;
                String diagnosis = prescription.getDiagnosis();
                if (diagnosis != null && !diagnosis.trim().isEmpty()) {
                    uniqueDiagnoses.add(diagnosis);

                    //count diagnoses
                    boolean diagnosisFound = false;
                    for (int i = 0; i < diagnosisIndex; i++) {
                        if (diagnosisArray[i].equals(diagnosis)) {
                            diagnosisCounts[i]++;
                            diagnosisFound = true;
                            break;
                        }
                    }
                    if (!diagnosisFound && diagnosisIndex < 100) {
                        diagnosisArray[diagnosisIndex] = diagnosis;
                        diagnosisCounts[diagnosisIndex] = 1;
                        diagnosisIndex++;
                    }
                }
            }

            //sort diagnoses by count (bubble sort)
            for (int i = 0; i < diagnosisIndex - 1; i++) {
                for (int j = 0; j < diagnosisIndex - i - 1; j++) {
                    if (diagnosisCounts[j] < diagnosisCounts[j + 1]) {
                        //swap diagnoses
                        String tempDiagnosis = diagnosisArray[j];
                        diagnosisArray[j] = diagnosisArray[j + 1];
                        diagnosisArray[j + 1] = tempDiagnosis;

                        //swap counts
                        int tempCount = diagnosisCounts[j];
                        diagnosisCounts[j] = diagnosisCounts[j + 1];
                        diagnosisCounts[j + 1] = tempCount;
                    }
                }
            }
        }

        //if still no data, use sample data
        if (diagnosisIndex == 0) {
            diagnosisArray[0] = "Hypertension";
            diagnosisArray[1] = "Depression";
            diagnosisArray[2] = "High Cholesterol";
            diagnosisArray[3] = "Arthritis";
            diagnosisArray[4] = "Asthma";
            diagnosisCounts[0] = 22;
            diagnosisCounts[1] = 22;
            diagnosisCounts[2] = 21;
            diagnosisCounts[3] = 19;
            diagnosisCounts[4] = 19;
            diagnosisIndex = 5;
        }

        //calculate unique and repeat patients for each disease
        int[] uniquePatientsPerDisease = new int[5];
        int[] repeatPatientsPerDisease = new int[5];

        //track actual patient-diagnosis relationships
        SetAndQueueInterface<String>[] patientsPerDisease = new SetAndQueueInterface[5];
        for (int i = 0; i < 5; i++) {
            patientsPerDisease[i] = new SetQueueArray<>();
        }

        //analyze actual patient-diagnosis data
        if (treatmentManagement != null) {
            Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
            for (Object prescObj : prescriptionsArray) {
                entity.Prescription prescription = (entity.Prescription) prescObj;
                String diagnosis = prescription.getDiagnosis();
                String patientId = prescription.getPatientId();
                
                if (diagnosis != null && !diagnosis.trim().isEmpty() && patientId != null) {
                    //find which disease this is
                    for (int i = 0; i < Math.min(diagnosisIndex, 5); i++) {
                        if (diagnosisArray[i].equals(diagnosis)) {
                            patientsPerDisease[i].add(patientId);
                            break;
                        }
                    }
                }
            }
        }

        //calculate actual unique vs repeat patients
        for (int i = 0; i < Math.min(diagnosisIndex, 5); i++) {
            int totalPatients = diagnosisCounts[i];
            int actualUniquePatients = patientsPerDisease[i].size();
            uniquePatientsPerDisease[i] = actualUniquePatients;
            repeatPatientsPerDisease[i] = totalPatients - actualUniquePatients;
        }

        //display table data
        for (int i = 0; i < Math.min(diagnosisIndex, 5); i++) {
            String disease = diagnosisArray[i];
            int totalCases = diagnosisCounts[i];
            int uniquePatients2 = uniquePatientsPerDisease[i];
            int repeatPatients = repeatPatientsPerDisease[i];
            double repeatRate = totalCases > 0 ? (double) repeatPatients / totalCases * 100 : 0;

            System.out.printf("| %-20s | %-12d | %-15d | %-15d | %-16.1f%% |%n",
                disease.length() > 20 ? disease.substring(0, 20) : disease,
                totalCases,
                uniquePatients2,
                repeatPatients,
                repeatRate);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                GRAPHICAL REPRESENTATION OF DISEASE PATIENT PATTERNS");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Unique Patients by Disease Chart:");
        System.out.println("   ^");

        int maxUniquePatients = 0;
        int numDiseases = Math.min(diagnosisIndex, 5);
        for (int i = 0; i < numDiseases; i++) {
            if (uniquePatientsPerDisease[i] > maxUniquePatients) maxUniquePatients = uniquePatientsPerDisease[i];
        }

        String uniqueColor = "\u001B[42m"; //green for unique patients

        for (int level = maxUniquePatients; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numDiseases; i++) {
                if (uniquePatientsPerDisease[i] >= level) {
                    System.out.print(" " + uniqueColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.print("   +");
        for (int i = 0; i < numDiseases; i++) {
            System.out.print("------");
        }
        System.out.println("> Diseases");

        System.out.print("    ");
        for (int i = 0; i < numDiseases; i++) {
            String shortDiag = diagnosisArray[i].length() > 4 ? diagnosisArray[i].substring(0, 4) : diagnosisArray[i];
            System.out.printf("%-4s  ", shortDiag);
        }
        System.out.println();
        System.out.println();

        System.out.println("Repeat Patients by Disease Chart:");
        System.out.println("   ^");

        int maxRepeatPatients = 0;
        for (int i = 0; i < numDiseases; i++) {
            if (repeatPatientsPerDisease[i] > maxRepeatPatients) maxRepeatPatients = repeatPatientsPerDisease[i];
        }

        String repeatColor = "\u001B[43m"; //yellow for repeat patients

        for (int level = maxRepeatPatients; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numDiseases; i++) {
                if (repeatPatientsPerDisease[i] >= level) {
                    System.out.print(" " + repeatColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.print("   +");
        for (int i = 0; i < numDiseases; i++) {
            System.out.print("------");
        }
        System.out.println("> Diseases");

        System.out.print("    ");
        for (int i = 0; i < numDiseases; i++) {
            String shortDiag = diagnosisArray[i].length() > 4 ? diagnosisArray[i].substring(0, 4) : diagnosisArray[i];
            System.out.printf("%-4s  ", shortDiag);
        }
        System.out.println();
        System.out.println();

        //summary
        if (diagnosisIndex > 0) {
            System.out.printf("Most common diagnosis: < %s with %d cases (%d unique, %d repeat) >%n",
                diagnosisArray[0], diagnosisCounts[0], uniquePatientsPerDisease[0], repeatPatientsPerDisease[0]);
        }

        //calculate overall statistics
        int totalUniquePatients = 0;
        int totalRepeatPatients = 0;
        for (int i = 0; i < Math.min(diagnosisIndex, 5); i++) {
            totalUniquePatients += uniquePatientsPerDisease[i];
            totalRepeatPatients += repeatPatientsPerDisease[i];
        }

        System.out.printf("Patient pattern summary: < Total Unique: %d, Total Repeat: %d >%n",
            totalUniquePatients, totalRepeatPatients);

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public int getTotalConsultationCount() {
        return consultationList.size(); //adt method
    }

    public Object[] getAllConsultations() {
        return consultationList.toArray(); //adt method
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

    private int getUserInputIntWithCancel(int min, int max) {
        int input;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number between " + min + " and " + max + " (or 0 to cancel): ");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) {
                return 0; //return 0 for cancellation
            }

            if (input < min || input > max) {
                System.out.print("Please enter a number between " + min + " and " + max + " (or 0 to cancel): ");
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