package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Doctor;
import dao.DataInitializer;
import java.util.Scanner;

public class DoctorManagement {
    private SetAndQueueInterface<Doctor> doctorList = new SetAndQueue<>();
    private SetAndQueueInterface<Doctor> onDutyDoctorList = new SetAndQueue<>();
    private Scanner scanner;
    
    public DoctorManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    private void loadSampleData() {
        Doctor[] sampleDoctors = DataInitializer.initializeSampleDoctors();
        for (Doctor doctor : sampleDoctors) {
            doctorList.add(doctor);
            if (doctor.isIsAvailable() && !doctor.isOnLeave()) {
                onDutyDoctorList.add(doctor);
            }
        }
    }
    
    public void displayAllDoctors() {
        System.out.println("\n" + repeatString("-", 80));
        System.out.printf("%-10s %-20s %-20s %-15s %-10s\n", "ID", "Name", "Specialization", "Contact", "Available");
        System.out.println(repeatString("-", 80));
        
        Object[] doctorsArray = doctorList.toArray();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            System.out.printf("%-10s %-20s %-20s %-15s %-10s\n", 
                doctor.getDoctorId(), 
                doctor.getName(), 
                doctor.getSpecialization(), 
                doctor.getContactNumber(),
                doctor.isIsAvailable() ? "Yes" : "No");
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayDoctorsOnDuty() {
        System.out.println("\n" + repeatString("-", 80));
        System.out.printf("%-10s %-20s %-20s %-15s\n", "ID", "Name", "Specialization", "Contact");
        System.out.println(repeatString("-", 80));
        
        Object[] doctorsArray = onDutyDoctorList.toArray();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            System.out.printf("%-10s %-20s %-20s %-15s\n", 
                doctor.getDoctorId(), 
                doctor.getName(), 
                doctor.getSpecialization(), 
                doctor.getContactNumber());
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void addDoctorToDuty() {
        System.out.print("Enter Doctor ID to add to duty: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            if (doctor.isIsAvailable() && !doctor.isOnLeave()) {
                if (!onDutyDoctorList.contains(doctor)) {
                    onDutyDoctorList.add(doctor);
                    System.out.println("Doctor " + doctor.getName() + " added to duty successfully!");
                } else {
                    System.out.println("Doctor is already on duty!");
                }
            } else {
                System.out.println("Doctor is not available or on leave!");
            }
        } else {
            System.out.println("Doctor not found!");
        }
    }
    
    public void removeDoctorFromDuty() {
        System.out.print("Enter Doctor ID to remove from duty: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null && onDutyDoctorList.contains(doctor)) {
            onDutyDoctorList.remove(doctor);
            System.out.println("Doctor " + doctor.getName() + " removed from duty successfully!");
        } else {
            System.out.println("Doctor not found or not on duty!");
        }
    }
    
    public void displayAllDoctorsSorted() {
        System.out.println("\n" + repeatString("-", 80));
        System.out.println("ALL DOCTORS (SORTED BY ID)");
        System.out.println(repeatString("-", 80));
        System.out.printf("%-10s %-20s %-20s %-15s %-10s\n", "ID", "Name", "Specialization", "Contact", "Available");
        System.out.println(repeatString("-", 80));
        
        Object[] doctorsArray = doctorList.toArray();
        Doctor[] doctorArray = new Doctor[doctorsArray.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            doctorArray[i] = (Doctor) doctorsArray[i];
        }
        
        utility.BubbleSort.sort(doctorArray);
        
        for (Doctor doctor : doctorArray) {
            System.out.printf("%-10s %-20s %-20s %-15s %-10s\n", 
                doctor.getDoctorId(), doctor.getName(), doctor.getSpecialization(), 
                doctor.getContactNumber(), doctor.isIsAvailable() ? "Yes" : "No");
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchDoctorById() {
        System.out.print("Enter doctor ID to search: ");
        String doctorId = scanner.nextLine();
        
        Doctor foundDoctor = findDoctorById(doctorId);
        if (foundDoctor != null) {
            displayDoctorDetails(foundDoctor);
        } else {
            System.out.println("Doctor not found!");
        }
    }
    
    public void searchDoctorBySpecialization() {
        System.out.print("Enter specialization to search: ");
        String specialization = scanner.nextLine();
        
        Object[] doctorsArray = doctorList.toArray();
        System.out.println("\nDoctors with specialization '" + specialization + "':");
        System.out.println(repeatString("-", 60));
        System.out.printf("%-10s %-20s %-15s %-10s\n", "ID", "Name", "Contact", "Available");
        System.out.println(repeatString("-", 60));
        
        boolean found = false;
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (doctor.getSpecialization().toLowerCase().contains(specialization.toLowerCase())) {
                System.out.printf("%-10s %-20s %-15s %-10s\n", 
                    doctor.getDoctorId(), doctor.getName(), 
                    doctor.getContactNumber(), doctor.isIsAvailable() ? "Yes" : "No");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No doctors found with this specialization.");
        }
        System.out.println(repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayDoctorDetails(Doctor doctor) {
        System.out.println("\n" + repeatString("-", 60));
        System.out.println("DOCTOR DETAILS");
        System.out.println(repeatString("-", 60));
        System.out.println("ID: " + doctor.getDoctorId());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Specialization: " + doctor.getSpecialization());
        System.out.println("Contact: " + doctor.getContactNumber());
        System.out.println("Email: " + doctor.getEmail());
        System.out.println("Available: " + (doctor.isIsAvailable() ? "Yes" : "No"));
        System.out.println("Duty Schedule: " + doctor.getDutySchedule());
        System.out.println("On Leave: " + (doctor.isOnLeave() ? "Yes" : "No"));
        if (doctor.isOnLeave()) {
            System.out.println("Leave Period: " + doctor.getLeaveDateStart() + " to " + doctor.getLeaveDateEnd());
        }
        System.out.println(repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updateDoctorSchedule() {
        System.out.print("Enter doctor ID to update schedule: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("Current doctor information:");
            displayDoctorDetails(doctor);
            
            System.out.println("\nEnter new information (press Enter to keep current value):");
            System.out.print("Duty Schedule [" + doctor.getDutySchedule() + "]: ");
            String schedule = scanner.nextLine();
            if (!schedule.isEmpty()) doctor.setDutySchedule(schedule);
            
            System.out.print("Available (y/n) [" + (doctor.isIsAvailable() ? "y" : "n") + "]: ");
            String available = scanner.nextLine();
            if (!available.isEmpty()) doctor.setIsAvailable(available.equalsIgnoreCase("y"));
            
            System.out.print("On Leave (y/n) [" + (doctor.isOnLeave() ? "y" : "n") + "]: ");
            String onLeave = scanner.nextLine();
            if (!onLeave.isEmpty()) {
                boolean leave = onLeave.equalsIgnoreCase("y");
                doctor.setOnLeave(leave);
                if (leave) {
                    System.out.print("Leave Start Date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    if (!startDate.isEmpty()) doctor.setLeaveDateStart(startDate);
                    
                    System.out.print("Leave End Date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    if (!endDate.isEmpty()) doctor.setLeaveDateEnd(endDate);
                }
            }
            
            System.out.println("Doctor schedule updated successfully!");
        } else {
            System.out.println("Doctor not found!");
        }
    }
    
    public void generateDoctorAvailabilityReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        DOCTOR AVAILABILITY REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] doctorsArray = doctorList.toArray();
        int totalDoctors = doctorsArray.length;
        int availableCount = 0, onLeaveCount = 0, onDutyCount = 0;
        
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (doctor.isIsAvailable()) availableCount++;
            if (doctor.isOnLeave()) onLeaveCount++;
        }
        
        onDutyCount = onDutyDoctorList.size();
        
        System.out.println("📊 Doctor Availability:");
        System.out.println("• Total Doctors: " + totalDoctors);
        System.out.println("• Available Doctors: " + availableCount);
        System.out.println("• Doctors on Leave: " + onLeaveCount);
        System.out.println("• Doctors on Duty: " + onDutyCount);
        System.out.println("• Unavailable Doctors: " + (totalDoctors - availableCount));
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateDoctorSpecializationReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        DOCTOR SPECIALIZATION REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] doctorsArray = doctorList.toArray();
        java.util.Map<String, Integer> specializationCount = new java.util.HashMap<>();
        
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            String specialization = doctor.getSpecialization();
            specializationCount.put(specialization, specializationCount.getOrDefault(specialization, 0) + 1);
        }
        
        System.out.println("📊 Specialization Distribution:");
        for (java.util.Map.Entry<String, Integer> entry : specializationCount.entrySet()) {
            System.out.println("• " + entry.getKey() + ": " + entry.getValue() + " doctors");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public Doctor findDoctorById(String doctorId) {
        Object[] doctorsArray = doctorList.toArray();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (doctor.getDoctorId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }
    
    public Doctor[] getDoctorsOnDuty() {
        Object[] doctorsArray = onDutyDoctorList.toArray();
        Doctor[] doctorArray = new Doctor[doctorsArray.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            doctorArray[i] = (Doctor) doctorsArray[i];
        }
        return doctorArray;
    }
    
    public int getTotalDoctorCount() {
        return doctorList.size();
    }
    
    public int getDoctorsOnDutyCount() {
        return onDutyDoctorList.size();
    }

    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
} 