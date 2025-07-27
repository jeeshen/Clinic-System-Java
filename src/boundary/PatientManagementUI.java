package boundary;

import control.PatientManagement;
import entity.Patient;
import utility.StringUtility;
import utility.InputValidator;
import java.util.Scanner;

public class PatientManagementUI {
    private Scanner scanner;
    private PatientManagement patientManagement;
    
    public PatientManagementUI(PatientManagement patientManagement) {
        this.scanner = new Scanner(System.in);
        this.patientManagement = patientManagement;
    }
    
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== PATIENT MANAGEMENT SYSTEM ===");
            System.out.println("1  . Add New Patient");
            System.out.println("2  . Remove Patient");
            System.out.println("3  . Update Patient Information");
            System.out.println("4  . List All Patients");
            System.out.println("5  . Search Patients by Name");
            System.out.println("6  . Search Patients by Phone");
            System.out.println("7  . View Patients by Age Group");
            System.out.println("8  . View Patients by Gender");
            System.out.println("9  . Update Patient Contact Info");
            System.out.println("10 . Update Patient Medical Info");
            System.out.println("11 . Generate Patient Report");
            System.out.println("0  . Return to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = InputValidator.getValidInt(scanner, 0, 11, "");
            
            switch (choice) {
                case 1:
                    addPatientMenu();
                    break;
                case 2:
                    removePatientMenu();
                    break;
                case 3:
                    updatePatientMenu();
                    break;
                case 4:
                    listPatientsMenu();
                    break;
                case 5:
                    searchByNameMenu();
                    break;
                case 6:
                    searchByPhoneMenu();
                    break;
                case 7:
                    viewByAgeGroupMenu();
                    break;
                case 8:
                    viewByGenderMenu();
                    break;
                case 9:
                    updateContactMenu();
                    break;
                case 10:
                    updateMedicalInfoMenu();
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
    
    private void addPatientMenu() {
        System.out.println("\n=== ADD NEW PATIENT ===");
        
        Patient patient = new Patient();
        
        // Get Patient ID with retry for duplicates
        int patientId;
        do {
            patientId = InputValidator.getValidId(scanner, "Enter Patient ID: ");
            patient.setId(patientId);
            
            // Check if patient ID already exists
            if (patientManagement.patientExists(patientId)) {
                System.out.println("Patient ID already exists. Please choose a different ID.");
                System.out.print("Press Enter to try again or '0' to return to menu: ");
                String choice = scanner.nextLine().trim();
                if (choice.equals("0")) {
                    return; // Return to main menu only if user chooses to
                }
            } else {
                break; // Exit loop if ID is unique
            }
        } while (true);
        
        // Get Patient Name
        String name = InputValidator.getValidString(scanner, "Enter Patient Name: ");
        patient.setName(name);
        
        // Get Age
        int age = InputValidator.getValidAge(scanner, "Enter Age: ");
        patient.setAge(age);
        
        // Get Gender
        String gender = InputValidator.getValidGender(scanner, "Enter Gender");
        patient.setGender(gender);
        
        // Get Allergies
        String allegy = InputValidator.getValidStringAllowEmpty(scanner, "Enter Allergies (or press Enter if none): ");
        patient.setAllegy(allegy);
        
        // Get Contact Number
        String contactNumber = InputValidator.getValidPhoneNumber(scanner, "Enter Contact Number: ");
        patient.setContactNumber(contactNumber);
        
        // Get Address
        String address = InputValidator.getValidString(scanner, "Enter Address: ");
        patient.setAddress(address);
        
        // Get Registration Date
        String registrationDate = InputValidator.getValidDate(scanner, "Enter Registration Date");
        patient.setRegistrationDate(registrationDate);
        
        // Get Medical History
        String medicalHistory = InputValidator.getValidStringAllowEmpty(scanner, "Enter Medical History (or press Enter if none): ");
        patient.setMedicalHistory(medicalHistory);
        
        patient.setIsInWaiting(false);
        patient.setCurrentStatus("registered");
        
        boolean success = patientManagement.addPatient(patient);
        
        if (success) {
            System.out.println("Patient added successfully!");
        } else {
            System.out.println("Failed to add patient. Please try again.");
        }
    }
    
    private void removePatientMenu() {
        System.out.println("\n=== REMOVE PATIENT ===");
        
        //show available patient IDs
        showAvailablePatientIds();
        
        int patientId = InputValidator.getValidId(scanner, "Enter Patient ID: ");
        
        boolean success = patientManagement.removePatient(patientId);
        
        if (success) {
            System.out.println("Patient removed successfully!");
        } else {
            System.out.println("Failed to remove patient. Patient not found.");
        }
    }
    
    private void updatePatientMenu() {
        System.out.println("\n=== UPDATE PATIENT INFORMATION ===");
        
        //show available patient IDs
        showAvailablePatientIds();
        
        int patientId = InputValidator.getValidId(scanner, "Enter Patient ID: ");
        
        Patient existingPatient = findPatientById(patientId);
        if (existingPatient == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        Patient updatedPatient = new Patient();
        updatedPatient.setId(patientId);
        
        // Update Name
        System.out.print("Enter new Patient Name (current: " + existingPatient.getName() + "): ");
        String name = scanner.nextLine().trim();
        updatedPatient.setName(name.isEmpty() ? existingPatient.getName() : name);
        
        // Update Age
        System.out.print("Enter new Age (current: " + existingPatient.getAge() + "): ");
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) {
            try {
                int age = Integer.parseInt(ageStr);
                if (age < 1 || age > 120) {
                    System.out.println("Invalid age. Using current age.");
                    updatedPatient.setAge(existingPatient.getAge());
                } else {
                    updatedPatient.setAge(age);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Using current age.");
                updatedPatient.setAge(existingPatient.getAge());
            }
        } else {
            updatedPatient.setAge(existingPatient.getAge());
        }
        
        // Update Gender
        System.out.print("Enter new Gender (current: " + existingPatient.getGender() + "): ");
        String gender = scanner.nextLine().trim();
        if (!gender.isEmpty() && (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))) {
            updatedPatient.setGender(gender);
        } else {
            updatedPatient.setGender(existingPatient.getGender());
        }
        
        // Update Allergies
        System.out.print("Enter new Allergies (current: " + existingPatient.getAllegy() + "): ");
        String allegy = scanner.nextLine().trim();
        updatedPatient.setAllegy(allegy.isEmpty() ? existingPatient.getAllegy() : allegy);
        
        // Update Contact Number
        System.out.print("Enter new Contact Number (current: " + existingPatient.getContactNumber() + "): ");
        String contactNumber = scanner.nextLine().trim();
        if (!contactNumber.isEmpty()) {
            // Validate phone number format
            if (contactNumber.matches("^(01[0-9]|03|04|05|06|07|08|09)-?[0-9]{7,8}$")) {
                updatedPatient.setContactNumber(contactNumber);
            } else {
                System.out.println("Invalid phone number format. Using current contact number.");
                updatedPatient.setContactNumber(existingPatient.getContactNumber());
            }
        } else {
            updatedPatient.setContactNumber(existingPatient.getContactNumber());
        }
        
        // Update Address
        System.out.print("Enter new Address (current: " + existingPatient.getAddress() + "): ");
        String address = scanner.nextLine().trim();
        updatedPatient.setAddress(address.isEmpty() ? existingPatient.getAddress() : address);
        
        // Update Medical History
        System.out.print("Enter new Medical History (current: " + existingPatient.getMedicalHistory() + "): ");
        String medicalHistory = scanner.nextLine().trim();
        updatedPatient.setMedicalHistory(medicalHistory.isEmpty() ? existingPatient.getMedicalHistory() : medicalHistory);
        
        updatedPatient.setRegistrationDate(existingPatient.getRegistrationDate());
        updatedPatient.setIsInWaiting(existingPatient.isIsInWaiting());
        updatedPatient.setCurrentStatus(existingPatient.getCurrentStatus());
        
        boolean success = patientManagement.updatePatient(updatedPatient);
        
        if (success) {
            System.out.println("Patient information updated successfully!");
        } else {
            System.out.println("Failed to update patient information.");
        }
    }
    
    private void listPatientsMenu() {
        System.out.println("\n=== LIST ALL PATIENTS ===");
        
        Patient[] patients = patientManagement.getAllPatients();
        
        if (patients.length == 0) {
            System.out.println("No patients found.");
        } else {
            System.out.println("ID\tName\t\tAge\tGender\tContact\t\tAddress\t\tStatus");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Patient patient : patients) {
                System.out.printf("%-5d\t%-15s\t%-5d\t%-8s\t%-15s\t%-20s\t%s\n",
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getAddress(),
                    patient.getCurrentStatus());
            }
        }
    }
    
    private void searchByNameMenu() {
        System.out.println("\n=== SEARCH PATIENTS BY NAME ===");
        String name = InputValidator.getValidString(scanner, "Enter name to search: ");
        
        Patient[] results = patientManagement.searchPatientsByName(name);
        
        if (results.length == 0) {
            System.out.println("No patients found with this name.");
        } else {
            System.out.println("Search Results:");
            System.out.println("ID\tName\t\tAge\tGender\tContact\t\tAddress\t\tStatus");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Patient patient : results) {
                System.out.printf("%-5d\t%-15s\t%-5d\t%-8s\t%-15s\t%-20s\t%s\n",
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getAddress(),
                    patient.getCurrentStatus());
            }
        }
    }
    
