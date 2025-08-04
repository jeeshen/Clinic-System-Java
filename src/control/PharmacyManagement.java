package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Medicine;
import dao.DataInitializer;
import utility.StringUtility;
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
        System.out.println("\n" + StringUtility.repeatString("-", 80));
        System.out.println("ALL MEDICINES (SORTED BY ID)");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-12s %-20s %-15s %-10s %-8s %-10s\n", "ID", "Name", "Brand", "Stock", "Price", "Purpose");
        System.out.println(StringUtility.repeatString("-", 80));
        
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
        System.out.println(StringUtility.repeatString("-", 80));
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
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-12s %-20s %-15s %-10s %-8s\n", "ID", "Name", "Brand", "Stock", "Price");
        System.out.println(StringUtility.repeatString("-", 80));
        
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
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
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
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
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
            
            System.out.printf("%-12s %-25s %-15s %-10s %-10s %-10s\n", 
                medicine.getMedicineId(), 
                medicine.getName(), 
                medicine.getBrand(),
                medicine.getStockQuantity(),
                "RM " + String.format("%.2f", medicine.getPrice()),
                status);
        }
        
        System.out.println(StringUtility.repeatString("-", 80));
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
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
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
            
            System.out.printf("%-12s %-25s %-15s %-10s %-10s %-10s\n", 
                medicine.getMedicineId(), 
                medicine.getName(), 
                medicine.getBrand(),
                medicine.getStockQuantity(),
                "RM " + String.format("%.2f", medicine.getPrice()),
                status);
        }
        
        System.out.println(StringUtility.repeatString("-", 80));
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
        
        Object[] medicinesArray = medicineList.toArray();
        int totalMedicines = medicinesArray.length;
        int lowStockCount = 0, outOfStockCount = 0;
        double totalInventoryValue = 0.0;
        
        //create separate lists for low stock and out of stock medicines
        SetAndQueueInterface<Medicine> lowStockMedicines = new SetAndQueue<>();
        SetAndQueueInterface<Medicine> outOfStockMedicines = new SetAndQueue<>();
        
        System.out.println("📊 Stock Status:");
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
        
        System.out.println("• Total Medicines: " + totalMedicines);
        System.out.println("• Low Stock (≤10): " + lowStockCount);
        System.out.println("• Out of Stock: " + outOfStockCount);
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalInventoryValue));
        
        //display low stock medicines
        if (lowStockCount > 0) {
            System.out.println("\n⚠️  LOW STOCK MEDICINES (≤10 items):");
            System.out.println(StringUtility.repeatString("-", 60));
            System.out.printf("%-12s %-20s %-10s %-8s %-10s\n", "ID", "Name", "Stock", "Price", "Status");
            System.out.println(StringUtility.repeatString("-", 60));
            
            Object[] lowStockArray = lowStockMedicines.toArray();
            for (Object obj : lowStockArray) {
                Medicine medicine = (Medicine) obj;
                String status = medicine.getStockQuantity() <= 5 ? "CRITICAL" : "LOW";
                System.out.printf("%-12s %-20s %-10s %-8s %-10s\n", 
                    medicine.getMedicineId(), 
                    medicine.getName(), 
                    medicine.getStockQuantity(),
                    "RM " + String.format("%.2f", medicine.getPrice()),
                    status);
            }
        }
        
        //display out of stock medicines
        if (outOfStockCount > 0) {
            System.out.println("\n🚨 OUT OF STOCK MEDICINES:");
            System.out.println(StringUtility.repeatString("-", 60));
            System.out.printf("%-12s %-20s %-8s %-10s\n", "ID", "Name", "Price", "Status");
            System.out.println(StringUtility.repeatString("-", 60));
            
            Object[] outOfStockArray = outOfStockMedicines.toArray();
            for (Object obj : outOfStockArray) {
                Medicine medicine = (Medicine) obj;
                System.out.printf("%-12s %-20s %-8s %-10s\n", 
                    medicine.getMedicineId(), 
                    medicine.getName(),
                    "RM " + String.format("%.2f", medicine.getPrice()),
                    "OUT OF STOCK");
            }
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateMedicineCategoryReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        MEDICINE CATEGORY REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        
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
        
        System.out.println("📊 Medicine Categories:");
        for (int i = 0; i < categoryIndex; i++) {
            System.out.println("• " + categoryArray[i] + ": " + categoryCounts[i] + " medicines");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateMedicinePriceAnalysis() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        MEDICINE PRICE ANALYSIS");
        System.out.println(StringUtility.repeatString("=", 60));
        
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
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        COMPREHENSIVE PHARMACY REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        
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
    
    public void generateInventoryValueReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        INVENTORY VALUE REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        double totalInventoryValue = 0.0;
        int totalItems = 0;
        int lowStockItems = 0;
        
        System.out.println("📊 Inventory Value Analysis:");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-20s %-10s %-10s %-15s %-15s\n", "Medicine", "Stock", "Unit Price", "Total Value", "Status");
        System.out.println(StringUtility.repeatString("-", 80));
        
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
        
        System.out.println(StringUtility.repeatString("-", 80));
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


}
