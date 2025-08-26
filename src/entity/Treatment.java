package entity;

//edward long wai pin
public class Treatment implements Comparable<Treatment> {
    private String treatmentId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String treatmentDate;

    public Treatment() {
        this("", "", "", "", "");
    }

    public Treatment(String treatmentId, String patientId, String doctorId, String diagnosis, String treatmentDate) {
        this.treatmentId = treatmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
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
                ", treatmentDate='" + treatmentDate + '\'' +
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
        final Treatment other = (Treatment) obj;
        if (!java.util.Objects.equals(this.treatmentId, other.treatmentId)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Treatment other) {
        return this.treatmentId.compareTo(other.treatmentId);
    }
}
