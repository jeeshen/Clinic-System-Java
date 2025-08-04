package entity;

public class Treatment {
    private String treatmentId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String prescribedMedications; //can make to list
    private String treatmentDate;

    public Treatment() {    
        this("", "", "", "", "", "");
    }

    public Treatment(String treatmentId, String patientId, String doctorId, String diagnosis, String prescribedMedications, String treatmentDate) {
        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.prescribedMedications = prescribedMedications;
        this.treatmentDate = treatmentDate;
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }



    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentId='" + treatmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", prescribedMedications='" + prescribedMedications + '\'' +
                ", treatmentDate='" + treatmentDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Treatment treatment = (Treatment) obj;
        return java.util.Objects.equals(treatmentId, treatment.treatmentId) &&
                java.util.Objects.equals(patientId, treatment.patientId) &&
                java.util.Objects.equals(doctorId, treatment.doctorId) &&
                java.util.Objects.equals(diagnosis, treatment.diagnosis) &&
                java.util.Objects.equals(prescribedMedications, treatment.prescribedMedications) &&
                java.util.Objects.equals(treatmentDate, treatment.treatmentDate);
    }
}
