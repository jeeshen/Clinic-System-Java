package control;

import adt.SetAndQueueInterface;
import adt.SetQueueArray;
import entity.Medicine;
import entity.Prescription;
import entity.PrescribedMedicine;
import dao.DataInitializer;
import utility.StringUtility;
import java.util.Scanner;
import utility.InputValidator;

public class PharmacyManagement {
    private SetAndQueueInterface<Medicine> medicineList = new SetQueueArray<>();
    private Scanner scanner;
    private TreatmentManagement treatmentManagement;
    private static final int LOW_STOCK_THRESHOLD = 10;
    
    public PharmacyManagement() {
        scanner = new Scanner(System.in);
        loadSampleData();
    }

    public void setTreatmentManagement(TreatmentManagement treatmentManagement) {
        this.treatmentManagement = treatmentManagement;
    }
    
    private void loadSampleData() {
        SetAndQueueInterface<Medicine> sampleMedicines = DataInitializer.initializeSampleMedicines();
        for (Object obj : sampleMedicines.toArray()) {
            medicineList.add((Medicine) obj); //adt method
        }
    }
    
    public void displayAllMedicinesSorted() {
        Object[] medicinesArray = medicineList.toArray(); //adt method
        SetAndQueueInterface<Medicine> tempList = new SetQueueArray<>();
        for (Object obj : medicinesArray) {
            tempList.add((Medicine) obj); //adt method
        }
        tempList.sort(); //adt method

        Object[] sortedMedicinesArray = tempList.toArray(); //adt method
        Medicine[] medicineArray = new Medicine[sortedMedicinesArray.length];
        for (int i = 0; i < sortedMedicinesArray.length; i++) {
            medicineArray[i] = (Medicine) sortedMedicinesArray[i];
        }
        
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price", "Purpose", "Expiry Date", "Active Ingredient", "Category"};
        Object[][] rows = new Object[medicineArray.length][headers.length];
        for (int i = 0; i < medicineArray.length; i++) {
            Medicine medicine = medicineArray[i];
            rows[i][0] = medicine.getMedicineId();
            rows[i][1] = medicine.getName();
            rows[i][2] = medicine.getBrand();
            rows[i][3] = medicine.getStockQuantity();
            rows[i][4] = "RM " + medicine.getPrice();
            rows[i][5] = medicine.getPurpose();
            rows[i][6] = medicine.getExpiryDate();
            rows[i][7] = medicine.getActiveIngredient();
            rows[i][8] = medicine.getCategory();
        }

        System.out.println("\n" + StringUtility.repeatString("=", 160));
        System.out.println("                                        ALL MEDICINES (SORTED BY ID)");
        System.out.println(StringUtility.repeatString("=", 160));
        System.out.printf("%-8s %-20s %-15s %-8s %-12s %-20s %-12s %-25s %-15s\n", 
            "ID", "Name", "Brand", "Stock", "Price", "Purpose", "Expiry Date", "Active Ingredient", "Category");
        System.out.println(StringUtility.repeatString("-", 160));
        
        for (int i = 0; i < medicineArray.length; i++) {
            Medicine medicine = medicineArray[i];
            
            System.out.printf("%-8s %-20s %-15s %-8s %-12s %-20s %-12s %-25s %-15s\n",
                medicine.getMedicineId(),
                medicine.getName().length() > 18 ? medicine.getName().substring(0, 17) + "..." : medicine.getName(),
                medicine.getBrand().length() > 13 ? medicine.getBrand().substring(0, 12) + "..." : medicine.getBrand(),
                medicine.getStockQuantity(),
                "RM " + String.format("%.2f", medicine.getPrice()),
                medicine.getPurpose().length() > 18 ? medicine.getPurpose().substring(0, 17) + "..." : medicine.getPurpose(),
                medicine.getExpiryDate(),
                medicine.getActiveIngredient().length() > 23 ? medicine.getActiveIngredient().substring(0, 22) + "..." : medicine.getActiveIngredient(),
                medicine.getCategory().length() > 13 ? medicine.getCategory().substring(0, 12) + "..." : medicine.getCategory());
        }
        System.out.println(StringUtility.repeatString("-", 160));
        System.out.println("Total Medicines: " + medicineArray.length);
        System.out.println(StringUtility.repeatString("=", 160));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void searchMedicineById() {
        System.out.print("Enter medicine ID to search (or press Enter to cancel): ");
        String medicineId = scanner.nextLine().trim();

        if (medicineId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Medicine foundMedicine = findMedicineById(medicineId);
        if (foundMedicine != null) {
            displayMedicineDetails(foundMedicine);
        } else {
            System.out.println("Medicine not found!");
        }
    }
    
    public void searchMedicineByName() {
        System.out.print("Enter medicine name to search (or press Enter to cancel): ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Object[] medicinesArray = medicineList.toArray(); //adt method
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price"};
        SetAndQueueInterface<Object[]> rowList = new SetQueueArray<>();
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            if (medicine.getName().toLowerCase().contains(name.toLowerCase())) {
                rowList.add(new Object[]{medicine.getMedicineId(), medicine.getName(), medicine.getBrand(), medicine.getStockQuantity(), "RM " + medicine.getPrice()}); //adt method
            }
        }
        Object[][] rows = new Object[rowList.size()][headers.length]; //adt method
        Object[] rowArray = rowList.toArray(); //adt method
        for (int i = 0; i < rowArray.length; i++) {
            rows[i] = (Object[]) rowArray[i];
        }
        System.out.print(StringUtility.formatTableNoDividers(
            "MEDICINES WITH NAME CONTAINING '" + name + "'",
            headers,
            rows
        ));
        if (rowList.isEmpty()) { //adt method
            System.out.println("No medicines found with this name.");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void addNewMedicine() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("                    ADD NEW MEDICINE");
        System.out.println(StringUtility.repeatString("=", 80));
        
        //generate new medicine ID
        String newMedicineId = generateNewMedicineId();
        System.out.println("New Medicine ID: " + newMedicineId);
        
        System.out.println("\nPlease enter the following information:");
        System.out.println(StringUtility.repeatString("-", 80));

        System.out.print("Medicine Name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("[ERROR] Medicine name cannot be empty!");
            return;
        }
        
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        if (brand.trim().isEmpty()) {
            System.out.println("[ERROR] Brand cannot be empty!");
            return;
        }
        
        System.out.print("Purpose (e.g., Pain Relief, Antibiotic): ");
        String purpose = scanner.nextLine();
        if (purpose.trim().isEmpty()) {
            System.out.println("[ERROR] Purpose cannot be empty!");
            return;
        }
        
        System.out.print("Active Ingredient: ");
        String activeIngredient = scanner.nextLine();
        if (activeIngredient.trim().isEmpty()) {
            System.out.println("[ERROR] Active ingredient cannot be empty!");
            return;
        }
        
        System.out.print("Category (e.g., Analgesic, Antibiotic): ");
        String category = scanner.nextLine();
        if (category.trim().isEmpty()) {
            System.out.println("[ERROR] Category cannot be empty!");
            return;
        }
        
        System.out.print("Stock Quantity: ");
        int stockQuantity = getUserInputInt(0, 10000);
        
        System.out.print("Price (RM): ");
        double price = getUserInputDouble(0.01, 10000.00);
        
        String expiryDate = "";
        boolean validExpiryDate = false;
        while (!validExpiryDate) {
            System.out.print("Expiry Date (YYYY-MM-DD): ");
            expiryDate = scanner.nextLine().trim();

            if (expiryDate.isEmpty()) {
                System.out.println("[ERROR] Expiry date cannot be empty!");
            } else if (!isValidDateFormat(expiryDate)) {
                System.out.println("[ERROR] Invalid date format! Please use YYYY-MM-DD format (e.g., 2025-12-31)");
            } else if (!isExpiryDateValid(expiryDate)) {
                System.out.println("[ERROR] Expiry date cannot be in the past! Please enter a future date.");
            } else {
                validExpiryDate = true;
                System.out.println("[OK] Valid expiry date entered.");
            }
        }

        Medicine newMedicine = new Medicine(newMedicineId, name, brand, stockQuantity, 
                                          expiryDate, price, purpose, activeIngredient, category);

        boolean added = medicineList.add(newMedicine);
        if (added) {
            //also add to treatment management if available
            if (treatmentManagement != null) {
                treatmentManagement.addMedicine(newMedicine);
            }
            
            System.out.println("\n[OK] Medicine added successfully!");
            System.out.println("\nNew Medicine Details:");
            displayMedicineDetails(newMedicine);
        } else {
            System.out.println("[ERROR] Failed to add medicine to system!");
        }
    }
    
    private String generateNewMedicineId() {
        Object[] medicinesArray = medicineList.toArray();
        int maxId = 0;
        
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            String medicineId = medicine.getMedicineId();
            if (medicineId.startsWith("MED")) {
                try {
                    int idNumber = Integer.parseInt(medicineId.substring(3));
                    if (idNumber > maxId) {
                        maxId = idNumber;
                    }
                } catch (NumberFormatException e) {
                    //skip invalid IDs
                }
            }
        }
        
        return "MED" + String.format("%03d", maxId + 1);
    }
    
    private boolean isValidDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        try {
            //parse the date to validate it's a real date
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            if (year < 2024 || year > 2050) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }

            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                return false;
            }
            if (month == 2) {
                if (day > 29) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isExpiryDateValid(String expiryDate) {
        if (!isValidDateFormat(expiryDate)) {
            return false;
        }

        try {
            //check if expiry date is not in the past
            java.time.LocalDate expiry = java.time.LocalDate.parse(expiryDate);
            java.time.LocalDate today = java.time.LocalDate.now();

            return !expiry.isBefore(today);
        } catch (Exception e) {
            return false;
        }
    }
    
    public void removeMedicine() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        REMOVE MEDICINE");
        System.out.println(StringUtility.repeatString("=", 60));
        
        System.out.println("CURRENT MEDICINE LIST:");
        
        Object[] medicinesArray = medicineList.toArray(); //adt method
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
        
        System.out.print("Enter medicine ID to remove (or press Enter to cancel): ");
        String medicineId = scanner.nextLine().trim();

        if (medicineId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            System.out.println("Medicine to be removed:");
            displayMedicineDetails(medicine);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this medicine? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removed = medicineList.remove(medicine); //adt method
                if (removed) {
                    //also remove from treatment management if available
                    if (treatmentManagement != null) {
                        treatmentManagement.removeMedicine(medicine);
                    }
                    System.out.println("[OK] Medicine removed successfully!");
                    System.out.println("Medicine has been removed from all systems including prescription options.");
                } else {
                    System.out.println("[ERROR] Failed to remove medicine from system!");
                }
            } else {
                System.out.println("[ERROR] Medicine removal cancelled.");
            }
        } else {
            System.out.println("[ERROR] Medicine not found!");
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
        
        System.out.print("\nEnter medicine ID to update (or press Enter to cancel): ");
        String medicineId = scanner.nextLine().trim();

        if (medicineId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Current medicine information:");
            displayMedicineDetails(medicine);
            System.out.println(StringUtility.repeatString("-", 60));
            
            System.out.println("\n" + StringUtility.repeatString("-", 60));
            System.out.println("Enter new information (press Enter to keep current value):");
            System.out.println(StringUtility.repeatString("-", 60));

            System.out.print("Name [" + medicine.getName() + "]: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) medicine.setName(name);

            System.out.print("Brand [" + medicine.getBrand() + "]: ");
            String brand = scanner.nextLine();
            if (!brand.isEmpty()) medicine.setBrand(brand);

            System.out.print("Purpose [" + medicine.getPurpose() + "]: ");
            String purpose = scanner.nextLine();
            if (!purpose.isEmpty()) medicine.setPurpose(purpose);

            System.out.print("Active Ingredient [" + medicine.getActiveIngredient() + "]: ");
            String activeIngredient = scanner.nextLine();
            if (!activeIngredient.isEmpty()) medicine.setActiveIngredient(activeIngredient);

            System.out.print("Category [" + medicine.getCategory() + "]: ");
            String category = scanner.nextLine();
            if (!category.isEmpty()) medicine.setCategory(category);

            String expiryDate = "";
            boolean validExpiryDate = false;
            while (!validExpiryDate) {
                System.out.print("Expiry Date [" + medicine.getExpiryDate() + "] (YYYY-MM-DD or press Enter to skip): ");
                expiryDate = scanner.nextLine().trim();

                if (expiryDate.isEmpty()) {
                    validExpiryDate = true; //skip update
                } else if (!isValidDateFormat(expiryDate)) {
                    System.out.println("[ERROR] Invalid date format! Please use YYYY-MM-DD format (e.g., 2025-12-31) or press Enter to skip.");
                } else if (!isExpiryDateValid(expiryDate)) {
                    System.out.println("[ERROR] Expiry date cannot be in the past! Please enter a future date or press Enter to skip.");
                } else {
                    medicine.setExpiryDate(expiryDate);
                    System.out.println("[OK] Expiry date updated successfully!");
                    validExpiryDate = true;
                }
            }

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
            
            System.out.println("\n[OK] Medicine information updated successfully!");
            System.out.println("Updated " + medicine.getName() + " information.");
            
            System.out.println("\nUpdated medicine information:");
            displayMedicineDetails(medicine);
        } else {
            System.out.println("[ERROR] Medicine not found!");
        }
    }
    
    public void updateMedicineStock() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("                    UPDATE MEDICINE STOCK");
        System.out.println(StringUtility.repeatString("=", 80));
        
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
        
        System.out.print("\nEnter medicine ID to update stock (or press Enter to cancel): ");
        String medicineId = scanner.nextLine().trim();

        if (medicineId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
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
                        //sync with treatment management inventory
                        if (treatmentManagement != null) {
                            Medicine tmMedicine = treatmentManagement.findMedicineById(medicine.getMedicineId());
                            if (tmMedicine != null) {
                                tmMedicine.setStockQuantity(newStock);
                            }
                        }
                        System.out.println("\n[OK] Medicine stock updated successfully!");
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
            System.out.println("[ERROR] Medicine not found!");
        }
    }
    
    public void generateMedicineStockReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           PHARMACY MANAGEMENT SUBSYSTEM");
        System.out.println("                              MEDICINE STOCK REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        
        Object[] medicinesArray = medicineList.toArray();
        int totalMedicines = medicinesArray.length;
        int lowStockCount = 0, outOfStockCount = 0;
        double totalInventoryValue = 0.0;
        
        //create separate lists for low stock and out of stock medicines
        SetAndQueueInterface<Medicine> lowStockMedicines = new SetQueueArray<>();
        SetAndQueueInterface<Medicine> outOfStockMedicines = new SetQueueArray<>();
        
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
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("• Total Medicines: " + totalMedicines);
        System.out.println("• Low Stock (≤10): " + lowStockCount);
        System.out.println("• Out of Stock: " + outOfStockCount);
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalInventoryValue));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    public void dispenseMedicines() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }

        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        MEDICINE DISPENSING");
        System.out.println(StringUtility.repeatString("=", 60));

        System.out.println("Prescriptions with Undispensed Medicines:");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.printf("%-15s %-10s %-20s %-15s %-10s %-15s\n", "Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status", "Payment");
        System.out.println(StringUtility.repeatString("-", 100));

        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
        boolean hasUndispensed = false;
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.getStatus().equals("active") && hasUndispensedMedicines(prescription)) {
                String paymentStatus = prescription.isPaid() ? "[PAID]" : "[UNPAID]";
                System.out.printf("%-15s %-10s %-20s %-15s %-10s %-15s\n",
                    prescription.getPrescriptionId(), prescription.getPatientId(),
                    prescription.getDiagnosis(), "RM " + String.format("%.2f", prescription.getTotalCost()),
                    prescription.getStatus(), paymentStatus);
                hasUndispensed = true;
            }
        }

        if (!hasUndispensed) {
            System.out.println("No prescriptions with undispensed medicines found.");
            System.out.println(StringUtility.repeatString("-", 100));
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println(StringUtility.repeatString("-", 100));

        System.out.print("Enter prescription ID to dispense medicines (or press Enter to cancel): ");
        String prescriptionId = scanner.nextLine().trim();

        if (prescriptionId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return;
        }

        Prescription prescription = treatmentManagement.findPrescriptionById(prescriptionId);
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

        System.out.println("\nPrescription Details:");
        System.out.println("Prescription ID: " + prescription.getPrescriptionId());
        System.out.println("Patient ID: " + prescription.getPatientId());
        System.out.println("Diagnosis: " + prescription.getDiagnosis());
        System.out.println("Date: " + prescription.getPrescriptionDate());
        System.out.println("Payment Status: " + (prescription.isPaid() ? "[PAID]" : "[UNPAID]"));

        System.out.println("\nPrescribed Medicines:");
        System.out.println(StringUtility.repeatString("-", 120));
        System.out.printf("%-20s %-10s %-20s %-20s %-10s %-10s %-15s\n", "Medicine", "Quantity", "Dosage", "Instructions", "Price", "Dispensed", "Payment Status");
        System.out.println(StringUtility.repeatString("-", 120));

        Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
        boolean allDispensed = true;
        for (Object obj : prescribedMedicinesArray) {
            PrescribedMedicine pm = (PrescribedMedicine) obj;
            String dosage = (pm.getDosage() == null || pm.getDosage().trim().isEmpty()) ? "-" : pm.getDosage();
            String instructions = (pm.getInstructions() == null || pm.getInstructions().trim().isEmpty()) ? "-" : pm.getInstructions();
            String paymentStatus = prescription.isPaid() ? "[PAID]" : "[UNPAID]";
            System.out.printf("%-20s %-10s %-20s %-20s %-10s %-10s %-15s\n",
                pm.getMedicineName(), pm.getQuantity(), dosage,
                instructions, "RM " + String.format("%.2f", pm.getUnitPrice()),
                pm.isDispensed() ? "Yes" : "No", paymentStatus);
            if (!pm.isDispensed()) allDispensed = false;
        }
        if (allDispensed) {
            System.out.println("\nAll medicines in this prescription have been dispensed!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println(StringUtility.repeatString("-", 120));
        System.out.println("\nDispensing Options:");
        System.out.println("1. Dispense all undispensed medicines");
        System.out.println("2. Dispense specific medicine");
        System.out.print("Select option (1-2): ");
        int option = getUserInputInt(1, 2);

        if (option == 1) {
            boolean success = true;
            for (Object obj : prescribedMedicinesArray) {
                PrescribedMedicine pm = (PrescribedMedicine) obj;
                if (!pm.isDispensed()) {
                    Medicine tmMed = treatmentManagement.findMedicineById(pm.getMedicineId());
                    if (tmMed != null) {
                        boolean dispensed = false;
                        if (tmMed.getStockQuantity() >= pm.getQuantity() && tmMed.getStockQuantity() <= LOW_STOCK_THRESHOLD) {
                            dispensed = handleAlternativeSuggestionAndMaybeDispense(prescription, pm, tmMed);
                        }
                        if (!dispensed) {
                            if (tmMed.getStockQuantity() >= pm.getQuantity()) {
                                //deduct from both inventories
                                tmMed.setStockQuantity(tmMed.getStockQuantity() - pm.getQuantity());
                                Medicine phMed = findMedicineById(pm.getMedicineId());
                                if (phMed != null) phMed.setStockQuantity(phMed.getStockQuantity() - pm.getQuantity());
                                pm.setDispensed(true);
                            } else {
                                boolean substituted = handleAlternativeSelectionWhenInsufficient(prescription, pm, tmMed);
                                if (!substituted) {
                                    System.out.println("Insufficient stock for " + pm.getMedicineName() +
                                            " (Required: " + pm.getQuantity() + ", Available: " + tmMed.getStockQuantity() + ")");
                                    success = false;
                                }
                            }
                        }
                    } else {
                        System.out.println("Medicine " + pm.getMedicineName() + " not found in inventory!");
                        success = false;
                    }
                }
            }
            if (success) {
                System.out.println("\n[OK] All medicines dispensed successfully!");
                System.out.println("Stock levels have been updated.");
            } else {
                System.out.println("\n[ERROR] Some medicines could not be dispensed due to insufficient stock.");
            }
        } else {
            System.out.print("Enter medicine name to dispense (or press Enter to cancel): ");
            String medicineName = scanner.nextLine().trim();

            if (medicineName.isEmpty()) {
                System.out.println("Operation cancelled.");
                return;
            }
            boolean found = false;
            for (Object obj : prescribedMedicinesArray) {
                PrescribedMedicine pm = (PrescribedMedicine) obj;
                if (pm.getMedicineName().equalsIgnoreCase(medicineName) && !pm.isDispensed()) {
                    found = true;
                    Medicine tmMed = treatmentManagement.findMedicineById(pm.getMedicineId());
                    if (tmMed != null) {
                        boolean dispensed = false;
                        if (tmMed.getStockQuantity() >= pm.getQuantity() && tmMed.getStockQuantity() <= LOW_STOCK_THRESHOLD) {
                            dispensed = handleAlternativeSuggestionAndMaybeDispense(prescription, pm, tmMed);
                        }
                        if (!dispensed) {
                            if (tmMed.getStockQuantity() >= pm.getQuantity()) {
                                tmMed.setStockQuantity(tmMed.getStockQuantity() - pm.getQuantity());
                                Medicine phMed = findMedicineById(pm.getMedicineId());
                                if (phMed != null) phMed.setStockQuantity(phMed.getStockQuantity() - pm.getQuantity());
                                pm.setDispensed(true);
                                System.out.println("\n[OK] " + pm.getMedicineName() + " dispensed successfully!");
                                System.out.println("Quantity dispensed: " + pm.getQuantity());
                                System.out.println("Remaining stock: " + tmMed.getStockQuantity());
                            } else {
                                boolean substituted = handleAlternativeSelectionWhenInsufficient(prescription, pm, tmMed);
                                if (!substituted) {
                                    System.out.println("[ERROR] Insufficient stock for " + pm.getMedicineName() +
                                            " (Required: " + pm.getQuantity() + ", Available: " + tmMed.getStockQuantity() + ")");
                                }
                            }
                        }
                    } else {
                        System.out.println("[ERROR] Medicine " + pm.getMedicineName() + " not found in inventory!");
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println("[ERROR] Medicine not found or already dispensed!");
            }
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void generateDispensingStatisticsReport() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }

        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           PHARMACY MANAGEMENT SUBSYSTEM");
        System.out.println("                         DISPENSING STATISTICS REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
        int totalPrescriptions = prescriptionsArray.length;
        int totalMedicines = 0;
        int dispensedMedicines = 0;
        double totalRevenue = 0.0;

        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            Object[] pmArray = prescription.getPrescribedMedicines().toArray();
            for (Object pmObj : pmArray) {
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

        System.out.println("DISPENSING STATISTICS SUMMARY:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("• Total Prescriptions: " + totalPrescriptions);
        System.out.println("• Total Medicines Prescribed: " + totalMedicines);
        System.out.println("• Prescriptions Dispensed: " + dispensedMedicines);
        System.out.println("• Dispensing Rate: " + String.format("%.1f", (double)dispensedMedicines/totalMedicines*100) + "%");
        System.out.println("• Total Revenue from Dispensed Medicines: RM " + String.format("%.2f", totalRevenue));

        if (totalMedicines > 0) {
            System.out.println("• Average Medicines per Prescription: " + String.format("%.1f", (double)totalMedicines/totalPrescriptions));
        }

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
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

    private double getUserInputDouble(double min, double max) {
        double input;
        do {
            while (!scanner.hasNextDouble()) {
                System.out.print("Invalid input! Please enter an amount between " + String.format("%.2f", min) + " and " + String.format("%.2f", max) + ": ");
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

    private boolean hasUndispensedMedicines(Prescription prescription) {
        Object[] arr = prescription.getPrescribedMedicines().toArray();
        for (Object obj : arr) {
            PrescribedMedicine pm = (PrescribedMedicine) obj;
            if (!pm.isDispensed()) return true;
        }
        return false;
    }

    private boolean handleAlternativeSuggestionAndMaybeDispense(Prescription prescription, PrescribedMedicine pm, Medicine originalMedicine) {
        //find medicines with same active ingredient using intersection
        SetAndQueueInterface<Medicine> sameActiveIngredient = findMedicinesByActiveIngredient(originalMedicine.getActiveIngredient());
        SetAndQueueInterface<Medicine> sufficientStock = findMedicinesWithSufficientStock(pm.getQuantity());
        SetAndQueueInterface<Medicine> alternatives = sameActiveIngredient.intersection(sufficientStock); //adt method

        //remove the original medicine from alternatives
        SetAndQueueInterface<Medicine> finalAlternatives = new SetQueueArray<>();
        Object[] altArray = alternatives.toArray(); //adt method
        for (Object obj : altArray) {
            Medicine alt = (Medicine) obj;
            if (!alt.getMedicineId().equals(originalMedicine.getMedicineId())) {
                finalAlternatives.add(alt); //adt method
            }
        }

        if (finalAlternatives.isEmpty()) return false; //adt method

        System.out.println("\nWARNING: Low stock for " + originalMedicine.getName() + " (Available: " + originalMedicine.getStockQuantity() + ")");
        System.out.println("Similar medicines with the same active ingredient available:");
        displayMedicineOptions(finalAlternatives);

        System.out.print("Use an alternative medicine? (y/n): ");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("y")) return false;

        System.out.print("Enter alternative medicine ID to dispense (or press Enter to cancel): ");
        String altId = scanner.nextLine().trim();

        if (altId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return false;
        }
        Medicine selectedAlt = findMedicineById(altId);
        if (selectedAlt == null || selectedAlt.getStockQuantity() < pm.getQuantity()) {
            System.out.println("[ERROR] Invalid selection or insufficient stock for selected alternative.");
            return false;
        }

        //deduct from both inventories
        selectedAlt.setStockQuantity(selectedAlt.getStockQuantity() - pm.getQuantity());
        Medicine tmAlt = treatmentManagement.findMedicineById(selectedAlt.getMedicineId());
        if (tmAlt != null) tmAlt.setStockQuantity(tmAlt.getStockQuantity() - pm.getQuantity());

        pm.setMedicineId(selectedAlt.getMedicineId());
        pm.setMedicineName(selectedAlt.getName());
        pm.setDispensed(true);

        System.out.println("\n[OK] Dispensed alternative: " + selectedAlt.getName() + " (Active Ingredient: " + selectedAlt.getActiveIngredient() + ")");
        return true;
    }

    private boolean handleAlternativeSelectionWhenInsufficient(Prescription prescription, PrescribedMedicine pm, Medicine originalMedicine) {
        //find medicines with same active ingredient using union
        SetAndQueueInterface<Medicine> sameActiveIngredient = findMedicinesByActiveIngredient(originalMedicine.getActiveIngredient());
        SetAndQueueInterface<Medicine> sufficientStock = findMedicinesWithSufficientStock(pm.getQuantity());
        SetAndQueueInterface<Medicine> alternatives = sameActiveIngredient.intersection(sufficientStock); //adt method

        //remove the original medicine from alternatives
        SetAndQueueInterface<Medicine> finalAlternatives = new SetQueueArray<>();
        Object[] altArray = alternatives.toArray(); //adt method
        for (Object obj : altArray) {
            Medicine alt = (Medicine) obj;
            if (!alt.getMedicineId().equals(originalMedicine.getMedicineId())) {
                finalAlternatives.add(alt); //adt method
            }
        }

        if (finalAlternatives.isEmpty()) return false; //adt method

        System.out.println("\nALERT: Insufficient stock for " + originalMedicine.getName() + ". Available alternatives with the same active ingredient:");
        displayMedicineOptions(finalAlternatives);

        System.out.print("Do you want to dispense one of these alternatives? (y/n): ");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("y")) return false;

        System.out.print("Enter alternative medicine ID to dispense (or press Enter to cancel): ");
        String altId = scanner.nextLine().trim();

        if (altId.isEmpty()) {
            System.out.println("Operation cancelled.");
            return false;
        }
        Medicine selectedAlt = findMedicineById(altId);
        if (selectedAlt == null || selectedAlt.getStockQuantity() < pm.getQuantity()) {
            System.out.println("[ERROR] Invalid selection or insufficient stock for selected alternative.");
            return false;
        }

        //deduct from both inventories
        selectedAlt.setStockQuantity(selectedAlt.getStockQuantity() - pm.getQuantity());
        Medicine tmAlt = treatmentManagement.findMedicineById(selectedAlt.getMedicineId());
        if (tmAlt != null) tmAlt.setStockQuantity(tmAlt.getStockQuantity() - pm.getQuantity());

        pm.setMedicineId(selectedAlt.getMedicineId());
        pm.setMedicineName(selectedAlt.getName());
        pm.setDispensed(true);

        System.out.println("\n[OK] Dispensed alternative: " + selectedAlt.getName() + " (Active Ingredient: " + selectedAlt.getActiveIngredient() + ")");
        return true;
    }

    private void displayMedicineOptions(SetAndQueueInterface<Medicine> medicines) {
        Object[] medicinesArray = medicines.toArray();
        String[] headers = {"ID", "Name", "Brand", "Stock", "Price", "Active Ingredient"};
        Object[][] rows = new Object[medicinesArray.length][headers.length];
        for (int i = 0; i < medicinesArray.length; i++) {
            Medicine m = (Medicine) medicinesArray[i];
            rows[i][0] = m.getMedicineId();
            rows[i][1] = m.getName();
            rows[i][2] = m.getBrand();
            rows[i][3] = m.getStockQuantity();
            rows[i][4] = "RM " + String.format("%.2f", m.getPrice());
            rows[i][5] = m.getActiveIngredient();
        }
        System.out.print(StringUtility.formatTableNoDividers("SIMILAR MEDICINES", headers, rows));
    }

    private SetAndQueueInterface<Medicine> findMedicinesByActiveIngredient(String activeIngredient) {
        SetAndQueueInterface<Medicine> result = new SetQueueArray<>();
        Object[] medicinesArray = medicineList.toArray(); //adt method
        for (Object obj : medicinesArray) {
            Medicine m = (Medicine) obj;
            if (m.getActiveIngredient() != null && m.getActiveIngredient().equalsIgnoreCase(activeIngredient)) {
                result.add(m); //adt method
            }
        }
        return result;
    }

    private SetAndQueueInterface<Medicine> findMedicinesWithSufficientStock(int requiredQuantity) {
        SetAndQueueInterface<Medicine> result = new SetQueueArray<>();
        Object[] medicinesArray = medicineList.toArray(); //adt method
        for (Object obj : medicinesArray) {
            Medicine m = (Medicine) obj;
            if (m.getStockQuantity() >= requiredQuantity) {
                result.add(m); //adt method
            }
        }
        return result;
    }
    
    public void generateMedicineCategoryReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        MEDICINE CATEGORY REPORT");
        System.out.println(StringUtility.repeatString("=", 60));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 60));
        
        Object[] medicinesArray = medicineList.toArray();
        SetAndQueueInterface<String> categorySet = new SetQueueArray<>();

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
        dummy.setMedicineId(medicineId.toUpperCase());
        return medicineList.search(dummy); //adt method
    }

    public Object[] getAllMedicines() {
        return medicineList.toArray(); //adt method
    }

    public void generatePharmacyInventoryAndUsageReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           PHARMACY MANAGEMENT SUBSYSTEM");
        System.out.println("                        PHARMACY INVENTORY & USAGE REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        String reset = "\u001B[0m";

        Object[] medicinesArray = medicineList.toArray();
        int totalMedicines = medicinesArray.length;

        //medicine usage analysis from prescriptions
        SetAndQueueInterface<String> uniqueMedicines = new SetQueueArray<>();
        int[] medicineUsageCounts = new int[100];
        String[] medicineArray = new String[100];
        double[] medicineValues = new double[100];
        int medicineIndex = 0;

        //stock level analysis
        int lowStock = 0, mediumStock = 0, highStock = 0, outOfStock = 0;

        //category analysis
        SetAndQueueInterface<String> uniqueCategories = new SetQueueArray<>();
        int[] categoryCounts = new int[20];
        String[] categoryArray = new String[20];
        int categoryIndex = 0;

        //analyze medicines
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            String medicineName = medicine.getName();
            String category = medicine.getPurpose();
            int stock = medicine.getStockQuantity();

            uniqueMedicines.add(medicineName);
            uniqueCategories.add(category);

            //stock level categorization
            if (stock == 0) outOfStock++;
            else if (stock <= LOW_STOCK_THRESHOLD) lowStock++;
            else if (stock <= 50) mediumStock++;
            else highStock++;

            //category analysis
            boolean categoryFound = false;
            for (int i = 0; i < categoryIndex; i++) {
                if (categoryArray[i].equals(category)) {
                    categoryCounts[i]++;
                    categoryFound = true;
                    break;
                }
            }
            if (!categoryFound && categoryIndex < 20) {
                categoryArray[categoryIndex] = category;
                categoryCounts[categoryIndex] = 1;
                categoryIndex++;
            }
        }

        //get usage data from prescriptions if available
        if (treatmentManagement != null) {
            Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
            for (Object prescObj : prescriptionsArray) {
                Prescription prescription = (Prescription) prescObj;
                Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();

                for (Object medObj : prescribedMedicinesArray) {
                    PrescribedMedicine prescribedMedicine = (PrescribedMedicine) medObj;
                    String medicineName = prescribedMedicine.getMedicineName();
                    int quantity = prescribedMedicine.getQuantity();

                    //count medicine usage
                    boolean medicineFound = false;
                    for (int i = 0; i < medicineIndex; i++) {
                        if (medicineArray[i].equals(medicineName)) {
                            medicineUsageCounts[i] += quantity;
                            medicineFound = true;
                            break;
                        }
                    }
                    if (!medicineFound && medicineIndex < 100) {
                        medicineArray[medicineIndex] = medicineName;
                        medicineUsageCounts[medicineIndex] = quantity;

                        //find medicine value
                        for (Object obj : medicinesArray) {
                            Medicine medicine = (Medicine) obj;
                            if (medicine.getName().equals(medicineName)) {
                                medicineValues[medicineIndex] = medicine.getPrice();
                                break;
                            }
                        }
                        medicineIndex++;
                    }
                }
            }
        }

        //sort medicines by usage (bubble sort)
        for (int i = 0; i < medicineIndex - 1; i++) {
            for (int j = 0; j < medicineIndex - i - 1; j++) {
                if (medicineUsageCounts[j] < medicineUsageCounts[j + 1]) {
                    //swap medicines
                    String tempMedicine = medicineArray[j];
                    medicineArray[j] = medicineArray[j + 1];
                    medicineArray[j + 1] = tempMedicine;

                    //swap counts
                    int tempCount = medicineUsageCounts[j];
                    medicineUsageCounts[j] = medicineUsageCounts[j + 1];
                    medicineUsageCounts[j + 1] = tempCount;

                    //swap values
                    double tempValue = medicineValues[j];
                    medicineValues[j] = medicineValues[j + 1];
                    medicineValues[j + 1] = tempValue;
                }
            }
        }

        //sort categories by count (bubble sort)
        for (int i = 0; i < categoryIndex - 1; i++) {
            for (int j = 0; j < categoryIndex - i - 1; j++) {
                if (categoryCounts[j] < categoryCounts[j + 1]) {
                    //swap categories
                    String tempCategory = categoryArray[j];
                    categoryArray[j] = categoryArray[j + 1];
                    categoryArray[j + 1] = tempCategory;

                    //swap counts
                    int tempCount = categoryCounts[j];
                    categoryCounts[j] = categoryCounts[j + 1];
                    categoryCounts[j + 1] = tempCount;
                }
            }
        }

        System.out.println("PHARMACY INVENTORY SUMMARY:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("Total Medicines in Inventory: %d%n", totalMedicines);
        System.out.printf("Total Medicine Categories: %d%n", categoryIndex);
        System.out.printf("Total Medicines Dispensed: %d%n", getTotalDispensedMedicines());
        System.out.println();

        System.out.println("Stock Level vs Reorder Analysis:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("| %-20s | %-68s |%n", "Stock Level", "Count");
        System.out.println(StringUtility.repeatString("-", 95));

        String[] stockLevels = {"Out of Stock", "Low Stock", "Medium Stock", "High Stock"};
        int[] stockCounts = {outOfStock, lowStock, mediumStock, highStock};

        for (int i = 0; i < stockLevels.length; i++) {
            System.out.printf("| %-20s | %-68d |%n",
                stockLevels[i],
                stockCounts[i]);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Medicine Usage:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("| %-20s | %-68s |%n", "Medicine Name", "Usage");
        System.out.println(StringUtility.repeatString("-", 95));

        for (int i = 0; i < Math.min(medicineIndex, 5); i++) {
            System.out.printf("| %-20s | %-68d |%n",
                medicineArray[i].length() > 20 ? medicineArray[i].substring(0, 20) : medicineArray[i],
                medicineUsageCounts[i]);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                GRAPHICAL REPRESENTATION OF PHARMACY MANAGEMENT");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Top Medicines by Usage Chart:");
        System.out.println("   ^");
        int maxUsage = 0;
        int numMedicines = Math.min(medicineIndex, 5);
        for (int i = 0; i < numMedicines; i++) {
            if (medicineUsageCounts[i] > maxUsage) maxUsage = medicineUsageCounts[i];
        }

        String barColor = "\u001B[42m"; //green background

        int increment = 50;
        int maxLevel = ((maxUsage / increment) + 1) * increment; //round up to next 50
        
        for (int level = maxLevel; level > 0; level -= increment) {
            System.out.printf("%3d |", level);
            for (int i = 0; i < numMedicines; i++) {
                if (medicineUsageCounts[i] >= level) {
                    System.out.print(" " + barColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        //draw axis line that matches the data width exactly (6 chars per column)
        System.out.print("    +");
        for (int i = 0; i < numMedicines; i++) {
            System.out.print("------");
        }
        System.out.println("> Medicines");

        //draw labels aligned with bars (4-char labels with proper spacing)
        System.out.print("     ");
        for (int i = 0; i < numMedicines; i++) {
            String shortName = medicineArray[i].length() > 4 ? medicineArray[i].substring(0, 4) : medicineArray[i];
            System.out.printf("%-4s  ", shortName);
        }
        System.out.println();
        System.out.println();

        if (medicineIndex > 0) {
            System.out.printf("Most used medicine: < %s with %d units dispensed >%n",
                medicineArray[0], medicineUsageCounts[0]);
        }

        System.out.printf("Stock level breakdown: < Out: %d, Low: %d, Medium: %d, High: %d >%n",
            outOfStock, lowStock, mediumStock, highStock);

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private int getTotalDispensedMedicines() {
        int total = 0;
        if (treatmentManagement != null) {
            Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
            for (Object prescObj : prescriptionsArray) {
                Prescription prescription = (Prescription) prescObj;
                Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
                for (Object medObj : prescribedMedicinesArray) {
                    PrescribedMedicine prescribedMedicine = (PrescribedMedicine) medObj;
                    total += prescribedMedicine.getQuantity();
                }
            }
        }
        return total;
    }

    public void generatePharmacyFinancialAndCategoryReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 95));
        System.out.println("                TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                           PHARMACY MANAGEMENT SUBSYSTEM");
        System.out.println("                      PHARMACY FINANCIAL & CATEGORY REPORT");
        System.out.println(StringUtility.repeatString("=", 95));
        System.out.println();
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("   TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();

        String reset = "\u001B[0m";

        Object[] medicinesArray = medicineList.toArray();
        int totalMedicines = medicinesArray.length;

        //category analysis
        int[] categoryCounts = new int[20];
        String[] categoryArray = new String[20];
        double[] categoryValues = new double[20];
        int categoryIndex = 0;

        //price range analysis
        int lowPrice = 0, mediumPrice = 0, highPrice = 0, premiumPrice = 0;

        //brand analysis
        int[] brandCounts = new int[50];
        String[] brandArray = new String[50];
        int brandIndex = 0;

        //financial metrics
        double totalInventoryValue = 0;
        double averagePrice = 0;
        double highestPrice = 0;
        double lowestPrice = Double.MAX_VALUE;

        //analyze medicines
        for (Object obj : medicinesArray) {
            Medicine medicine = (Medicine) obj;
            String category = medicine.getPurpose();
            String brand = medicine.getBrand();
            double price = medicine.getPrice();
            int stock = medicine.getStockQuantity();
            double itemValue = price * stock;

            totalInventoryValue += itemValue;
            averagePrice += price;

            if (price > highestPrice) highestPrice = price;
            if (price < lowestPrice) lowestPrice = price;

            //price range categorization
            if (price <= 10.0) lowPrice++;
            else if (price <= 50.0) mediumPrice++;
            else if (price <= 100.0) highPrice++;
            else premiumPrice++;

            //category analysis
            boolean categoryFound = false;
            for (int i = 0; i < categoryIndex; i++) {
                if (categoryArray[i].equals(category)) {
                    categoryCounts[i]++;
                    categoryValues[i] += itemValue;
                    categoryFound = true;
                    break;
                }
            }
            if (!categoryFound && categoryIndex < 20) {
                categoryArray[categoryIndex] = category;
                categoryCounts[categoryIndex] = 1;
                categoryValues[categoryIndex] = itemValue;
                categoryIndex++;
            }

            //brand analysis
            boolean brandFound = false;
            for (int i = 0; i < brandIndex; i++) {
                if (brandArray[i].equals(brand)) {
                    brandCounts[i]++;
                    brandFound = true;
                    break;
                }
            }
            if (!brandFound && brandIndex < 50) {
                brandArray[brandIndex] = brand;
                brandCounts[brandIndex] = 1;
                brandIndex++;
            }
        }

        averagePrice = totalMedicines > 0 ? averagePrice / totalMedicines : 0;

        //sort categories by count (bubble sort)
        for (int i = 0; i < categoryIndex - 1; i++) {
            for (int j = 0; j < categoryIndex - i - 1; j++) {
                if (categoryCounts[j] < categoryCounts[j + 1]) {
                    //swap categories
                    String tempCategory = categoryArray[j];
                    categoryArray[j] = categoryArray[j + 1];
                    categoryArray[j + 1] = tempCategory;

                    //swap counts
                    int tempCount = categoryCounts[j];
                    categoryCounts[j] = categoryCounts[j + 1];
                    categoryCounts[j + 1] = tempCount;

                    //swap values
                    double tempValue = categoryValues[j];
                    categoryValues[j] = categoryValues[j + 1];
                    categoryValues[j + 1] = tempValue;
                }
            }
        }

        //sort brands by count (bubble sort)
        for (int i = 0; i < brandIndex - 1; i++) {
            for (int j = 0; j < brandIndex - i - 1; j++) {
                if (brandCounts[j] < brandCounts[j + 1]) {
                    //swap brands
                    String tempBrand = brandArray[j];
                    brandArray[j] = brandArray[j + 1];
                    brandArray[j + 1] = tempBrand;

                    //swap counts
                    int tempCount = brandCounts[j];
                    brandCounts[j] = brandCounts[j + 1];
                    brandCounts[j + 1] = tempCount;
                }
            }
        }

        System.out.println("PHARMACY FINANCIAL SUMMARY:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("Total Inventory Value: RM %.2f%n", totalInventoryValue);
        System.out.printf("Average Medicine Price: RM %.2f%n", averagePrice);
        System.out.printf("Highest Priced Medicine: RM %.2f%n", highestPrice);
        System.out.printf("Lowest Priced Medicine: RM %.2f%n", lowestPrice);
        System.out.printf("Total Medicine Categories: %d%n", categoryIndex);
        System.out.printf("Total Brands Available: %d%n", brandIndex);
        System.out.println();

        System.out.println("Price Range Analysis:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("| %-20s | %-10s | %-55s |%n", "Price Range", "Count", "Percentage");
        System.out.println(StringUtility.repeatString("-", 95));

        double lowPricePct = totalMedicines > 0 ? (double) lowPrice / totalMedicines * 100 : 0;
        double mediumPricePct = totalMedicines > 0 ? (double) mediumPrice / totalMedicines * 100 : 0;
        double highPricePct = totalMedicines > 0 ? (double) highPrice / totalMedicines * 100 : 0;
        double premiumPricePct = totalMedicines > 0 ? (double) premiumPrice / totalMedicines * 100 : 0;

        System.out.printf("| %-20s | %-10d | %-54.1f%% |%n", "Low (≤RM10)", lowPrice, lowPricePct);
        System.out.printf("| %-20s | %-10d | %-54.1f%% |%n", "Medium (RM11-50)", mediumPrice, mediumPricePct);
        System.out.printf("| %-20s | %-10d | %-54.1f%% |%n", "High (RM51-100)", highPrice, highPricePct);
        System.out.printf("| %-20s | %-10d | %-54.1f%% |%n", "Premium (>RM100)", premiumPrice, premiumPricePct);
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Top Medicine Categories Data:");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.printf("| %-20s | %-10s | %-12s | %-40s |%n", "Category", "Count", "Total Value", "Percentage");
        System.out.println(StringUtility.repeatString("-", 95));

        for (int i = 0; i < Math.min(categoryIndex, 5); i++) {
            double categoryPct = totalMedicines > 0 ? (double) categoryCounts[i] / totalMedicines * 100 : 0;
            System.out.printf("| %-20s | %-10d | RM %-8.2f | %-40.1f%% |%n",
                categoryArray[i].length() > 20 ? categoryArray[i].substring(0, 20) : categoryArray[i],
                categoryCounts[i],
                categoryValues[i],
                categoryPct);
        }
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println("                GRAPHICAL REPRESENTATION OF PHARMACY FINANCIALS");
        System.out.println(StringUtility.repeatString("-", 95));
        System.out.println();

        System.out.println("Top Medicine Categories Chart:");
        System.out.println("   ^");
        int maxCategory = 0;
        int numCategories = Math.min(categoryIndex, 5);
        for (int i = 0; i < numCategories; i++) {
            if (categoryCounts[i] > maxCategory) maxCategory = categoryCounts[i];
        }

        String barColor = "\u001B[44m"; //blue background

        for (int level = maxCategory; level > 0; level--) {
            System.out.printf("%2d |", level);
            for (int i = 0; i < numCategories; i++) {
                if (categoryCounts[i] >= level) {
                    System.out.print(" " + barColor + "    " + reset + " ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        //draw axis line that matches the data width exactly (6 chars per column)
        System.out.print("   +");
        for (int i = 0; i < numCategories; i++) {
            System.out.print("------");
        }
        System.out.println("> Categories");

        //draw labels aligned with bars (4-char labels with proper spacing)
        System.out.print("     ");
        for (int i = 0; i < numCategories; i++) {
            String shortName = categoryArray[i].length() > 4 ? categoryArray[i].substring(0, 4) : categoryArray[i];
            System.out.printf("%-4s  ", shortName);
        }
        System.out.println();
        System.out.println();

        if (categoryIndex > 0) {
            System.out.printf("Most common category: < %s with %d medicines >%n",
                categoryArray[0], categoryCounts[0]);
        }
        if (brandIndex > 0) {
            System.out.printf("Most common brand: < %s with %d medicines >%n",
                brandArray[0], brandCounts[0]);
        }

        System.out.printf("Price distribution: < Low: %d, Medium: %d, High: %d, Premium: %d >%n",
            lowPrice, mediumPrice, highPrice, premiumPrice);

        System.out.println();
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println("                                END OF THE REPORT");
        System.out.println(StringUtility.repeatString("*", 95));
        System.out.println();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
