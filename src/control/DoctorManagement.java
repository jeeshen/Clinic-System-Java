package control;

import adt.SetAndQueueInterface;
import adt.SetQueueArray;
import entity.Doctor;
import entity.Consultation;
import entity.Treatment;
import entity.Prescription;
import dao.DataInitializer;
import java.util.Scanner;
import utility.StringUtility;
import utility.InputValidator;

//leow wei lun
public class DoctorManagement {
    private SetAndQueueInterface<Doctor> doctorList = new SetQueueArray<>();
    private SetAndQueueInterface<Doctor> onDutyDoctorList = new SetQueueArray<>();
    private Scanner scanner;
    private int doctorIdCounter = 21;
    private ConsultationManagement consultationManagement;
    
    public DoctorManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    public void setConsultationManagement(ConsultationManagement consultationManagement) {
        this.consultationManagement = consultationManagement;
    }
    
    private void loadSampleData() {
        SetAndQueueInterface<Doctor> sampleDoctors = DataInitializer.initializeSampleDoctors();
        Object[] sampleDoctorsArray = sampleDoctors.toArray();
        for (Object obj : sampleDoctorsArray) {
            Doctor doctor = (Doctor) obj;
            doctorList.add(doctor); //adt method
            if (doctor.isIsAvailable() && !doctor.isOnLeave()) {
                onDutyDoctorList.add(doctor); //adt method
            }
        }

        if (sampleDoctors.size() > 0) {
            int maxId = 0;
            for (Object obj : sampleDoctorsArray) {
                Doctor doctor = (Doctor) obj;
                String doctorId = doctor.getDoctorId();
                if (doctorId.startsWith("DOC")) {
                    try {
                        int idNumber = Integer.parseInt(doctorId.substring(3));
                        if (idNumber > maxId) {
                            maxId = idNumber;
                        }
                    } catch (NumberFormatException e) {
                        //skip invalid IDs
                    }
                }
            }
            doctorIdCounter = maxId + 1;
        }
    }
    
