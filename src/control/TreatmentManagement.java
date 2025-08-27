package control;

import adt.SetAndQueueInterface;
import adt.SetQueueArray;
import entity.Prescription;
import entity.PrescribedMedicine;
import entity.Patient;
import entity.Doctor;
import entity.Medicine;
import entity.Treatment;
import entity.PharmacyTransaction;
import dao.DataInitializer;
import java.util.Scanner;
import utility.StringUtility;
import utility.InputValidator;

//edward long wai pin
public class TreatmentManagement {
    private SetAndQueueInterface<Prescription> prescriptionList = new SetQueueArray<>();
    private SetAndQueueInterface<Treatment> treatmentList = new SetQueueArray<>();
    private SetAndQueueInterface<Medicine> medicineList = new SetQueueArray<>();
    private Scanner scanner;
    private int prescriptionIdCounter = 3001;
    private int prescribedMedicineIdCounter = 4001;
    private int treatmentIdCounter = 211;
    private PatientManagement patientManagement;
    private DoctorManagement doctorManagement;
    
    public TreatmentManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    public void setPatientManagement(PatientManagement patientManagement) {
        this.patientManagement = patientManagement;
    }
    
    public void setDoctorManagement(DoctorManagement doctorManagement) {
        this.doctorManagement = doctorManagement;
    }

    private void loadSampleData() {
        SetAndQueueInterface<Medicine> sampleMedicines = DataInitializer.initializeSampleMedicines();
        for (Object obj : sampleMedicines.toArray()) {
            medicineList.add((Medicine) obj); //adt method
        }

        SetAndQueueInterface<Prescription> samplePrescriptions = DataInitializer.initializeSamplePrescriptions();
        for (Object obj : samplePrescriptions.toArray()) {
            prescriptionList.add((Prescription) obj); //adt method
        }

        SetAndQueueInterface<Treatment> sampleTreatments = DataInitializer.initializeSampleTreatments();
        for (Object obj : sampleTreatments.toArray()) {
            treatmentList.add((Treatment) obj); //adt method
        }

        //update prescription ID counter to continue from the last used ID
        if (samplePrescriptions.size() > 0) {
            int maxPrescriptionId = 0;
            int maxPrescribedMedicineId = 0;
            int maxTreatmentId = 0;
            
            for (Object obj : samplePrescriptions.toArray()) {
                Prescription prescription = (Prescription) obj;
                String prescriptionId = prescription.getPrescriptionId();
                if (prescriptionId.startsWith("PRE")) {
                    try {
                        int idNumber = Integer.parseInt(prescriptionId.substring(3));
                        if (idNumber > maxPrescriptionId) {
                            maxPrescriptionId = idNumber;
                        }
                    } catch (NumberFormatException e) {
                        //skip invalid IDs
                    }
                }
                
                //check prescribed medicine IDs within each prescription
                Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
                for (Object pmObj : prescribedMedicinesArray) {
                    PrescribedMedicine pm = (PrescribedMedicine) pmObj;
                    String pmId = pm.getPrescribedMedicineId();
                    if (pmId.startsWith("PM")) {
                        try {
                            int idNumber = Integer.parseInt(pmId.substring(2));
                            if (idNumber > maxPrescribedMedicineId) {
                                maxPrescribedMedicineId = idNumber;
                            }
                        } catch (NumberFormatException e) {
                            //skip invalid IDs
                        }
                    }
                }
            }

            for (Object obj : sampleTreatments.toArray()) {
                Treatment treatment = (Treatment) obj;
                String treatmentId = treatment.getTreatmentId();
                if (treatmentId.startsWith("TRE")) {
                    try {
                        int idNumber = Integer.parseInt(treatmentId.substring(3));
                        if (idNumber > maxTreatmentId) {
                            maxTreatmentId = idNumber;
                        }
                    } catch (NumberFormatException e) {
                        //skip invalid IDs
                    }
                }
            }

            prescriptionIdCounter = maxPrescriptionId + 1;
            prescribedMedicineIdCounter = maxPrescribedMedicineId + 1;
            treatmentIdCounter = maxTreatmentId + 1;
        }
    }
    
