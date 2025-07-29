package boundary;

import control.DoctorManagement;
import entity.Doctor;
import utility.StringUtility;
import utility.InputValidator;
import java.util.Scanner;

public class DoctorManagementUI {
    private Scanner scanner;
    private DoctorManagement doctorManagement;
    
    public DoctorManagementUI(DoctorManagement doctorManagement) {
        this.scanner = new Scanner(System.in);
        this.doctorManagement = doctorManagement;
    }
    
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== DOCTOR MANAGEMENT SYSTEM ===");
            System.out.println("1  . Add New Doctor");
            System.out.println("2  . Remove Doctor");
            System.out.println("3  . Update Doctor Information");
            System.out.println("4  . List All Doctors");
            System.out.println("5  . Search Doctors by Name");
            System.out.println("6  . Search Doctors by Specialization");
            System.out.println("7  . View Available Doctors");
            System.out.println("8  . View Doctors on Leave");
            System.out.println("9  . Update Doctor Availability");
            System.out.println("10 . Update Doctor Leave Status");
            System.out.println("11 . Update Doctor Duty Schedule");
            System.out.println("12 . Generate Doctor Report");
            System.out.println("0  . Return to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = InputValidator.getValidInt(scanner, 0, 12, "");
            
            switch (choice) {
                case 1:
                    addDoctorMenu();
                    break;
                case 2:
                    removeDoctorMenu();
                    break;
                case 3:
                    updateDoctorMenu();
                    break;
                case 4:
                    listDoctorsMenu();
                    break;
                case 5:
                    searchByNameMenu();
                    break;
                case 6:
                    searchBySpecializationMenu();
                    break;
                case 7:
                    viewAvailableDoctorsMenu();
                    break;
                case 8:
                    viewDoctorsOnLeaveMenu();
                    break;
                case 9:
                    updateAvailabilityMenu();
                    break;
                case 10:
                    updateLeaveStatusMenu();
                    break;
                case 11:
                    updateDutyScheduleMenu();
                    break;
                case 12:
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
    
    private void addDoctorMenu() {
        System.out.println("\n=== ADD NEW DOCTOR ===");
        
        Doctor doctor = new Doctor();
        
        //get doctor ID with retry for duplicates
        String doctorId;
        do {
            doctorId = InputValidator.getValidString(scanner, "Enter Doctor ID: ");
            doctor.setDoctorId(doctorId);
            
            //check if doctor ID already exists
            if (doctorManagement.doctorExists(doctorId)) {
                System.out.println("Doctor ID already exists. Please choose a different ID.");
                System.out.print("Press Enter to try again or '0' to return to menu: ");
                String choice = scanner.nextLine().trim();
                if (choice.equals("0")) {
                    return; //return to main menu only if user chooses to
                }
            } else {
                break; //exit loop if ID is unique
            }
        } while (true);
        
        //get doctor name
        String name = InputValidator.getValidString(scanner, "Enter Doctor Name: ");
        doctor.setName(name);
        
        //get specialization
        String specialization = InputValidator.getValidString(scanner, "Enter Specialization: ");
        doctor.setSpecialization(specialization);
        
        //get contact number
        String contactNumber = InputValidator.getValidPhoneNumber(scanner, "Enter Contact Number: ");
        doctor.setContactNumber(contactNumber);
        
        //get email
        String email = InputValidator.getValidEmail(scanner, "Enter Email: ");
        doctor.setEmail(email);
        
        //get availability
        boolean isAvailable = InputValidator.getValidBoolean(scanner, "Is Available?");
        doctor.setIsAvailable(isAvailable);
        
        //get duty schedule
        String dutySchedule = InputValidator.getValidString(scanner, "Enter Duty Schedule: ");
        doctor.setDutySchedule(dutySchedule);
        
        //get leave status
        boolean onLeave = InputValidator.getValidBoolean(scanner, "On Leave?");
        doctor.setOnLeave(onLeave);
        
        if (!onLeave) {
            doctor.setLeaveDateStart("");
            doctor.setLeaveDateEnd("");
        } else {
            String leaveStart = InputValidator.getValidDate(scanner, "Enter Leave Start Date");
            doctor.setLeaveDateStart(leaveStart);
            String leaveEnd = InputValidator.getValidDate(scanner, "Enter Leave End Date");
            doctor.setLeaveDateEnd(leaveEnd);
        }
        
        boolean success = doctorManagement.addDoctor(doctor);
        
        if (success) {
            System.out.println("Doctor added successfully!");
        } else {
            System.out.println("Failed to add doctor. Please try again.");
        }
    }
    
    private void removeDoctorMenu() {
        System.out.println("\n=== REMOVE DOCTOR ===");
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        String doctorId = InputValidator.getValidString(scanner, "Enter Doctor ID: ");
        
        boolean success = doctorManagement.removeDoctor(doctorId);
        
        if (success) {
            System.out.println("Doctor removed successfully!");
        } else {
            System.out.println("Failed to remove doctor. Doctor not found.");
        }
    }
    
    private void updateDoctorMenu() {
        System.out.println("\n=== UPDATE DOCTOR INFORMATION ===");
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        String doctorId = InputValidator.getValidString(scanner, "Enter Doctor ID: ");
        
        Doctor existingDoctor = findDoctorById(doctorId);
        if (existingDoctor == null) {
            System.out.println("Doctor not found.");
            return;
        }
        
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setDoctorId(doctorId);
        
        //update name
        System.out.print("Enter new Doctor Name (current: " + existingDoctor.getName() + "): ");
        String name = scanner.nextLine().trim();
        updatedDoctor.setName(name.isEmpty() ? existingDoctor.getName() : name);
        
        //update specialization
        System.out.print("Enter new Specialization (current: " + existingDoctor.getSpecialization() + "): ");
        String specialization = scanner.nextLine().trim();
        updatedDoctor.setSpecialization(specialization.isEmpty() ? existingDoctor.getSpecialization() : specialization);
        
        //update contact number
        System.out.print("Enter new Contact Number (current: " + existingDoctor.getContactNumber() + "): ");
        String contactNumber = scanner.nextLine().trim();
        if (!contactNumber.isEmpty()) {
            if (contactNumber.matches("^(01[0-9]|03|04|05|06|07|08|09)-?[0-9]{7,8}$")) {
                updatedDoctor.setContactNumber(contactNumber);
            } else {
                System.out.println("Invalid phone number format. Using current contact number.");
                updatedDoctor.setContactNumber(existingDoctor.getContactNumber());
            }
        } else {
            updatedDoctor.setContactNumber(existingDoctor.getContactNumber());
        }
        
        //update email
        System.out.print("Enter new Email (current: " + existingDoctor.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                updatedDoctor.setEmail(email);
            } else {
                System.out.println("Invalid email format. Using current email.");
                updatedDoctor.setEmail(existingDoctor.getEmail());
            }
        } else {
            updatedDoctor.setEmail(existingDoctor.getEmail());
        }
        
        //update availability
        System.out.print("Enter new Availability (current: " + existingDoctor.isIsAvailable() + "): ");
        String availStr = scanner.nextLine().trim();
        if (!availStr.isEmpty()) {
            if (availStr.equalsIgnoreCase("true") || availStr.equalsIgnoreCase("false")) {
                updatedDoctor.setIsAvailable(Boolean.parseBoolean(availStr));
            } else {
                System.out.println("Invalid availability. Using current availability.");
                updatedDoctor.setIsAvailable(existingDoctor.isIsAvailable());
            }
        } else {
            updatedDoctor.setIsAvailable(existingDoctor.isIsAvailable());
        }
        
        //update duty schedule
        System.out.print("Enter new Duty Schedule (current: " + existingDoctor.getDutySchedule() + "): ");
        String dutySchedule = scanner.nextLine().trim();
        updatedDoctor.setDutySchedule(dutySchedule.isEmpty() ? existingDoctor.getDutySchedule() : dutySchedule);
        
        // Update Leave Status
        System.out.print("Enter new Leave Status (current: " + existingDoctor.isOnLeave() + "): ");
        String leaveStr = scanner.nextLine().trim();
        if (!leaveStr.isEmpty()) {
            if (leaveStr.equalsIgnoreCase("true") || leaveStr.equalsIgnoreCase("false")) {
                updatedDoctor.setOnLeave(Boolean.parseBoolean(leaveStr));
            } else {
                System.out.println("Invalid leave status. Using current status.");
                updatedDoctor.setOnLeave(existingDoctor.isOnLeave());
            }
        } else {
            updatedDoctor.setOnLeave(existingDoctor.isOnLeave());
        }
        
        updatedDoctor.setLeaveDateStart(existingDoctor.getLeaveDateStart());
        updatedDoctor.setLeaveDateEnd(existingDoctor.getLeaveDateEnd());
        
        boolean success = doctorManagement.updateDoctor(updatedDoctor);
        
        if (success) {
            System.out.println("Doctor information updated successfully!");
        } else {
            System.out.println("Failed to update doctor information.");
        }
    }
    
    private void listDoctorsMenu() {
        System.out.println("\n=== LIST ALL DOCTORS ===");
        
        Doctor[] doctors = doctorManagement.getAllDoctors();
        
        if (doctors.length == 0) {
            System.out.println("No doctors found.");
        } else {
            System.out.println("ID\tName\t\t\tSpecialization\t\tContact\t\tEmail\t\t\tAvailable\tOn Leave");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Doctor doctor : doctors) {
                System.out.printf("%-10s\t%-20s\t%-20s\t%-15s\t%-20s\t%-10s\t%s\n",
                    doctor.getDoctorId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getContactNumber(),
                    doctor.getEmail(),
                    doctor.isIsAvailable() ? "Yes" : "No",
                    doctor.isOnLeave() ? "Yes" : "No");
            }
        }
    }
    