    public void displayDoctorsOnDuty() {
        Object[] doctorsArray = onDutyDoctorList.toArray(); //adt method
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
        Object[] doctorsArray = doctorList.toArray(); //adt method
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available", "On Leave"};
        Object[][] rows = new Object[doctorsArray.length][headers.length];
        int rowIdx = 0;
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (!onDutyDoctorList.contains(doctor) && doctor.isIsAvailable() && !doctor.isOnLeave()) { //adt method
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
        System.out.print("\nEnter Doctor ID to add to duty (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            if (doctor.isIsAvailable() && !doctor.isOnLeave()) {
                if (!onDutyDoctorList.contains(doctor)) { //adt method
                    onDutyDoctorList.add(doctor); //adt method
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
        System.out.print("Enter Doctor ID to remove from duty (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null && onDutyDoctorList.contains(doctor)) { //adt method
            onDutyDoctorList.remove(doctor); //adt method
            
            //sort the remaining doctors on duty to maintain proper order
            if (onDutyDoctorList.size() > 1) {
                Object[] remainingDoctors = onDutyDoctorList.toArray();
                SetAndQueueInterface<Doctor> tempList = new SetQueueArray<>();
                for (Object obj : remainingDoctors) {
                    tempList.add((Doctor) obj); //adt method
                }
                tempList.sort(); //adt method
                
                //clear and re-add sorted doctors
                onDutyDoctorList.clearSet(); //adt method
                Object[] sortedDoctors = tempList.toArray();
                for (Object obj : sortedDoctors) {
                    onDutyDoctorList.add((Doctor) obj); //adt method
                }
            }
            
            System.out.println("Doctor " + doctor.getName() + " removed from duty successfully!");
            System.out.println("Remaining doctors on duty have been sorted by ID.");
        } else {
            System.out.println("Doctor not found or not on duty!");
        }
    }
    
    public void displayAllDoctorsSorted() {
        Object[] doctorsArray = doctorList.toArray(); //adt method

        SetAndQueueInterface<Doctor> tempList = new SetQueueArray<>();
        for (Object obj : doctorsArray) {
            tempList.add((Doctor) obj); //adt method
        }
        tempList.sort(); //adt method

        Object[] sortedDoctorsArray = tempList.toArray(); //adt method
        Doctor[] doctorArray = new Doctor[sortedDoctorsArray.length];
        for (int i = 0; i < sortedDoctorsArray.length; i++) {
            doctorArray[i] = (Doctor) sortedDoctorsArray[i];
        }
        
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Email", "Duty Schedule", "On Leave", "Available"};
        Object[][] rows = new Object[doctorArray.length][headers.length];
        for (int i = 0; i < doctorArray.length; i++) {
            Doctor doctor = doctorArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
            rows[i][4] = doctor.getEmail();
            rows[i][5] = doctor.getDutySchedule();
            rows[i][6] = doctor.isOnLeave() ? "Yes" : "No";
            rows[i][7] = doctor.isIsAvailable() ? "Yes" : "No";
        }
        
        System.out.println("\n" + StringUtility.repeatString("=", 160));
        System.out.println("                                        ALL DOCTORS (SORTED BY ID)");
        System.out.println(StringUtility.repeatString("=", 160));
        System.out.printf("%-8s %-25s %-20s %-15s %-30s %-25s %-10s %-10s\n", 
            "ID", "Name", "Specialization", "Contact", "Email", "Duty Schedule", "On Leave", "Available");
        System.out.println(StringUtility.repeatString("-", 160));
        
        for (int i = 0; i < doctorArray.length; i++) {
            Doctor doctor = doctorArray[i];
            String email = doctor.getEmail();
            String dutySchedule = doctor.getDutySchedule();
            
            //truncate long fields to fit in table
            if (email.length() > 28) email = email.substring(0, 25) + "...";
            if (dutySchedule.length() > 23) dutySchedule = dutySchedule.substring(0, 20) + "...";
            
            System.out.printf("%-8s %-25s %-20s %-15s %-30s %-25s %-10s %-10s\n",
                doctor.getDoctorId(),
                doctor.getName().length() > 23 ? doctor.getName().substring(0, 22) + "..." : doctor.getName(),
                doctor.getSpecialization().length() > 18 ? doctor.getSpecialization().substring(0, 17) + "..." : doctor.getSpecialization(),
                doctor.getContactNumber(),
                email,
                dutySchedule,
                doctor.isOnLeave() ? "Yes" : "No",
                doctor.isIsAvailable() ? "Yes" : "No");
        }
        System.out.println(StringUtility.repeatString("-", 160));
        System.out.println("Total Doctors: " + doctorArray.length);
        System.out.println(StringUtility.repeatString("=", 160));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchDoctorById() {
        System.out.print("Enter doctor ID to search (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        Doctor foundDoctor = findDoctorById(doctorId);
        if (foundDoctor != null) {
            displayDoctorDetails(foundDoctor);
        } else {
            System.out.println("Doctor not found!");
        }
    }
    
    public void searchDoctorBySpecialization() {
        System.out.print("Enter specialization to search (or press Enter to cancel): ");
        String specialization = scanner.nextLine().trim();

        if (specialization.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Object[] doctorsArray = doctorList.toArray(); //adt method
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Available"};
        SetAndQueueInterface<Object[]> rowList = new SetQueueArray<>();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (doctor.getSpecialization().toLowerCase().contains(specialization.toLowerCase())) {
                rowList.add(new Object[]{doctor.getDoctorId(), doctor.getName(), doctor.getSpecialization(), doctor.getContactNumber(), doctor.isIsAvailable() ? "Yes" : "No"}); //adt method
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length]; //adt method
        Object[] rowArray = rowList.toArray(); //adt method
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "DOCTORS WITH SPECIALIZATION",
            headers,
            rows
        ));

        if (rowList.isEmpty()) { //adt method
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
            String start = doctor.getLeaveDateStart();
            String end = doctor.getLeaveDateEnd();
            if (start != null && !start.isEmpty() && end != null && !end.isEmpty()) {
                System.out.println("Leave Period: " + start + " to " + end);
            }
        }
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updateDoctorDetails() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        UPDATE DOCTOR DETAILS");
        System.out.println(StringUtility.repeatString("=", 60));

        Object[] doctorsArray = doctorList.toArray(); //adt method

        SetAndQueueInterface<Doctor> tempList = new SetQueueArray<>();
        for (Object obj : doctorsArray) {
            tempList.add((Doctor) obj); //adt method
        }
        tempList.sort(); //adt method

        Object[] sortedDoctorsArray = tempList.toArray(); //adt method
        Doctor[] doctorArray = new Doctor[sortedDoctorsArray.length];
        for (int i = 0; i < sortedDoctorsArray.length; i++) {
            doctorArray[i] = (Doctor) sortedDoctorsArray[i];
        }
        
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Email", "Available"};
        Object[][] rows = new Object[doctorArray.length][headers.length];
        for (int i = 0; i < doctorArray.length; i++) {
            Doctor doctor = doctorArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
            rows[i][4] = doctor.getEmail();
            rows[i][5] = doctor.isIsAvailable() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "AVAILABLE DOCTORS",
            headers,
            rows
        ));
        System.out.println("Total Doctors: " + doctorArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter doctor ID to update (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        
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
            String contact = "";
            boolean validContact = false;
            while (!validContact) {
                contact = InputValidator.getValidStringAllowEmpty(scanner, "Contact Number [" + doctor.getContactNumber() + "]: ");
                if (contact.isEmpty()) {
                    validContact = true; //skip update
                } else if (contact.matches("^(01[0-9]|03|04|05|06|07|08|09)-?[0-9]{7,8}$")) {
                    //check if contact number already exists for another doctor
                    if (!contact.equals(doctor.getContactNumber()) && isContactNumberExists(contact)) {
                        System.out.println("[ERROR] Contact number already exists for another doctor! Please enter a different number or press Enter to skip.");
                    } else {
                        doctor.setContactNumber(contact);
                        System.out.println("[OK] Contact number updated successfully!");
                        validContact = true;
                    }
                } else {
                    System.out.println("Invalid phone number format. Please enter a valid Malaysian phone number (e.g., 012-3456789) or press Enter to skip.");
                }
            }

            //update email
            String email = "";
            boolean validEmail = false;
            while (!validEmail) {
                email = InputValidator.getValidStringAllowEmpty(scanner, "Email [" + doctor.getEmail() + "]: ");
                if (email.isEmpty()) {
                    validEmail = true; //skip update
                } else if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    //check if email already exists for another doctor
                    if (!email.equals(doctor.getEmail()) && isEmailExists(email)) {
                        System.out.println("[ERROR] Email already exists for another doctor! Please enter a different email or press Enter to skip.");
                    } else {
                        doctor.setEmail(email);
                        System.out.println("[OK] Email updated successfully!");
                        validEmail = true;
                    }
                } else {
                    System.out.println("Invalid email format. Please enter a valid email address or press Enter to skip.");
                }
            }
            
            System.out.println("\n[OK] Doctor details updated successfully!");
            System.out.println("\nUpdated doctor information:");
            displayDoctorDetails(doctor);
        } else {
            System.out.println("[ERROR] Doctor not found!");
        }
    }
    
    public void updateDoctorSchedule() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        UPDATE DOCTOR SCHEDULE");
        System.out.println(StringUtility.repeatString("=", 60));

        Object[] doctorsArray = doctorList.toArray(); //adt method

        SetAndQueueInterface<Doctor> tempList = new SetQueueArray<>();
        for (Object obj : doctorsArray) {
            tempList.add((Doctor) obj); //adt method
        }
        tempList.sort(); //adt method

        Object[] sortedDoctorsArray = tempList.toArray(); //adt method
        Doctor[] doctorArray = new Doctor[sortedDoctorsArray.length];
        for (int i = 0; i < sortedDoctorsArray.length; i++) {
            doctorArray[i] = (Doctor) sortedDoctorsArray[i];
        }
        
        String[] headers = {"ID", "Name", "Specialization", "Contact", "Email", "Available"};
        Object[][] rows = new Object[doctorArray.length][headers.length];
        for (int i = 0; i < doctorArray.length; i++) {
            Doctor doctor = doctorArray[i];
            rows[i][0] = doctor.getDoctorId();
            rows[i][1] = doctor.getName();
            rows[i][2] = doctor.getSpecialization();
            rows[i][3] = doctor.getContactNumber();
            rows[i][4] = doctor.getEmail();
            rows[i][5] = doctor.isIsAvailable() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "AVAILABLE DOCTORS",
            headers,
            rows
        ));
        System.out.println("Total Doctors: " + doctorArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter doctor ID to update schedule (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        
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
                System.out.print("Leave Start Date [" + doctor.getLeaveDateStart() + "] (YYYY-MM-DD): ");
                String leaveStart = scanner.nextLine();
                if (!leaveStart.isEmpty()) doctor.setLeaveDateStart(leaveStart);
                
                System.out.print("Leave End Date [" + doctor.getLeaveDateEnd() + "] (YYYY-MM-DD): ");
                String leaveEnd = scanner.nextLine();
                if (!leaveEnd.isEmpty()) doctor.setLeaveDateEnd(leaveEnd);
            }
            
            System.out.println("\n[OK] Doctor schedule updated successfully!");
            System.out.println("\nUpdated doctor information:");
            displayDoctorDetails(doctor);
        } else {
            System.out.println("[ERROR] Doctor not found!");
        }
    }
    
    public void addNewDoctor() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("        ADD NEW DOCTOR");
        System.out.println(StringUtility.repeatString("=", 80));
        
        System.out.println("Enter new doctor information:");
        System.out.println(StringUtility.repeatString("-", 80));
        
        //auto-generate doctor ID
        String doctorId = "DOC" + String.format("%03d", doctorIdCounter++);
        System.out.println("Generated Doctor ID: " + doctorId);

        String name;
        do {
            System.out.print("Enter Doctor Name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Doctor name cannot be empty. Please try again.");
            }
        } while (name.isEmpty());

