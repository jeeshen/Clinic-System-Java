package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Prescription;
import entity.PrescribedMedicine;
import entity.Patient;
import entity.Doctor;
import entity.Medicine;
import dao.DataInitializer;
import java.util.Scanner;
import utility.StringUtility;
import utility.InputValidator;

public class TreatmentManagement {
    private SetAndQueueInterface<Prescription> prescriptionList = new SetAndQueue<>();
    private SetAndQueueInterface<Medicine> medicineList = new SetAndQueue<>();
    private PharmacyManagement pharmacyManagement;
    private Scanner scanner;
    private int prescriptionIdCounter = 3001;
    private int prescribedMedicineIdCounter = 4001;
    
    public TreatmentManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    public void setPharmacyManagement(PharmacyManagement pharmacyManagement) {
        this.pharmacyManagement = pharmacyManagement;
    }
    
    private void loadSampleData() {
        Medicine[] sampleMedicines = DataInitializer.initializeSampleMedicines();
        for (Medicine medicine : sampleMedicines) {
            medicineList.add(medicine);
        }

        Prescription[] samplePrescriptions = DataInitializer.initializeSamplePrescriptions();
        for (Prescription prescription : samplePrescriptions) {
            prescriptionList.add(prescription);
        }
    }
    
    public void createPrescription(String consultationId, Patient currentPatient, Doctor selectedDoctor, String diagnosis, String consultationDate) {
        String prescriptionId = "PRE" + String.format("%03d", prescriptionIdCounter++);
        Prescription prescription = new Prescription(prescriptionId, consultationId, String.valueOf(currentPatient.getId()), selectedDoctor.getDoctorId(), diagnosis, new SetAndQueue<>(), consultationDate, "active", 0.0, false);
        
        //prescribe medicine
        System.out.println("\n=== PRESCRIBE MEDICINES ===");
        System.out.println("Available medicines:");
        displayAvailableMedicines();
        
        boolean addMoreMedicines = true;
        while (addMoreMedicines) {
            System.out.print("Enter medicine ID to prescribe (or 'done' to finish): ");
            String medicineId = scanner.nextLine();
            
            if (medicineId.equalsIgnoreCase("done")) {
                addMoreMedicines = false;
                break;
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
                
                prescription.getPrescribedMedicines().add(prescribedMedicine);
                prescription.setTotalCost(prescription.getTotalCost() + totalPrice);
                
                System.out.println("Medicine " + medicine.getName() + " prescribed successfully!");
            } else {
                System.out.println("Medicine not found!");
            }
        }
        
        prescriptionList.add(prescription);
        System.out.println("Prescription ID: " + prescriptionId);
        System.out.println("Total cost: RM " + String.format("%.2f", prescription.getTotalCost()));
    }
    