    private void searchByNameMenu() {
        System.out.println("\n=== SEARCH DOCTORS BY NAME ===");
        String name = InputValidator.getValidString(scanner, "Enter name to search: ");
        
        Doctor[] results = doctorManagement.searchDoctorsByName(name);
        
        if (results.length == 0) {
            System.out.println("No doctors found with this name.");
        } else {
            System.out.println("Search Results:");
            System.out.println("ID\tName\t\t\tSpecialization\t\tContact\t\tEmail\t\t\tAvailable\tOn Leave");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Doctor doctor : results) {
                System.out.printf("%-10s\t%-20s\t%-20s\t%-15s\t%-20s\t%-10s\t%s\n",
                    doctor.getDoctorId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getContactNumber(),
                    doctor.getEmail(),
                    doctor.isIsAvailable() ? "Yes" : "No",
                    doctor.isOnLeave() ? "Yes" : "No");
            }
        }
    }
    
    private void searchBySpecializationMenu() {
        System.out.println("\n=== SEARCH DOCTORS BY SPECIALIZATION ===");
        String specialization = InputValidator.getValidString(scanner, "Enter specialization to search: ");
        
        Doctor[] results = doctorManagement.searchDoctorsBySpecialization(specialization);
        
        if (results.length == 0) {
            System.out.println("No doctors found with this specialization.");
        } else {
            System.out.println("Search Results:");
            System.out.println("ID\tName\t\t\tSpecialization\t\tContact\t\tEmail\t\t\tAvailable\tOn Leave");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Doctor doctor : results) {
                System.out.printf("%-10s\t%-20s\t%-20s\t%-15s\t%-20s\t%-10s\t%s\n",
                    doctor.getDoctorId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getContactNumber(),
                    doctor.getEmail(),
                    doctor.isIsAvailable() ? "Yes" : "No",
                    doctor.isOnLeave() ? "Yes" : "No");
            }
        }
    }
    
    private void viewAvailableDoctorsMenu() {
        System.out.println("\n=== VIEW AVAILABLE DOCTORS ===");
        
        Doctor[] availableDoctors = doctorManagement.getAvailableDoctors();
        
        if (availableDoctors.length == 0) {
            System.out.println("No available doctors found.");
        } else {
            System.out.println("Available Doctors:");
            System.out.println("ID\tName\t\t\tSpecialization\t\tContact\t\tEmail\t\t\tDuty Schedule");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Doctor doctor : availableDoctors) {
                System.out.printf("%-10s\t%-20s\t%-20s\t%-15s\t%-20s\t%-20s\n",
                    doctor.getDoctorId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getContactNumber(),
                    doctor.getEmail(),
                    doctor.getDutySchedule());
            }
        }
    }
    
    private void viewDoctorsOnLeaveMenu() {
        System.out.println("\n=== VIEW DOCTORS ON LEAVE ===");
        
        Doctor[] doctorsOnLeave = doctorManagement.getDoctorsOnLeave();
        
        if (doctorsOnLeave.length == 0) {
            System.out.println("No doctors on leave found.");
        } else {
            System.out.println("Doctors on Leave:");
            System.out.println("ID\tName\t\t\tSpecialization\t\tContact\t\tEmail\t\t\tLeave Period");
            System.out.println(StringUtility.repeatString("-", 120));
            for (Doctor doctor : doctorsOnLeave) {
                System.out.printf("%-10s\t%-20s\t%-20s\t%-15s\t%-20s\t%s to %s\n",
                    doctor.getDoctorId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getContactNumber(),
                    doctor.getEmail(),
                    doctor.getLeaveDateStart(),
                    doctor.getLeaveDateEnd());
            }
        }
    }
    
    private void updateAvailabilityMenu() {
        System.out.println("\n=== UPDATE DOCTOR AVAILABILITY ===");
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        String doctorId = InputValidator.getValidString(scanner, "Enter Doctor ID: ");
        boolean isAvailable = InputValidator.getValidBoolean(scanner, "Is Available?");
        
        boolean success = doctorManagement.updateDoctorAvailability(doctorId, isAvailable);
        
        if (success) {
            System.out.println("Doctor availability updated successfully!");
        } else {
            System.out.println("Failed to update doctor availability. Doctor not found.");
        }
    }
    
    private void updateLeaveStatusMenu() {
        System.out.println("\n=== UPDATE DOCTOR LEAVE STATUS ===");
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        String doctorId = InputValidator.getValidString(scanner, "Enter Doctor ID: ");
        boolean onLeave = InputValidator.getValidBoolean(scanner, "On Leave?");
        
        String leaveStart = "";
        String leaveEnd = "";
        
        if (onLeave) {
            leaveStart = InputValidator.getValidDate(scanner, "Enter Leave Start Date");
            leaveEnd = InputValidator.getValidDate(scanner, "Enter Leave End Date");
        }
        
        boolean success = doctorManagement.updateDoctorLeave(doctorId, onLeave, leaveStart, leaveEnd);
        
        if (success) {
            System.out.println("Doctor leave status updated successfully!");
        } else {
            System.out.println("Failed to update doctor leave status. Doctor not found.");
        }
    }
    
    private void updateDutyScheduleMenu() {
        System.out.println("\n=== UPDATE DOCTOR DUTY SCHEDULE ===");
        
        //show available doctor IDs
        showAvailableDoctorIds();
        
        String doctorId = InputValidator.getValidString(scanner, "Enter Doctor ID: ");
        String dutySchedule = InputValidator.getValidString(scanner, "Enter new Duty Schedule: ");
        
        boolean success = doctorManagement.updateDutySchedule(doctorId, dutySchedule);
        
        if (success) {
            System.out.println("Doctor duty schedule updated successfully!");
        } else {
            System.out.println("Failed to update doctor duty schedule. Doctor not found.");
        }
    }
    
    private void generateReportMenu() {
        System.out.println("\n=== GENERATE DOCTOR REPORT ===");
        
        String report = doctorManagement.generateDoctorReport();
        System.out.println(report);
    }
    
    private Doctor findDoctorById(String doctorId) {
        Doctor[] doctors = doctorManagement.getAllDoctors();
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }
    
    //helper method to display available doctor IDs
    private void showAvailableDoctorIds() {
        Doctor[] doctors = doctorManagement.getAllDoctors();
        
        if (doctors.length == 0) {
            System.out.println("No doctors available in the system.");
            return;
        }
        
        System.out.println("Available Doctor IDs:");
        System.out.println("ID\t\tName\t\t\tSpecialization\t\tContact\t\tEmail\t\t\tAvailable");
        System.out.println(StringUtility.repeatString("-", 100));
        
        for (Doctor doctor : doctors) {
            System.out.printf("%-10s\t%-20s\t%-20s\t%-15s\t%-20s\t%s\n",
                doctor.getDoctorId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getContactNumber(),
                doctor.getEmail(),
                doctor.isIsAvailable() ? "Yes" : "No");
        }
        System.out.println();
    }
} 