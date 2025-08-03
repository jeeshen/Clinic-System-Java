package entity;

public class Consultation implements Comparable<Consultation> {
    private String consultationId;
    private String patientId;
    private String doctorId;
    private String consultationDate;
    private String status; //scheduled or completed
    private String notes;
    private String nextAppointmentDate;

    public Consultation() {
        this("", "", "", "", "", "", "");
    }

    public Consultation(String consultationId, String patientId, String doctorId, String consultationDate, String status, String notes, String nextAppointmentDate) {
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.consultationDate = consultationDate;
        this.status = status;
        this.notes = notes;
        this.nextAppointmentDate = nextAppointmentDate;
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

    public String getNextAppointmentDate() {
        return nextAppointmentDate;
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

    public void setNextAppointmentDate(String nextAppointmentDate) {
        this.nextAppointmentDate = nextAppointmentDate;
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
                ", nextAppointmentDate='" + nextAppointmentDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Consultation that = (Consultation) obj;
        return java.util.Objects.equals(consultationId, that.consultationId) &&
                java.util.Objects.equals(patientId, that.patientId) &&
                java.util.Objects.equals(doctorId, that.doctorId) &&
                java.util.Objects.equals(consultationDate, that.consultationDate) &&
                java.util.Objects.equals(status, that.status) &&
                java.util.Objects.equals(notes, that.notes) &&
                java.util.Objects.equals(nextAppointmentDate, that.nextAppointmentDate);
    }
    
    @Override
    public int compareTo(Consultation other) {
        return this.consultationId.compareTo(other.consultationId);
    }
}