        String specialization;
        do {
            System.out.print("Enter Specialization: ");
            specialization = scanner.nextLine().trim();
            if (specialization.isEmpty()) {
                System.out.println("Specialization cannot be empty. Please try again.");
            }
        } while (specialization.isEmpty());

        String contactNumber;
        do {
            contactNumber = InputValidator.getValidPhoneNumber(scanner, "Enter Contact Number: ");
            if (isContactNumberExists(contactNumber)) {
                System.out.println("Contact number already exists for another doctor! Please use a different number.");
            }
        } while (isContactNumberExists(contactNumber));

        String email;
        do {
            email = InputValidator.getValidEmail(scanner, "Enter Email: ");
            if (isEmailExists(email)) {
                System.out.println("Email already exists for another doctor! Please use a different email.");
            }
        } while (isEmailExists(email));

        String dutySchedule;
        do {
            System.out.print("Enter Duty Schedule (e.g., Mon-Fri 9AM-5PM): ");
            dutySchedule = scanner.nextLine().trim();
            if (dutySchedule.isEmpty()) {
                System.out.println("Duty schedule cannot be empty. Please try again.");
            }
        } while (dutySchedule.isEmpty());

        boolean isAvailable = true;
        boolean onLeave = false;
        String leaveDateStart = "";
        String leaveDateEnd = "";