    public void displayAllPrescriptionsSorted() {
        System.out.println("\n" + StringUtility.repeatString("-", 100));
        System.out.println("ALL PRESCRIPTIONS (SORTED BY ID)");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.printf("%-15s %-10s %-10s %-20s %-12s %-10s\n", "Prescription ID", "Patient ID", "Doctor ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 100));
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        SetAndQueueInterface<Prescription> tempList = new SetAndQueue<>();
        for (Object obj : prescriptionsArray) {
            tempList.add((Prescription) obj);
        }
        tempList.sort();
        
        Object[] sortedPrescriptionsArray = tempList.toArray();
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
        System.out.print("Enter prescription ID to search: ");
        String prescriptionId = scanner.nextLine();
        
        Prescription foundPrescription = findPrescriptionById(prescriptionId);
        if (foundPrescription != null) {
            displayPrescriptionDetails(foundPrescription);
        } else {
            System.out.println("Prescription not found!");
        }
    }
    
    public void searchPrescriptionsByPatient() {
        System.out.print("Enter patient ID to search prescriptions: ");
        String patientId = scanner.nextLine();
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        String[] headers = {"Prescription ID", "Doctor ID", "Diagnosis", "Total Cost", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetAndQueue<>();
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
        System.out.print("Enter doctor ID to search prescriptions: ");
        String doctorId = scanner.nextLine();
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        String[] headers = {"Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status"};
        SetAndQueueInterface<Object[]> rowList = new SetAndQueue<>();
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
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        REMOVE PRESCRIPTION");
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.println("📋 CURRENT PRESCRIPTION LIST:");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.printf("%-15s %-10s %-10s %-20s %-12s %-10s\n", "Prescription ID", "Patient ID", "Doctor ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            System.out.printf("%-15s %-10s %-10s %-20s %-12s %-10s\n", 
                prescription.getPrescriptionId(), 
                prescription.getPatientId(), 
                prescription.getDoctorId(), 
                prescription.getDiagnosis(), 
                "RM " + String.format("%.2f", prescription.getTotalCost()), 
                prescription.getStatus());
        }
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Total Prescriptions: " + prescriptionsArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter prescription ID to remove: ");
        String prescriptionId = scanner.nextLine();
        
        Prescription prescription = findPrescriptionById(prescriptionId);
        if (prescription != null) {
            System.out.println("Prescription to be removed:");
            displayPrescriptionDetails(prescription);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this prescription? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removed = prescriptionList.remove(prescription);
                if (removed) {
                    System.out.println("✅ Prescription removed successfully!");
                } else {
                    System.out.println("❌ Failed to remove prescription from system!");
                }
            } else {
                System.out.println("❌ Prescription removal cancelled.");
            }
        } else {
            System.out.println("❌ Prescription not found!");
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
    
    public void generateTreatmentStatisticsReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("        TREATMENT STATISTICS REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 80));

        Object[] prescriptionsArray = prescriptionList.toArray();
        int totalPrescriptions = prescriptionsArray.length;
        int paidCount = 0;

        SetAndQueueInterface<String> uniqueDiagnoses = new SetAndQueue<>();
        int maxDiagnosis = 0;
        
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.isPaid()) paidCount++;
            String diagnosis = prescription.getDiagnosis();
            uniqueDiagnoses.add(diagnosis);
        }

        Object[] diagnosesArray = uniqueDiagnoses.toArray();
        int[] diagnosisCounts = new int[diagnosesArray.length];
        
        for (int i = 0; i < diagnosesArray.length; i++) {
            String diagnosis = (String) diagnosesArray[i];
            int count = countDiagnosisOccurrences(diagnosis, prescriptionsArray);
            diagnosisCounts[i] = count;
            if (count > maxDiagnosis) {
                maxDiagnosis = count;
            }
        }

