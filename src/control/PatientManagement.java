package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Patient;
import dao.DataInitializer;
import utility.InputValidator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entity.Consultation;

public class PatientManagement {
    private SetAndQueueInterface<Patient> patientList = new SetAndQueue<>();
    private SetAndQueueInterface<Patient> waitingPatientList = new SetAndQueue<>();
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
            patientList.add(patient);
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
            System.out.println("❌ Error: A patient with this phone number already exists!");
            System.out.println("Please use a different phone number or check existing patient records.");
            return;
        }
        
        String registrationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        Patient newPatient = new Patient(patientIdCounter++, name, age, gender, allergies, contactNumber, address, registrationDate, medicalHistory, false, "Registered");
        
        patientList.add(newPatient);
        waitingPatientList.enqueue(newPatient);
        newPatient.setIsInWaiting(true);
        newPatient.setCurrentStatus("Waiting");
        
        System.out.println("\n✅ Patient registered successfully!");
        System.out.println("Patient ID: " + newPatient.getId());
        System.out.println("Patient added to waiting queue.");
    }
    
    public void selectExistingPatient() {
        System.out.println("\n=== SELECT EXISTING PATIENT ===");
        
        if (patientList.size() == 0) {
            System.out.println("No patients registered in the system.");
            return;
        }
        
        System.out.println("Available patients:");
        System.out.println(repeatString("-", 70));
        System.out.printf("%-8s %-30s %-8s %-8s %-15s\n", "ID", "Name", "Age", "Gender", "Contact");
        System.out.println(repeatString("-", 70));
        
        Object[] patientsArray = patientList.toArray();
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            System.out.printf("%-8s %-30s %-8s %-8s %-15s\n", 
                patient.getId(), patient.getName(), patient.getAge(), 
                patient.getGender(), patient.getContactNumber());
        }
        System.out.println(repeatString("-", 70));

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
                waitingPatientList.enqueue(selectedPatient);
                selectedPatient.setIsInWaiting(true);
                selectedPatient.setCurrentStatus("waiting");
                System.out.println("Patient " + selectedPatient.getName() + " added to waiting queue.");
            } else {
                System.out.println("Patient is already in the waiting queue!");
            }
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void displayWaitingQueue() {
        System.out.println("\n" + repeatString("-", 70));
        System.out.println("CURRENT WAITING QUEUE");
        System.out.println(repeatString("-", 70));
        
        if (waitingPatientList.isQueueEmpty()) {
            System.out.println("No patients in waiting queue.");
        } else {
            System.out.printf("%-8s %-30s %-8s %-8s %-15s\n", "ID", "Name", "Age", "Gender", "Contact");
            System.out.println(repeatString("-", 70));
            
            Object[] queueArray = waitingPatientList.toQueueArray();
            for (Object obj : queueArray) {
                Patient patient = (Patient) obj;
                System.out.printf("%-8s %-30s %-8s %-8s %-15s\n", 
                    patient.getId(), patient.getName(), patient.getAge(), 
                    patient.getGender(), patient.getContactNumber());
            }
        }
        System.out.println(repeatString("-", 70));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void removePatientFromQueue() {
        if (waitingPatientList.isQueueEmpty()) {
            System.out.println("No patients in waiting queue.");
            return;
        }

        System.out.println("\n" + repeatString("-", 70));
        System.out.println("CURRENT WAITING QUEUE");
        System.out.println(repeatString("-", 70));
        System.out.printf("%-8s %-30s %-8s %-8s %-15s\n", "ID", "Name", "Age", "Gender", "Contact");
        System.out.println(repeatString("-", 70));
        
        Object[] displayQueueArray = waitingPatientList.toQueueArray();
        for (Object obj : displayQueueArray) {
            Patient patient = (Patient) obj;
            System.out.printf("%-8s %-30s %-8s %-8s %-15s\n", 
                patient.getId(), patient.getName(), patient.getAge(), 
                patient.getGender(), patient.getContactNumber());
        }
        System.out.println(repeatString("-", 70));

        int patientId = InputValidator.getValidInt(scanner, 1, 9999, "Enter patient ID to remove from queue: ");
        
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            boolean foundInQueue = false;
            SetAndQueueInterface<Patient> newQueue = new SetAndQueue<>();
            Object[] queueArray = waitingPatientList.toQueueArray();
            
            for (Object obj : queueArray) {
                Patient queuePatient = (Patient) obj;
                if (queuePatient.getId() == patient.getId()) {
                    foundInQueue = true;
                    patient.setIsInWaiting(false);
                    patient.setCurrentStatus("Removed");
                    System.out.println("Patient " + patient.getName() + " removed from waiting queue.");
                } else {
                    newQueue.enqueue(queuePatient);
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
    
    public void displayAllPatientsSorted() {
        System.out.println("\n" + repeatString("-", 90));
        System.out.println("ALL PATIENTS (SORTED BY ID)");
        System.out.println(repeatString("-", 90));
        System.out.printf("%-8s %-30s %-8s %-8s %-15s %-15s\n", "ID", "Name", "Age", "Gender", "Contact", "Status");
        System.out.println(repeatString("-", 90));
        
        Object[] patientsArray = patientList.toArray();
        Patient[] patientArray = new Patient[patientsArray.length];
        for (int i = 0; i < patientsArray.length; i++) {
            patientArray[i] = (Patient) patientsArray[i];
        }
        
        utility.BubbleSort.sort(patientArray);
        
        for (Patient patient : patientArray) {
            System.out.printf("%-8s %-30s %-8s %-8s %-15s %-15s\n", 
                patient.getId(), patient.getName(), patient.getAge(), 
                patient.getGender(), patient.getContactNumber(), patient.getCurrentStatus());
        }
        System.out.println(repeatString("-", 90));
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
        
        Object[] patientsArray = patientList.toArray();
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
        System.out.println("\n" + repeatString("-", 60));
        System.out.println("PATIENT DETAILS");
        System.out.println(repeatString("-", 60));
        System.out.println("ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact: " + patient.getContactNumber());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Allergies: " + (patient.getAllegy().isEmpty() ? "None" : patient.getAllegy()));
        System.out.println("Medical History: " + (patient.getMedicalHistory().isEmpty() ? "None" : patient.getMedicalHistory()));
        System.out.println("Registration Date: " + patient.getRegistrationDate());
        System.out.println("Current Status: " + patient.getCurrentStatus());
        System.out.println(repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updatePatientInformation() {
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
            
            System.out.println("✅ Patient information updated successfully!");
        } else {
            System.out.println("❌ Patient not found!");
        }
    }
    
    public void generatePatientStatisticsReport() {
        System.out.println("\n" + repeatString("=", 70));
        System.out.println("        PATIENT STATISTICS REPORT");
        System.out.println(repeatString("=", 70));
        
        Object[] patientsArray = patientList.toArray();
        int totalPatients = patientsArray.length;
        int maleCount = 0, femaleCount = 0;

        SetAndQueueInterface<String> patientsWithConsultations = new SetAndQueue<>();

        if (consultationManagement != null) {
            Object[] consultationsArray = consultationManagement.getAllConsultations();
            for (Object obj : consultationsArray) {
                Consultation consultation = (Consultation) obj;
                patientsWithConsultations.add(consultation.getPatientId());
            }
        } else {
            for (int i = 1; i <= 20; i++) {
                patientsWithConsultations.add(String.valueOf(i));
            }
        }
        
        System.out.println("📊 Patient Statistics:");
        System.out.println("• Total Patients: " + totalPatients);
        System.out.println("• Male Patients: " + maleCount);
        System.out.println("• Female Patients: " + femaleCount);
        
        System.out.println("\n📈 Gender Distribution:");
        if (totalPatients > 0) {
            int maleBars = (int) Math.round((double) maleCount / totalPatients * 20);
            int femaleBars = (int) Math.round((double) femaleCount / totalPatients * 20);
            
            System.out.printf("%-10s [%s] %2d (%.1f%%)\n", "Male:", createColoredBar(BLUE_BG, maleBars, 20), maleCount, (double)maleCount/totalPatients*100);
            System.out.println("");
            System.out.printf("%-10s [%s] %2d (%.1f%%)\n", "Female:", createColoredBar(BLUE_BG, femaleBars, 20), femaleCount, (double)femaleCount/totalPatients*100);
        }
        
        System.out.println("\n📈 Age Range Distribution:");
        if (totalPatients > 0) {
            //calculate age distribution
            int[] ageGroups = new int[6]; // 0-10, 11-20, 21-30, 31-40, 41-50, 50+
            String[] ageLabels = {"0-10", "11-20", "21-30", "31-40", "41-50", "50+"};
            
            for (Object obj : patientsArray) {
                Patient patient = (Patient) obj;
                int age = patient.getAge();
                if (age <= 10) ageGroups[0]++;
                else if (age <= 20) ageGroups[1]++;
                else if (age <= 30) ageGroups[2]++;
                else if (age <= 40) ageGroups[3]++;
                else if (age <= 50) ageGroups[4]++;
                else ageGroups[5]++;
            }
            
            //find the maximum count for scaling
            int maxCount = 0;
            for (int count : ageGroups) {
                if (count > maxCount) maxCount = count;
            }
            
            //display age range distribution with bar charts
            for (int i = 0; i < ageGroups.length; i++) {
                int bars = maxCount > 0 ? (int) Math.round((double) ageGroups[i] / maxCount * 25) : 0;
                String percentage = totalPatients > 0 ? String.format("%.1f", (double)ageGroups[i]/totalPatients*100) : "0.0";
                System.out.printf("%-8s [%s] %d patients (%s%%)\n", 
                    ageLabels[i] + ":", 
                    createColoredBar(BLUE_BG, bars, 25),
                    ageGroups[i], 
                    percentage);
                System.out.println("");
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generatePatientAgeDistributionReport() {
        System.out.println("\n" + repeatString("=", 70));
        System.out.println("        PATIENT AGE DISTRIBUTION REPORT");
        System.out.println(repeatString("=", 70));
        
        Object[] patientsArray = patientList.toArray();
        int[] ageGroups = new int[6]; // 0-10, 11-20, 21-30, 31-40, 41-50, 50+
        String[] ageLabels = {"0-10", "11-20", "21-30", "31-40", "41-50", "50+"};
        
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            int age = patient.getAge();
            if (age <= 10) ageGroups[0]++;
            else if (age <= 20) ageGroups[1]++;
            else if (age <= 30) ageGroups[2]++;
            else if (age <= 40) ageGroups[3]++;
            else if (age <= 50) ageGroups[4]++;
            else ageGroups[5]++;
        }
        
        int totalPatients = patientsArray.length;
        System.out.println("📊 Age Distribution:");
        
        //find the maximum count for scaling
        int maxCount = 0;
        for (int count : ageGroups) {
            if (count > maxCount) maxCount = count;
        }
        
        //display bar chart with single color
        for (int i = 0; i < ageGroups.length; i++) {
            int bars = maxCount > 0 ? (int) Math.round((double) ageGroups[i] / maxCount * 30) : 0;
            String percentage = totalPatients > 0 ? String.format("%.1f", (double)ageGroups[i]/totalPatients*100) : "0.0";
            System.out.printf("%-8s [%s] %d patients (%s%%)\n", 
                ageLabels[i] + ":", 
                createColoredBar(BLUE_BG, bars, 30),
                ageGroups[i], 
                percentage);
            System.out.println("");
        }
        
        //summary statistics
        System.out.println("\n📈 Summary:");
        System.out.println("• Total Patients: " + totalPatients);
        System.out.println("• Average Age: " + String.format("%.1f", calculateAverageAge(patientsArray)));
        System.out.println("• Youngest Age Group: " + getYoungestAgeGroup(ageGroups, ageLabels));
        System.out.println("• Oldest Age Group: " + getOldestAgeGroup(ageGroups, ageLabels));
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public Patient findPatientById(int patientId) {
        Object[] patientsArray = patientList.toArray();
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getId() == patientId) {
                return patient;
            }
        }
        return null;
    }
    
    private boolean isPatientExists(String phoneNumber) {
        Object[] patientsArray = patientList.toArray();
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getContactNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
    
    public Patient getNextPatientFromQueue() {
        return waitingPatientList.dequeue();
    }
    
    public int getTotalPatientCount() {
        return patientList.size();
    }
    
    public Object[] getAllPatients() {
        return patientList.toArray();
    }
    
    public int getQueueSize() {
        return waitingPatientList.toQueueArray().length;
    }
    
    public boolean isQueueEmpty() {
        return waitingPatientList.isQueueEmpty();
    }

    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BG = "\u001B[44m";
    
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    private String createColoredBar(String color, int barCount, int totalWidth) {
        return color + repeatString(" ", barCount) + RESET + repeatString(" ", totalWidth - barCount);
    }
    
    private double calculateAverageAge(Object[] patientsArray) {
        if (patientsArray.length == 0) return 0.0;
        
        int totalAge = 0;
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            totalAge += patient.getAge();
        }
        return (double) totalAge / patientsArray.length;
    }
    
    private String getYoungestAgeGroup(int[] ageGroups, String[] ageLabels) {
        for (int i = 0; i < ageGroups.length; i++) {
            if (ageGroups[i] > 0) {
                return ageLabels[i] + " (" + ageGroups[i] + " patients)";
            }
        }
        return "None";
    }
    
    private String getOldestAgeGroup(int[] ageGroups, String[] ageLabels) {
        for (int i = ageGroups.length - 1; i >= 0; i--) {
            if (ageGroups[i] > 0) {
                return ageLabels[i] + " (" + ageGroups[i] + " patients)";
            }
        }
        return "None";
    }
}