        Doctor newDoctor = new Doctor(doctorId, name, specialization, contactNumber, email, 
                                    isAvailable, dutySchedule, onLeave, leaveDateStart, leaveDateEnd);

        boolean added = doctorList.add(newDoctor);
        
        if (added) {
            //add to on-duty list if available
            if (isAvailable && !onLeave) {
                onDutyDoctorList.add(newDoctor);
            }
            
            System.out.println("\n[OK] Doctor added successfully!");
            System.out.println("Auto-generated Doctor ID: " + doctorId);
            System.out.println("Name: " + name);
            System.out.println("Specialization: " + specialization);
            System.out.println("Status: Available and added to duty");
            
            System.out.println("\nNew doctor information:");
            displayDoctorDetails(newDoctor);
        } else {
            System.out.println("[ERROR] Failed to add doctor to system!");
        }
    }
    
    public void removeDoctor() {
        System.out.println("\n" + StringUtility.repeatString("=", 110));
        System.out.println("        REMOVE DOCTOR");
        System.out.println(StringUtility.repeatString("=", 110));
        
        System.out.println("CURRENT DOCTOR LIST:");
        System.out.println(StringUtility.repeatString("-", 110));
        System.out.printf("%-8s %-40s %-25s %-15s %-10s\n", "ID", "Name", "Specialization", "Contact", "Available");
        System.out.println(StringUtility.repeatString("-", 110));

        Object[] doctorsArray = doctorList.toArray(); //adt method
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            System.out.printf("%-8s %-40s %-25s %-15s %-10s\n", 
                doctor.getDoctorId(), 
                doctor.getName(), 
                doctor.getSpecialization(), 
                doctor.getContactNumber(), 
                doctor.isIsAvailable() ? "Yes" : "No");
        }
        System.out.println(StringUtility.repeatString("-", 110));
        System.out.println("Total Doctors: " + doctorsArray.length);
        System.out.println(StringUtility.repeatString("=", 110));
        
        System.out.print("Enter doctor ID to remove (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            System.out.println("Doctor to be removed:");
            displayDoctorDetails(doctor);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this doctor? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removedFromList = doctorList.remove(doctor); //adt method

                boolean removedFromDuty = onDutyDoctorList.remove(doctor); //adt method
                
                if (removedFromList) {
                    System.out.println("[OK] Doctor removed successfully!");
                    if (removedFromDuty) {
                        System.out.println("[OK] Doctor also removed from duty list.");
                    }
                } else {
                    System.out.println("[ERROR] Failed to remove doctor from system!");
                }
            } else {
                System.out.println("[ERROR] Doctor removal cancelled.");
            }
        } else {
            System.out.println("[ERROR] Doctor not found!");
        }
    }

    public void generateDoctorPerformanceAndWorkloadReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           DOCTOR MANAGEMENT SUBSYSTEM");
        System.out.println("                            DOCTOR PERFORMANCE REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        Object[] doctorsArray = doctorList.toArray();
        int totalDoctors = doctorsArray.length;

        String reset = "\u001B[0m";

        int[] consultationCounts = new int[totalDoctors];

        //analyze doctor performance
        if (consultationManagement != null) {
            Object[] consultationsArray = consultationManagement.getAllConsultations();

            for (int i = 0; i < totalDoctors; i++) {
                Doctor doctor = (Doctor) doctorsArray[i];
                String doctorId = doctor.getDoctorId();

                //count consultations
                for (Object obj : consultationsArray) {
                    Consultation consultation = (Consultation) obj;
                    if (consultation.getDoctorId().equals(doctorId)) {
                        consultationCounts[i]++;
                    }
                }
            }
        }

        //sort doctors by consultation count (bubble sort)
        for (int i = 0; i < totalDoctors - 1; i++) {
            for (int j = 0; j < totalDoctors - i - 1; j++) {
                if (consultationCounts[j] < consultationCounts[j + 1]) {
                    //swap doctors
                    Object tempDoctor = doctorsArray[j];
                    doctorsArray[j] = doctorsArray[j + 1];
                    doctorsArray[j + 1] = tempDoctor;

                    //swap counts
                    int tempConsult = consultationCounts[j];
                    consultationCounts[j] = consultationCounts[j + 1];
                    consultationCounts[j + 1] = tempConsult;
                }
            }
        }

        //display top performing doctors table
        System.out.println("TOP DOCTORS BY CONSULTATION COUNT");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.print("| Doctor ID | Doctor Name               | Specialization    | Consultations                   |");
        System.out.println();
        System.out.println(StringUtility.repeatString("-", 95));

        for (int i = 0; i < Math.min(totalDoctors, 10); i++) {
            Doctor d = (Doctor) doctorsArray[i];
            System.out.printf("| %-9s | %-25s | %-17s | %-31d |%n",
                d.getDoctorId(),
                d.getName().length() > 25 ? d.getName().substring(0, 25) : d.getName(),
                d.getSpecialization().length() > 17 ? d.getSpecialization().substring(0, 17) : d.getSpecialization(),
                consultationCounts[i]);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        //calculate totals
        int totalConsultations = 0;
        for (int i = 0; i < totalDoctors; i++) {
            totalConsultations += consultationCounts[i];
        }

        System.out.println("Total Number of Doctors: " + totalDoctors);
        System.out.println("Total Number of Consultations: " + totalConsultations);
        System.out.println("Average Consultations per Doctor: " + (totalDoctors > 0 ? totalConsultations / totalDoctors : 0));
        System.out.println();

        //top doctors consultation count chart
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                TOP DOCTORS CONSULTATION COUNT CHART");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Top Doctors by Consultation Count Chart:");
        System.out.println("   ^");
        int maxConsultations = 0;
        int numDoctors = Math.min(totalDoctors, 10); //show up to 10 doctors
        for (int i = 0; i < numDoctors; i++) {
            if (consultationCounts[i] > maxConsultations) maxConsultations = consultationCounts[i];
        }

        String barColor = "\u001B[44m"; //blue background

        for (int level = maxConsultations; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numDoctors; i++) {
                if (consultationCounts[i] >= level) {
                    System.out.print(" " + barColor + "  " + reset + " ");
                } else {
                    System.out.print("    ");
                }
                //add 1 space between bars
                System.out.print(" ");
            }
            System.out.println();
        }

        //draw axis line that matches the data width exactly (4 characters per doctor + 1 space between)
        System.out.print("   +");
        for (int i = 0; i < numDoctors; i++) {
            System.out.print("----");
            if (i < numDoctors - 1) { //don't add space after the last bar
                System.out.print("-");
            }
        }
        System.out.println("> Doctors");

        //draw labels aligned with bars (clean names without Dr. prefix, max 4 characters)
        System.out.print("    ");
        for (int i = 0; i < numDoctors; i++) {
            Doctor d = (Doctor) doctorsArray[i];
            String displayName = d.getName();
            //remove "Dr." prefix if present
            if (displayName.startsWith("Dr. ")) {
                displayName = displayName.substring(4);
            }
            //show first 4 characters maximum
            if (displayName.length() > 4) {
                displayName = displayName.substring(0, 4);
            }
            System.out.printf("%-4s", displayName);
            //add 1 space between labels
            if (i < numDoctors - 1) { //don't add space after the last label
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.println();

        if (totalDoctors > 0) {
            Doctor topDoctor = (Doctor) doctorsArray[0];
            System.out.printf("Doctor with most consultations: < %s with %d consultations >%n",
                topDoctor.getName(), consultationCounts[0]);
        }

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public void generateDoctorSpecializationAndAvailabilityReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           DOCTOR MANAGEMENT SUBSYSTEM");
        System.out.println("                     DOCTOR SPECIALIZATION ANALYSIS REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        Object[] doctorsArray = doctorList.toArray();
        int totalDoctors = doctorsArray.length;

        //specialization analysis
        SetAndQueueInterface<String> specializationSet = new SetQueueArray<>();
        int[] specializationCounts = new int[100];
        String[] specializationArray = new String[100];
        int specializationIndex = 0;

        //analyze specializations
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
            if (!found && specializationIndex < 100) {
                specializationArray[specializationIndex] = specialization;
                specializationCounts[specializationIndex] = 1;
                specializationIndex++;
            }
        }

        //sort specializations by count (bubble sort)
        for (int i = 0; i < specializationIndex - 1; i++) {
            for (int j = 0; j < specializationIndex - i - 1; j++) {
                if (specializationCounts[j] < specializationCounts[j + 1]) {
                    //swap specializations
                    String tempSpec = specializationArray[j];
                    specializationArray[j] = specializationArray[j + 1];
                    specializationArray[j + 1] = tempSpec;

                    //swap counts
                    int tempCount = specializationCounts[j];
                    specializationCounts[j] = specializationCounts[j + 1];
                    specializationCounts[j + 1] = tempCount;
                }
            }
        }

        //display specialization and consultation count table
        System.out.println("TOP 10 SPECIALIZATIONS AND CONSULTATION COUNT TABLE");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.print("| Specialization            | Number of Doctors | Total Consultations                         |");
        System.out.println();
        System.out.println(StringUtility.repeatString("-", 95));

        //calculate consultation counts for each specialization
        int[] consultationCountsBySpec = new int[specializationIndex];
        if (consultationManagement != null) {
            Object[] consultationsArray = consultationManagement.getAllConsultations();
            
            for (Object obj : consultationsArray) {
                Consultation consultation = (Consultation) obj;
                String doctorId = consultation.getDoctorId();
                
                //find the doctor and their specialization
                for (Object doctorObj : doctorsArray) {
                    Doctor doctor = (Doctor) doctorObj;
                    if (doctor.getDoctorId().equals(doctorId)) {
                        String specialization = doctor.getSpecialization();
                        
                        //find specialization index and add consultation count
                        for (int i = 0; i < specializationIndex; i++) {
                            if (specializationArray[i].equals(specialization)) {
                                consultationCountsBySpec[i]++;
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }

        int topSpecializationsCount = Math.min(specializationIndex, 10); // Show top 10 specializations
        for (int i = 0; i < topSpecializationsCount; i++) {
            System.out.printf("| %-25s | %-17d | %-43d |%n",
                specializationArray[i].length() > 25 ? specializationArray[i].substring(0, 25) : specializationArray[i],
                specializationCounts[i],
                consultationCountsBySpec[i]);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Total Number of Doctors: " + totalDoctors);
        System.out.println("Total Number of Specializations: " + specializationIndex);
        System.out.println();

        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                SPECIALIZATION VS CONSULTATIONS CHART");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Specialization vs Consultations Chart:");
        System.out.println("   ^");

        //use actual consultation counts by specialization
        int maxLoad = 0;
        for (int load : consultationCountsBySpec) {
            if (load > maxLoad) maxLoad = load;
        }

        String barColor = "\u001B[46m"; //cyan background
        String reset = "\u001B[0m";

        for (int level = maxLoad; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < Math.min(specializationIndex, 5); i++) {
                if (consultationCountsBySpec[i] >= level) {
                    System.out.print(" " + barColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.print("   +");
        for (int i = 0; i < Math.min(specializationIndex, 5); i++) {
            System.out.print("------");
        }
        System.out.println("> Specializations");

        System.out.print("    ");
        for (int i = 0; i < Math.min(specializationIndex, 5); i++) {
            String shortSpec = specializationArray[i].length() > 4 ? specializationArray[i].substring(0, 4) : specializationArray[i];
            System.out.printf("%-4s  ", shortSpec);
        }
        System.out.println();
        System.out.println();

        //summary
        if (specializationIndex > 0) {
            System.out.printf("Most common specialization: < %s with %d doctors >%n",
                specializationArray[0], specializationCounts[0]);
        }

        if (specializationIndex > 1) {
            System.out.printf("Least common specialization: < %s with %d doctors >%n",
                specializationArray[specializationIndex - 1], specializationCounts[specializationIndex - 1]);
        }

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public Doctor findDoctorById(String doctorId) {
        Doctor dummy = new Doctor();
        dummy.setDoctorId(doctorId);
        return doctorList.search(dummy); //adt method
    }
    
    private String normalizePhoneNumber(String phoneNumber) {
        //remove all dashes and spaces to normalize phone number for comparison
        return phoneNumber.replaceAll("[-\\s]", "");
    }

    private boolean isContactNumberExists(String contactNumber) {
        String normalizedInput = normalizePhoneNumber(contactNumber);
        Object[] doctorsArray = doctorList.toArray();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            String normalizedExisting = normalizePhoneNumber(doctor.getContactNumber());
            if (normalizedExisting.equals(normalizedInput)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isEmailExists(String email) {
        Object[] doctorsArray = doctorList.toArray();
        for (Object obj : doctorsArray) {
            Doctor doctor = (Doctor) obj;
            if (doctor.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Doctor[] getDoctorsOnDuty() {
        Object[] doctorsArray = onDutyDoctorList.toArray(); //adt method
        Doctor[] doctorArray = new Doctor[doctorsArray.length];
        for (int i = 0; i < doctorsArray.length; i++) {
            doctorArray[i] = (Doctor) doctorsArray[i];
        }
        return doctorArray;
    }

    public int getTotalDoctorCount() {
        return doctorList.size(); //adt method
    }

    public int getDoctorsOnDutyCount() {
        return onDutyDoctorList.size(); //adt method
    }

    public Object[] getAllDoctors() {
        return doctorList.toArray(); //adt method
    }

    public void searchDoctorConsultations() {
        System.out.print("Enter doctor ID to search consultations (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }
        
        Object[] consultationsArray = doctor.getConsultations().toArray();
        String[] headers = {"Consultation ID", "Patient ID", "Date", "Status", "Notes"};
        Object[][] rows = new Object[consultationsArray.length][headers.length];
        for (int i = 0; i < consultationsArray.length; i++) {
            Consultation consultation = (Consultation) consultationsArray[i];
            rows[i][0] = consultation.getConsultationId();
            rows[i][1] = consultation.getPatientId();
            rows[i][2] = consultation.getConsultationDate();
            rows[i][3] = consultation.getStatus();
            rows[i][4] = consultation.getNotes();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CONSULTATIONS FOR DOCTOR: " + doctor.getName(),
            headers,
            rows
        ));
        if (consultationsArray.length == 0) {
            System.out.println("No consultations found for this doctor.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchDoctorTreatments() {
        System.out.print("Enter doctor ID to search treatments (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }
        
        Object[] treatmentsArray = doctor.getTreatments().toArray();
        String[] headers = {"Treatment ID", "Patient ID", "Diagnosis", "Date"};
        Object[][] rows = new Object[treatmentsArray.length][headers.length];
        for (int i = 0; i < treatmentsArray.length; i++) {
            Treatment treatment = (Treatment) treatmentsArray[i];
            rows[i][0] = treatment.getTreatmentId();
            rows[i][1] = treatment.getPatientId();
            rows[i][2] = treatment.getDiagnosis();
            rows[i][4] = treatment.getTreatmentDate();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "TREATMENTS BY DOCTOR: " + doctor.getName(),
            headers,
            rows
        ));
        if (treatmentsArray.length == 0) {
            System.out.println("No treatments found for this doctor.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchDoctorPrescriptions() {
        System.out.print("Enter doctor ID to search prescriptions (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        
        Doctor doctor = findDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }
        
        Object[] prescriptionsArray = doctor.getPrescriptions().toArray();
        String[] headers = {"Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status", "Paid"};
        Object[][] rows = new Object[prescriptionsArray.length][headers.length];
        for (int i = 0; i < prescriptionsArray.length; i++) {
            Prescription prescription = (Prescription) prescriptionsArray[i];
            rows[i][0] = prescription.getPrescriptionId();
            rows[i][1] = prescription.getPatientId();
            rows[i][2] = prescription.getDiagnosis();
            rows[i][3] = "RM " + String.format("%.2f", prescription.getTotalCost());
            rows[i][4] = prescription.getStatus();
            rows[i][5] = prescription.isPaid() ? "Yes" : "No";
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "PRESCRIPTIONS BY DOCTOR: " + doctor.getName(),
            headers,
            rows
        ));
        if (prescriptionsArray.length == 0) {
            System.out.println("No prescriptions found for this doctor.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
} 