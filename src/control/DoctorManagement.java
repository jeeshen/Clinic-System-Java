package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Doctor;
import entity.Consultation;
import dao.DataInitializer;
import java.util.Scanner;
import utility.StringUtility;
import utility.InputValidator;

public class DoctorManagement {
    private SetAndQueueInterface<Doctor> doctorList = new SetAndQueue<>();
    private SetAndQueueInterface<Doctor> onDutyDoctorList = new SetAndQueue<>();
    private Scanner scanner;
    private ConsultationManagement consultationManagement;
    
    public DoctorManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    public void setConsultationManagement(ConsultationManagement consultationManagement) {
        this.consultationManagement = consultationManagement;
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
        Object[] doctorsArray = doctorList.toArray();
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available"};
        Object[][] rows = new Object[doctorsArray.length][headers.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            Doctor doctor = (Doctor) doctorsArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
            rows[i][4] = doctor.isIsAvailable() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "ALL DOCTORS",
            headers,
            rows
        ));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayDoctorsOnDuty() {
        Object[] doctorsArray = onDutyDoctorList.toArray();
        String[] headers = {"ID", "Name", "Specialization", "Contact"};
        Object[][] rows = new Object[doctorsArray.length][headers.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            Doctor doctor = (Doctor) doctorsArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "DOCTORS ON DUTY",
            headers,
            rows
        ));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void addDoctorToDuty() {
        Object[] doctorsArray = doctorList.toArray();
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available", "On Leave"};
        Object[][] rows = new Object[doctorsArray.length][headers.length];
        int rowIdx = 0;
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (!onDutyDoctorList.contains(doctor) && doctor.isIsAvailable() && !doctor.isOnLeave()) {
                rows[rowIdx][0] = doctor.getDoctorId();
                rows[rowIdx][1] = doctor.getName();
                rows[rowIdx][2] = doctor.getSpecialization();
                rows[rowIdx][3] = doctor.getContactNumber();
                rows[rowIdx][4] = doctor.isIsAvailable() ? "Yes" : "No";
                rows[rowIdx][5] = doctor.isOnLeave() ? "Yes" : "No";
                rowIdx++;
            }
        }
        if (rowIdx == 0) {
            System.out.println("No doctors available to add to duty (all doctors are already on duty).");
            return;
        }
        Object[][] filteredRows = new Object[rowIdx][headers.length];
        for (int i = 0; i < rowIdx; i++) {
            filteredRows[i] = rows[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "AVAILABLE DOCTORS FOR DUTY",
            headers,
            filteredRows
        ));
        System.out.print("\nEnter Doctor ID to add to duty: ");
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
        Object[] doctorsArray = doctorList.toArray();

        SetAndQueueInterface<Doctor> tempList = new SetAndQueue<>();
        for (Object obj : doctorsArray) {
            tempList.add((Doctor) obj);
        }
        tempList.sort();
        
