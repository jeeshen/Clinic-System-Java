package control;

import adt.SetAndQueueInterface;
import adt.SetQueueArray;
import entity.Patient;
import dao.DataInitializer;
import utility.InputValidator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entity.Consultation;
import entity.Treatment;
import entity.Prescription;
import utility.StringUtility;

public class PatientManagement {
    private SetAndQueueInterface<Patient> patientList = new SetQueueArray<>();
    private SetAndQueueInterface<Patient> waitingPatientList = new SetQueueArray<>();
    private Scanner scanner;
    private int patientIdCounter = 1001;
    private ConsultationManagement consultationManagement;
    
    public PatientManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    public void setConsultationManagement(ConsultationManagement consultationManagement) {
        this.consultationManagement = consultationManagement;
    }
    
    private void loadSampleData() {
        Patient[] samplePatients = DataInitializer.initializeSamplePatients();
        for (Patient patient : samplePatients) {
            patient.setStatus("Active");
            patientList.add(patient); //adt method
        }
    }
    
    public void registerNewPatient() {
        System.out.println("\n=== REGISTER NEW PATIENT ===");
        
        String name = InputValidator.getValidString(scanner, "Enter patient name: ");
        
        int age = InputValidator.getValidAge(scanner, "Enter patient age: ");
        
        String gender = InputValidator.getValidGender(scanner, "Enter gender");
        
        String contactNumber = InputValidator.getValidPhoneNumber(scanner, "Enter contact number: ");
        
        String address = InputValidator.getValidString(scanner, "Enter address: ");
        
        String allergies = InputValidator.getValidStringAllowEmpty(scanner, "Enter allergies (if any, or press Enter): ");
        
        String medicalHistory = InputValidator.getValidStringAllowEmpty(scanner, "Enter medical history (if any, or press Enter): ");

        if (isPatientExists(contactNumber)) {
            System.out.println("[ERROR] A patient with this phone number already exists!");
            System.out.println("Please use a different phone number or check existing patient records.");
            return;
        }
        
        String registrationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        Patient newPatient = new Patient(patientIdCounter++, name, age, gender, allergies, contactNumber, address, registrationDate, medicalHistory, "Waiting");

        patientList.add(newPatient); //adt method
        waitingPatientList.enqueue(newPatient); //adt method
        newPatient.setStatus("Waiting");
        
        System.out.println("\n[OK] Patient registered successfully!");
        System.out.println("Patient ID: " + newPatient.getId());
        System.out.println("Patient added to waiting queue.");
    }
    
    public void selectExistingPatient() {
        System.out.println("\n=== SELECT EXISTING PATIENT ===");

        if (patientList.size() == 0) { //adt method
            System.out.println("No patients registered in the system.");
            return;
        }

        System.out.println("Available patients:");

        Object[] patientsArray = patientList.toArray(); //adt method
        String[] headers = {"ID", "Name", "Age", "Gender", "Contact"};
        Object[][] rows = new Object[patientsArray.length][headers.length];
        for (int i = 0; i < patientsArray.length; i++) {
            Patient patient = (Patient) patientsArray[i];
            rows[i][0] = patient.getId();
            rows[i][1] = patient.getName();
            rows[i][2] = patient.getAge();
            rows[i][3] = patient.getGender();
            rows[i][4] = patient.getContactNumber();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "AVAILABLE PATIENTS",
            headers,
            rows
        ));

        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to add to queue: ");
        
