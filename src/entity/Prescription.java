package entity;

import adt.SetAndQueueInterface;
import adt.SetQueueArray;

//chan jee shen
public class Prescription implements Comparable<Prescription> {
    private String prescriptionId;
    private String consultationId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private SetAndQueueInterface<PrescribedMedicine> prescribedMedicines;
    private String prescriptionDate;
    private String status; //active, completed, cancelled
    private double totalCost;
    private boolean isPaid;

    public Prescription() {
        this("", "", "", "", "", new SetQueueArray<>(), "", "", 0.0, false);
    }

    public Prescription(String prescriptionId, String consultationId, String patientId, String doctorId, String diagnosis, SetAndQueueInterface<PrescribedMedicine> prescribedMedicines, String prescriptionDate, String status, double totalCost, boolean isPaid) {
        this.prescriptionId = prescriptionId;
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.prescribedMedicines = prescribedMedicines;
        this.prescriptionDate = prescriptionDate;
        this.status = status;
        this.totalCost = totalCost;
        this.isPaid = isPaid;
    }

    public String getPrescriptionId() { return prescriptionId; }
    public String getConsultationId() { return consultationId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getDiagnosis() { return diagnosis; }
    public SetAndQueueInterface<PrescribedMedicine> getPrescribedMedicines() { return prescribedMedicines; }
    public String getPrescriptionDate() { return prescriptionDate; }
    public String getStatus() { return status; }
    public double getTotalCost() { return totalCost; }
    public boolean isPaid() { return isPaid; }

    public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }
    public void setConsultationId(String consultationId) { this.consultationId = consultationId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setPrescribedMedicines(SetAndQueueInterface<PrescribedMedicine> prescribedMedicines) { 
        this.prescribedMedicines = prescribedMedicines; 
    }
    public void setPrescriptionDate(String prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public void setStatus(String status) { this.status = status; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public void setPaid(boolean paid) { isPaid = paid; }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", consultationId='" + consultationId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", prescriptionDate='" + prescriptionDate + '\'' +
                ", status='" + status + '\'' +
                ", totalCost=" + totalCost +
                ", isPaid=" + isPaid +
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
        final Prescription other = (Prescription) obj;
        if (!java.util.Objects.equals(this.prescriptionId, other.prescriptionId)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Prescription other) {
        return this.prescriptionId.compareTo(other.prescriptionId);
    }
} 