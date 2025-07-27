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
                if (medicine != null && medicine.getStockQuantity() > 0) {
                    //create record
                    PharmacyTransaction transaction = new PharmacyTransaction(generateTransactionId(), patientId, medicineId, quantity, dateFormat.format(new Date()));
                    transactions.enqueue(transaction);
                    
                    //update stock
                    medicine.setStockQuantity(medicine.getStockQuantity() - 1);
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
}
