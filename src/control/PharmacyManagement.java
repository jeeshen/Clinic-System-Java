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
        Medicine[] sampleMedicines = DataInitializer.initializeSampleMedicines();
        for (Medicine medicine : sampleMedicines) {
            medicineList.add(medicine); //adt method
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
        
        System.out.print("Enter medicine ID to remove: ");
        String medicineId = scanner.nextLine();
        
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            System.out.println("Medicine to be removed:");
            displayMedicineDetails(medicine);
            
            String confirm = InputValidator.getValidString(scanner, "Are you sure you want to remove this medicine? (yes/no): ");
            if (confirm.toLowerCase().equals("yes")) {
                boolean removed = medicineList.remove(medicine); //adt method
                if (removed) {
                    System.out.println("[OK] Medicine removed successfully!");
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

        System.out.println("CURRENT MEDICINE LIST:");
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

            System.out.print("Expiry Date [" + medicine.getExpiryDate() + "] (YYYY-MM-DD): ");
            String expiryDate = scanner.nextLine();
            if (!expiryDate.isEmpty()) medicine.setExpiryDate(expiryDate);

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

        System.out.println("CURRENT MEDICINE LIST:");
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
        System.out.println("• Total Medicines: " + totalMedicines);
        System.out.println("• Low Stock (≤10): " + lowStockCount);
        System.out.println("• Out of Stock: " + outOfStockCount);
        System.out.println("• Total Inventory Value: RM " + String.format("%.2f", totalInventoryValue));
        System.out.println(StringUtility.repeatString("=", 60));
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
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s %-10s\n", "Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 80));

        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
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

        System.out.print("Enter prescription ID to dispense medicines: ");
        String prescriptionId = scanner.nextLine();

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

        System.out.println("\nPrescribed Medicines:");
        System.out.println(StringUtility.repeatString("-", 100));
        System.out.printf("%-20s %-10s %-20s %-10s %-10s %-10s\n", "Medicine", "Quantity", "Dosage", "Instructions", "Price", "Dispensed");
        System.out.println(StringUtility.repeatString("-", 100));

        Object[] prescribedMedicinesArray = prescription.getPrescribedMedicines().toArray();
        boolean allDispensed = true;
        for (Object obj : prescribedMedicinesArray) {
            PrescribedMedicine pm = (PrescribedMedicine) obj;
            String dosage = (pm.getDosage() == null || pm.getDosage().trim().isEmpty()) ? "-" : pm.getDosage();
            String instructions = (pm.getInstructions() == null || pm.getInstructions().trim().isEmpty()) ? "-" : pm.getInstructions();
            System.out.printf("%-20s %-10s %-20s %-20s %-10s %-10s\n",
                pm.getMedicineName(), pm.getQuantity(), dosage,
                instructions, "RM " + String.format("%.2f", pm.getUnitPrice()),
                pm.isDispensed() ? "Yes" : "No");
            if (!pm.isDispensed()) allDispensed = false;
        }
        if (allDispensed) {
            System.out.println("\nAll medicines in this prescription have been dispensed!");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println(StringUtility.repeatString("-", 100));
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
            System.out.print("Enter medicine name to dispense: ");
            String medicineName = scanner.nextLine();
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

    public void processPayment() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }

        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        PAYMENT PROCESSING");
        System.out.println(StringUtility.repeatString("=", 60));

        System.out.println("Unpaid Active Prescriptions:");
        System.out.println(StringUtility.repeatString("-", 80));
        System.out.printf("%-15s %-10s %-20s %-15s %-10s\n", "Prescription ID", "Patient ID", "Diagnosis", "Total Cost", "Status");
        System.out.println(StringUtility.repeatString("-", 80));

        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
        boolean hasUnpaid = false;
        for (Object obj : prescriptionsArray) {
            Prescription prescription = (Prescription) obj;
            if (prescription.getStatus().equals("active") && !prescription.isPaid()) {
                System.out.printf("%-15s %-10s %-20s %-15s %-10s\n",
                        prescription.getPrescriptionId(), prescription.getPatientId(),
                        prescription.getDiagnosis(), "RM " + String.format("%.2f", prescription.getTotalCost()),
                        prescription.getStatus());
                hasUnpaid = true;
            }
        }

        if (!hasUnpaid) {
            System.out.println("No unpaid active prescriptions found.");
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println(StringUtility.repeatString("-", 80));
        System.out.print("Enter prescription ID to process payment: ");
        String prescriptionId = scanner.nextLine();
        Prescription prescription = treatmentManagement.findPrescriptionById(prescriptionId);
        if (prescription == null) {
            System.out.println("Prescription not found!");
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

    public void generateDispensingStatisticsReport() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }

        System.out.println("\n" + StringUtility.repeatString("=", 60));
        System.out.println("        DISPENSING STATISTICS REPORT");
        System.out.println(StringUtility.repeatString("=", 60));

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

        System.out.println("Dispensing Statistics:");
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

        System.out.print("Enter alternative medicine ID to dispense: ");
        String altId = scanner.nextLine();
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

        System.out.print("Enter alternative medicine ID to dispense: ");
        String altId = scanner.nextLine();
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
        dummy.setMedicineId(medicineId);
        return medicineList.search(dummy); //adt method
    }

    public Object[] getAllMedicines() {
        return medicineList.toArray(); //adt method
    }
}
