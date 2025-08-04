package entity;

public class PrescribedMedicine implements Comparable<PrescribedMedicine> {
    private String prescribedMedicineId;
    private String prescriptionId;
    private String medicineId;
    private String medicineName;
    private int quantity;
    private String dosage; //e.g., "1 tablet twice daily"
    private String instructions; //e.g., "Take with food"
    private double unitPrice;
    private double totalPrice;
    private boolean isDispensed;

    public PrescribedMedicine() {
        this("", "", "", "", 0, "", "", 0.0, 0.0, false);
    }

    public PrescribedMedicine(String prescribedMedicineId, String prescriptionId, String medicineId, String medicineName, int quantity, String dosage, String instructions, double unitPrice, double totalPrice, boolean isDispensed) {
        this.prescribedMedicineId = prescribedMedicineId;
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.dosage = dosage;
        this.instructions = instructions;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.isDispensed = isDispensed;
    }

    public String getPrescribedMedicineId() { return prescribedMedicineId; }
    public String getPrescriptionId() { return prescriptionId; }
    public String getMedicineId() { return medicineId; }
    public String getMedicineName() { return medicineName; }
    public int getQuantity() { return quantity; }
    public String getDosage() { return dosage; }
    public String getInstructions() { return instructions; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public boolean isDispensed() { return isDispensed; }

    public void setPrescribedMedicineId(String prescribedMedicineId) { this.prescribedMedicineId = prescribedMedicineId; }
    public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }
    public void setMedicineId(String medicineId) { this.medicineId = medicineId; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setDispensed(boolean dispensed) { isDispensed = dispensed; }

    @Override
    public String toString() {
        return "PrescribedMedicine{" +
                "prescribedMedicineId='" + prescribedMedicineId + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", medicineId='" + medicineId + '\'' +
                ", medicineName='" + medicineName + '\'' +
                ", quantity=" + quantity +
                ", dosage='" + dosage + '\'' +
                ", instructions='" + instructions + '\'' +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", isDispensed=" + isDispensed +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PrescribedMedicine that = (PrescribedMedicine) obj;
        return quantity == that.quantity &&
                Double.compare(that.unitPrice, unitPrice) == 0 &&
                Double.compare(that.totalPrice, totalPrice) == 0 &&
                isDispensed == that.isDispensed &&
                java.util.Objects.equals(prescribedMedicineId, that.prescribedMedicineId) &&
                java.util.Objects.equals(prescriptionId, that.prescriptionId) &&
                java.util.Objects.equals(medicineId, that.medicineId) &&
                java.util.Objects.equals(medicineName, that.medicineName) &&
                java.util.Objects.equals(dosage, that.dosage) &&
                java.util.Objects.equals(instructions, that.instructions);
    }
    
    @Override
    public int compareTo(PrescribedMedicine other) {
        return this.prescribedMedicineId.compareTo(other.prescribedMedicineId);
    }
} 