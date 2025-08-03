package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Patient;
import dao.DataInitializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PatientManagement {
    private SetAndQueueInterface<Patient> patientList = new SetAndQueue<>();
    private SetAndQueueInterface<Patient> waitingPatientList = new SetAndQueue<>();
    private Scanner scanner;
    private int patientIdCounter = 1001;
    
    public PatientManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    private void loadSampleData() {
        Patient[] samplePatients = DataInitializer.initializeSamplePatients();
        for (Patient patient : samplePatients) {
            patientList.add(patient);
        }
    }
    
    public void registerNewPatient() {
        System.out.println("\n=== REGISTER NEW PATIENT ===");
        
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter patient age: ");
        int age = getUserInputInt(0, 150);
        
        System.out.print("Enter gender (M/F): ");
        String gender = scanner.nextLine();
        
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter allergies (if any, or press Enter): ");
        String allergies = scanner.nextLine();
        
        System.out.print("Enter medical history (if any, or press Enter): ");
        String medicalHistory = scanner.nextLine();
        
        String registrationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        Patient newPatient = new Patient(patientIdCounter++, name, age, gender, allergies, contactNumber, address, registrationDate, medicalHistory, false, "Registered");
        
        patientList.add(newPatient);
        waitingPatientList.enqueue(newPatient);
        newPatient.setIsInWaiting(true);
        newPatient.setCurrentStatus("Waiting");
        
        System.out.println("Patient registered successfully! Patient ID: " + newPatient.getId());
        System.out.println("Patient added to waiting queue.");
    }
    
    public void selectExistingPatient() {
        System.out.println("\n=== SELECT EXISTING PATIENT ===");
        
        if (patientList.size() == 0) {
            System.out.println("No patients registered in the system.");
            return;
        }
        
        System.out.println("Available patients:");
        System.out.println(repeatString("-", 60));
        System.out.printf("%-8s %-20s %-8s %-8s %-15s\n", "ID", "Name", "Age", "Gender", "Contact");
        System.out.println(repeatString("-", 60));
        
        Object[] patientsArray = patientList.toArray();
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            System.out.printf("%-8s %-20s %-8s %-8s %-15s\n", 
                patient.getId(), patient.getName(), patient.getAge(), 
                patient.getGender(), patient.getContactNumber());
        }
        System.out.println(repeatString("-", 60));
        
        System.out.print("Enter patient ID to add to queue: ");
        int patientId = getUserInputInt(0, 9999);
        
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
        System.out.println("\n" + repeatString("-", 60));
        System.out.println("CURRENT WAITING QUEUE");
        System.out.println(repeatString("-", 60));
        
        if (waitingPatientList.isQueueEmpty()) {
            System.out.println("No patients in waiting queue.");
        } else {
            System.out.printf("%-8s %-20s %-8s %-8s %-15s\n", "ID", "Name", "Age", "Gender", "Contact");
            System.out.println(repeatString("-", 60));
            
            Object[] queueArray = waitingPatientList.toQueueArray();
            for (Object obj : queueArray) {
                Patient patient = (Patient) obj;
                System.out.printf("%-8s %-20s %-8s %-8s %-15s\n", 
                    patient.getId(), patient.getName(), patient.getAge(), 
                    patient.getGender(), patient.getContactNumber());
            }
        }
        System.out.println(repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void removePatientFromQueue() {
        if (waitingPatientList.isQueueEmpty()) {
            System.out.println("No patients in waiting queue.");
            return;
        }
        
        System.out.print("Enter patient ID to remove from queue: ");
        int patientId = getUserInputInt(0, 9999);
        
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
                    patient.setCurrentStatus("removed");
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
        System.out.println("\n" + repeatString("-", 80));
        System.out.println("ALL PATIENTS (SORTED BY ID)");
        System.out.println(repeatString("-", 80));
        System.out.printf("%-8s %-20s %-8s %-8s %-15s %-15s\n", "ID", "Name", "Age", "Gender", "Contact", "Status");
        System.out.println(repeatString("-", 80));
        
        Object[] patientsArray = patientList.toArray();
        Patient[] patientArray = new Patient[patientsArray.length];
        for (int i = 0; i < patientsArray.length; i++) {
            patientArray[i] = (Patient) patientsArray[i];
        }
        
        // Sort patients by ID using bubble sort
        utility.BubbleSort.sort(patientArray);
        
        for (Patient patient : patientArray) {
            System.out.printf("%-8s %-20s %-8s %-8s %-15s %-15s\n", 
                patient.getId(), patient.getName(), patient.getAge(), 
                patient.getGender(), patient.getContactNumber(), patient.getCurrentStatus());
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchPatientById() {
        System.out.print("Enter patient ID to search: ");
        int patientId = getUserInputInt(0, 9999);
        
        Patient foundPatient = findPatientById(patientId);
        if (foundPatient != null) {
            displayPatientDetails(foundPatient);
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void searchPatientByName() {
        System.out.print("Enter patient name to search: ");
        String patientName = scanner.nextLine();
        
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
        System.out.print("Enter patient ID to update: ");
        int patientId = getUserInputInt(0, 9999);
        
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            System.out.println("Current patient information:");
            displayPatientDetails(patient);
            
            System.out.println("\nEnter new information (press Enter to keep current value):");
            System.out.print("Name [" + patient.getName() + "]: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) patient.setName(name);
            
            System.out.print("Contact [" + patient.getContactNumber() + "]: ");
            String contact = scanner.nextLine();
            if (!contact.isEmpty()) patient.setContactNumber(contact);
            
            System.out.print("Address [" + patient.getAddress() + "]: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) patient.setAddress(address);
            
            System.out.print("Allergies [" + patient.getAllegy() + "]: ");
            String allergies = scanner.nextLine();
            if (!allergies.isEmpty()) patient.setAllegy(allergies);
            
            System.out.println("Patient information updated successfully!");
        } else {
            System.out.println("Patient not found!");
        }
    }
    
    public void generatePatientStatisticsReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        PATIENT STATISTICS REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] patientsArray = patientList.toArray();
        int totalPatients = patientsArray.length;
        int maleCount = 0, femaleCount = 0;
        int waitingCount = 0, consultedCount = 0, completedCount = 0;
        
        for (Object obj : patientsArray) {
            Patient patient = (Patient) obj;
            if (patient.getGender().equalsIgnoreCase("M")) maleCount++;
            else if (patient.getGender().equalsIgnoreCase("F")) femaleCount++;
            
            switch (patient.getCurrentStatus()) {
                case "waiting": waitingCount++; break;
                case "consulted": consultedCount++; break;
                case "completed": completedCount++; break;
            }
        }
        
        System.out.println("📊 Patient Statistics:");
        System.out.println("• Total Patients: " + totalPatients);
        System.out.println("• Male Patients: " + maleCount);
        System.out.println("• Female Patients: " + femaleCount);
        System.out.println("• Patients in Queue: " + waitingCount);
        System.out.println("• Patients Consulted: " + consultedCount);
        System.out.println("• Patients Completed: " + completedCount);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generatePatientAgeDistributionReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        PATIENT AGE DISTRIBUTION REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] patientsArray = patientList.toArray();
        int[] ageGroups = new int[6]; // 0-10, 11-20, 21-30, 31-40, 41-50, 50+
        
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
        
        System.out.println("📊 Age Distribution:");
        System.out.println("• 0-10 years: " + ageGroups[0] + " patients");
        System.out.println("• 11-20 years: " + ageGroups[1] + " patients");
        System.out.println("• 21-30 years: " + ageGroups[2] + " patients");
        System.out.println("• 31-40 years: " + ageGroups[3] + " patients");
        System.out.println("• 41-50 years: " + ageGroups[4] + " patients");
        System.out.println("• 50+ years: " + ageGroups[5] + " patients");
        
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
    
    public Patient getNextPatientFromQueue() {
        return waitingPatientList.dequeue();
    }
    
    public int getTotalPatientCount() {
        return patientList.size();
    }
    
    public int getQueueSize() {
        return waitingPatientList.toQueueArray().length;
    }
    
    public boolean isQueueEmpty() {
        return waitingPatientList.isQueueEmpty();
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