    private void searchByPhoneMenu() {
        System.out.println("\n=== SEARCH PATIENTS BY PHONE ===");
        String phone = InputValidator.getValidString(scanner, "Enter phone number to search: ");
        
        Patient[] results = patientManagement.searchPatientsByPhone(phone);
        
        if (results.length == 0) {
            System.out.println("No patients found with this phone number.");
        } else {
            System.out.println("Search Results:");
            System.out.println("ID\tName\t\tAge\tGender\tContact\t\tAddress\t\tStatus");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Patient patient : results) {
                System.out.printf("%-5d\t%-15s\t%-5d\t%-8s\t%-15s\t%-20s\t%s\n",
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getAddress(),
                    patient.getCurrentStatus());
            }
        }
    }
    
    private void viewByAgeGroupMenu() {
        System.out.println("\n=== VIEW PATIENTS BY AGE GROUP ===");
        int minAge = InputValidator.getValidInt(scanner, 0, 120, "Enter minimum age: ");
        int maxAge = InputValidator.getValidInt(scanner, minAge, 120, "Enter maximum age: ");
        
        Patient[] results = patientManagement.getPatientsByAgeGroup(minAge, maxAge);
        
        if (results.length == 0) {
            System.out.println("No patients found in this age group.");
        } else {
            System.out.println("Patients aged " + minAge + "-" + maxAge + ":");
            System.out.println("ID\tName\t\tAge\tGender\tContact\t\tAddress\t\tStatus");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Patient patient : results) {
                System.out.printf("%-5d\t%-15s\t%-5d\t%-8s\t%-15s\t%-20s\t%s\n",
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getAddress(),
                    patient.getCurrentStatus());
            }
        }
    }
    
    private void viewByGenderMenu() {
        System.out.println("\n=== VIEW PATIENTS BY GENDER ===");
        String gender = InputValidator.getValidGender(scanner, "Enter gender");
        
        Patient[] results = patientManagement.getPatientsByGender(gender);
        
        if (results.length == 0) {
            System.out.println("No patients found with this gender.");
        } else {
            System.out.println("Patients with gender: " + gender);
            System.out.println("ID\tName\t\tAge\tGender\tContact\t\tAddress\t\tStatus");
            System.out.println(StringUtility.repeatString("-", 100));
            for (Patient patient : results) {
                System.out.printf("%-5d\t%-15s\t%-5d\t%-8s\t%-15s\t%-20s\t%s\n",
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getAddress(),
                    patient.getCurrentStatus());
            }
        }
    }
    
    private void updateContactMenu() {
        System.out.println("\n=== UPDATE PATIENT CONTACT INFO ===");
        
        //show available patient IDs
        showAvailablePatientIds();
        
        int patientId = InputValidator.getValidId(scanner, "Enter Patient ID: ");
        
        String phone = InputValidator.getValidPhoneNumber(scanner, "Enter new Contact Number: ");
        String address = InputValidator.getValidString(scanner, "Enter new Address: ");
        
        boolean success = patientManagement.updatePatientContact(patientId, phone, address);
        
        if (success) {
            System.out.println("Patient contact information updated successfully!");
        } else {
            System.out.println("Failed to update patient contact information. Patient not found.");
        }
    }
    
    private void updateMedicalInfoMenu() {
        System.out.println("\n=== UPDATE PATIENT MEDICAL INFO ===");
        
        //show available patient IDs
        showAvailablePatientIds();
        
        int patientId = InputValidator.getValidId(scanner, "Enter Patient ID: ");
        
        String allegy = InputValidator.getValidStringAllowEmpty(scanner, "Enter new Allergies (or press Enter if none): ");
        
        boolean success = patientManagement.updatePatientMedicalInfo(patientId, allegy);
        
        if (success) {
            System.out.println("Patient medical information updated successfully!");
        } else {
            System.out.println("Failed to update patient medical information. Patient not found.");
        }
    }
    
    private void generateReportMenu() {
        System.out.println("\n=== GENERATE PATIENT REPORT ===");
        
        String report = patientManagement.generatePatientReport();
        System.out.println(report);
    }
    
    private Patient findPatientById(int patientId) {
        Patient[] patients = patientManagement.getAllPatients();
        for (Patient patient : patients) {
            if (patient.getId() == patientId) {
                return patient;
            }
        }
        return null;
    }
    
    // Helper method to display available patient IDs
    private void showAvailablePatientIds() {
        Patient[] patients = patientManagement.getAllPatients();
        
        if (patients.length == 0) {
            System.out.println("No patients available in the system.");
            return;
        }
        
        System.out.println("Available Patient IDs:");
        System.out.println("ID\tName\t\t\tAge\tGender\tContact");
        System.out.println(StringUtility.repeatString("-", 60));
        
        for (Patient patient : patients) {
            System.out.printf("%-5d\t%-20s\t%-5d\t%-8s\t%s\n",
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getContactNumber());
        }
        System.out.println();
    }
}