    public void initializeEntityRelationships() {
        if (patientManagement == null || doctorManagement == null) {
            return;
        }
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            
            try {
                int patientId = Integer.parseInt(prescription.getPatientId());
                Patient patient = patientManagement.findPatientById(patientId);
                if (patient != null) {
                    patient.getPrescriptions().add(prescription);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing patient ID: " + prescription.getPatientId());
            }
            
            Doctor doctor = doctorManagement.findDoctorById(prescription.getDoctorId());
            if (doctor != null) {
                doctor.getPrescriptions().add(prescription);
            }
        }
        
        Object[] treatmentsArray = treatmentList.toArray();
        for (Object obj : treatmentsArray) {
            Treatment treatment = (Treatment) obj;
            try {
                int patientId = Integer.parseInt(treatment.getPatientId());
                Patient patient = patientManagement.findPatientById(patientId);
                if (patient != null) {
                    patient.getTreatments().add(treatment);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing patient ID: " + treatment.getPatientId());
            }

            Doctor doctor = doctorManagement.findDoctorById(treatment.getDoctorId());
            if (doctor != null) {
                doctor.getTreatments().add(treatment);
            }
        }
        
        SetAndQueueInterface<PharmacyTransaction> sampleTransactions = DataInitializer.initializeSampleTransactions();
        for (Object obj : sampleTransactions.toArray()) {
            PharmacyTransaction transaction = (PharmacyTransaction) obj;
            try {
                int patientId = Integer.parseInt(transaction.getPatientId());
                Patient patient = patientManagement.findPatientById(patientId);
                if (patient != null) {
                    patient.getPharmacyTransactions().add(transaction);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing patient ID: " + transaction.getPatientId());
            }
        }
    }
    
    public void createPrescription(String consultationId, Patient currentPatient, Doctor selectedDoctor, String diagnosis, String consultationDate) {
        String prescriptionId = "PRE" + String.format("%03d", prescriptionIdCounter++);
        Prescription prescription = new Prescription(prescriptionId, consultationId, String.valueOf(currentPatient.getId()), selectedDoctor.getDoctorId(), diagnosis, new SetQueueArray<>(), consultationDate, "active", 0.0, false);
        
        //prescribe medicine
        System.out.println("\n=== PRESCRIBE MEDICINES ===");
        System.out.println("Available medicines:");
        displayAvailableMedicines();
        
        boolean addMoreMedicines = true;
        while (addMoreMedicines) {
            System.out.print("Enter medicine ID to prescribe (or 'done' to finish, or press Enter to cancel): ");
            String medicineId = scanner.nextLine().trim();
            
            if (medicineId.equalsIgnoreCase("done")) {
                addMoreMedicines = false;
                break;
            } else if (medicineId.isEmpty()) {
                System.out.println("Operation cancelled.");
                return;
            }
            
            Medicine medicine = findMedicineById(medicineId);
            if (medicine != null) {
                //check for allergies
                if (currentPatient.getAllegy() != null && !currentPatient.getAllegy().isEmpty()) {
                    if (medicine.getActiveIngredient().toLowerCase().contains(currentPatient.getAllegy().toLowerCase()) ||
                        medicine.getName().toLowerCase().contains(currentPatient.getAllegy().toLowerCase())) {
                        System.out.println("WARNING: Patient is allergic to " + currentPatient.getAllegy() + 
                                         "! This medicine contains " + medicine.getActiveIngredient());
                        System.out.print("Do you want to prescribe it anyway? (y/n): ");
                        String continueChoice = scanner.nextLine();
                        if (!continueChoice.equalsIgnoreCase("y")) {
                            continue;
                        }
                    }
                }
                
                System.out.print("Enter quantity: ");
                int quantity = getUserInputInt(1, 100);
                
                System.out.print("Enter dosage (e.g., '1 tablet twice daily'): ");
                String dosage = scanner.nextLine();
                
                System.out.print("Enter instructions (e.g., 'Take with food'): ");
                String instructions = scanner.nextLine();

                String prescribedMedicineId = "PM" + prescribedMedicineIdCounter++;
                double totalPrice = medicine.getPrice() * quantity;
                
                PrescribedMedicine prescribedMedicine = new PrescribedMedicine(
                    prescribedMedicineId, prescriptionId, medicineId, medicine.getName(),
                    quantity, dosage, instructions, medicine.getPrice(), totalPrice, false
                );
                
                prescription.getPrescribedMedicines().add(prescribedMedicine); //adt method
                prescription.setTotalCost(prescription.getTotalCost() + totalPrice);

                System.out.println("Medicine " + medicine.getName() + " prescribed successfully!");
            } else {
                System.out.println("Medicine not found!");
            }
        }

        if (prescription.getPrescribedMedicines().isEmpty()) {
            System.out.println("No medicines were prescribed for this consultation.");
            System.out.println("No prescription created.");
            return;
        }

        prescriptionList.add(prescription); //adt method
        
        if (patientManagement != null) {
            Patient patient = patientManagement.findPatientById(currentPatient.getId());
            if (patient != null) {
                patient.getPrescriptions().add(prescription);
            }
        }
        
        if (doctorManagement != null) {
            Doctor doctor = doctorManagement.findDoctorById(selectedDoctor.getDoctorId());
            if (doctor != null) {
                doctor.getPrescriptions().add(prescription);
            }
        }
        
        System.out.println("Prescription ID: " + prescriptionId);
        System.out.println("Total cost: RM " + String.format("%.2f", prescription.getTotalCost()));
    }
    
    public void displayAllPrescriptionsSorted() {
        System.out.println("\n" + StringUtility.repeatString("-", 100));
        System.out.println("ALL PRESCRIPTIONS (SORTED BY ID)");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.printf("%-15s %-10s %-10s %-20s %-12s %-10s\n", "Prescription ID", "Patient ID", "Doctor ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 100));
        
        Object[] prescriptionsArray = prescriptionList.toArray(); //adt method
        SetAndQueueInterface<Prescription> tempList = new SetQueueArray<>();
        for (Object obj : prescriptionsArray) {
            tempList.add((Prescription) obj); //adt method
        }
        tempList.sort(); //adt method

        Object[] sortedPrescriptionsArray = tempList.toArray(); //adt method
        Prescription[] prescriptionArray = new Prescription[sortedPrescriptionsArray.length];
        for (int i = 0; i < sortedPrescriptionsArray.length; i++) {
            prescriptionArray[i] = (Prescription) sortedPrescriptionsArray[i];
        }
        
        String[] headers = {"Prescription ID", "Patient ID", "Doctor ID", "Diagnosis", "Total Cost", "Status"};
        Object[][] rows = new Object[prescriptionArray.length][headers.length];
        for (int i = 0; i < prescriptionArray.length; i++) {
            Prescription prescription = prescriptionArray[i];
            rows[i][0] = prescription.getPrescriptionId();
            rows[i][1] = prescription.getPatientId();
            rows[i][2] = prescription.getDoctorId();
            rows[i][3] = prescription.getDiagnosis();
            rows[i][4] = "RM " + String.format("%.2f", prescription.getTotalCost());
            rows[i][5] = prescription.getStatus();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "ALL PRESCRIPTIONS (SORTED BY ID)",
            headers,
            rows
        ));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchPrescriptionById() {
        System.out.print("Enter prescription ID to search (or press Enter to cancel): ");
        String prescriptionId = scanner.nextLine().trim();
        
        if (prescriptionId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Prescription foundPrescription = findPrescriptionById(prescriptionId);
        if (foundPrescription != null) {
            displayPrescriptionDetails(foundPrescription);
        } else {
            System.out.println("Prescription not found!");
        }
    }
    
    public void searchPrescriptionsByPatient() {
        System.out.print("Enter patient ID to search prescriptions (or press Enter to cancel): ");
        String patientId = scanner.nextLine().trim();
        
        if (patientId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        String[] headers = {"Prescription ID", "Doctor ID", "Diagnosis", "Total Cost", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetQueueArray<>();
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.getPatientId().equals(patientId)) {
                rowList.add(new Object[]{prescription.getPrescriptionId(), prescription.getDoctorId(), prescription.getDiagnosis(), "RM " + String.format("%.2f", prescription.getTotalCost()), prescription.getStatus()});
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length];
        Object[] rowArray = rowList.toArray();
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "PRESCRIPTIONS BY PATIENT ID: " + patientId,
            headers,
            rows
        ));
        if (rowList.isEmpty()) {
            System.out.println("No prescriptions found for this patient.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchPrescriptionsByDoctor() {
        System.out.print("Enter doctor ID to search prescriptions (or press Enter to cancel): ");
        String doctorId = scanner.nextLine().trim();

        if (doctorId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        doctorId = doctorId.toUpperCase();
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        String[] headers = {"Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetQueueArray<>();
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.getDoctorId().equals(doctorId)) {
                rowList.add(new Object[]{prescription.getPrescriptionId(), prescription.getPatientId(), prescription.getDiagnosis(), "RM " + String.format("%.2f", prescription.getTotalCost()), prescription.getStatus()});
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length];
        Object[] rowArray = rowList.toArray();
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "PRESCRIPTIONS BY DOCTOR ID: " + doctorId,
            headers,
            rows
        ));
        if (rowList.isEmpty()) {
            System.out.println("No prescriptions found for this doctor.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void removePrescription() {
        System.out.println("\n" + StringUtility.repeatString("=", 110));
        System.out.println("        REMOVE PRESCRIPTION");
        System.out.println(StringUtility.repeatString("=", 110));
        
        System.out.println("CURRENT PRESCRIPTION LIST:");
        System.out.println(StringUtility.repeatString("-", 110));
        System.out.printf("%-15s %-10s %-10s %-40s %-12s %-10s\n", "Prescription ID", "Patient ID", "Doctor ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 110));
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            System.out.printf("%-15s %-10s %-10s %-40s %-12s %-10s\n", 
                prescription.getPrescriptionId(), 
                prescription.getPatientId(), 
                prescription.getDoctorId(), 
                prescription.getDiagnosis(), 
                "RM " + String.format("%.2f", prescription.getTotalCost()), 
                prescription.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 110));
        System.out.println("Total Prescriptions: " + prescriptionsArray.length);
        System.out.println(StringUtility.repeatString("=", 110));
        
        System.out.print("Enter prescription ID to remove (or press Enter to cancel): ");
        String prescriptionId = scanner.nextLine().trim();
        
        if (prescriptionId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Prescription prescription = findPrescriptionById(prescriptionId);
        if (prescription != null) {
            System.out.println("Prescription to be removed:");
            displayPrescriptionDetails(prescription);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this prescription? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removed = prescriptionList.remove(prescription);
                if (removed) {
                    System.out.println("[OK] Prescription removed successfully!");
                } else {
                    System.out.println("[ERROR] Failed to remove prescription from system!");
                }
            } else {
                System.out.println("[ERROR] Prescription removal cancelled.");
            }
        } else {
            System.out.println("[ERROR] Prescription not found!");
        }
    }
    
    public void displayPrescriptionDetails(Prescription prescription) {
        System.out.println("\n" + StringUtility.repeatString("-", 60));
        System.out.println("PRESCRIPTION DETAILS");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Prescription ID: " + prescription.getPrescriptionId());
        System.out.println("Consultation ID: " + prescription.getConsultationId());
        System.out.println("Patient ID: " + prescription.getPatientId());
        System.out.println("Doctor ID: " + prescription.getDoctorId());
        System.out.println("Diagnosis: " + prescription.getDiagnosis());
        System.out.println("Date: " + prescription.getPrescriptionDate());
        System.out.println("Status: " + prescription.getStatus());
        System.out.println("Total Cost: RM " + String.format("%.2f", prescription.getTotalCost()));
        System.out.println("Paid: " + (prescription.isPaid() ? "Yes" : "No"));
        
        System.out.println("\nPrescribed Medicines:");
        Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
        if (prescribedMedicinesArray.length > 0) {
            System.out.printf("%-20s %-10s %-20s %-10s\n", "Medicine", "Quantity", "Dosage", "Dispensed");
            System.out.println(StringUtility.repeatString("-", 60));
            for (Object obj : prescribedMedicinesArray) {
                PrescribedMedicine pm = (PrescribedMedicine) obj;
                System.out.printf("%-20s %-10s %-20s %-10s\n", 
                    pm.getMedicineName(), pm.getQuantity(), pm.getDosage(),
                    pm.isDispensed() ? "Yes" : "No");
            }
        } else {
            System.out.println("No medicines prescribed.");
        }
        
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generatePatientMedicineAdherenceReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                          TREATMENT MANAGEMENT SUBSYSTEM");
        System.out.println("                     TREATMENT PRESCRIPTION ANALYSIS REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        Object[] prescriptionsArray = prescriptionList.toArray();

        String reset = "\u001B[0m";

        //patient prescription analysis
        SetAndQueueInterface<String> uniquePatients = new SetQueueArray<>();
        int[] patientPrescriptionCounts = new int[100];
        String[] patientArray = new String[100];
        int patientIndex = 0;

        //analyze prescriptions per patient
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            String patientId = prescription.getPatientId();

            uniquePatients.add(patientId);

            //count prescriptions per patient
            boolean patientFound = false;
            for (int i = 0; i < patientIndex; i++) {
                if (patientArray[i].equals(patientId)) {
                    patientPrescriptionCounts[i]++;
                    patientFound = true;
                    break;
                }
            }
            if (!patientFound && patientIndex < 100) {
                patientArray[patientIndex] = patientId;
                patientPrescriptionCounts[patientIndex] = 1;
                patientIndex++;
            }


        }

        //sort patients by prescription count
        for (int i = 0; i < patientIndex - 1; i++) {
            for (int j = 0; j < patientIndex - i - 1; j++) {
                if (patientPrescriptionCounts[j] < patientPrescriptionCounts[j + 1]) {
                    //swap patients
                    String tempPatient = patientArray[j];
                    patientArray[j] = patientArray[j + 1];
                    patientArray[j + 1] = tempPatient;

                    //swap counts
                    int tempCount = patientPrescriptionCounts[j];
                    patientPrescriptionCounts[j] = patientPrescriptionCounts[j + 1];
                    patientPrescriptionCounts[j + 1] = tempCount;
                }
            }
        }

        System.out.println("PATIENT MEDICINE TABLE");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.print("| Patient ID | Patient Name               | Prescriptions                                     |");
        System.out.println();
        System.out.println(StringUtility.repeatString("-", 95));

        for (int i = 0; i < Math.min(patientIndex, 10); i++) {
            String patientId = patientArray[i];
            String patientName = getPatientName(patientId);
            int prescriptions = patientPrescriptionCounts[i];

            System.out.printf("| %-10s | %-25s | %-50d |%n",
                patientId,
                patientName.length() > 25 ? patientName.substring(0, 25) : patientName,
                prescriptions);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        //prescriptions vs patients chart
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                PRESCRIPTIONS VS PATIENTS CHART");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Prescriptions vs Patients Chart:");
        System.out.println("   ^");

        int maxPrescriptions = 0;
        int numPatients = Math.min(patientIndex, 5);
        for (int i = 0; i < numPatients; i++) {
            if (patientPrescriptionCounts[i] > maxPrescriptions) maxPrescriptions = patientPrescriptionCounts[i];
        }

        String chartColor = "\u001B[42m"; //green for prescriptions

        for (int level = maxPrescriptions; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numPatients; i++) {
                if (patientPrescriptionCounts[i] >= level) {
                    System.out.print(" " + chartColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.print("   +");
        for (int i = 0; i < numPatients; i++) {
            System.out.print("------");
        }
        System.out.println("> Patients");

        System.out.print("     ");
        for (int i = 0; i < numPatients; i++) {
            String shortName = getPatientName(patientArray[i]);
            String shortNameDisplay = shortName.length() > 4 ? shortName.substring(0, 4) : shortName;
            System.out.printf("%-4s  ", shortNameDisplay);
        }
        System.out.println();
        System.out.println();

        if (patientIndex > 0) {
            System.out.printf("Patient with most prescriptions: < %s with %d prescriptions >%n",
                getPatientName(patientArray[0]), patientPrescriptionCounts[0]);
        }

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public void generateDoctorPrescriptionEfficiencyReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                          TREATMENT MANAGEMENT SUBSYSTEM");
        System.out.println("                     TREATMENT PRESCRIPTION EFFICIENCY REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        Object[] prescriptionsArray = prescriptionList.toArray();
        int totalPrescriptions = prescriptionsArray.length;

        String reset = "\u001B[0m";

        //doctor efficiency analysis
        SetAndQueueInterface<String> uniqueDoctors = new SetQueueArray<>();
        int[] doctorPrescriptionCounts = new int[100];
        String[] doctorArray = new String[100];
        double[] doctorRevenue = new double[100];
        int doctorIndex = 0;

        //analyze prescriptions for doctor efficiency
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            String doctorId = prescription.getDoctorId();
            double cost = prescription.getTotalCost();

            uniqueDoctors.add(doctorId);

            //count prescriptions per doctor
            boolean doctorFound = false;
            for (int i = 0; i < doctorIndex; i++) {
                if (doctorArray[i].equals(doctorId)) {
                    doctorPrescriptionCounts[i]++;
                    doctorRevenue[i] += cost;
                    doctorFound = true;
                    break;
                }
            }
            if (!doctorFound && doctorIndex < 100) {
                doctorArray[doctorIndex] = doctorId;
                doctorPrescriptionCounts[doctorIndex] = 1;
                doctorRevenue[doctorIndex] = cost;
                doctorIndex++;
            }
        }

        //sort doctors by prescription count
        for (int i = 0; i < doctorIndex - 1; i++) {
            for (int j = 0; j < doctorIndex - i - 1; j++) {
                if (doctorPrescriptionCounts[j] < doctorPrescriptionCounts[j + 1]) {
                    //swap doctors
                    String tempDoctor = doctorArray[j];
                    doctorArray[j] = doctorArray[j + 1];
                    doctorArray[j + 1] = tempDoctor;

                    //swap counts
                    int tempCount = doctorPrescriptionCounts[j];
                    doctorPrescriptionCounts[j] = doctorPrescriptionCounts[j + 1];
                    doctorPrescriptionCounts[j + 1] = tempCount;

                    //swap revenue
                    double tempRevenue = doctorRevenue[j];
                    doctorRevenue[j] = doctorRevenue[j + 1];
                    doctorRevenue[j + 1] = tempRevenue;
                }
            }
        }

        //display doctor efficiency table
        System.out.println("DOCTOR PRESCRIPTION EFFICIENCY TABLE");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.print("| Doctor ID | Doctor Name               | Prescriptions | Total Revenue                       |");
        System.out.println();
        System.out.println(StringUtility.repeatString("-", 95));

        for (int i = 0; i < Math.min(doctorIndex, 10); i++) {
            String doctorId = doctorArray[i];
            String doctorName = getDoctorName(doctorId);
            int prescriptions = doctorPrescriptionCounts[i];
            double revenue = doctorRevenue[i];

            System.out.printf("| %-9s | %-25s | %-13d | RM %-32.2f |%n",
                doctorId,
                doctorName.length() > 25 ? doctorName.substring(0, 25) : doctorName,
                prescriptions,
                revenue);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                DOCTOR EFFICIENCY CHART");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Doctor Efficiency Chart:");
        System.out.println("   ^");

        int maxPrescriptions = 0;
        int numDoctors = Math.min(doctorIndex, 5);
        for (int i = 0; i < numDoctors; i++) {
            if (doctorPrescriptionCounts[i] > maxPrescriptions) maxPrescriptions = doctorPrescriptionCounts[i];
        }

        String efficiencyColor = "\u001B[44m"; //blue for efficiency

        for (int level = maxPrescriptions; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numDoctors; i++) {
                if (doctorPrescriptionCounts[i] >= level) {
                    System.out.print(" " + efficiencyColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.print("   +");
        for (int i = 0; i < numDoctors; i++) {
            System.out.print("------");
        }
        System.out.println("> Doctors");

        System.out.print("    ");
        for (int i = 0; i < numDoctors; i++) {
            String shortName = getDoctorName(doctorArray[i]);
            String shortNameDisplay = shortName.length() > 4 ? shortName.substring(0, 4) : shortName;
            System.out.printf("%-4s  ", shortNameDisplay);
        }
        System.out.println();
        System.out.println();

        //summary
        if (doctorIndex > 0) {
            System.out.printf("Most efficient doctor: < %s with %d prescriptions >%n",
                getDoctorName(doctorArray[0]), doctorPrescriptionCounts[0]);
            System.out.printf("Highest revenue doctor: < %s with RM %.2f >%n",
                getDoctorName(doctorArray[0]), doctorRevenue[0]);
        }

        System.out.println("Average prescriptions per doctor: " + String.format("%.1f", 
            (double)totalPrescriptions / doctorIndex));

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
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
    
    private String getDoctorName(String doctorId) {
        if (doctorManagement != null) {
            Doctor doctor = doctorManagement.findDoctorById(doctorId);
            if (doctor != null) {
                String name = doctor.getName();
                //remove "Dr." prefix if present
                if (name.startsWith("Dr.")) {
                    name = name.substring(3).trim();
                }
                return name;
            }
        }
        return "Unknown Doctor";
    }
    
    public void displayAvailableMedicines() {
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-12s %-20s %-15s %-10s %-8s %-10s\n", "ID", "Name", "Brand", "Stock", "Price", "Purpose");
        System.out.println(StringUtility.repeatString("-", 80));
        
        Object[] medicinesArray = medicineList.toArray();
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            System.out.printf("%-12s %-20s %-15s %-10s %-8s %-10s\n", 
                medicine.getMedicineId(), medicine.getName(), medicine.getBrand(),
                medicine.getStockQuantity(), "RM " + medicine.getPrice(), medicine.getPurpose());
        }
        System.out.println(StringUtility.repeatString("-", 80));
    }
    
    public Prescription findPrescriptionById(String prescriptionId) {
        Prescription dummy = new Prescription();
        dummy.setPrescriptionId(prescriptionId.toUpperCase());
        return prescriptionList.search(dummy); //adt method
    }
    
    public Medicine findMedicineById(String medicineId) {
        Object[] medicinesArray = medicineList.toArray();
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            if (medicine.getMedicineId().equalsIgnoreCase(medicineId)) {
                return medicine;
            }
        }
        return null;
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

    public int getTotalPrescriptionCount() {
        return prescriptionList.size(); //adt method
    }
    
    public double getTotalRevenue() {
        double totalRevenue = 0.0;
        Object[] prescriptionsArray = prescriptionList.toArray();
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.isPaid()) {
                totalRevenue += prescription.getTotalCost();
            }
        }
        return totalRevenue;
    }
    
    public int getPaidPrescriptionCount() {
        int paidCount = 0;
        Object[] prescriptionsArray = prescriptionList.toArray();
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.isPaid()) {
                paidCount++;
            }
        }
        return paidCount;
    }

    public void processPayment() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        PAYMENT PROCESSING");
        System.out.println(StringUtility.repeatString("=", 60));

        System.out.println("Unpaid Prescriptions:");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s %-10s\n", "Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        boolean hasUnpaid = false;
        
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (!prescription.isPaid() && prescription.getStatus().equals("active")) {
                System.out.printf("%-15s %-10s %-20s %-15s %-10s\n", 
                    prescription.getPrescriptionId(), prescription.getPatientId(),
                    prescription.getDiagnosis(), "RM " + String.format("%.2f", prescription.getTotalCost()),
                    prescription.getStatus());
                hasUnpaid = true;
            }
        }
        
        if (!hasUnpaid) {
            System.out.println("No unpaid prescriptions found.");
            System.out.println(StringUtility.repeatString("-", 80));
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.println("Enter prescription ID to process payment (or press Enter to cancel):");
        System.out.print("Prescription ID: ");
        String prescriptionId = scanner.nextLine().trim();
        
        if (prescriptionId.isEmpty()) {
            System.out.println("\nPayment process cancelled. Returning to main menu...");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        Prescription prescription = findPrescriptionById(prescriptionId);
        if (prescription == null) {
            System.out.println("Prescription not found!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        if (prescription.isPaid()) {
            System.out.println("This prescription has already been paid!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        if (!prescription.getStatus().equals("active")) {
            System.out.println("Cannot process payment for non-active prescription!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println("\nPrescription Details:");
        System.out.println("Prescription ID: " + prescription.getPrescriptionId());
        System.out.println("Patient ID: " + prescription.getPatientId());
        System.out.println("Diagnosis: " + prescription.getDiagnosis());
        System.out.println("Total Cost: RM " + String.format("%.2f", prescription.getTotalCost()));

        System.out.println("\nPayment Methods:");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        System.out.println("3. Debit Card");
        System.out.println("4. Online Banking");
        System.out.println("0. Cancel Payment");
        System.out.print("Select payment method (0-4): ");
        
        int paymentMethod = getUserInputInt(0, 4);
        
        if (paymentMethod == 0) {
            System.out.println("\nPayment cancelled. Returning to main menu...");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        String paymentMethodStr = "";
        switch (paymentMethod) {
            case 1: paymentMethodStr = "Cash"; break;
            case 2: paymentMethodStr = "Credit Card"; break;
            case 3: paymentMethodStr = "Debit Card"; break;
            case 4: paymentMethodStr = "Online Banking"; break;
        }
        
        double amountReceived;
        double change;
        
        if (paymentMethod == 1) {
            System.out.println("\nCash payment selected. Please enter the amount received or 0 to cancel:");
            System.out.print("Amount: RM ");
            amountReceived = getUserInputDouble(0, prescription.getTotalCost() * 2);
            
            if (amountReceived == 0) {
                System.out.println("\nPayment cancelled. Returning to main menu...");
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                return;
            }
            
            change = amountReceived - prescription.getTotalCost();
        } else {
            System.out.println("\n" + paymentMethodStr + " payment selected. Processing payment automatically...");
            amountReceived = prescription.getTotalCost();
            change = 0.0;
        }

        prescription.setPaid(true);

        System.out.println("\n" + StringUtility.repeatString("=", 50));
        System.out.println("        PAYMENT RECEIPT");
        System.out.println(StringUtility.repeatString("=", 50));
        System.out.println("Prescription ID: " + prescription.getPrescriptionId());
        System.out.println("Patient ID: " + prescription.getPatientId());
        System.out.println("Diagnosis: " + prescription.getDiagnosis());
        System.out.println("Total Cost: RM " + String.format("%.2f", prescription.getTotalCost()));
        
        if (paymentMethod == 1) { //cash payment
            System.out.println("Amount Received: RM " + String.format("%.2f", amountReceived));
            System.out.println("Change: RM " + String.format("%.2f", change));
        } else { //other payment methods
            System.out.println("Amount Paid: RM " + String.format("%.2f", amountReceived));
            System.out.println("Change: RM 0.00");
        }
        
        System.out.println("Payment Method: " + paymentMethodStr);
        System.out.println("Payment Status: PAID");
        System.out.println("Date: " + java.time.LocalDate.now());
        System.out.println("Time: " + java.time.LocalTime.now().toString().substring(0, 8));
        System.out.println(StringUtility.repeatString("=", 50));
        
        if (paymentMethod == 1) {
            System.out.println("Thank you for your payment!");
        } else {
            System.out.println("Payment processed successfully!");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private double getUserInputDouble(double min, double max) {
        double input;
        do {
            while (!scanner.hasNextDouble()) {
                System.out.print("Invalid input! Please enter a number between " + String.format("%.2f", min) + " and " + String.format("%.2f", max) + ": ");
                scanner.next();
            }
            input = scanner.nextDouble();
            scanner.nextLine();
            
            if (input < min || input > max) {
                System.out.print("Please enter a number between " + String.format("%.2f", min) + " and " + String.format("%.2f", max) + ": ");
            }
        } while (input < min || input > max);
        
        return input;
    }
    
    public Object[] getAllPrescriptions() {
        return prescriptionList.toArray(); //adt method
    }
    
    public void addMedicine(Medicine medicine) {
        medicineList.add(medicine);
    }

    public void removeMedicine(Medicine medicine) {
        medicineList.remove(medicine);
    }

    public void createTreatment(String patientId, String doctorId, String diagnosis, String treatmentDate) {
        String treatmentId = "TRE" + String.format("%03d", treatmentIdCounter++);
        Treatment treatment = new Treatment(treatmentId, patientId, doctorId, diagnosis, treatmentDate);

        treatmentList.add(treatment); //adt method

        //link to patient and doctor
        if (patientManagement != null) {
            try {
                int patId = Integer.parseInt(patientId);
                Patient patient = patientManagement.findPatientById(patId);
                if (patient != null) {
                    patient.getTreatments().add(treatment);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing patient ID: " + patientId);
            }
        }

        if (doctorManagement != null) {
            Doctor doctor = doctorManagement.findDoctorById(doctorId);
            if (doctor != null) {
                doctor.getTreatments().add(treatment);
            }
        }

        System.out.println("[OK] Treatment created successfully with ID: " + treatmentId);
    }

    public SetAndQueueInterface<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void displayAllTreatments() {
        System.out.println("\n" + StringUtility.repeatString("=", 90));
        System.out.println("        ALL TREATMENTS");
        System.out.println(StringUtility.repeatString("=", 90));

        Object[] treatmentsArray = treatmentList.toArray(); //adt method
        if (treatmentsArray.length == 0) {
            System.out.println("No treatments found in the system.");
            return;
        }

        System.out.println(StringUtility.repeatString("-", 90));
        System.out.printf("%-15s %-15s %-15s %-30s %-15s\n", "Treatment ID", "Patient ID", "Doctor ID", "Diagnosis", "Date");
        System.out.println(StringUtility.repeatString("-", 90));

        for (Object obj : treatmentsArray) {
            Treatment treatment = (Treatment) obj;
            System.out.printf("%-15s %-15s %-15s %-30s %-15s\n",
                treatment.getTreatmentId(),
                treatment.getPatientId(),
                treatment.getDoctorId(),
                treatment.getDiagnosis().length() > 28 ? treatment.getDiagnosis().substring(0, 27) + "..." : treatment.getDiagnosis(),
                treatment.getTreatmentDate());
        }
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.println("Total Treatments: " + treatmentsArray.length);
        System.out.println(StringUtility.repeatString("=", 90));
    }

    public void updatePrescription() {
        System.out.println("\n" + StringUtility.repeatString("=", 90));
        System.out.println("        UPDATE PRESCRIPTION");
        System.out.println(StringUtility.repeatString("=", 90));

        System.out.println("CURRENT PRESCRIPTION LIST:");
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.printf("%-15s %-15s %-15s %-25s %-15s\n", "Prescription ID", "Patient ID", "Doctor ID", "Diagnosis", "Status");
        System.out.println(StringUtility.repeatString("-", 90));

        Object[] prescriptionsArray = prescriptionList.toArray(); //adt method
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            System.out.printf("%-15s %-15s %-15s %-25s %-15s\n",
                prescription.getPrescriptionId(),
                prescription.getPatientId(),
                prescription.getDoctorId(),
                prescription.getDiagnosis().length() > 23 ? prescription.getDiagnosis().substring(0, 22) + "..." : prescription.getDiagnosis(),
                prescription.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.println("Total Prescriptions: " + prescriptionsArray.length);
        System.out.println(StringUtility.repeatString("=", 90));

        System.out.print("Enter prescription ID to update (or press Enter to cancel): ");
        String prescriptionId = scanner.nextLine().trim();

        if (prescriptionId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Prescription prescription = findPrescriptionById(prescriptionId);
        if (prescription != null) {
            System.out.println("Current prescription information:");
            displayPrescriptionDetails(prescription);

            System.out.println("\nEnter new information (press Enter to keep current value):");

            //update status
            System.out.print("Status [" + prescription.getStatus() + "] (active/dispensed/cancelled): ");
            String status = scanner.nextLine().trim();
            if (!status.isEmpty()) {
                if (status.equalsIgnoreCase("active") || status.equalsIgnoreCase("dispensed") || status.equalsIgnoreCase("cancelled")) {
                    prescription.setStatus(status);
                    System.out.println("[OK] Status updated successfully!");
                } else {
                    System.out.println("[WARNING] Invalid status. Status not updated.");
                }
            }

            //update diagnosis
            System.out.print("Diagnosis [" + prescription.getDiagnosis() + "]: ");
            String diagnosis = scanner.nextLine().trim();
            if (!diagnosis.isEmpty()) {
                prescription.setDiagnosis(diagnosis);
                System.out.println("[OK] Diagnosis updated successfully!");
            }

            //update prescribed medicines
            System.out.println("\nCurrent prescribed medicines:");
            Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray(); //adt method
            if (prescribedMedicinesArray.length > 0) {
                for (int i = 0; i < prescribedMedicinesArray.length; i++) {
                    PrescribedMedicine pm = (PrescribedMedicine) prescribedMedicinesArray[i];
                    System.out.println((i + 1) + ". " + pm.getMedicineName() + " - Quantity: " + pm.getQuantity() + " - Dosage: " + pm.getDosage());
                }

                System.out.print("\nDo you want to modify prescribed medicines? (yes/no): ");
                String modifyChoice = scanner.nextLine().trim();

                if (modifyChoice.equalsIgnoreCase("yes")) {
                    System.out.println("\nOptions:");
                    System.out.println("1. Add new medicine");
                    System.out.println("2. Remove existing medicine");
                    System.out.println("3. Update medicine quantity/dosage");
                    System.out.print("Enter your choice (1-3 or press Enter to skip): ");
                    String choice = scanner.nextLine().trim();

                    if (!choice.isEmpty()) {
                        switch (choice) {
                            case "1":
                                addMedicineToPrescription(prescription);
                                break;
                            case "2":
                                removeMedicineFromPrescription(prescription);
                                break;
                            case "3":
                                updateMedicineInPrescription(prescription);
                                break;
                            default:
                                System.out.println("Invalid choice. Skipping medicine modification.");
                        }
                    }
                }
            } else {
                System.out.println("No medicines currently prescribed.");
                System.out.print("Do you want to add medicines to this prescription? (yes/no): ");
                String addChoice = scanner.nextLine().trim();
                if (addChoice.equalsIgnoreCase("yes")) {
                    addMedicineToPrescription(prescription);
                }
            }

            System.out.println("\n[OK] Prescription information updated successfully!");
            System.out.println("\nUpdated prescription information:");
            displayPrescriptionDetails(prescription);
        } else {
            System.out.println("[ERROR] Prescription not found!");
        }
    }

    private void addMedicineToPrescription(Prescription prescription) {
        System.out.println("\nAvailable medicines:");
        displayAvailableMedicines();

        System.out.print("Enter medicine ID to add (or press Enter to cancel): ");
        String medicineId = scanner.nextLine().trim();

        if (medicineId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            Integer quantity = InputValidator.getValidIntAllowCancel(scanner, 1, 100, "Enter quantity");
            if (quantity == null) {
                System.out.println("Operation cancelled.");
                return;
            }

            String dosage = InputValidator.getValidStringAllowCancel(scanner, "Enter dosage instructions (or press Enter to cancel)");
            if (dosage == null) {
                System.out.println("Operation cancelled.");
                return;
            }

            String prescribedMedicineId = "PM" + String.format("%04d", prescribedMedicineIdCounter++);
            double totalPrice = medicine.getPrice() * quantity;

            PrescribedMedicine prescribedMedicine = new PrescribedMedicine(
                prescribedMedicineId,
                prescription.getPrescriptionId(),
                medicine.getMedicineId(),
                medicine.getName(),
                quantity,
                dosage,
                "",
                medicine.getPrice(),
                totalPrice,
                false
            );

            prescription.getPrescribedMedicines().add(prescribedMedicine); //adt method
            prescription.setTotalCost(prescription.getTotalCost() + totalPrice);

            System.out.println("[OK] Medicine added to prescription successfully!");
        } else {
            System.out.println("[ERROR] Medicine not found!");
        }
    }

    private void removeMedicineFromPrescription(Prescription prescription) {
        Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray(); //adt method
        if (prescribedMedicinesArray.length == 0) {
            System.out.println("No medicines to remove.");
            return;
        }

        System.out.println("\nCurrent prescribed medicines:");
        for (int i = 0; i < prescribedMedicinesArray.length; i++) {
            PrescribedMedicine pm = (PrescribedMedicine) prescribedMedicinesArray[i];
            System.out.println((i + 1) + ". " + pm.getMedicineName() + " - Quantity: " + pm.getQuantity() + " - Total: RM" + String.format("%.2f", pm.getTotalPrice()));
        }

        Integer choice = InputValidator.getValidIntAllowCancel(scanner, 1, prescribedMedicinesArray.length, "Enter medicine number to remove");
        if (choice == null) {
            System.out.println("Operation cancelled.");
            return;
        }

        PrescribedMedicine toRemove = (PrescribedMedicine) prescribedMedicinesArray[choice - 1];
        prescription.getPrescribedMedicines().remove(toRemove); //adt method
        prescription.setTotalCost(prescription.getTotalCost() - toRemove.getTotalPrice());

        System.out.println("[OK] Medicine removed from prescription successfully!");
    }

    private void updateMedicineInPrescription(Prescription prescription) {
        Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray(); //adt method
        if (prescribedMedicinesArray.length == 0) {
            System.out.println("No medicines to update.");
            return;
        }

        System.out.println("\nCurrent prescribed medicines:");
        for (int i = 0; i < prescribedMedicinesArray.length; i++) {
            PrescribedMedicine pm = (PrescribedMedicine) prescribedMedicinesArray[i];
            System.out.println((i + 1) + ". " + pm.getMedicineName() + " - Quantity: " + pm.getQuantity() + " - Dosage: " + pm.getDosage());
        }

        Integer choice = InputValidator.getValidIntAllowCancel(scanner, 1, prescribedMedicinesArray.length, "Enter medicine number to update");
        if (choice == null) {
            System.out.println("Operation cancelled.");
            return;
        }

        PrescribedMedicine toUpdate = (PrescribedMedicine) prescribedMedicinesArray[choice - 1];

        System.out.println("\nUpdating: " + toUpdate.getMedicineName());
        System.out.print("New quantity [" + toUpdate.getQuantity() + "] (or press Enter to keep current): ");
        String quantityStr = scanner.nextLine().trim();

        if (!quantityStr.isEmpty()) {
            try {
                int newQuantity = Integer.parseInt(quantityStr);
                if (newQuantity > 0 && newQuantity <= 100) {
                    //update total cost
                    prescription.setTotalCost(prescription.getTotalCost() - toUpdate.getTotalPrice());
                    toUpdate.setQuantity(newQuantity);
                    toUpdate.setTotalPrice(toUpdate.getUnitPrice() * newQuantity);
                    prescription.setTotalCost(prescription.getTotalCost() + toUpdate.getTotalPrice());
                    System.out.println("[OK] Quantity updated successfully!");
                } else {
                    System.out.println("[WARNING] Invalid quantity. Quantity not updated.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[WARNING] Invalid quantity format. Quantity not updated.");
            }
        }

        System.out.print("New dosage [" + toUpdate.getDosage() + "] (or press Enter to keep current): ");
        String newDosage = scanner.nextLine().trim();
        if (!newDosage.isEmpty()) {
            toUpdate.setDosage(newDosage);
            System.out.println("[OK] Dosage updated successfully!");
        }
    }

    public void updateTreatment() {
        System.out.println("\n" + StringUtility.repeatString("=", 90));
        System.out.println("        UPDATE TREATMENT");
        System.out.println(StringUtility.repeatString("=", 90));

        System.out.println("CURRENT TREATMENT LIST:");
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.printf("%-15s %-20s %-20s %-25s %-15s\n", "Treatment ID", "Patient ID", "Doctor ID", "Diagnosis", "Date");
        System.out.println(StringUtility.repeatString("-", 90));

        Object[] treatmentsArray = treatmentList.toArray(); //adt method
        for (Object obj : treatmentsArray) {
            Treatment treatment = (Treatment) obj;
            System.out.printf("%-15s %-20s %-20s %-25s %-15s\n",
                treatment.getTreatmentId(),
                treatment.getPatientId(),
                treatment.getDoctorId(),
                treatment.getDiagnosis().length() > 23 ? treatment.getDiagnosis().substring(0, 22) + "..." : treatment.getDiagnosis(),
                treatment.getTreatmentDate());
        }
        System.out.println(StringUtility.repeatString("-", 90));
        System.out.println("Total Treatments: " + treatmentsArray.length);
        System.out.println(StringUtility.repeatString("=", 90));

        System.out.print("Enter treatment ID to update (or press Enter to cancel): ");
        String treatmentId = scanner.nextLine().trim();

        if (treatmentId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Treatment treatment = findTreatmentById(treatmentId);
        if (treatment != null) {
            System.out.println("Current treatment information:");
            displayTreatmentDetails(treatment);

            System.out.println("\nEnter new information (press Enter to keep current value):");

            //update diagnosis
            System.out.print("Diagnosis [" + treatment.getDiagnosis() + "]: ");
            String diagnosis = scanner.nextLine().trim();
            if (!diagnosis.isEmpty()) {
                treatment.setDiagnosis(diagnosis);
                System.out.println("[OK] Diagnosis updated successfully!");
            }

            System.out.println("\n[OK] Treatment information updated successfully!");
            System.out.println("\nUpdated treatment information:");
            displayTreatmentDetails(treatment);
        } else {
            System.out.println("[ERROR] Treatment not found!");
        }
    }

    private Treatment findTreatmentById(String treatmentId) {
        Object[] treatmentsArray = treatmentList.toArray(); //adt method
        for (Object obj : treatmentsArray) {
            Treatment treatment = (Treatment) obj;
            if (treatment.getTreatmentId().equals(treatmentId)) {
                return treatment;
            }
        }
        return null;
    }

    private void displayTreatmentDetails(Treatment treatment) {
        System.out.println("\n" + StringUtility.repeatString("-", 60));
        System.out.println("TREATMENT DETAILS");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Treatment ID: " + treatment.getTreatmentId());
        System.out.println("Patient ID: " + treatment.getPatientId());
        System.out.println("Doctor ID: " + treatment.getDoctorId());
        System.out.println("Diagnosis: " + treatment.getDiagnosis());
        System.out.println("Treatment Date: " + treatment.getTreatmentDate());
        System.out.println("Note: Prescribed medications are managed through prescriptions.");
        System.out.println(StringUtility.repeatString("-", 60));
    }
}