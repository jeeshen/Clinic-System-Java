package entity;

public class Consultation {
    private String consultationId;
    private String patientId;
    private String doctorId;
    private String consultationDate;
    private String status; // e.g., "Scheduled", "Completed"
    private String notes;
    private String nextAppointmentDate;

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
}
