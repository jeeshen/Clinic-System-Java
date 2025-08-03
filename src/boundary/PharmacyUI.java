package boundary;

import control.PharmacyManagement;
import control.TreatmentManagement;
import entity.Prescription;
import entity.PrescribedMedicine;
import entity.Medicine;
import java.util.Scanner;

public class PharmacyUI {
    private PharmacyManagement pharmacyManagement;
    private TreatmentManagement treatmentManagement;
    private Scanner scanner;
    
    public PharmacyUI(PharmacyManagement pharmacyManagement) {
        this.pharmacyManagement = pharmacyManagement;
        this.scanner = new Scanner(System.in);
    }
    
    public void setDependencies(TreatmentManagement treatmentManagement) {
        this.treatmentManagement = treatmentManagement;
    }
    
    public void managePharmacyOperations() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + repeatString("=", 50));
            System.out.println("        PHARMACY OPERATIONS MANAGEMENT");
            System.out.println(repeatString("=", 50));
            System.out.println("1 . View All Medicines (Sorted by ID)");
            System.out.println("2 . Search Medicine by ID");
            System.out.println("3 . Search Medicine by Name");
            System.out.println("4 . Update Medicine Stock");
            System.out.println("5 . Dispense Medicines");
            System.out.println("6 . Medicine Stock Report");
            System.out.println("7 . Process Payment");
            System.out.println("0 . Back to Main Menu");
            System.out.println(repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 7);
            
            switch (choice) {
                case 1:
                    pharmacyManagement.displayAllMedicinesSorted();
                    break;
                case 2:
                    pharmacyManagement.searchMedicineById();
                    break;
                case 3:
                    pharmacyManagement.searchMedicineByName();
                    break;
                case 4:
                    pharmacyManagement.updateMedicineStock();
                    break;
                case 5:
                    dispenseMedicines();
                    break;
                case 6:
                    pharmacyManagement.generateMedicineStockReport();
                    break;
                case 7:
                    processPayment();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    public void generatePharmacyReports() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + repeatString("=", 50));
            System.out.println("        PHARMACY REPORTS & INVENTORY");
            System.out.println(repeatString("=", 50));
            System.out.println("1 . Medicine Stock Report");
            System.out.println("2 . Medicine Category Report");
            System.out.println("3 . Medicine Price Analysis");
            System.out.println("4 . Dispensing Statistics Report");
            System.out.println("5 . Inventory Value Report");
            System.out.println("6 . Comprehensive Pharmacy Report");
            System.out.println("0 . Back to Main Menu");
            System.out.println(repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 6);
            
            switch (choice) {
                case 1:
                    pharmacyManagement.generateMedicineStockReport();
                    break;
                case 2:
                    pharmacyManagement.generateMedicineCategoryReport();
                    break;
                case 3:
                    pharmacyManagement.generateMedicinePriceAnalysis();
                    break;
                case 4:
                    generateDispensingStatisticsReport();
                    break;
                case 5:
                    generateInventoryValueReport();
                    break;
                case 6:
                    pharmacyManagement.generateComprehensivePharmacyReport();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    public void processPayment() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }
        
        treatmentManagement.processPayment();
    }
    
    private void dispenseMedicines() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }
        
        treatmentManagement.dispenseMedicines();
    }
    
    private void generateDispensingStatisticsReport() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }
        
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        DISPENSING STATISTICS REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
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
                }
                if (prescription.isPaid()) {
                    totalRevenue += pm.getTotalPrice();
                }
            }
        }
        
        System.out.println("📊 Dispensing Statistics:");
        System.out.println("• Total Prescriptions: " + totalPrescriptions);
        System.out.println("• Total Medicines Prescribed: " + totalMedicines);
        System.out.println("• Medicines Dispensed: " + dispensedMedicines);
        System.out.println("• Medicines Pending: " + (totalMedicines - dispensedMedicines));
        System.out.println("• Dispensing Rate: " + String.format("%.1f", (double)dispensedMedicines/totalMedicines*100) + "%");
        System.out.println("• Total Revenue from Dispensed Medicines: RM " + String.format("%.2f", totalRevenue));
        
        if (totalMedicines > 0) {
            System.out.println("• Average Medicines per Prescription: " + String.format("%.1f", (double)totalMedicines/totalPrescriptions));
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void generateInventoryValueReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        INVENTORY VALUE REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] medicinesArray = pharmacyManagement.getAllMedicines();
        double totalInventoryValue = 0.0;
        int totalItems = 0;
        int lowStockItems = 0;
        
        System.out.println("📊 Inventory Value Analysis:");
        System.out.println(repeatString("-", 80));
        System.out.printf("%-20s %-10s %-10s %-15s %-15s\n", "Medicine", "Stock", "Unit Price", "Total Value", "Status");
        System.out.println(repeatString("-", 80));
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            double itemValue = medicine.getStockQuantity() * medicine.getPrice();
            totalInventoryValue += itemValue;
            totalItems += medicine.getStockQuantity();
            
            String status = medicine.getStockQuantity() <= 10 ? "LOW STOCK" : "OK";
            if (medicine.getStockQuantity() <= 10) {
                lowStockItems++;
            }
            
            System.out.printf("%-20s %-10s %-10s %-15s %-15s\n", 
                medicine.getName(), medicine.getStockQuantity(), 
                "RM " + String.format("%.2f", medicine.getPrice()),
                "RM " + String.format("%.2f", itemValue), status);
        }
        
        System.out.println(repeatString("-", 80));
        System.out.println("\n📈 Summary:");
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalInventoryValue));
        System.out.println("• Total Items in Stock: " + totalItems);
        System.out.println("• Low Stock Items (≤10): " + lowStockItems);
        System.out.println("• Average Item Value: RM " + String.format("%.2f", totalInventoryValue/totalItems));
        
        if (lowStockItems > 0) {
            System.out.println("\n⚠️  WARNING: " + lowStockItems + " items are running low on stock!");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
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