        String[] headers = {"Prescription ID", "Patient ID", "Diagnosis", "Date", "Paid"};
        Object[][] rows = new Object[prescriptionsArray.length][headers.length];
        for (int i = 0; i < prescriptionsArray.length; i++) {
            Prescription p = (Prescription) prescriptionsArray[i];
            rows[i][0] = p.getPrescriptionId();
            rows[i][1] = p.getPatientId();
            rows[i][2] = p.getDiagnosis();
            rows[i][3] = p.getPrescriptionDate();
            rows[i][4] = p.isPaid() ? "Yes" : "No";
        }
        System.out.println("\nPRESCRIPTION LIST:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nDIAGNOSIS COUNTS:");
        int barWidth = 30;
        for (int i = 0; i < diagnosesArray.length; i++) {
            String diag = (String) diagnosesArray[i];
            int count = diagnosisCounts[i];
            System.out.printf("%-30s [%s] %d cases\n", diag, StringUtility.greenBarChart(count, maxDiagnosis, barWidth), count);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Prescriptions: " + totalPrescriptions);
        System.out.println("• Paid Prescriptions: " + paidCount);
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private int countDiagnosisOccurrences(String diagnosis, Object[] prescriptionsArray) {
        int count = 0;
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.getDiagnosis().equals(diagnosis)) {
                count++;
            }
        }
        return count;
    }
    
    public void generateDiagnosisAnalysisReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        DIAGNOSIS ANALYSIS REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 60));

        Object[] prescriptionsArray = prescriptionList.toArray();
        SetAndQueueInterface<String> uniqueDiagnoses = new SetAndQueue<>();
        int maxDiagnosis = 0;
        
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            String diagnosis = prescription.getDiagnosis();
            uniqueDiagnoses.add(diagnosis);
        }

        Object[] diagnosesArray = uniqueDiagnoses.toArray();
        int[] diagnosisCounts = new int[diagnosesArray.length];
        
        for (int i = 0; i < diagnosesArray.length; i++) {
            String diagnosis = (String) diagnosesArray[i];
            int count = countDiagnosisOccurrences(diagnosis, prescriptionsArray);
            diagnosisCounts[i] = count;
            if (count > maxDiagnosis) {
                maxDiagnosis = count;
            }
        }

        String[] headers = {"Diagnosis", "Cases"};
        Object[][] rows = new Object[diagnosesArray.length][headers.length];
        
        for (int i = 0; i < diagnosesArray.length; i++) {
            String diag = (String) diagnosesArray[i];
            rows[i][0] = diag;
            rows[i][1] = diagnosisCounts[i];
        }
        
        System.out.println("\nDIAGNOSIS ANALYSIS:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nDIAGNOSIS DISTRIBUTION:");
        int barWidth = 30;
        for (int i = 0; i < diagnosesArray.length; i++) {
            String diag = (String) diagnosesArray[i];
            int count = diagnosisCounts[i];
            System.out.printf("%-30s [%s] %d cases\n", diag, StringUtility.greenBarChart(count, maxDiagnosis, barWidth), count);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Diagnoses: " + diagnosesArray.length);
        System.out.println("• Most Common Diagnosis: " + getMostCommonDiagnosis(diagnosesArray, diagnosisCounts));
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private String getMostCommonDiagnosis(Object[] diagnosesArray, int[] diagnosisCounts) {
        int maxIndex = 0;
        for (int i = 1; i < diagnosisCounts.length; i++) {
            if (diagnosisCounts[i] > diagnosisCounts[maxIndex]) {
                maxIndex = i;
            }
        }
        return (String) diagnosesArray[maxIndex];
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
        dummy.setPrescriptionId(prescriptionId);
        return prescriptionList.search(dummy);
    }
    
    public Medicine findMedicineById(String medicineId) {
        Object[] medicinesArray = medicineList.toArray();
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            if (medicine.getMedicineId().equals(medicineId)) {
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
        return prescriptionList.size();
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

        System.out.print("Enter prescription ID to process payment: ");
        String prescriptionId = scanner.nextLine();
        
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
        System.out.print("Select payment method (1-4): ");
        
        int paymentMethod = getUserInputInt(1, 4);
        String paymentMethodStr = "";
        switch (paymentMethod) {
            case 1: paymentMethodStr = "Cash"; break;
            case 2: paymentMethodStr = "Credit Card"; break;
            case 3: paymentMethodStr = "Debit Card"; break;
            case 4: paymentMethodStr = "Online Banking"; break;
        }

        System.out.print("Enter amount received: RM ");
        double amountReceived = getUserInputDouble(prescription.getTotalCost(), prescription.getTotalCost() * 2);

        double change = amountReceived - prescription.getTotalCost();

        prescription.setPaid(true);

        System.out.println("\n" + StringUtility.repeatString("=", 50));
        System.out.println("        PAYMENT RECEIPT");
        System.out.println(StringUtility.repeatString("=", 50));
        System.out.println("Prescription ID: " + prescription.getPrescriptionId());
        System.out.println("Patient ID: " + prescription.getPatientId());
        System.out.println("Diagnosis: " + prescription.getDiagnosis());
        System.out.println("Total Cost: RM " + String.format("%.2f", prescription.getTotalCost()));
        System.out.println("Amount Received: RM " + String.format("%.2f", amountReceived));
        System.out.println("Change: RM " + String.format("%.2f", change));
        System.out.println("Payment Method: " + paymentMethodStr);
        System.out.println("Payment Status: PAID");
        System.out.println("Date: " + java.time.LocalDate.now());
        System.out.println("Time: " + java.time.LocalTime.now().toString().substring(0, 8));
        System.out.println(StringUtility.repeatString("=", 50));
        System.out.println("Thank you for your payment!");
        
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

    public void dispenseMedicines() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        MEDICINE DISPENSING");
        System.out.println(StringUtility.repeatString("=", 60));
        
        //display prescriptions with undispensed medicines
        System.out.println("Prescriptions with Undispensed Medicines:");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s %-10s\n", "Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        boolean hasUndispensed = false;
        
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.getStatus().equals("active") && hasUndispensedMedicines(prescription)) {
                System.out.printf("%-15s %-10s %-20s %-15s %-10s\n", 
                    prescription.getPrescriptionId(), prescription.getPatientId(),
                    prescription.getDiagnosis(), "RM " + String.format("%.2f", prescription.getTotalCost()),
                    prescription.getStatus());
                hasUndispensed = true;
            }
        }
        
        if (!hasUndispensed) {
            System.out.println("No prescriptions with undispensed medicines found.");
            System.out.println(StringUtility.repeatString("-", 80));
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        System.out.println(StringUtility.repeatString("-", 80));
        
        //get prescription ID for dispensing
        System.out.print("Enter prescription ID to dispense medicines: ");
        String prescriptionId = scanner.nextLine();
        
        Prescription prescription = findPrescriptionById(prescriptionId);
        if (prescription == null) {
            System.out.println("Prescription not found!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        if (!prescription.getStatus().equals("active")) {
            System.out.println("Cannot dispense medicines for non-active prescription!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        if (!prescription.isPaid()) {
            System.out.println("Cannot dispense medicines for unpaid prescription!");
            System.out.println("Please process payment first.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Display prescription details and medicines
        System.out.println("\nPrescription Details:");
        System.out.println("Prescription ID: " + prescription.getPrescriptionId());
        System.out.println("Patient ID: " + prescription.getPatientId());
        System.out.println("Diagnosis: " + prescription.getDiagnosis());
        System.out.println("Date: " + prescription.getPrescriptionDate());
        
        System.out.println("\nPrescribed Medicines:");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.printf("%-20s %-10s %-20s %-10s %-10s %-10s\n", "Medicine", "Quantity", "Dosage", "Instructions", "Price", "Dispensed");
        System.out.println(StringUtility.repeatString("-", 100));
        
        Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
        boolean allDispensed = true;
        
        for (Object obj : prescribedMedicinesArray) {
            PrescribedMedicine pm = (PrescribedMedicine) obj;
            System.out.printf("%-20s %-10s %-20s %-10s %-10s %-10s\n", 
                pm.getMedicineName(), pm.getQuantity(), pm.getDosage(),
                pm.getInstructions(), "RM " + String.format("%.2f", pm.getUnitPrice()),
                pm.isDispensed() ? "Yes" : "No");
            
            if (!pm.isDispensed()) {
                allDispensed = false;
            }
        }
        
        if (allDispensed) {
            System.out.println("\nAll medicines in this prescription have been dispensed!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        System.out.println(StringUtility.repeatString("-", 100));
        
        //dispense medicines
        System.out.println("\nDispensing Options:");
        System.out.println("1. Dispense all undispensed medicines");
        System.out.println("2. Dispense specific medicine");
        System.out.print("Select option (1-2): ");
        
        int option = getUserInputInt(1, 2);
        
        if (option == 1) {
            //dispense all undispensed medicines
            boolean success = true;
            for (Object obj : prescribedMedicinesArray) {
                PrescribedMedicine pm = (PrescribedMedicine) obj;
                if (!pm.isDispensed()) {
                    Medicine medicine = findMedicineById(pm.getMedicineId());
                    if (medicine != null) {
                        if (medicine.getStockQuantity() >= pm.getQuantity()) {
                            // Update stock in TreatmentManagement
                            medicine.setStockQuantity(medicine.getStockQuantity() - pm.getQuantity());
                            
                            //also update stock in PharmacyManagement if available
                            if (pharmacyManagement != null) {
                                Medicine pharmacyMedicine = pharmacyManagement.findMedicineById(pm.getMedicineId());
                                if (pharmacyMedicine != null) {
                                    pharmacyMedicine.setStockQuantity(pharmacyMedicine.getStockQuantity() - pm.getQuantity());
                                }
                            }
                            
                            //mark as dispensed
                            pm.setDispensed(true);
                        } else {
                            System.out.println("Insufficient stock for " + pm.getMedicineName() + 
                                             " (Required: " + pm.getQuantity() + ", Available: " + medicine.getStockQuantity() + ")");
                            success = false;
                        }
                    } else {
                        System.out.println("Medicine " + pm.getMedicineName() + " not found in inventory!");
                        success = false;
                    }
                }
            }
            
            if (success) {
                System.out.println("\n✅ All medicines dispensed successfully!");
                System.out.println("Stock levels have been updated.");
            } else {
                System.out.println("\n❌ Some medicines could not be dispensed due to insufficient stock.");
            }
        } else {
            //dispense specific medicine
            System.out.print("Enter medicine name to dispense: ");
            String medicineName = scanner.nextLine();
            
            boolean found = false;
            for (Object obj : prescribedMedicinesArray) {
                PrescribedMedicine pm = (PrescribedMedicine) obj;
                if (pm.getMedicineName().equalsIgnoreCase(medicineName) && !pm.isDispensed()) {
                    found = true;
                    Medicine medicine = findMedicineById(pm.getMedicineId());
                    if (medicine != null) {
                        if (medicine.getStockQuantity() >= pm.getQuantity()) {
                            //update stock in TreatmentManagement
                            medicine.setStockQuantity(medicine.getStockQuantity() - pm.getQuantity());
                            
                            //also update stock in PharmacyManagement if available
                            if (pharmacyManagement != null) {
                                Medicine pharmacyMedicine = pharmacyManagement.findMedicineById(pm.getMedicineId());
                                if (pharmacyMedicine != null) {
                                    pharmacyMedicine.setStockQuantity(pharmacyMedicine.getStockQuantity() - pm.getQuantity());
                                }
                            }
                            
                            //mark as dispensed
                            pm.setDispensed(true);
                            System.out.println("\n✅ " + pm.getMedicineName() + " dispensed successfully!");
                            System.out.println("Quantity dispensed: " + pm.getQuantity());
                            System.out.println("Remaining stock: " + medicine.getStockQuantity());
                        } else {
                            System.out.println("❌ Insufficient stock for " + pm.getMedicineName() + 
                                             " (Required: " + pm.getQuantity() + ", Available: " + medicine.getStockQuantity() + ")");
                        }
                    } else {
                        System.out.println("❌ Medicine " + pm.getMedicineName() + " not found in inventory!");
                    }
                    break;
                }
            }
            
            if (!found) {
                System.out.println("❌ Medicine not found or already dispensed!");
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private boolean hasUndispensedMedicines(Prescription prescription) {
        Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
        for (Object obj : prescribedMedicinesArray) {
            PrescribedMedicine pm = (PrescribedMedicine) obj;
            if (!pm.isDispensed()) {
                return true;
            }
        }
        return false;
    }
    
    public Object[] getAllPrescriptions() {
        return prescriptionList.toArray();
    }
    
    public void generateDispensingStatisticsReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        DISPENSING STATISTICS REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        
        Object[] prescriptionsArray = prescriptionList.toArray();
        int totalPrescriptions = prescriptionsArray.length;
        int totalMedicines = 0;
        int dispensedMedicines = 0;
        double totalRevenue = 0.0;
        
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
            
            for (Object pmObj : prescribedMedicinesArray) {
                PrescribedMedicine pm = (PrescribedMedicine) pmObj;
                totalMedicines++;
                if (pm.isDispensed()) {
                    dispensedMedicines++;
                    if (prescription.isPaid()) {
                        totalRevenue += pm.getTotalPrice();
                    }
                }
            }
        }
        
        System.out.println("📊 Dispensing Statistics:");
        System.out.println("• Total Prescriptions: " + totalPrescriptions);
        System.out.println("• Total Medicines Prescribed: " + totalMedicines);
        System.out.println("• Prescriptions Dispensed: " + dispensedMedicines);
        System.out.println("• Dispensing Rate: " + String.format("%.1f", (double)dispensedMedicines/totalMedicines*100) + "%");
        System.out.println("• Total Revenue from Dispensed Medicines: RM " + String.format("%.2f", totalRevenue));
        
        if (totalMedicines > 0) {
            System.out.println("• Average Medicines per Prescription: " + String.format("%.1f", (double)totalMedicines/totalPrescriptions));
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BG = "\u001B[44m";
    
    private String createColoredBar(String color, int barCount, int totalWidth) {
        return color + StringUtility.repeatString(" ", barCount) + RESET + StringUtility.repeatString(" ", totalWidth - barCount);
    }
} 