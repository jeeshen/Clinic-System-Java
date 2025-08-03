package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Medicine;
import dao.DataInitializer;
import java.util.Scanner;

public class PharmacyManagement {
    private SetAndQueueInterface<Medicine> medicineList = new SetAndQueue<>();
    private Scanner scanner;
    
    public PharmacyManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }
    
    private void loadSampleData() {
        Medicine[] sampleMedicines = DataInitializer.initializeSampleMedicines();
        for (Medicine medicine : sampleMedicines) {
            medicineList.add(medicine);
        }
    }
    
    public void displayAllMedicinesSorted() {
        System.out.println("\n" + repeatString("-", 80));
        System.out.println("ALL MEDICINES (SORTED BY ID)");
        System.out.println(repeatString("-", 80));
        System.out.printf("%-12s %-20s %-15s %-10s %-8s %-10s\n", "ID", "Name", "Brand", "Stock", "Price", "Purpose");
        System.out.println(repeatString("-", 80));
        
        Object[] medicinesArray = medicineList.toArray();
        Medicine[] medicineArray = new Medicine[medicinesArray.length];
        for (int i = 0; i < medicinesArray.length; i++) {
            medicineArray[i] = (Medicine) medicinesArray[i];
        }
        
        utility.BubbleSort.sort(medicineArray);
        
        for (Medicine medicine : medicineArray) {
            System.out.printf("%-12s %-20s %-15s %-10s %-8s %-10s\n", 
                medicine.getMedicineId(), medicine.getName(), medicine.getBrand(),
                medicine.getStockQuantity(), "RM " + medicine.getPrice(), medicine.getPurpose());
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchMedicineById() {
        System.out.print("Enter medicine ID to search: ");
        String medicineId = scanner.nextLine();
        
        Medicine foundMedicine = findMedicineById(medicineId);
        if (foundMedicine != null) {
            displayMedicineDetails(foundMedicine);
        } else {
            System.out.println("Medicine not found!");
        }
    }
    
    public void searchMedicineByName() {
        System.out.print("Enter medicine name to search: ");
        String name = scanner.nextLine();
        
        Object[] medicinesArray = medicineList.toArray();
        System.out.println("\nMedicines with name containing '" + name + "':");
        System.out.println(repeatString("-", 80));
        System.out.printf("%-12s %-20s %-15s %-10s %-8s\n", "ID", "Name", "Brand", "Stock", "Price");
        System.out.println(repeatString("-", 80));
        
        boolean found = false;
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            if (medicine.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.printf("%-12s %-20s %-15s %-10s %-8s\n", 
                    medicine.getMedicineId(), medicine.getName(), medicine.getBrand(),
                    medicine.getStockQuantity(), "RM " + medicine.getPrice());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No medicines found with this name.");
        }
        System.out.println(repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void displayMedicineDetails(Medicine medicine) {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("MEDICINE DETAILS");
        System.out.println("-".repeat(60));
        System.out.println("ID: " + medicine.getMedicineId());
        System.out.println("Name: " + medicine.getName());
        System.out.println("Brand: " + medicine.getBrand());
        System.out.println("Stock Quantity: " + medicine.getStockQuantity());
        System.out.println("Price: RM " + String.format("%.2f", medicine.getPrice()));
        System.out.println("Purpose: " + medicine.getPurpose());
        System.out.println("Active Ingredient: " + medicine.getActiveIngredient());
        System.out.println("Category: " + medicine.getCategory());
        System.out.println("Expiry Date: " + medicine.getExpiryDate());
        System.out.println("-".repeat(60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updateMedicineStock() {
        System.out.print("Enter medicine ID to update stock: ");
        String medicineId = scanner.nextLine();
        
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            System.out.println("Current medicine information:");
            displayMedicineDetails(medicine);
            
            System.out.print("Enter new stock quantity: ");
            int newStock = getUserInputInt(0, 10000);
            medicine.setStockQuantity(newStock);
            
            System.out.println("Medicine stock updated successfully!");
        } else {
            System.out.println("Medicine not found!");
        }
    }
    
    public void generateMedicineStockReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        MEDICINE STOCK REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        int totalMedicines = medicinesArray.length;
        int lowStockCount = 0, outOfStockCount = 0;
        double totalInventoryValue = 0.0;
        
        System.out.println("📊 Stock Status:");
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            int stock = medicine.getStockQuantity();
            double value = stock * medicine.getPrice();
            totalInventoryValue += value;
            
            if (stock == 0) outOfStockCount++;
            else if (stock <= 10) lowStockCount++;
        }
        
        System.out.println("• Total Medicines: " + totalMedicines);
        System.out.println("• Low Stock (≤10): " + lowStockCount);
        System.out.println("• Out of Stock: " + outOfStockCount);
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalInventoryValue));
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateMedicineCategoryReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        MEDICINE CATEGORY REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        java.util.Map<String, Integer> categoryCount = new java.util.HashMap<>();
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            String purpose = medicine.getPurpose();
            categoryCount.put(purpose, categoryCount.getOrDefault(purpose, 0) + 1);
        }
        
        System.out.println("📊 Medicine Categories:");
        for (java.util.Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println("• " + entry.getKey() + ": " + entry.getValue() + " medicines");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateMedicinePriceAnalysis() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        MEDICINE PRICE ANALYSIS");
        System.out.println(repeatString("=", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        double totalValue = 0.0, minPrice = Double.MAX_VALUE, maxPrice = 0.0;
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            double price = medicine.getPrice();
            totalValue += price * medicine.getStockQuantity();
            if (price < minPrice) minPrice = price;
            if (price > maxPrice) maxPrice = price;
        }
        
        System.out.println("📊 Price Analysis:");
        System.out.println("• Minimum Price: RM " + String.format("%.2f", minPrice));
        System.out.println("• Maximum Price: RM " + String.format("%.2f", maxPrice));
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalValue));
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateComprehensivePharmacyReport() {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        COMPREHENSIVE PHARMACY REPORT");
        System.out.println(repeatString("=", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        int totalMedicines = medicinesArray.length;
        int totalStock = 0;
        double totalValue = 0.0;
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            totalStock += medicine.getStockQuantity();
            totalValue += medicine.getPrice() * medicine.getStockQuantity();
        }
        
        System.out.println("📊 Pharmacy Overview:");
        System.out.println("• Total Medicines: " + totalMedicines);
        System.out.println("• Total Stock Items: " + totalStock);
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalValue));
        System.out.println("• Average Stock per Medicine: " + String.format("%.1f", (double)totalStock/totalMedicines));
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
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
    
    public Object[] getAllMedicines() {
        return medicineList.toArray();
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
