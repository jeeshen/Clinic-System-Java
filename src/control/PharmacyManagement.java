package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Medicine;
import dao.DataInitializer;
import utility.StringUtility;
import java.util.Scanner;
import utility.InputValidator;

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
        Object[] medicinesArray = medicineList.toArray();
        SetAndQueueInterface<Medicine> tempList = new SetAndQueue<>();
        for (Object obj : medicinesArray) {
            tempList.add((Medicine) obj);
        }
        tempList.sort();
        
        Object[] sortedMedicinesArray = tempList.toArray();
        Medicine[] medicineArray = new Medicine[sortedMedicinesArray.length];
        for (int i = 0; i < sortedMedicinesArray.length; i++) {
            medicineArray[i] = (Medicine) sortedMedicinesArray[i];
        }
        
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price", "Purpose"};
        Object[][] rows = new Object[medicineArray.length][headers.length];
        for (int i = 0; i < medicineArray.length; i++) {
            Medicine medicine = medicineArray[i];
            rows[i][0] = medicine.getMedicineId();
            rows[i][1] = medicine.getName();
            rows[i][2] = medicine.getBrand();
            rows[i][3] = medicine.getStockQuantity();
            rows[i][4] = "RM " + medicine.getPrice();
            rows[i][5] = medicine.getPurpose();
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "ALL MEDICINES (SORTED BY ID)",
            headers,
            rows
        ));
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
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price"};
        SetAndQueueInterface<Object[]> rowList = new SetAndQueue<>();
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            if (medicine.getName().toLowerCase().contains(name.toLowerCase())) {
                rowList.add(new Object[]{medicine.getMedicineId(), medicine.getName(), medicine.getBrand(), medicine.getStockQuantity(), "RM " + medicine.getPrice()});
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length];
        Object[] rowArray = rowList.toArray();
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "MEDICINES WITH NAME CONTAINING '" + name + "'",
            headers,
            rows
        ));
        if (rowList.isEmpty()) {
            System.out.println("No medicines found with this name.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void removeMedicine() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        REMOVE MEDICINE");
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.println("📋 CURRENT MEDICINE LIST:");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.printf("%-8s %-20s %-15s %-8s %-10s %-10s\n", "ID", "Name", "Brand", "Stock", "Price", "Category");
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            System.out.printf("%-8s %-20s %-15s %-8s %-10s %-10s\n", 
                medicine.getMedicineId(), 
                medicine.getName(), 
                medicine.getBrand(), 
                medicine.getStockQuantity(), 
                "RM " + medicine.getPrice(), 
                medicine.getCategory());
        }
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Total Medicines: " + medicinesArray.length);
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.print("Enter medicine ID to remove: ");
        String medicineId = scanner.nextLine();
        
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            System.out.println("Medicine to be removed:");
            displayMedicineDetails(medicine);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this medicine? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removed = medicineList.remove(medicine);
                if (removed) {
                    System.out.println("✅ Medicine removed successfully!");
                } else {
                    System.out.println("❌ Failed to remove medicine from system!");
                }
            } else {
                System.out.println("❌ Medicine removal cancelled.");
            }
        } else {
            System.out.println("❌ Medicine not found!");
        }
    }
    
    public void displayMedicineDetails(Medicine medicine) {
        System.out.println("\n" + StringUtility.repeatString("-", 60));
        System.out.println("MEDICINE DETAILS");
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("ID: " + medicine.getMedicineId());
        System.out.println("Name: " + medicine.getName());
        System.out.println("Brand: " + medicine.getBrand());
        System.out.println("Stock Quantity: " + medicine.getStockQuantity());
        System.out.println("Price: RM " + String.format("%.2f", medicine.getPrice()));
        System.out.println("Purpose: " + medicine.getPurpose());
        System.out.println("Active Ingredient: " + medicine.getActiveIngredient());
        System.out.println("Category: " + medicine.getCategory());
        System.out.println("Expiry Date: " + medicine.getExpiryDate());
        System.out.println(StringUtility.repeatString("-", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void updateMedicineInfo() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("                    UPDATE MEDICINE INFORMATION");
        System.out.println(StringUtility.repeatString("=", 80));

        System.out.println("📋 CURRENT MEDICINE LIST:");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-12s %-25s %-15s %-10s %-10s %-10s\n", "ID", "Name", "Brand", "Stock", "Price", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
        Object[] medicinesArray = medicineList.toArray();
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price", "Status"};
        Object[][] rows = new Object[medicinesArray.length][headers.length];
        for (int i = 0; i < medicinesArray.length; i++) {
            Medicine medicine = (Medicine) medicinesArray[i];
            String status;
            if (medicine.getStockQuantity() == 0) {
                status = "OUT OF STOCK";
            } else if (medicine.getStockQuantity() <= 5) {
                status = "CRITICAL";
            } else if (medicine.getStockQuantity() <= 10) {
                status = "LOW";
            } else {
                status = "OK";
            }
            rows[i][0] = medicine.getMedicineId();
            rows[i][1] = medicine.getName();
            rows[i][2] = medicine.getBrand();
            rows[i][3] = medicine.getStockQuantity();
            rows[i][4] = "RM " + String.format("%.2f", medicine.getPrice());
            rows[i][5] = status;
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CURRENT MEDICINE LIST",
            headers,
            rows
        ));
        System.out.println("Total Medicines: " + medicinesArray.length);
        System.out.println(StringUtility.repeatString("=", 80));
        
        System.out.print("\nEnter medicine ID to update: ");
        String medicineId = scanner.nextLine();
        
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Current medicine information:");
            displayMedicineDetails(medicine);
            System.out.println(StringUtility.repeatString("-", 60));
            
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Enter new information (press Enter to keep current value):");
            System.out.println(StringUtility.repeatString("-", 60));
            
            // Update name
            System.out.print("Name [" + medicine.getName() + "]: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) medicine.setName(name);
            
            // Update brand
            System.out.print("Brand [" + medicine.getBrand() + "]: ");
            String brand = scanner.nextLine();
            if (!brand.isEmpty()) medicine.setBrand(brand);
            
            // Update purpose
            System.out.print("Purpose [" + medicine.getPurpose() + "]: ");
            String purpose = scanner.nextLine();
            if (!purpose.isEmpty()) medicine.setPurpose(purpose);
            
            // Update active ingredient
            System.out.print("Active Ingredient [" + medicine.getActiveIngredient() + "]: ");
            String activeIngredient = scanner.nextLine();
            if (!activeIngredient.isEmpty()) medicine.setActiveIngredient(activeIngredient);
            
            // Update category
            System.out.print("Category [" + medicine.getCategory() + "]: ");
            String category = scanner.nextLine();
            if (!category.isEmpty()) medicine.setCategory(category);
            
            // Update expiry date
            System.out.print("Expiry Date [" + medicine.getExpiryDate() + "] (YYYY-MM-DD): ");
            String expiryDate = scanner.nextLine();
            if (!expiryDate.isEmpty()) medicine.setExpiryDate(expiryDate);
            
            // Update price
            System.out.print("Price [" + String.format("%.2f", medicine.getPrice()) + "] (Enter new price or press Enter): ");
            String priceInput = scanner.nextLine();
            if (!priceInput.isEmpty()) {
                try {
                    double newPrice = Double.parseDouble(priceInput);
                    if (newPrice >= 0) {
                        medicine.setPrice(newPrice);
                    } else {
                        System.out.println("Invalid price. Price not updated.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Price not updated.");
                }
            }
            
            System.out.println("\n✅ Medicine information updated successfully!");
            System.out.println("Updated " + medicine.getName() + " information.");
            
            System.out.println("\nUpdated medicine information:");
            displayMedicineDetails(medicine);
        } else {
            System.out.println("❌ Medicine not found!");
        }
    }
    
    public void updateMedicineStock() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("                    UPDATE MEDICINE STOCK");
        System.out.println(StringUtility.repeatString("=", 80));

        System.out.println("📋 CURRENT MEDICINE LIST:");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-12s %-25s %-15s %-10s %-10s %-10s\n", "ID", "Name", "Brand", "Stock", "Price", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
        Object[] medicinesArray = medicineList.toArray();
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price", "Status"};
        Object[][] rows = new Object[medicinesArray.length][headers.length];
        for (int i = 0; i < medicinesArray.length; i++) {
            Medicine medicine = (Medicine) medicinesArray[i];
            String status;
            if (medicine.getStockQuantity() == 0) {
                status = "OUT OF STOCK";
            } else if (medicine.getStockQuantity() <= 5) {
                status = "CRITICAL";
            } else if (medicine.getStockQuantity() <= 10) {
                status = "LOW";
            } else {
                status = "OK";
            }
            rows[i][0] = medicine.getMedicineId();
            rows[i][1] = medicine.getName();
            rows[i][2] = medicine.getBrand();
            rows[i][3] = medicine.getStockQuantity();
            rows[i][4] = "RM " + String.format("%.2f", medicine.getPrice());
            rows[i][5] = status;
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "CURRENT MEDICINE LIST",
            headers,
            rows
        ));
        System.out.println("Total Medicines: " + medicinesArray.length);
        System.out.println(StringUtility.repeatString("=", 80));
        
        System.out.print("\nEnter medicine ID to update stock: ");
        String medicineId = scanner.nextLine();
        
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Current medicine information:");
            displayMedicineDetails(medicine);
            System.out.println(StringUtility.repeatString("-", 60));
            
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Enter new stock information:");
            System.out.println(StringUtility.repeatString("-", 60));
            
            //update stock quantity
            System.out.print("Stock Quantity [" + medicine.getStockQuantity() + "] (Enter new quantity or press Enter): ");
            String stockInput = scanner.nextLine();
            if (!stockInput.isEmpty()) {
                try {
                    int newStock = Integer.parseInt(stockInput);
                    if (newStock >= 0) {
                        medicine.setStockQuantity(newStock);
                        System.out.println("\n✅ Medicine stock updated successfully!");
                        System.out.println("Updated " + medicine.getName() + " stock to: " + newStock + " units");
                        
                        //show updated status
                        String status;
                        if (newStock == 0) {
                            status = "OUT OF STOCK";
                        } else if (newStock <= 5) {
                            status = "CRITICAL";
                        } else if (newStock <= 10) {
                            status = "LOW";
                        } else {
                            status = "OK";
                        }
                        System.out.println("Stock Status: " + status);
                    } else {
                        System.out.println("Invalid stock quantity. Stock not updated.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock format. Stock not updated.");
                }
            } else {
                System.out.println("No changes made to stock quantity.");
            }
        } else {
            System.out.println("❌ Medicine not found!");
        }
    }
    
    public void generateMedicineStockReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        MEDICINE STOCK REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        int totalMedicines = medicinesArray.length;
        int lowStockCount = 0, outOfStockCount = 0;
        double totalInventoryValue = 0.0;
        
        //create separate lists for low stock and out of stock medicines
        SetAndQueueInterface<Medicine> lowStockMedicines = new SetAndQueue<>();
        SetAndQueueInterface<Medicine> outOfStockMedicines = new SetAndQueue<>();
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            int stock = medicine.getStockQuantity();
            double value = stock * medicine.getPrice();
            totalInventoryValue += value;
            
            if (stock == 0) {
                outOfStockCount++;
                outOfStockMedicines.add(medicine);
            } else if (stock <= 10) {
                lowStockCount++;
                lowStockMedicines.add(medicine);
            }
        }
        
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price", "Category"};
        Object[][] rows = new Object[medicinesArray.length][headers.length];
        for (int i = 0; i < medicinesArray.length; i++) {
            Medicine m = (Medicine) medicinesArray[i];
            rows[i][0] = m.getMedicineId();
            rows[i][1] = m.getName();
            rows[i][2] = m.getBrand();
            rows[i][3] = m.getStockQuantity();
            rows[i][4] = String.format("RM %.2f", m.getPrice());
            rows[i][5] = m.getCategory();
        }
        System.out.println("\nMEDICINE LIST:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nSTOCK STATUS DISTRIBUTION:");
        int barWidth = 30;
        int maxStock = 0;
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            if (medicine.getStockQuantity() > maxStock) {
                maxStock = medicine.getStockQuantity();
            }
        }
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            int stock = medicine.getStockQuantity();
            System.out.printf("%-25s [%s] %d\n", 
                medicine.getName() + ":", 
                StringUtility.greenBarChart(stock, maxStock, barWidth),
                stock);
        }
        
        //display low stock medicines
        if (lowStockCount > 0) {
            System.out.println("\nLOW STOCK MEDICINES (≤10 items):");
            String[] lowStockHeaders = {"ID", "Name", "Stock", "Price", "Status"};
            Object[][] lowStockRows = new Object[lowStockCount][lowStockHeaders.length];
            Object[] lowStockArray = lowStockMedicines.toArray();
            for (int i = 0; i < lowStockCount; i++) {
                Medicine medicine = (Medicine) lowStockArray[i];
                String status = medicine.getStockQuantity() <= 5 ? "CRITICAL" : "LOW";
                lowStockRows[i][0] = medicine.getMedicineId();
                lowStockRows[i][1] = medicine.getName();
                lowStockRows[i][2] = medicine.getStockQuantity();
                lowStockRows[i][3] = String.format("RM %.2f", medicine.getPrice());
                lowStockRows[i][4] = status;
            }
            System.out.print(StringUtility.formatTableWithDividers(lowStockHeaders, lowStockRows));
        }
        
        //display out of stock medicines
        if (outOfStockCount > 0) {
            System.out.println("\nOUT OF STOCK MEDICINES:");
            String[] outOfStockHeaders = {"ID", "Name", "Price", "Status"};
            Object[][] outOfStockRows = new Object[outOfStockCount][outOfStockHeaders.length];
            Object[] outOfStockArray = outOfStockMedicines.toArray();
            for (int i = 0; i < outOfStockCount; i++) {
                Medicine medicine = (Medicine) outOfStockArray[i];
                outOfStockRows[i][0] = medicine.getMedicineId();
                outOfStockRows[i][1] = medicine.getName();
                outOfStockRows[i][2] = String.format("RM %.2f", medicine.getPrice());
                outOfStockRows[i][3] = "OUT OF STOCK";
            }
            System.out.print(StringUtility.formatTableWithDividers(outOfStockHeaders, outOfStockRows));
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Medicines: " + totalMedicines);
        System.out.println("• Low Stock (≤10): " + lowStockCount);
        System.out.println("• Out of Stock: " + outOfStockCount);
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalInventoryValue));
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateMedicineCategoryReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        MEDICINE CATEGORY REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        SetAndQueueInterface<String> categorySet = new SetAndQueue<>();

        int[] categoryCounts = new int[100];
        String[] categoryArray = new String[100];
        int categoryIndex = 0;
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            String purpose = medicine.getPurpose();
            categorySet.add(purpose);

            boolean found = false;
            for (int i = 0; i < categoryIndex; i++) {
                if (categoryArray[i].equals(purpose)) {
                    categoryCounts[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                categoryArray[categoryIndex] = purpose;
                categoryCounts[categoryIndex] = 1;
                categoryIndex++;
            }
        }
        
        String[] headers = {"ID", "Name", "Brand", "Category", "Stock", "Price"};
        Object[][] rows = new Object[medicinesArray.length][headers.length];
        for (int i = 0; i < medicinesArray.length; i++) {
            Medicine m = (Medicine) medicinesArray[i];
            rows[i][0] = m.getMedicineId();
            rows[i][1] = m.getName();
            rows[i][2] = m.getBrand();
            rows[i][3] = m.getPurpose();
            rows[i][4] = m.getStockQuantity();
            rows[i][5] = String.format("RM %.2f", m.getPrice());
        }
        System.out.println("\nMEDICINE LIST:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nCATEGORY DISTRIBUTION:");
        int barWidth = 30;
        int maxCategory = 0;
        for (int i = 0; i < categoryIndex; i++) {
            if (categoryCounts[i] > maxCategory) {
                maxCategory = categoryCounts[i];
            }
        }
        
        for (int i = 0; i < categoryIndex; i++) {
            String category = categoryArray[i];
            int count = categoryCounts[i];
            double percentage = (double) count / medicinesArray.length * 100;
            System.out.printf("%-25s [%s] %d medicines (%.1f%%)\n", 
                category + ":", 
                StringUtility.greenBarChart(count, maxCategory, barWidth),
                count, 
                percentage);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Medicines: " + medicinesArray.length);
        System.out.println("• Total Categories: " + categoryIndex);
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateInventoryValueReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        INVENTORY VALUE REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        double totalInventoryValue = 0.0;
        int totalItems = 0;
        int lowStockItems = 0;
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            double itemValue = medicine.getStockQuantity() * medicine.getPrice();
            totalInventoryValue += itemValue;
            totalItems += medicine.getStockQuantity();
            
            if (medicine.getStockQuantity() <= 10) {
                lowStockItems++;
            }
        }

        String[] headers = {"ID", "Name", "Stock", "Unit Price", "Total Value", "Status"};
        Object[][] rows = new Object[medicinesArray.length][headers.length];
        for (int i = 0; i < medicinesArray.length; i++) {
            Medicine m = (Medicine) medicinesArray[i];
            double itemValue = m.getStockQuantity() * m.getPrice();
            String status = m.getStockQuantity() <= 10 ? "LOW STOCK" : "OK";
            rows[i][0] = m.getMedicineId();
            rows[i][1] = m.getName();
            rows[i][2] = m.getStockQuantity();
            rows[i][3] = String.format("RM %.2f", m.getPrice());
            rows[i][4] = String.format("RM %.2f", itemValue);
            rows[i][5] = status;
        }
        System.out.println("\nMEDICINE LIST:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nINVENTORY VALUE DISTRIBUTION:");
        int barWidth = 30;
        double maxValue = 0.0;
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            double itemValue = medicine.getStockQuantity() * medicine.getPrice();
            if (itemValue > maxValue) {
                maxValue = itemValue;
            }
        }
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            double itemValue = medicine.getStockQuantity() * medicine.getPrice();
            System.out.printf("%-25s [%s] RM %.2f\n", 
                medicine.getName() + ":", 
                StringUtility.greenBarChart((int)(itemValue * 100), (int)(maxValue * 100), barWidth),
                itemValue);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Medicines: " + medicinesArray.length);
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalInventoryValue));
        System.out.println("• Total Items in Stock: " + totalItems);
        System.out.println("• Low Stock Items (≤10): " + lowStockItems);
        System.out.println("• Average Item Value: RM " + String.format("%.2f", totalInventoryValue/totalItems));
        
        if (lowStockItems > 0) {
            System.out.println("• WARNING: " + lowStockItems + " items are running low on stock!");
        }
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public Medicine findMedicineById(String medicineId) {
        Medicine dummy = new Medicine();
        dummy.setMedicineId(medicineId);
        return medicineList.search(dummy);
    }
    
    public Object[] getAllMedicines() {
        return medicineList.toArray();
    }
}
