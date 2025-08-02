package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Medicine;
import entity.PharmacyTransaction;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PharmacyManagement {
    private SetAndQueueInterface<Medicine> medicineInventory = new SetAndQueue<>();
    private SetAndQueueInterface<PharmacyTransaction> transactions = new SetAndQueue<>();
    private SetAndQueueInterface<Medicine> dispensingQueue = new SetAndQueue<>(); //queue for medicine dispensing
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public PharmacyManagement(SetAndQueueInterface<Medicine> medicineInventory, SetAndQueueInterface<PharmacyTransaction> transactions) {
        this.medicineInventory = medicineInventory;
        this.transactions = transactions;
    }
    
    //record medicine dispensing after consultation
    public boolean dispenseMedicine(String patientId, String consultationId, String[] medicineList, int quantity) {
        try {
            for (String medicineId : medicineList) {
                Medicine medicine = findMedicineById(medicineId);
                if (medicine != null && medicine.getStockQuantity() >= quantity) {
                    //create record
                    PharmacyTransaction transaction = new PharmacyTransaction(generateTransactionId(), patientId, medicineId, quantity, dateFormat.format(new Date()));
                    transactions.enqueue(transaction);
                    
                    //add to dispensing queue for processing
                    dispensingQueue.enqueue(medicine);
                    
                    //update stock - deduct the actual quantity requested
                    medicine.setStockQuantity(medicine.getStockQuantity() - quantity);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    //update medicine stock levels
    public boolean updateMedicineStock(String medicineId, int quantity) {
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            medicine.setStockQuantity(quantity);
            return true;
        }
        return false;
    }
    
    //check current stock for a medicine
    public int checkMedicineStock(String medicineId) {
        Medicine medicine = findMedicineById(medicineId);
        return medicine != null ? medicine.getStockQuantity() : -1;
    }
    
    //add new medicine to inventory
    public boolean addNewMedicine(Medicine medicineInfo) {
        if (medicineInfo != null && !medicineInventory.contains(medicineInfo)) {
            return medicineInventory.add(medicineInfo);
        }
        return false;
    }
    
    //remove medicine from inventory
    public boolean removeMedicine(String medicineId) {
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null) {
            return medicineInventory.remove(medicine);
        }
        return false;
    }
    
    //retrieve list of all medicines in stock
    public Medicine[] listAllMedicines() {
        Object[] medicineArray = medicineInventory.toArray();
        Medicine[] medicines = new Medicine[medicineArray.length];
        
        for (int i = 0; i < medicineArray.length; i++) {
            if (medicineArray[i] instanceof Medicine) {
                medicines[i] = (Medicine) medicineArray[i];
            }
        }
        return medicines;
    }
    
    //search medicines by name, type, or other attributes
    public Medicine[] searchMedicine(String criteria) {
        Object[] medicineArray = medicineInventory.toArray();
        Medicine[] tempResults = new Medicine[medicineArray.length];
        int count = 0;
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getMedicineId().equalsIgnoreCase(criteria) ||
                    medicine.getName().toLowerCase().contains(criteria.toLowerCase()) ||
                    medicine.getBrand().toLowerCase().contains(criteria.toLowerCase()) ||
                    medicine.getCategory().toLowerCase().contains(criteria.toLowerCase()) ||
                    medicine.getPurpose().toLowerCase().contains(criteria.toLowerCase()) ||
                    medicine.getActiveIngredient().toLowerCase().contains(criteria.toLowerCase())) {
                    tempResults[count++] = medicine;
                }
            }
        }
        
        Medicine[] results = new Medicine[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //create report on medicine stock status
    public String generateStockReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== MEDICINE STOCK REPORT ===\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");
        
        Object[] medicineArray = medicineInventory.toArray();
        int totalMedicines = 0;
        int lowStockCount = 0;
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                totalMedicines++;
                
                report.append(String.format("ID: %s | Name: %s | Stock: %d | Price: $%.2f\n", 
                    medicine.getMedicineId(), medicine.getName(), 
                    medicine.getStockQuantity(), medicine.getPrice()));
                
                if (medicine.getStockQuantity() < 10) {
                    lowStockCount++;
                }
            }
        }
        
        report.append("\n=== SUMMARY ===\n");
        report.append("Total Medicines: ").append(totalMedicines).append("\n");
        report.append("Low Stock Items (< 10): ").append(lowStockCount).append("\n");
        
        return report.toString();
    }
    
    //suggest medicines with similar effects or therapeutic uses
    public Medicine[] recommendAlternativeMedicine(String medicineId) {
        Medicine requestedMedicine = findMedicineById(medicineId);
        Object[] medicineArray = medicineInventory.toArray();
        Medicine[] tempAlternatives = new Medicine[medicineArray.length];
        int count = 0;
        
        if (requestedMedicine != null) {
            for (Object obj : medicineArray) {
                if (obj instanceof Medicine) {
                    Medicine medicine = (Medicine) obj;
                    if (!medicine.getMedicineId().equalsIgnoreCase(medicineId) && 
                        medicine.getStockQuantity() > 0) {
                        
                        // Check for similar purpose, category, or active ingredient
                        if (medicine.getPurpose().equals(requestedMedicine.getPurpose()) ||
                            medicine.getCategory().equals(requestedMedicine.getCategory()) ||
                            medicine.getActiveIngredient().equals(requestedMedicine.getActiveIngredient())) {
                            tempAlternatives[count++] = medicine;
                        }
                    }
                }
            }
        }
        
        Medicine[] alternatives = new Medicine[count];
        for (int i = 0; i < count; i++) {
            alternatives[i] = tempAlternatives[i];
        }
        return alternatives;
    }
    
    //modify pricing details of medicines in stock
    public boolean updateMedicinePrice(String medicineId, double newPrice) {
        Medicine medicine = findMedicineById(medicineId);
        if (medicine != null && newPrice >= 0) {
            medicine.setPrice(newPrice);
            return true;
        }
        return false;
    }
    
    //get next medicine to be dispensed
    public Medicine getNextMedicineToDispense() {
        return dispensingQueue.getFront();
    }
    
    //process next medicine dispensing (dequeue)
    public Medicine processNextMedicineDispensing() {
        return dispensingQueue.dequeue();
    }
    
    //check if dispensing queue is empty
    public boolean isDispensingQueueEmpty() {
        return dispensingQueue.isQueueEmpty();
    }
    
    //clear dispensing queue
    public void clearDispensingQueue() {
        dispensingQueue.clearQueue();
    }
    
    //get dispensing queue size
    public int getDispensingQueueSize() {
        return dispensingQueue.size();
    }

    //get medicines by category using set operations
    public SetAndQueueInterface<Medicine> getMedicinesByCategorySet(String category) {
        SetAndQueueInterface<Medicine> categoryMedicines = new SetAndQueue<>();
        Object[] medicineArray = medicineInventory.toArray();
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getCategory().toLowerCase().contains(category.toLowerCase())) {
                    categoryMedicines.add(medicine);
                }
            }
        }
        return categoryMedicines;
    }
    
    //get medicines by purpose using set operations
    public SetAndQueueInterface<Medicine> getMedicinesByPurposeSet(String purpose) {
        SetAndQueueInterface<Medicine> purposeMedicines = new SetAndQueue<>();
        Object[] medicineArray = medicineInventory.toArray();
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getPurpose().toLowerCase().contains(purpose.toLowerCase())) {
                    purposeMedicines.add(medicine);
                }
            }
        }
        return purposeMedicines;
    }
    
    //get low stock medicines using set operations
    public SetAndQueueInterface<Medicine> getLowStockMedicinesSet() {
        SetAndQueueInterface<Medicine> lowStockMedicines = new SetAndQueue<>();
        Object[] medicineArray = medicineInventory.toArray();
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getStockQuantity() < 10) {
                    lowStockMedicines.add(medicine);
                }
            }
        }
        return lowStockMedicines;
    }
    
    //get medicines in stock using set operations
    public SetAndQueueInterface<Medicine> getMedicinesInStockSet() {
        SetAndQueueInterface<Medicine> inStockMedicines = new SetAndQueue<>();
        Object[] medicineArray = medicineInventory.toArray();
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getStockQuantity() > 0) {
                    inStockMedicines.add(medicine);
                }
            }
        }
        return inStockMedicines;
    }
    
    //union: medicines by category OR by purpose
    public SetAndQueueInterface<Medicine> getMedicinesByCategoryOrPurpose(String category, String purpose) {
        SetAndQueueInterface<Medicine> categoryMedicines = getMedicinesByCategorySet(category);
        SetAndQueueInterface<Medicine> purposeMedicines = getMedicinesByPurposeSet(purpose);
        return categoryMedicines.union(purposeMedicines);
    }
    
    //intersection: medicines by category AND by purpose
    public SetAndQueueInterface<Medicine> getMedicinesByCategoryAndPurpose(String category, String purpose) {
        SetAndQueueInterface<Medicine> categoryMedicines = getMedicinesByCategorySet(category);
        SetAndQueueInterface<Medicine> purposeMedicines = getMedicinesByPurposeSet(purpose);
        return categoryMedicines.intersection(purposeMedicines);
    }
    
    //difference: medicines by category but NOT low stock
    public SetAndQueueInterface<Medicine> getMedicinesByCategoryNotLowStock(String category) {
        SetAndQueueInterface<Medicine> categoryMedicines = getMedicinesByCategorySet(category);
        SetAndQueueInterface<Medicine> lowStockMedicines = getLowStockMedicinesSet();
        return categoryMedicines.difference(lowStockMedicines);
    }
    
    //check if all medicines in category are in stock (subset operation)
    public boolean areAllCategoryMedicinesInStock(String category) {
        SetAndQueueInterface<Medicine> categoryMedicines = getMedicinesByCategorySet(category);
        SetAndQueueInterface<Medicine> inStockMedicines = getMedicinesInStockSet();
        return categoryMedicines.isSubsetOf(inStockMedicines);
    }
    
    //get medicines with multiple criteria using intersection
    public SetAndQueueInterface<Medicine> getMedicinesWithMultipleCriteria(String category, String purpose, boolean inStock) {
        SetAndQueueInterface<Medicine> categoryMedicines = getMedicinesByCategorySet(category);
        SetAndQueueInterface<Medicine> purposeMedicines = getMedicinesByPurposeSet(purpose);
        SetAndQueueInterface<Medicine> stockMedicines = inStock ? getMedicinesInStockSet() : getLowStockMedicinesSet();
        
        SetAndQueueInterface<Medicine> temp = categoryMedicines.intersection(purposeMedicines);
        return temp.intersection(stockMedicines);
    }
    
    //get essential medicines (specific categories + in stock)
    public SetAndQueueInterface<Medicine> getEssentialMedicines() {
        String[] essentialCategories = {"antibiotic", "painkiller", "antiviral", "emergency"};
        SetAndQueueInterface<Medicine> essentialCategoryMedicines = new SetAndQueue<>();
        
        Object[] medicineArray = medicineInventory.toArray();
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                for (String essentialCategory : essentialCategories) {
                    if (medicine.getCategory().toLowerCase().contains(essentialCategory.toLowerCase())) {
                        essentialCategoryMedicines.add(medicine);
                        break;
                    }
                }
            }
        }
        
        SetAndQueueInterface<Medicine> inStockMedicines = getMedicinesInStockSet();
        return essentialCategoryMedicines.intersection(inStockMedicines);
    }
    
    //get medicines by price range
    public SetAndQueueInterface<Medicine> getMedicinesByPriceRange(double minPrice, double maxPrice) {
        SetAndQueueInterface<Medicine> priceRangeMedicines = new SetAndQueue<>();
        Object[] medicineArray = medicineInventory.toArray();
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getPrice() >= minPrice && medicine.getPrice() <= maxPrice) {
                    priceRangeMedicines.add(medicine);
                }
            }
        }
        return priceRangeMedicines;
    }
    
    //check if medicine sets are equal
    public boolean areMedicineSetsEqual(Medicine[] set1, Medicine[] set2) {
        SetAndQueueInterface<Medicine> queue1 = new SetAndQueue<>();
        SetAndQueueInterface<Medicine> queue2 = new SetAndQueue<>();
        
        for (Medicine medicine : set1) {
            queue1.add(medicine);
        }
        for (Medicine medicine : set2) {
            queue2.add(medicine);
        }
        
        return queue1.isEqual(queue2);
    }
    
    //get medicine inventory statistics using set operations
    public String getMedicineInventoryStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== MEDICINE INVENTORY STATISTICS ===\n");
        
        SetAndQueueInterface<Medicine> lowStockMedicines = getLowStockMedicinesSet();
        SetAndQueueInterface<Medicine> inStockMedicines = getMedicinesInStockSet();
        SetAndQueueInterface<Medicine> essentialMedicines = getEssentialMedicines();
        
        stats.append("Low Stock Medicines: ").append(lowStockMedicines.size()).append("\n");
        stats.append("Medicines In Stock: ").append(inStockMedicines.size()).append("\n");
        stats.append("Essential Medicines: ").append(essentialMedicines.size()).append("\n");
        
        //union of in stock and essential medicines
        SetAndQueueInterface<Medicine> priorityMedicines = inStockMedicines.union(essentialMedicines);
        stats.append("Priority Medicines (In Stock OR Essential): ").append(priorityMedicines.size()).append("\n");
        
        return stats.toString();
    }
    
    //get medicine by ID
    private Medicine findMedicineById(String medicineId) {
        Object[] medicineArray = medicineInventory.toArray();
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getMedicineId().equalsIgnoreCase(medicineId)) {
                    return medicine;
                }
            }
        }
        return null;
    }
    
    //check if medicine ID already exists
    public boolean medicineIdExists(String medicineId) {
        return findMedicineById(medicineId) != null;
    }
    
    //generate unique transaction ID
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
    
    //get low stock medicines (stock < 10)
    public Medicine[] getLowStockMedicines() {
        Object[] medicineArray = medicineInventory.toArray();
        Medicine[] tempLowStock = new Medicine[medicineArray.length];
        int count = 0;
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                if (medicine.getStockQuantity() < 10) {
                    tempLowStock[count++] = medicine;
                }
            }
        }
        
        Medicine[] lowStockMedicines = new Medicine[count];
        for (int i = 0; i < count; i++) {
            lowStockMedicines[i] = tempLowStock[i];
        }
        return lowStockMedicines;
    }   
    
    //get medicines expiring soon (within 30 days)
    public Medicine[] getExpiringMedicines() {
        Object[] medicineArray = medicineInventory.toArray();
        Medicine[] tempExpiring = new Medicine[medicineArray.length];
        int count = 0;
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                //sample check for expiry date
                if (medicine.getExpiryDate() != null && 
                    medicine.getExpiryDate().contains("2024")) {
                    tempExpiring[count++] = medicine;
                }
            }
        }
        
        Medicine[] expiringMedicines = new Medicine[count];
        for (int i = 0; i < count; i++) {
            expiringMedicines[i] = tempExpiring[i];
        }
        return expiringMedicines;
    }
    
    //get total inventory value
    public double getTotalInventoryValue() {
        double totalValue = 0.0;
        Object[] medicineArray = medicineInventory.toArray();
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                totalValue += medicine.getPrice() * medicine.getStockQuantity();
            }
        }
        return totalValue;
    }
    
    //check if medicine inventory is empty
    public boolean isMedicineInventoryEmpty() {
        return medicineInventory.isEmpty();
    }
    
    //clear all medicines
    public void clearAllMedicines() {
        medicineInventory.clearSet();
        dispensingQueue.clearQueue();
    }
    
    //get total number of medicines
    public int getTotalMedicineCount() {
        return medicineInventory.size();
    }
    
    //check if all medicines contain specific category
    public boolean containsAllMedicinesWithCategory(String category) {
        SetAndQueueInterface<Medicine> allMedicines = new SetAndQueue<>();
        Object[] medicineArray = medicineInventory.toArray();
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                allMedicines.add((Medicine) obj);
            }
        }
        
        SetAndQueueInterface<Medicine> categoryMedicines = getMedicinesByCategorySet(category);
        return allMedicines.containsAll(categoryMedicines);
    }
    
    //enhanced stock report with ADT analytics
    public String generateEnhancedStockReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== ENHANCED MEDICINE STOCK REPORT ===\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");
        
        Object[] medicineArray = medicineInventory.toArray();
        int totalMedicines = 0;
        int lowStockCount = 0;
        
        for (Object obj : medicineArray) {
            if (obj instanceof Medicine) {
                Medicine medicine = (Medicine) obj;
                totalMedicines++;
                
                report.append(String.format("ID: %s | Name: %s | Stock: %d | Price: $%.2f\n", 
                    medicine.getMedicineId(), medicine.getName(), 
                    medicine.getStockQuantity(), medicine.getPrice()));
                
                if (medicine.getStockQuantity() < 10) {
                    lowStockCount++;
                }
            }
        }
        
        report.append("\n=== SUMMARY ===\n");
        report.append("Total Medicines: ").append(totalMedicines).append("\n");
        report.append("Low Stock Items (< 10): ").append(lowStockCount).append("\n");

        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        report.append("Dispensing Queue Size: ").append(getDispensingQueueSize()).append("\n");
        report.append("Queue Empty: ").append(isDispensingQueueEmpty() ? "Yes" : "No").append("\n");
        
        SetAndQueueInterface<Medicine> essentialMedicines = getEssentialMedicines();
        report.append("Essential Medicines: ").append(essentialMedicines.size()).append("\n");
        
        SetAndQueueInterface<Medicine> inStockOrEssential = getMedicinesInStockSet().union(getEssentialMedicines());
        report.append("In Stock OR Essential: ").append(inStockOrEssential.size()).append("\n");
        
        SetAndQueueInterface<Medicine> inStockAndEssential = getMedicinesInStockSet().intersection(getEssentialMedicines());
        report.append("In Stock AND Essential: ").append(inStockAndEssential.size()).append("\n");
        
        report.append(getMedicineInventoryStatistics());
        
        return report.toString();
    }
}