        Object[] sortedDoctorsArray = tempList.toArray();
        Doctor[] doctorArray = new Doctor[sortedDoctorsArray.length];
        for (int i = 0; i < sortedDoctorsArray.length; i++) {
            doctorArray[i] = (Doctor) sortedDoctorsArray[i];
        }
        
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available"};
        Object[][] rows = new Object[doctorArray.length][headers.length];
        for (int i = 0; i < doctorArray.length; i++) {
            Doctor doctor = doctorArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
            rows[i][4] = doctor.isIsAvailable() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "ALL DOCTORS (SORTED BY ID)",
            headers,
            rows
        ));
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
        String[] headers = {"ID", "Name", "Contact", "Available"};
        SetAndQueueInterface<Object[]> rowList = new SetAndQueue<>();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (doctor.getSpecialization().toLowerCase().contains(specialization.toLowerCase())) {
                rowList.add(new Object[]{doctor.getDoctorId(), doctor.getName(), doctor.getContactNumber(), doctor.isIsAvailable() ? "Yes" : "No"});
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length];
        Object[] rowArray = rowList.toArray();
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "DOCTORS WITH SPECIALIZATION",
            headers,
            rows
        ));
        
        if (rowList.isEmpty()) {
            System.out.println("No doctors found with this specialization.");
        }
        System.out.println(StringUtility.repeatString("-", 70));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayDoctorDetails(Doctor doctor) {
        System.out.println("\n" + StringUtility.repeatString("-", 60));
        System.out.println("DOCTOR DETAILS");
        System.out.println(StringUtility.repeatString("-", 60));
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
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updateDoctorDetails() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        UPDATE DOCTOR DETAILS");
        System.out.println(StringUtility.repeatString("=", 60));

        Object[] doctorsArray = doctorList.toArray();

        SetAndQueueInterface<Doctor> tempList = new SetAndQueue<>();
        for (Object obj : doctorsArray) {
            tempList.add((Doctor) obj);
        }
        tempList.sort();
        
        Object[] sortedDoctorsArray = tempList.toArray();
        Doctor[] doctorArray = new Doctor[sortedDoctorsArray.length];
        for (int i = 0; i < sortedDoctorsArray.length; i++) {
            doctorArray[i] = (Doctor) sortedDoctorsArray[i];
        }
        
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available"};
        Object[][] rows = new Object[doctorArray.length][headers.length];
        for (int i = 0; i < doctorArray.length; i++) {
            Doctor doctor = doctorArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
            rows[i][4] = doctor.isIsAvailable() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "AVAILABLE DOCTORS",
            headers,
            rows
        ));
        System.out.println("Total Doctors: " + doctorArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter doctor ID to update: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("\nCurrent doctor information:");
            displayDoctorDetails(doctor);
            
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Enter new information (press Enter to keep current value):");
            System.out.println(StringUtility.repeatString("-", 60));
            
            //update name
            System.out.print("Name [" + doctor.getName() + "]: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) doctor.setName(name);
            
            //update specialization
            System.out.print("Specialization [" + doctor.getSpecialization() + "]: ");
            String specialization = scanner.nextLine();
            if (!specialization.isEmpty()) doctor.setSpecialization(specialization);
            
            //update contact number
            System.out.print("Contact Number [" + doctor.getContactNumber() + "]: ");
            String contact = scanner.nextLine();
            if (!contact.isEmpty()) doctor.setContactNumber(contact);
            
            System.out.println("\n✅ Doctor details updated successfully!");
            System.out.println("\nUpdated doctor information:");
            displayDoctorDetails(doctor);
        } else {
            System.out.println("❌ Doctor not found!");
        }
    }
    
    public void updateDoctorSchedule() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        UPDATE DOCTOR SCHEDULE");
        System.out.println(StringUtility.repeatString("=", 60));

        Object[] doctorsArray = doctorList.toArray();
        
        SetAndQueueInterface<Doctor> tempList = new SetAndQueue<>();
        for (Object obj : doctorsArray) {
            tempList.add((Doctor) obj);
        }
        tempList.sort();
        
        Object[] sortedDoctorsArray = tempList.toArray();
        Doctor[] doctorArray = new Doctor[sortedDoctorsArray.length];
        for (int i = 0; i < sortedDoctorsArray.length; i++) {
            doctorArray[i] = (Doctor) sortedDoctorsArray[i];
        }
        
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available"};
        Object[][] rows = new Object[doctorArray.length][headers.length];
        for (int i = 0; i < doctorArray.length; i++) {
            Doctor doctor = doctorArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
            rows[i][4] = doctor.isIsAvailable() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "AVAILABLE DOCTORS",
            headers,
            rows
        ));
        System.out.println("Total Doctors: " + doctorArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter doctor ID to update schedule: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("\nCurrent doctor information:");
            displayDoctorDetails(doctor);
            
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Enter new schedule information (press Enter to keep current value):");
            System.out.println(StringUtility.repeatString("-", 60));
            
            //update duty schedule
            System.out.print("Duty Schedule [" + doctor.getDutySchedule() + "]: ");
            String dutySchedule = scanner.nextLine();
            if (!dutySchedule.isEmpty()) doctor.setDutySchedule(dutySchedule);
            
            //update availability
            System.out.print("Available (yes/no) [" + (doctor.isIsAvailable() ? "yes" : "no") + "]: ");
            String available = scanner.nextLine();
            if (!available.isEmpty()) {
                doctor.setIsAvailable(available.toLowerCase().equals("yes"));
            }
            
            //update leave status
            System.out.print("On Leave (yes/no) [" + (doctor.isOnLeave() ? "yes" : "no") + "]: ");
            String onLeave = scanner.nextLine();
            if (!onLeave.isEmpty()) {
                doctor.setOnLeave(onLeave.toLowerCase().equals("yes"));
            }
            
            if (doctor.isOnLeave()) {
                System.out.print("Leave Start Date [" + doctor.getLeaveDateStart() + "]: ");
                String leaveStart = scanner.nextLine();
                if (!leaveStart.isEmpty()) doctor.setLeaveDateStart(leaveStart);
                
                System.out.print("Leave End Date [" + doctor.getLeaveDateEnd() + "]: ");
                String leaveEnd = scanner.nextLine();
                if (!leaveEnd.isEmpty()) doctor.setLeaveDateEnd(leaveEnd);
            }
            
            System.out.println("\n✅ Doctor schedule updated successfully!");
            System.out.println("\nUpdated doctor information:");
            displayDoctorDetails(doctor);
        } else {
            System.out.println("❌ Doctor not found!");
        }
    }
    
    public void removeDoctor() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        REMOVE DOCTOR");
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.println("📋 CURRENT DOCTOR LIST:");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.printf("%-8s %-40s %-15s %-15s %-10s\n", "ID", "Name", "Specialization", "Contact", "Available");
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] doctorsArray = doctorList.toArray();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            System.out.printf("%-8s %-40s %-15s %-15s %-10s\n", 
                doctor.getDoctorId(), 
                doctor.getName(), 
                doctor.getSpecialization(), 
                doctor.getContactNumber(), 
                doctor.isIsAvailable() ? "Yes" : "No");
        }
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Total Doctors: " + doctorsArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter doctor ID to remove: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("Doctor to be removed:");
            displayDoctorDetails(doctor);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this doctor? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removedFromList = doctorList.remove(doctor);

                boolean removedFromDuty = onDutyDoctorList.remove(doctor);
                
                if (removedFromList) {
                    System.out.println("✅ Doctor removed successfully!");
                    if (removedFromDuty) {
                        System.out.println("✅ Doctor also removed from duty list.");
                    }
                } else {
                    System.out.println("❌ Failed to remove doctor from system!");
                }
            } else {
                System.out.println("❌ Doctor removal cancelled.");
            }
        } else {
            System.out.println("❌ Doctor not found!");
        }
    }
    
    public void generateDoctorAvailabilityReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        DOCTOR AVAILABILITY REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 60));

        Object[] doctorsArray = doctorList.toArray();
        int totalDoctors = doctorsArray.length;
        int availableCount = 0, onLeaveCount = 0, onDutyCount = 0;

        String[] headers = {"ID", "Name", "Specialty", "Available", "On Leave", "On Duty"};
        Object[][] rows = new Object[doctorsArray.length][headers.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            Doctor d = (Doctor) doctorsArray[i];
            boolean available = d.isIsAvailable();
            boolean onLeave = d.isOnLeave();
            boolean onDuty = onDutyDoctorList.contains(d);
            if (available) availableCount++;
            if (onLeave) onLeaveCount++;
            if (onDuty) onDutyCount++;
            rows[i][0] = d.getDoctorId();
            rows[i][1] = d.getName();
            rows[i][2] = d.getSpecialization();
            rows[i][3] = available ? "Yes" : "No";
            rows[i][4] = onLeave ? "Yes" : "No";
            rows[i][5] = onDuty ? "Yes" : "No";
        }
        System.out.println("\nDOCTOR LIST:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Doctors: " + totalDoctors);
        System.out.println("• Available Doctors: " + availableCount);
        System.out.println("• Doctors on Leave: " + onLeaveCount);
        System.out.println("• Doctors on Duty: " + onDutyCount);
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateDoctorSpecializationReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        DOCTOR SPECIALIZATION REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 60));

        Object[] doctorsArray = doctorList.toArray();
        int totalDoctors = doctorsArray.length;
        SetAndQueueInterface<String> specializationSet = new SetAndQueue<>();

        int[] specializationCounts = new int[100];
        String[] specializationArray = new String[100];
        int specializationIndex = 0;
        
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            String specialization = doctor.getSpecialization();
            specializationSet.add(specialization);
            
            //count specializations
            boolean found = false;
            for (int i = 0; i < specializationIndex; i++) {
                if (specializationArray[i].equals(specialization)) {
                    specializationCounts[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                specializationArray[specializationIndex] = specialization;
                specializationCounts[specializationIndex] = 1;
                specializationIndex++;
            }
        }

        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available"};
        Object[][] rows = new Object[doctorsArray.length][headers.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            Doctor d = (Doctor) doctorsArray[i];
            rows[i][0] = d.getDoctorId();
            rows[i][1] = d.getName();
            rows[i][2] = d.getSpecialization();
            rows[i][3] = d.getContactNumber();
            rows[i][4] = d.isIsAvailable() ? "Yes" : "No";
        }
        System.out.println("\nDOCTOR LIST:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nSPECIALIZATION DISTRIBUTION:");
        int barWidth = 30;
        int maxSpecialization = 0;
        for (int i = 0; i < specializationIndex; i++) {
            if (specializationCounts[i] > maxSpecialization) {
                maxSpecialization = specializationCounts[i];
            }
        }
        
        for (int i = 0; i < specializationIndex; i++) {
            String specialization = specializationArray[i];
            int count = specializationCounts[i];
            double percentage = (double) count / totalDoctors * 100;
            System.out.printf("%-25s [%s] %d doctors (%.1f%%)\n", 
                specialization + ":", 
                StringUtility.greenBarChart(count, maxSpecialization, barWidth),
                count, 
                percentage);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Doctors: " + totalDoctors);
        System.out.println("• Total Specializations: " + specializationIndex);
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public Doctor findDoctorById(String doctorId) {
        Doctor dummy = new Doctor();
        dummy.setDoctorId(doctorId);
        return doctorList.search(dummy);
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
    
    public Object[] getAllDoctors() {
        return doctorList.toArray();
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
    
    public void generateDoctorPerformanceReport() {
        if (consultationManagement == null) {
            System.out.println("Consultation management not available!");
            return;
        }
        
        System.out.println("\n" + utility.StringUtility.repeatString("=", 80));
        System.out.println("        DOCTOR PERFORMANCE REPORT");
        System.out.println(utility.StringUtility.repeatString("=", 80));
        System.out.println("Generated at: " + utility.StringUtility.getCurrentDateTime());
        System.out.println(utility.StringUtility.repeatString("-", 80));

        Object[] consultationsArray = consultationManagement.getAllConsultations();

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
        int[] daysWorkedCounts = new int[doctorsArray.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            String doctorId = (String) doctorsArray[i];
            consultationCounts[i] = countConsultationsForDoctor(doctorId, consultationsArray);
            daysWorkedCounts[i] = countUniqueConsultationDaysForDoctor(doctorId, consultationsArray);
        }

        String[] headers = {"Doctor Name", "Doctor ID", "Consultations", "Days Worked"};
        Object[][] rows = new Object[doctorsArray.length][headers.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            String doctorId = (String) doctorsArray[i];
            rows[i][0] = getDoctorName(doctorId);
            rows[i][1] = doctorId;
            rows[i][2] = consultationCounts[i];
            rows[i][3] = daysWorkedCounts[i];
        }
        System.out.println("\nDOCTOR PERFORMANCE:");
        System.out.print(utility.StringUtility.formatTableWithDividers(headers, rows));
        
        //calculate max days worked for bar chart scaling
        int maxDaysWorked = 0;
        for (int i = 0; i < daysWorkedCounts.length; i++) {
            if (daysWorkedCounts[i] > maxDaysWorked) {
                maxDaysWorked = daysWorkedCounts[i];
            }
        }
        
        System.out.println("\nDAYS WORKED PER DOCTOR:");
        int barWidth = 30;
        for (int i = 0; i < doctorsArray.length; i++) {
            String doctorId = (String) doctorsArray[i];
            int daysWorked = daysWorkedCounts[i];
            System.out.printf("%-35s [%s] %d days\n", getDoctorName(doctorId), utility.StringUtility.greenBarChart(daysWorked, maxDaysWorked, barWidth), daysWorked);
        }
        System.out.println("\nSUMMARY:");
        System.out.println("• Total Consultations: " + consultationsArray.length);
        System.out.println("• Total Doctors: " + doctorsArray.length);
        System.out.println(utility.StringUtility.repeatString("=", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private int countUniqueConsultationDaysForDoctor(String doctorId, Object[] consultationsArray) {
        java.util.HashSet<String> uniqueDates = new java.util.HashSet<>();
        for (Object obj : consultationsArray) {
            Consultation consultation = (Consultation) obj;
            if (consultation.getDoctorId().equals(doctorId)) {
                uniqueDates.add(consultation.getConsultationDate());
            }
        }
        return uniqueDates.size();
    }
    
    private String getDoctorName(String doctorId) {
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            return doctor.getName();
        }
        return "Unknown Doctor";
    }
} 