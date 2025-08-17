package entity;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;

public class Consultation implements Comparable<Consultation> {
    private String consultationId;
    private String patientId;
    private String doctorId;
    private String consultationDate;
    private String status; //completed
    private String notes;
    private SetAndQueueInterface<Prescription> prescriptions = new SetAndQueue<>();

    public Consultation() {
        this("", "", "", "", "", "");
    }

    public Consultation(String consultationId, String patientId, String doctorId, String consultationDate, String status, String notes) {
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.consultationDate = consultationDate;
        this.status = status;
        this.notes = notes;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getConsultationDate() {
        return consultationDate;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setConsultationDate(String consultationDate) {
        this.consultationDate = consultationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SetAndQueueInterface<Prescription> getPrescriptions() {
        return prescriptions;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "consultationId='" + consultationId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", consultationDate='" + consultationDate + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
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
        final Consultation other = (Consultation) obj;
        if (!java.util.Objects.equals(this.consultationId, other.consultationId)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Consultation other) {
        return this.consultationId.compareTo(other.consultationId);
    }
}