        Patient selectedPatient = findPatientById(patientId);
        if (selectedPatient != null) {
            boolean alreadyInQueue = false;
            Object[] queueArray = waitingPatientList.toQueueArray();
            
            for (Object obj : queueArray) {
                Patient queuePatient = (Patient) obj;
                if (queuePatient.getId() == selectedPatient.getId()) {
                    alreadyInQueue = true;
                    break;
                }
            }
            
            if (!alreadyInQueue) {
                waitingPatientList.enqueue(selectedPatient); //adt method
                selectedPatient.setStatus("Waiting");
                System.out.println("Patient " + selectedPatient.getName() + " added to waiting queue.");
            } else {
                System.out.println("Patient is already in the waiting queue!");
            }
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void displayWaitingQueue() {
        Object[] queueArray = waitingPatientList.toQueueArray();
        String[] headers = {"ID", "Name", "Age", "Gender", "Contact"};
        Object[][] rows = new Object[queueArray.length][headers.length];
        for (int i = 0; i < queueArray.length; i++) {
            Patient patient = (Patient) queueArray[i];
            rows[i][0] = patient.getId();
            rows[i][1] = patient.getName();
            rows[i][2] = patient.getAge();
            rows[i][3] = patient.getGender();
            rows[i][4] = patient.getContactNumber();
        }
        if (queueArray.length == 0) {
            System.out.println("\n" + StringUtility.repeatString("-", 70));
            System.out.println("CURRENT WAITING QUEUE");
            System.out.println(StringUtility.repeatString("-", 70));
            System.out.println("No patients in waiting queue.");
            System.out.println(StringUtility.repeatString("-", 70));
        } else {
            System.out.print(StringUtility.formatTableNoDividers(
                "CURRENT WAITING QUEUE",
                headers,
                rows
            ));
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void removePatientFromQueue() {
        Object[] displayQueueArray = waitingPatientList.toQueueArray();
        String[] headers = {"ID", "Name", "Age", "Gender", "Contact"};
        Object[][] rows = new Object[displayQueueArray.length][headers.length];
        for (int i = 0; i < displayQueueArray.length; i++) {
            Patient patient = (Patient) displayQueueArray[i];
            rows[i][0] = patient.getId();
            rows[i][1] = patient.getName();
            rows[i][2] = patient.getAge();
            rows[i][3] = patient.getGender();
            rows[i][4] = patient.getContactNumber();
        }
        if (displayQueueArray.length == 0) {
            System.out.println("\n" + StringUtility.repeatString("-", 70));
            System.out.println("CURRENT WAITING QUEUE");
            System.out.println(StringUtility.repeatString("-", 70));
            System.out.println("No patients in waiting queue.");
            System.out.println(StringUtility.repeatString("-", 70));
        } else {
            System.out.print(StringUtility.formatTableNoDividers(
                "CURRENT WAITING QUEUE",
                headers,
                rows
            ));
        }

        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to remove from queue: ");
        
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            boolean foundInQueue = false;
            SetAndQueueInterface<Patient> newQueue = new SetQueueArray<>();
            Object[] queueArray = waitingPatientList.toQueueArray();
            
            for (Object obj : queueArray) {
                Patient queuePatient = (Patient) obj;
                if (queuePatient.getId() == patient.getId()) {
                    foundInQueue = true;
                    patient.setStatus("Active");
                    System.out.println("Patient " + patient.getName() + " removed from waiting queue.");
                } else {
                    newQueue.enqueue(queuePatient); //adt method
                }
            }
            
            waitingPatientList = newQueue;
            
            if (!foundInQueue) {
                System.out.println("Patient not found in waiting queue!");
            }
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void clearPatientQueue() {
        if (waitingPatientList.isQueueEmpty()) {
            System.out.println("No patients in waiting queue to clear.");
            return;
        }
        
        int queueSize = waitingPatientList.toQueueArray().length; //adt method
        waitingPatientList.clearQueue(); //adt method

        //update patient status for all patients in the queue
        Object[] allPatients = patientList.toArray(); //adt method
        for (Object obj : allPatients) {
            Patient patient = (Patient) obj;
            if (isPatientInQueue(patient)) {
                patient.setStatus("Active");
            }
        }
        
        System.out.println("[OK] Patient queue cleared successfully! " + queueSize + " patients removed from queue.");
    }
    
    public void viewNextPatientInQueue() {
        if (waitingPatientList.isQueueEmpty()) {
            System.out.println("No patients in waiting queue.");
            return;
        }
        
        Patient nextPatient = waitingPatientList.getFront();
        if (nextPatient != null) {
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("NEXT PATIENT IN QUEUE");
            System.out.println(StringUtility.repeatString("-", 60));
            System.out.println("ID: " + nextPatient.getId());
            System.out.println("Name: " + nextPatient.getName());
            System.out.println("Age: " + nextPatient.getAge());
            System.out.println("Gender: " + nextPatient.getGender());
            System.out.println("Contact: " + nextPatient.getContactNumber());
            System.out.println("Status: " + nextPatient.getStatus());
            System.out.println(StringUtility.repeatString("-", 60));
        } else {
            System.out.println("Error retrieving next patient from queue.");
        }
    }
    
    public void displayAllPatientsSorted() {
        Object[] patientsArray = patientList.toArray(); //adt method

        SetAndQueueInterface<Patient> tempList = new SetQueueArray<>();
        for (Object obj : patientsArray) {
            tempList.add((Patient) obj); //adt method
        }
        tempList.sort(); //adt method

        Object[] sortedPatientsArray = tempList.toArray(); //adt method
        Patient[] patientArray = new Patient[sortedPatientsArray.length];
        for (int i = 0; i < sortedPatientsArray.length; i++) {
            patientArray[i] = (Patient) sortedPatientsArray[i];
        }
        
        String[] headers = {"ID", "Name", "Age", "Gender", "Contact", "Status"};
        Object[][] rows = new Object[patientArray.length][headers.length];
        for (int i = 0; i < patientArray.length; i++) {
            Patient patient = patientArray[i];
            rows[i][0] = patient.getId();
            rows[i][1] = patient.getName();
            rows[i][2] = patient.getAge();
            rows[i][3] = patient.getGender();
            rows[i][4] = patient.getContactNumber();
            rows[i][5] = patient.getStatus();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "ALL PATIENTS (SORTED BY ID)",
            headers,
            rows
        ));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchPatientById() {
        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to search: ");
        
        Patient foundPatient = findPatientById(patientId);
        if (foundPatient != null) {
            displayPatientDetails(foundPatient);
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void searchPatientByName() {
        String patientName = InputValidator.getValidString(scanner, "Enter patient name to search: ");
        
        Object[] patientsArray = patientList.toArray(); //adt method
        Patient foundPatient = null;

        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getName().toLowerCase().contains(patientName.toLowerCase())) {
                foundPatient = patient;
                break;
            }
        }
        
        if (foundPatient != null) {
            displayPatientDetails(foundPatient);
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void displayPatientDetails(Patient patient) {
        System.out.println("\n" + StringUtility.repeatString("-", 60));
        System.out.println("PATIENT DETAILS");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact: " + patient.getContactNumber());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Allergies: " + (patient.getAllegy().isEmpty() ? "None" : patient.getAllegy()));
        System.out.println("Medical History: " + (patient.getMedicalHistory().isEmpty() ? "None" : patient.getMedicalHistory()));
        System.out.println("Registration Date: " + patient.getRegistrationDate());
        System.out.println("Current Status: " + patient.getStatus());
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updatePatientInformation() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        UPDATE PATIENT INFORMATION");
        System.out.println(StringUtility.repeatString("=", 60));

        System.out.println("📋 CURRENT PATIENT LIST:");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.printf("%-8s %-20s %-8s %-10s %-15s %-10s\n", "ID", "Name", "Age", "Gender", "Contact", "Status");
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] patientsArray = patientList.toArray(); //adt method
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            System.out.printf("%-8s %-20s %-8s %-10s %-15s %-10s\n", 
                patient.getId(), 
                patient.getName(), 
                patient.getAge(), 
                patient.getGender(), 
                patient.getContactNumber(), 
                patient.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Total Patients: " + patientsArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to update: ");
        
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            System.out.println("Current patient information:");
            displayPatientDetails(patient);
            
            System.out.println("\nEnter new information (press Enter to keep current value):");
            String name = InputValidator.getValidStringAllowEmpty(scanner, "Name [" + patient.getName() + "]: ");
            if (!name.isEmpty()) patient.setName(name);
            
            String contact = InputValidator.getValidStringAllowEmpty(scanner, "Contact [" + patient.getContactNumber() + "]: ");
            if (!contact.isEmpty()) {
                if (contact.matches("^(01[0-9]|03|04|05|06|07|08|09)-?[0-9]{7,8}$")) {
                    patient.setContactNumber(contact);
                } else {
                    System.out.println("Invalid phone number format. Contact number not updated.");
                }
            }
            
            String address = InputValidator.getValidStringAllowEmpty(scanner, "Address [" + patient.getAddress() + "]: ");
            if (!address.isEmpty()) patient.setAddress(address);
            
            String allergies = InputValidator.getValidStringAllowEmpty(scanner, "Allergies [" + patient.getAllegy() + "]: ");
            if (!allergies.isEmpty()) patient.setAllegy(allergies);
            
            System.out.println("[OK] Patient information updated successfully!");
        } else {
            System.out.println("[ERROR] Patient not found!");
        }
    }
    
    public void removePatient() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        REMOVE PATIENT");
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.println("📋 CURRENT PATIENT LIST:");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.printf("%-8s %-20s %-8s %-10s %-15s %-10s\n", "ID", "Name", "Age", "Gender", "Contact", "Status");
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] patientsArray = patientList.toArray(); //adt method
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            System.out.printf("%-8s %-20s %-8s %-10s %-15s %-10s\n", 
                patient.getId(), 
                patient.getName(), 
                patient.getAge(), 
                patient.getGender(), 
                patient.getContactNumber(), 
                patient.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Total Patients: " + patientsArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to remove: ");
        
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            System.out.println("Patient to be removed:");
            displayPatientDetails(patient);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this patient? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removedFromList = patientList.remove(patient); //adt method
                
                boolean removedFromQueue = false;
                SetAndQueueInterface<Patient> newQueue = new SetQueueArray<>();
                Object[] queueArray = waitingPatientList.toQueueArray();
                
                for (Object obj : queueArray) {
                    Patient queuePatient = (Patient) obj;
                    if (queuePatient.getId() != patient.getId()) {
                        newQueue.enqueue(queuePatient); //adt method
                    } else {
                        removedFromQueue = true;
                    }
                }
                
                if (removedFromQueue) {
                    waitingPatientList = newQueue;
                }
                
                if (removedFromList) {
                    System.out.println("[OK] Patient removed successfully!");
                } else {
                    System.out.println("[ERROR] Failed to remove patient from system!");
                }
            } else {
                System.out.println("[ERROR] Patient removal cancelled.");
            }
        } else {
            System.out.println("[ERROR] Patient not found!");
        }
    }
    
    public void generatePatientStatisticsReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 70));
        System.out.println("        PATIENT STATISTICS REPORT");
        System.out.println(StringUtility.repeatString("=", 70));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 70));

        Object[] patientsArray = patientList.toArray(); //adt method
        int totalPatients = patientsArray.length;
        int maleCount = 0, femaleCount = 0;

        //create sets for set operations analysis
        SetAndQueueInterface<Patient> malePatients = new SetQueueArray<>();
        SetAndQueueInterface<Patient> femalePatients = new SetQueueArray<>();
        SetAndQueueInterface<Patient> patientsWithConsultations = new SetQueueArray<>();
        SetAndQueueInterface<Patient> patientsInQueue = new SetQueueArray<>();

        //categorize patients and count gender
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getGender().equalsIgnoreCase("Male")) {
                maleCount++;
                malePatients.add(patient); //adt method
            } else {
                femalePatients.add(patient); //adt method
            }
        }
        femaleCount = totalPatients - maleCount;

        SetAndQueueInterface<String> patientIdsWithConsultations = new SetQueueArray<>();
        int maxConsultations = 0;
        
        if (consultationManagement != null) {
            Object[] consultationsArray = consultationManagement.getAllConsultations();
            for (Object obj : consultationsArray) {
                Consultation consultation = (Consultation) obj;
                String patientId = consultation.getPatientId();
                patientIdsWithConsultations.add(patientId); //adt method

                //count consultations for this patient
                int consultationCount = countConsultationsForPatient(patientId, consultationsArray);
                if (consultationCount > maxConsultations) {
                    maxConsultations = consultationCount;
                }

                Patient patient = findPatientById(Integer.parseInt(patientId));
                if (patient != null) {
                    patientsWithConsultations.add(patient); //adt method
                }
            }
        }

        //get patients in queue
        Object[] queueArray = waitingPatientList.toQueueArray(); //adt method
        for (Object obj : queueArray) {
            Patient patient = (Patient) obj;
            patientsInQueue.add(patient); //adt method
        }

        //perform set operations
        SetAndQueueInterface<Patient> patientsWithBoth = patientsWithConsultations.intersection(patientsInQueue); //adt method
        SetAndQueueInterface<Patient> allActivePatients = patientsWithConsultations.union(patientsInQueue);

        String[] headers = {"ID", "Name", "Age", "Gender", "Contact", "Status"};
        Object[][] rows = new Object[patientsArray.length][headers.length];
        for (int i = 0; i < patientsArray.length; i++) {
            Patient p = (Patient) patientsArray[i];
            rows[i][0] = p.getId();
            rows[i][1] = p.getName();
            rows[i][2] = p.getAge();
            rows[i][3] = p.getGender();
            rows[i][4] = p.getContactNumber();
            rows[i][5] = p.getStatus();
        }
        System.out.println("\nPATIENT LIST:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        int barWidth = 30;
        int maxGender = Math.max(maleCount, femaleCount);
        System.out.println("\nGENDER DISTRIBUTION:");
        System.out.printf("%-25s [%s] %d\n","Male : ", StringUtility.greenBarChart(maleCount, maxGender, barWidth), maleCount);
        System.out.printf("%-25s [%s] %d\n","Female : ", StringUtility.greenBarChart(femaleCount, maxGender, barWidth), femaleCount);

        System.out.println("\nCONSULTATIONS PER PATIENT:");
        for (Object obj : patientsArray) {
            Patient p = (Patient) obj;
            int count = countConsultationsForPatient(String.valueOf(p.getId()), consultationManagement.getAllConsultations());
            System.out.printf("%-25s [%s] %d\n", p.getName(), StringUtility.greenBarChart(count, maxConsultations, barWidth), count);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Patients: " + totalPatients);
        System.out.println("• Male Patients: " + maleCount);
        System.out.println("• Female Patients: " + femaleCount);
        System.out.println("• Patients with Consultations: " + patientsWithConsultations.size()); //adt method
        System.out.println("• Patients in Queue: " + patientsInQueue.size()); //adt method
        System.out.println("• Active Patients: " + allActivePatients.size()); //adt method
        System.out.println("• Patients with Both (Consultation & Queue): " + patientsWithBoth.size()); //adt method

        System.out.println(StringUtility.repeatString("=", 70));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public Patient findPatientById(int patientId) {
        Patient dummy = new Patient();
        dummy.setId(patientId);
        return patientList.search(dummy); //adt method
    }

    private boolean isPatientExists(String phoneNumber) {
        Object[] patientsArray = patientList.toArray(); //adt method
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getContactNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
    
    public Patient getNextPatientFromQueue() {
        return waitingPatientList.dequeue(); //adt method
    }

    public void clearSet() {
        patientList.clearSet(); //adt method
        System.out.println("[OK] All patient records cleared successfully!");
    }

    public int getTotalPatientCount() {
        return patientList.size(); //adt method
    }

    public Object[] getAllPatients() {
        return patientList.toArray(); //adt method
    }
    
    public int getQueueSize() {
        return waitingPatientList.toQueueArray().length;
    }
    
    public boolean isQueueEmpty() {
        return waitingPatientList.isQueueEmpty();
    }

    private int countConsultationsForPatient(String patientId, Object[] consultationsArray) {
        int count = 0;
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getPatientId().equals(patientId)) {
                count++;
            }
        }
        return count;
    }

    private boolean isPatientInQueue(Patient patient) {
        Object[] queueArray = waitingPatientList.toQueueArray();
        for (Object obj : queueArray) {
            Patient queuePatient = (Patient) obj;
            if (queuePatient.getId() == patient.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public void searchPatientConsultations() {
        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to search consultations: ");
        
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }
        
        Object[] consultationsArray = patient.getConsultations().toArray();
        String[] headers = {"Consultation ID", "Doctor ID", "Date", "Status", "Notes"};
        Object[][] rows = new Object[consultationsArray.length][headers.length];
        for (int i = 0; i < consultationsArray.length; i++) {
            Consultation consultation = (Consultation) consultationsArray[i];
            rows[i][0] = consultation.getConsultationId();
            rows[i][1] = consultation.getDoctorId();
            rows[i][2] = consultation.getConsultationDate();
            rows[i][3] = consultation.getStatus();
            rows[i][4] = consultation.getNotes();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CONSULTATIONS FOR PATIENT: " + patient.getName(),
            headers,
            rows
        ));
        if (consultationsArray.length == 0) {
            System.out.println("No consultations found for this patient.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchPatientTreatments() {
        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to search treatments: ");
        
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }
        
        Object[] treatmentsArray = patient.getTreatments().toArray();
        String[] headers = {"Treatment ID", "Doctor ID", "Diagnosis", "Medications", "Date"};
        Object[][] rows = new Object[treatmentsArray.length][headers.length];
        for (int i = 0; i < treatmentsArray.length; i++) {
            Treatment treatment = (Treatment) treatmentsArray[i];
            rows[i][0] = treatment.getTreatmentId();
            rows[i][1] = treatment.getDoctorId();
            rows[i][2] = treatment.getDiagnosis();
            rows[i][3] = treatment.getPrescribedMedications();
            rows[i][4] = treatment.getTreatmentDate();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "TREATMENTS FOR PATIENT: " + patient.getName(),
            headers,
            rows
        ));
        if (treatmentsArray.length == 0) {
            System.out.println("No treatments found for this patient.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchPatientPrescriptions() {
        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to search prescriptions: ");
        
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }
        
        Object[] prescriptionsArray = patient.getPrescriptions().toArray();
        String[] headers = {"Prescription ID", "Doctor ID", "Diagnosis", "Total Cost", "Status", "Paid"};
        Object[][] rows = new Object[prescriptionsArray.length][headers.length];
        for (int i = 0; i < prescriptionsArray.length; i++) {
            Prescription prescription = (Prescription) prescriptionsArray[i];
            rows[i][0] = prescription.getPrescriptionId();
            rows[i][1] = prescription.getDoctorId();
            rows[i][2] = prescription.getDiagnosis();
            rows[i][3] = "RM " + String.format("%.2f", prescription.getTotalCost());
            rows[i][4] = prescription.getStatus();
            rows[i][5] = prescription.isPaid() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "PRESCRIPTIONS FOR PATIENT: " + patient.getName(),
            headers,
            rows
        ));
        if (prescriptionsArray.length == 0) {
            System.out.println("No prescriptions found for this patient.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
