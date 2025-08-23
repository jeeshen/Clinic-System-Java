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
        
        if (samplePatients.length > 0) {
            int maxId = 0;
            for (Patient patient : samplePatients) {
                if (patient.getId() > maxId) {
                    maxId = patient.getId();
                }
            }
            patientIdCounter = maxId + 1;
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
        
        String[] headers = {"ID", "Name", "Age", "Gender", "Contact", "Address", "Allergies", "Medical History", "Status"};
        Object[][] rows = new Object[patientArray.length][headers.length];
        for (int i = 0; i < patientArray.length; i++) {
            Patient patient = patientArray[i];
            rows[i][0] = patient.getId();
            rows[i][1] = patient.getName();
            rows[i][2] = patient.getAge();
            rows[i][3] = patient.getGender();
            rows[i][4] = patient.getContactNumber();
            rows[i][5] = patient.getAddress();
            rows[i][6] = patient.getAllegy().isEmpty() ? "None" : patient.getAllegy();
            rows[i][7] = patient.getMedicalHistory().isEmpty() ? "None" : patient.getMedicalHistory();
            rows[i][8] = patient.getStatus();
        }

        System.out.println("\n" + StringUtility.repeatString("=", 150));
        System.out.println("                                    ALL PATIENTS (SORTED BY ID)");
        System.out.println(StringUtility.repeatString("=", 150));
        System.out.printf("%-6s %-25s %-6s %-8s %-15s %-30s %-20s %-25s %-10s\n", 
            "ID", "Name", "Age", "Gender", "Contact", "Address", "Allergies", "Medical History", "Status");
        System.out.println(StringUtility.repeatString("-", 150));
        
        for (int i = 0; i < patientArray.length; i++) {
            Patient patient = patientArray[i];
            String address = patient.getAddress();
            String allergies = patient.getAllegy().isEmpty() ? "None" : patient.getAllegy();
            String medicalHistory = patient.getMedicalHistory().isEmpty() ? "None" : patient.getMedicalHistory();
            
            //truncate long fields to fit in table
            if (address.length() > 28) address = address.substring(0, 25) + "...";
            if (allergies.length() > 18) allergies = allergies.substring(0, 15) + "...";
            if (medicalHistory.length() > 23) medicalHistory = medicalHistory.substring(0, 20) + "...";
            
            System.out.printf("%-6s %-25s %-6s %-8s %-15s %-30s %-20s %-25s %-10s\n",
                patient.getId(),
                patient.getName().length() > 23 ? patient.getName().substring(0, 22) + "..." : patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getContactNumber(),
                address,
                allergies,
                medicalHistory,
                patient.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 150));
        System.out.println("Total Patients: " + patientArray.length);
        System.out.println(StringUtility.repeatString("=", 150));
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
        System.out.println("\n" + StringUtility.repeatString("=", 85));
        System.out.println("        UPDATE PATIENT INFORMATION");
        System.out.println(StringUtility.repeatString("=", 85));

        System.out.println("CURRENT PATIENT LIST:");
        System.out.println(StringUtility.repeatString("-", 85));
        System.out.printf("%-8s %-30s %-8s %-10s %-15s %-10s\n", "ID", "Name", "Age", "Gender", "Contact", "Status");
        System.out.println(StringUtility.repeatString("-", 85));
        
        Object[] patientsArray = patientList.toArray(); //adt method
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            System.out.printf("%-8s %-30s %-8s %-10s %-15s %-10s\n", 
                patient.getId(), 
                patient.getName(), 
                patient.getAge(), 
                patient.getGender(), 
                patient.getContactNumber(), 
                patient.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 85));
        System.out.println("Total Patients: " + patientsArray.length);
        System.out.println(StringUtility.repeatString("=", 85));
        
        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to update: ");
        
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            System.out.println("Current patient information:");
            displayPatientDetails(patient);
            
            System.out.println("\nEnter new information (press Enter to keep current value):");
            String name = InputValidator.getValidStringAllowEmpty(scanner, "Name [" + patient.getName() + "]: ");
            if (!name.isEmpty()) patient.setName(name);
            
            String contact = "";
            boolean validContact = false;
            while (!validContact) {
                contact = InputValidator.getValidStringAllowEmpty(scanner, "Contact [" + patient.getContactNumber() + "]: ");
                if (contact.isEmpty()) {
                    validContact = true; // Skip update
                } else if (contact.matches("^(01[0-9]|03|04|05|06|07|08|09)-?[0-9]{7,8}$")) {
                    // Check if phone number already exists for another patient
                    if (isPhoneNumberExistsForOtherPatient(contact, patient.getId())) {
                        System.out.println("[ERROR] Phone number already exists for another patient. Please enter a different number or press Enter to skip.");
                    } else {
                        patient.setContactNumber(contact);
                        System.out.println("[OK] Contact number updated successfully!");
                        validContact = true;
                    }
                } else {
                    System.out.println("Invalid phone number format. Please enter a valid number or press Enter to skip.");
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



    public void generatePatientDemographicsAndDiseaseAnalysisReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 100));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           PATIENT MANAGEMENT SUBSYSTEM");
        System.out.println("                      PATIENT DEMOGRAPHICS & DISEASE ANALYSIS REPORT");
        System.out.println(StringUtility.repeatString("=", 100));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 100));
        System.out.println("      TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 100));
        System.out.println();

        Object[] patientsArray = patientList.toArray();
        int totalPatients = patientsArray.length;
        int maleCount = 0, femaleCount = 0;

        String reset = "\u001B[0m";

        int[] ageGroups = {0, 18, 30, 45, 60, 100};
        String[] ageGroupNames = {"0-17", "18-29", "30-44", "45-59", "60+"};
        int[] ageGroupCounts = new int[ageGroups.length - 1];

        //disease analysis from prescriptions
        SetAndQueueInterface<String> uniqueDiseases = new SetQueueArray<>();
        int[] diseaseCounts = new int[100];
        String[] diseaseArray = new String[100];
        int[] diseaseCountsMale = new int[100];
        int[] diseaseCountsFemale = new int[100];
        int diseaseIndex = 0;

        //analyze patients
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getGender().equalsIgnoreCase("Male")) {
                maleCount++;
            } else {
                femaleCount++;
            }

            //age group classification
            int age = patient.getAge();
            for (int i = 0; i < ageGroups.length - 1; i++) {
                if (age >= ageGroups[i] && age < ageGroups[i + 1]) {
                    ageGroupCounts[i]++;
                    break;
                }
            }
        }

        //analyze diseases from patient prescriptions
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            boolean isMale = patient.getGender().equalsIgnoreCase("Male");
            Object[] prescriptionsArray = patient.getPrescriptions().toArray();
            for (Object prescObj : prescriptionsArray) {
                entity.Prescription prescription = (entity.Prescription) prescObj;
                String diagnosis = prescription.getDiagnosis();
                if (diagnosis != null && !diagnosis.trim().isEmpty()) {
                    uniqueDiseases.add(diagnosis);

                    // Count diseases
                    boolean found = false;
                    for (int i = 0; i < diseaseIndex; i++) {
                        if (diseaseArray[i].equals(diagnosis)) {
                            diseaseCounts[i]++;
                            if (isMale) {
                                diseaseCountsMale[i]++;
                            } else {
                                diseaseCountsFemale[i]++;
                            }
                            found = true;
                            break;
                        }
                    }
                    if (!found && diseaseIndex < 100) {
                        diseaseArray[diseaseIndex] = diagnosis;
                        diseaseCounts[diseaseIndex] = 1;
                        if (isMale) {
                            diseaseCountsMale[diseaseIndex] = 1;
                            diseaseCountsFemale[diseaseIndex] = 0;
                        } else {
                            diseaseCountsMale[diseaseIndex] = 0;
                            diseaseCountsFemale[diseaseIndex] = 1;
                        }
                        diseaseIndex++;
                    }
                }
            }
        }

        System.out.println("Total Number of Patients: " + totalPatients);
        System.out.println("Total Number of Diseases Recorded: " + diseaseIndex);
        System.out.println();

        System.out.println(StringUtility.repeatString("-", 100));
        System.out.println("                        GRAPHICAL REPRESENTATION OF PATIENT MANAGEMENT");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.println();

        System.out.println("Age Group vs Health Risk Analysis:");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.printf("| %-15s | %-15s | %-30s | %-27s |%n", "Age Group", "Patients", "Avg Consultations", "Risk Level");
        System.out.println(StringUtility.repeatString("-", 100));

        String[] riskLevels = {"Low", "Low", "Medium", "High", "High"};

        //calculate total consultations if consultation management is available
        int totalConsultations = 0;
        if (consultationManagement != null) {
            Object[] consultationsArray = consultationManagement.getAllConsultations();
            totalConsultations = consultationsArray.length;
        }

        for (int i = 0; i < ageGroupNames.length; i++) {
            double avgConsultations = ageGroupCounts[i] > 0 ? (double) totalConsultations / ageGroupCounts[i] * 0.3 : 0;
            System.out.printf("| %-15s | %-15d | %-30.1f | %-27s |%n",
                ageGroupNames[i],
                ageGroupCounts[i],
                avgConsultations,
                riskLevels[i]);
        }
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.println();

        //age group chart
        System.out.println("Patient Age Group Distribution Chart:");
        System.out.println("   ^");
        int maxAgeGroup = 0;
        for (int count : ageGroupCounts) {
            if (count > maxAgeGroup) maxAgeGroup = count;
        }

        String barColor = "\u001B[44m"; //blue background

        for (int level = maxAgeGroup; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < ageGroupCounts.length; i++) {
                if (ageGroupCounts[i] >= level) {
                    System.out.print(" " + barColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        //draw axis line that matches the data width exactly (6 chars per column)
        System.out.print("   +");
        for (int i = 0; i < ageGroupNames.length; i++) {
            System.out.print("------");
        }
        System.out.println("> Age Groups");

        //draw labels aligned with bars (4-char labels with proper spacing)
        System.out.print("    ");
        for (String ageGroup : ageGroupNames) {
            System.out.printf("%-4s  ", ageGroup); // 4-char label with 2 spaces
        }
        System.out.println();
        System.out.println();

        //sort diseases by count for proper display
        for (int i = 0; i < diseaseIndex - 1; i++) {
            for (int j = 0; j < diseaseIndex - i - 1; j++) {
                if (diseaseCounts[j] < diseaseCounts[j + 1]) {
                    //swap diseases
                    String tempDisease = diseaseArray[j];
                    diseaseArray[j] = diseaseArray[j + 1];
                    diseaseArray[j + 1] = tempDisease;

                    //swap counts
                    int tempCount = diseaseCounts[j];
                    diseaseCounts[j] = diseaseCounts[j + 1];
                    diseaseCounts[j + 1] = tempCount;
                }
            }
        }

        System.out.println("Disease-Treatment Correlation Analysis by Gender:");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.printf("| %-30s | %-30s | %-30s |%n", "Disease Name", "Male", "Female");
        System.out.println(StringUtility.repeatString("-", 100));

        for (int i = 0; i < Math.min(diseaseIndex, 5); i++) {
            System.out.printf("| %-30s | %-30d | %-30d |%n",
                diseaseArray[i].length() > 30 ? diseaseArray[i].substring(0, 30) : diseaseArray[i],
                diseaseCountsMale[i],
                diseaseCountsFemale[i]);
        }
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.println();

        //disease occurrences by gender chart
        System.out.println("Disease Occurrences by Gender Chart:");
        System.out.println("   ^");
        int maxDisease = 0;
        int numDiseases = Math.min(diseaseIndex, 5);
        for (int i = 0; i < numDiseases; i++) {
            if (diseaseCountsMale[i] > maxDisease) maxDisease = diseaseCountsMale[i];
            if (diseaseCountsFemale[i] > maxDisease) maxDisease = diseaseCountsFemale[i];
        }

        //colors for male and female bars
        String maleColor = "\u001B[44m"; //blue background for male
        String femaleColor = "\u001B[45m"; //magenta background for female

        for (int level = maxDisease; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numDiseases; i++) {
                //show male count first, then female count for each disease with consistent spacing
                if (diseaseCountsMale[i] >= level) {
                    System.out.print(" " + maleColor + "  " + reset);
                } else {
                    System.out.print("   ");
                }
                if (diseaseCountsFemale[i] >= level) {
                    System.out.print(" " + femaleColor + "  " + reset);
                } else {
                    System.out.print("   ");
                }
                System.out.print(" "); //add consistent spacing between disease groups
            }
            System.out.println();
        }

        //draw axis line that matches the data width exactly (7 chars per disease - M+F+space)
        System.out.print("   +");
        for (int i = 0; i < numDiseases; i++) {
            System.out.print("-------");
        }
        System.out.println("> Diseases");

        //draw labels aligned with bars (centered under each M+F pair)
        System.out.print("    ");
        for (int i = 0; i < numDiseases; i++) {
            String shortName = diseaseArray[i].length() > 4 ? diseaseArray[i].substring(0, 4) : diseaseArray[i];
            System.out.printf(" %-4s  ", shortName); //center the 4-char label in 7-char space
        }
        System.out.println();

        System.out.println("    Legend: " + maleColor + "  " + reset + " Male, " + femaleColor + "  " + reset + " Female");
        System.out.println();

        System.out.println("Patients by gender:");
        System.out.printf("< Male: %d, Female: %d >%n", maleCount, femaleCount);
        System.out.println();

        if (diseaseIndex > 0) {
            //find most common disease overall
            int maxDiseaseIndex = 0;
            for (int i = 1; i < diseaseIndex; i++) {
                if (diseaseCounts[i] > diseaseCounts[maxDiseaseIndex]) {
                    maxDiseaseIndex = i;
                }
            }
            System.out.printf("Most common disease: < %s with %d cases >%n",
                diseaseArray[maxDiseaseIndex], diseaseCounts[maxDiseaseIndex]);
            
            //find most common disease by gender
            int maxMaleIndex = 0, maxFemaleIndex = 0;
            for (int i = 1; i < diseaseIndex; i++) {
                if (diseaseCountsMale[i] > diseaseCountsMale[maxMaleIndex]) {
                    maxMaleIndex = i;
                }
                if (diseaseCountsFemale[i] > diseaseCountsFemale[maxFemaleIndex]) {
                    maxFemaleIndex = i;
                }
            }
            System.out.printf("Most common disease among males: < %s with %d cases >%n",
                diseaseArray[maxMaleIndex], diseaseCountsMale[maxMaleIndex]);
            System.out.printf("Most common disease among females: < %s with %d cases >%n",
                diseaseArray[maxFemaleIndex], diseaseCountsFemale[maxFemaleIndex]);
        }

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 100));
        System.out.println("                                       END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 100));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public void generatePatientTreatmentOutcomesReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           PATIENT MANAGEMENT SUBSYSTEM");
        System.out.println("                         PATIENT TREATMENT ANALYSIS REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        Object[] patientsArray = patientList.toArray();
        String reset = "\u001B[0m";
        int[] consultationCounts = new int[patientsArray.length];
        double[] treatmentCosts = new double[patientsArray.length];

        //analyze treatment outcomes
        for (int i = 0; i < patientsArray.length; i++) {
            Patient patient = (Patient) patientsArray[i];
            String patientId = String.valueOf(patient.getId());

            //count consultations
            if (consultationManagement != null) {
                Object[] consultationsArray = consultationManagement.getAllConsultations();
                for (Object obj : consultationsArray) {
                    Consultation consultation = (Consultation) obj;
                    if (consultation.getPatientId().equals(patientId)) {
                        consultationCounts[i]++;
                    }
                }
            }

            //calculate total treatment costs from prescriptions
            Object[] prescriptionsArray = patient.getPrescriptions().toArray();
            for (Object prescObj : prescriptionsArray) {
                entity.Prescription prescription = (entity.Prescription) prescObj;
                treatmentCosts[i] += prescription.getTotalCost();
            }
        }

        //sort patients by consultation count (descending order)
        for (int i = 0; i < patientsArray.length - 1; i++) {
            for (int j = 0; j < patientsArray.length - i - 1; j++) {
                if (consultationCounts[j] < consultationCounts[j + 1]) {
                    //swap patients
                    Object tempPatient = patientsArray[j];
                    patientsArray[j] = patientsArray[j + 1];
                    patientsArray[j + 1] = tempPatient;

                    //swap consultation counts
                    int tempConsult = consultationCounts[j];
                    consultationCounts[j] = consultationCounts[j + 1];
                    consultationCounts[j + 1] = tempConsult;

                    //swap treatment costs
                    double tempCost = treatmentCosts[j];
                    treatmentCosts[j] = treatmentCosts[j + 1];
                    treatmentCosts[j + 1] = tempCost;
                }
            }
        }

        System.out.println("Top Patients by Consultation Count:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("| %-20s | %-40s | %-25s |%n", "Patient ID", "Patient Name", "Consultations");
        System.out.println(StringUtility.repeatString("-", 95));

        for (int i = 0; i < Math.min(patientsArray.length, 10); i++) {
            Patient p = (Patient) patientsArray[i];
            System.out.printf("| %-20s | %-40s | %-25d |%n",
                p.getId(),
                p.getName().length() > 40 ? p.getName().substring(0, 40) : p.getName(),
                consultationCounts[i]);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        //top patients by consultation count chart
        System.out.println("Top Patients by Consultation Count Chart:");
        System.out.println("   ^");
        int maxConsultations = 0;
        int numPatients = Math.min(patientsArray.length, 5);
        for (int i = 0; i < numPatients; i++) {
            if (consultationCounts[i] > maxConsultations) maxConsultations = consultationCounts[i];
        }

        String barColor = "\u001B[41m"; //red background

        for (int level = maxConsultations; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numPatients; i++) {
                if (consultationCounts[i] >= level) {
                    System.out.print(" " + barColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        //draw axis line that matches the data width exactly (6 chars per column)
        System.out.print("   +");
        for (int i = 0; i < numPatients; i++) {
            System.out.print("------");
        }
        System.out.println("> Patients");

        //draw labels aligned with bars (4-char labels with proper spacing)
        System.out.print("    ");
        for (int i = 0; i < numPatients; i++) {
            Patient p = (Patient) patientsArray[i];
            String shortName = p.getName().length() > 4 ? p.getName().substring(0, 4) : p.getName();
            System.out.printf("%-4s  ", shortName); // 4-char label with 2 spaces
        }
        System.out.println();
        System.out.println();

        System.out.println();
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
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
    
    private boolean isPhoneNumberExistsForOtherPatient(String contactNumber, int currentPatientId) {
        Object[] patientsArray = patientList.toArray(); //adt method
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getContactNumber().equals(contactNumber) && patient.getId() != currentPatientId) {
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
