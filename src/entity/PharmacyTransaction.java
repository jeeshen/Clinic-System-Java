package entity;

//pharmarcy
public class PharmacyTransaction {
    private String transactionId;
    private String patientId;
    private String medicineId;
    private int quantityDispensed;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantityDispensed() {
        return quantityDispensed;
    }

    public void setQuantityDispensed(int quantityDispensed) {
        this.quantityDispensed = quantityDispensed;
    }

    public String getDispenseDate() {
        return dispenseDate;
    }

    public void setDispenseDate(String dispenseDate) {
        this.dispenseDate = dispenseDate;
    }
    private String dispenseDate;
}
