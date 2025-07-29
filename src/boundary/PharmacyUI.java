package boundary;

import entity.Medicine;
import control.PharmacyManagement;
import utility.StringUtility;
import utility.InputValidator;
import java.util.Scanner;

public class PharmacyUI {
    private Scanner scanner;
    private PharmacyManagement pharmacyManagement;
    
    public PharmacyUI(PharmacyManagement pharmacyManagement) {
        this.scanner = new Scanner(System.in);
        this.pharmacyManagement = pharmacyManagement;
    }
    
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== PHARMACY MANAGEMENT SYSTEM ===");
            System.out.println("1  . Dispense Medicine");
            System.out.println("2  . Update Medicine Stock");
            System.out.println("3  . Check Medicine Stock");
            System.out.println("4  . Add New Medicine");
            System.out.println("5  . Remove Medicine");
            System.out.println("6  . List All Medicines");
            System.out.println("7  . Search Medicine");
            System.out.println("8  . Generate Stock Report");
            System.out.println("9  . Recommend Alternative Medicine");
            System.out.println("10 . Update Medicine Price");
            System.out.println("11 . View Low Stock Medicines");
            System.out.println("12 . View Expiring Medicines");
            System.out.println("13 . View Total Inventory Value");
            System.out.println("0  . Return to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = InputValidator.getValidInt(scanner, 0, 13, "");
            
            switch (choice) {
                case 1:
                    dispenseMedicineMenu();
                    break;
                case 2:
                    updateStockMenu();
                    break;
                case 3:
                    checkStockMenu();
                    break;
                case 4:
                    addMedicineMenu();
                    break;
                case 5:
                    removeMedicineMenu();
                    break;
                case 6:
                    listMedicinesMenu();
                    break;
                case 7:
                    searchMedicineMenu();
                    break;
                case 8:
                    generateReportMenu();
                    break;
                case 9:
                    recommendAlternativeMenu();
                    break;
                case 10:
                    updatePriceMenu();
                    break;
                case 11:
                    viewLowStockMenu();
                    break;
                case 12:
                    viewExpiringMenu();
                    break;
                case 13:
                    viewInventoryValueMenu();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void dispenseMedicineMenu() {
        System.out.println("\n=== DISPENSE MEDICINE ===");
        
        String patientId = InputValidator.getValidString(scanner, "Enter Patient ID: ");
        String consultationId = InputValidator.getValidString(scanner, "Enter Consultation ID: ");
        
        //show available medicine IDs
        showAvailableMedicineIds();
        
        String medicineId = getMedicineId("Enter Medicine ID: ");
        
        int dispenseQuantity = InputValidator.getValidQuantity(scanner, "Enter Dispense Quantity: ");

        String[] medicineList = {medicineId};
        boolean success = pharmacyManagement.dispenseMedicine(patientId, consultationId, medicineList, dispenseQuantity);
        
        if (success) {
            System.out.println("Medicine dispensed successfully!");
        } else {
            System.out.println("Failed to dispense medicine. Please check stock availability.");
        }
    }
    
    private void updateStockMenu() {
        System.out.println("\n=== UPDATE MEDICINE STOCK ===");
        
        //show available medicine IDs
        showAvailableMedicineIds();
        
        String medicineId = getMedicineId("Enter Medicine ID: ");
        int newStock = InputValidator.getValidInt(scanner, 0, 10000, "Enter new stock quantity: ");
        
        boolean success = pharmacyManagement.updateMedicineStock(medicineId, newStock);
        
        if (success) {
            System.out.println("Medicine stock updated successfully!");
        } else {
            System.out.println("Failed to update medicine stock. Medicine not found.");
        }
    }
    
    private void checkStockMenu() {
        System.out.println("\n=== CHECK MEDICINE STOCK ===");
        
        //show available medicine IDs
        showAvailableMedicineIds();
        
        String medicineId = getMedicineId("Enter Medicine ID: ");
        
        int stock = pharmacyManagement.checkMedicineStock(medicineId);
        
        if (stock >= 0) {
            System.out.println("Current stock for Medicine ID " + medicineId + ": " + stock + " units");
        } else {
            System.out.println("Medicine not found.");
        }
    }
    
    private void addMedicineMenu() {
        System.out.println("\n=== ADD NEW MEDICINE ===");
        
        Medicine medicine = new Medicine();
        
        //get medicine ID with retry for duplicates
        String medicineId;
        do {
            medicineId = InputValidator.getValidString(scanner, "Enter Medicine ID: ");
            
            //check if medicine ID already exists
            if (pharmacyManagement.medicineIdExists(medicineId)) {
                System.out.println("Medicine ID '" + medicineId + "' already exists. Please enter a different ID.");
                continue; //ask for input again
            }
            
            medicine.setMedicineId(medicineId);
            break; //exit loop and proceed
        } while (true);
        
        String name = InputValidator.getValidString(scanner, "Enter Medicine Name: ");
        medicine.setName(name);
        
        String brand = InputValidator.getValidString(scanner, "Enter Brand: ");
        medicine.setBrand(brand);
        
        int stock = InputValidator.getValidInt(scanner, 0, 10000, "Enter Stock Quantity: ");
        medicine.setStockQuantity(stock);
        
        double price = InputValidator.getValidPrice(scanner, "Enter Price (RM): ");
        medicine.setPrice(price);
        
        String purpose = InputValidator.getValidString(scanner, "Enter Purpose: ");
        medicine.setPurpose(purpose);
        
        String expiryDate = InputValidator.getValidDate(scanner, "Enter Expiry Date");
        medicine.setExpiryDate(expiryDate);
        
        boolean success = pharmacyManagement.addNewMedicine(medicine);
        
        if (success) {
            System.out.println("Medicine added successfully!");
        } else {
            System.out.println("Failed to add medicine. Medicine ID may already exist.");
        }
    }
    
    private void removeMedicineMenu() {
        System.out.println("\n=== REMOVE MEDICINE ===");
        
        //show available medicine IDs
        showAvailableMedicineIds();
        
        String medicineId = getMedicineId("Enter Medicine ID: ");
        
        boolean success = pharmacyManagement.removeMedicine(medicineId);
        
        if (success) {
            System.out.println("Medicine removed successfully!");
        } else {
            System.out.println("Failed to remove medicine. Medicine not found.");
        }
    }
    
    private void listMedicinesMenu() {
        System.out.println("\n=== LIST ALL MEDICINES ===");
        
        Medicine[] medicines = pharmacyManagement.listAllMedicines();
        
        if (medicines.length == 0) {
            System.out.println("No medicines found.");
        } else {
            System.out.println("ID\t\tName\t\t\tBrand\t\tStock\tPrice\t\tPurpose");
            System.out.println(StringUtility.repeatString("-", 80));
            for (Medicine medicine : medicines) {
                System.out.printf("%-10s\t%-20s\t%-15s\t%-5d\tRM%.2f\t\t%s\n",
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getBrand(),
                    medicine.getStockQuantity(),
                    medicine.getPrice(),
                    medicine.getPurpose());
            }
        }
    }
    
    private void searchMedicineMenu() {
        System.out.println("\n=== SEARCH MEDICINE ===");
        String searchTerm = InputValidator.getValidString(scanner, "Enter search term (name or ID): ");
        
        //convert search term to uppercase for case-insensitive search
        searchTerm = searchTerm.toUpperCase();
        
        Medicine[] results = pharmacyManagement.searchMedicine(searchTerm);
        
        if (results.length == 0) {
            System.out.println("No medicines found.");
        } else {
            System.out.println("Search Results:");
            System.out.println("ID\t\tName\t\t\tBrand\t\tStock\tPrice\t\tPurpose");
            System.out.println(StringUtility.repeatString("-", 80));
            for (Medicine medicine : results) {
                System.out.printf("%-10s\t%-20s\t%-15s\t%-5d\tRM%.2f\t\t%s\n",
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getBrand(),
                    medicine.getStockQuantity(),
                    medicine.getPrice(),
                    medicine.getPurpose());
            }
        }
    }
    
    private void generateReportMenu() {
        System.out.println("\n=== GENERATE STOCK REPORT ===");
        
        String report = pharmacyManagement.generateStockReport();
        System.out.println(report);
    }
    
    private void recommendAlternativeMenu() {
        System.out.println("\n=== RECOMMEND ALTERNATIVE MEDICINE ===");
        
        //show available medicine IDs
        showAvailableMedicineIds();
        
        String medicineId = getMedicineId("Enter Medicine ID: ");
        
        Medicine[] alternatives = pharmacyManagement.recommendAlternativeMedicine(medicineId);
        
        if (alternatives.length == 0) {
            System.out.println("No alternative medicines found.");
        } else {
            System.out.println("Alternative Medicines:");
            System.out.println("ID\t\tName\t\t\tBrand\t\tStock\tPrice\t\tPurpose");
            System.out.println(StringUtility.repeatString("-", 80));
            for (Medicine medicine : alternatives) {
                System.out.printf("%-10s\t%-20s\t%-15s\t%-5d\tRM%.2f\t\t%s\n",
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getBrand(),
                    medicine.getStockQuantity(),
                    medicine.getPrice(),
                    medicine.getPurpose());
            }
        }
    }
    
    private void updatePriceMenu() {
        System.out.println("\n=== UPDATE MEDICINE PRICE ===");
        
        //show available medicine IDs
        showAvailableMedicineIds();
        
        String medicineId = getMedicineId("Enter Medicine ID: ");
        double newPrice = InputValidator.getValidPrice(scanner, "Enter new price (RM): ");
        
        boolean success = pharmacyManagement.updateMedicinePrice(medicineId, newPrice);
        
        if (success) {
            System.out.println("Medicine price updated successfully!");
        } else {
            System.out.println("Failed to update medicine price. Medicine not found.");
        }
    }
    
    private void viewLowStockMenu() {
        System.out.println("\n=== VIEW LOW STOCK MEDICINES ===");
        
        Medicine[] lowStockMedicines = pharmacyManagement.getLowStockMedicines();
        
        if (lowStockMedicines.length == 0) {
            System.out.println("No low stock medicines found.");
        } else {
            System.out.println("Low Stock Medicines (< 10 units):");
            System.out.println("ID\t\tName\t\t\tBrand\t\tStock\tPrice\t\tPurpose");
            System.out.println(StringUtility.repeatString("-", 80));
            for (Medicine medicine : lowStockMedicines) {
                System.out.printf("%-10s\t%-20s\t%-15s\t%-5d\tRM%.2f\t\t%s\n",
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getBrand(),
                    medicine.getStockQuantity(),
                    medicine.getPrice(),
                    medicine.getPurpose());
            }
        }
    }
    
    private void viewExpiringMenu() {
        System.out.println("\n=== VIEW EXPIRING MEDICINES ===");
        
        Medicine[] expiringMedicines = pharmacyManagement.getExpiringMedicines();
        
        if (expiringMedicines.length == 0) {
            System.out.println("No expiring medicines found.");
        } else {
            System.out.println("Expiring Medicines:");
            System.out.println("ID\t\tName\t\t\tBrand\t\tStock\tPrice\t\tExpiry Date");
            System.out.println(StringUtility.repeatString("-", 80));
            for (Medicine medicine : expiringMedicines) {
                System.out.printf("%-10s\t%-20s\t%-15s\t%-5d\tRM%.2f\t\t%s\n",
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getBrand(),
                    medicine.getStockQuantity(),
                    medicine.getPrice(),
                    medicine.getExpiryDate());
            }
        }
    }
    
    private void viewInventoryValueMenu() {
        System.out.println("\n=== VIEW TOTAL INVENTORY VALUE ===");
        
        double totalValue = pharmacyManagement.getTotalInventoryValue();
        System.out.printf("Total Inventory Value: RM%.2f\n", totalValue);
    }

    //helper method to get case-insensitive medicine ID input
    private String getMedicineId(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        //convert to uppercase to match the stored format
        return input.toUpperCase();
    }
    
    //helper method to display available medicine IDs
    private void showAvailableMedicineIds() {
        Medicine[] medicines = pharmacyManagement.listAllMedicines();
        
        if (medicines.length == 0) {
            System.out.println("No medicines available in the system.");
            return;
        }
        
        System.out.println("Available Medicine IDs:");
        System.out.println("ID\t\tName\t\t\tBrand\t\tStock\tPrice\t\tPurpose");
        System.out.println(StringUtility.repeatString("-", 80));
        
        for (Medicine medicine : medicines) {
            System.out.printf("%-10s\t%-20s\t%-15s\t%-5d\tRM%.2f\t\t%s\n",
                medicine.getMedicineId(),
                medicine.getName(),
                medicine.getBrand(),
                medicine.getStockQuantity(),
                medicine.getPrice(),
                medicine.getPurpose());
        }
        System.out.println();
    }
}
