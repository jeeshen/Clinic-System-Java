package entity;

//pharmarcy
public class PharmacyTransaction {
    private String transactionId;
    private String patientId;
    private String medicineId;
    private int quantityDispensed;
    private String dispenseDate;

    public PharmacyTransaction() {
        this("", "", "", 0, "");
    }

    public PharmacyTransaction(String transactionId, String patientId, String medicineId, int quantityDispensed, String dispenseDate) {
        this.transactionId = transactionId;
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.quantityDispensed = quantityDispensed;
        this.dispenseDate = dispenseDate;
    }

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

    @Override
    public String toString() {
        return "PharmacyTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", medicineId='" + medicineId + '\'' +
                ", quantityDispensed=" + quantityDispensed +
                ", dispenseDate='" + dispenseDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PharmacyTransaction other = (PharmacyTransaction) obj;
        if (!java.util.Objects.equals(this.transactionId, other.transactionId)) {
            return false;
        }
        return true;
    }
}
