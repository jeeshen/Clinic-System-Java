package entity;

public class Doctor implements Comparable<Doctor> {
    private String doctorId;
    private String name;
    private String specialization; //faculty such as cardiology, pediatrics
    private String contactNumber;
    private String email;
    private boolean isAvailable;
    private String dutySchedule; //mon-wed 9am-5pm
    private boolean onLeave;
    private String leaveDateStart;
    private String leaveDateEnd;

    public Doctor() {
        this("", "", "", "", "", false, "", false, "", "");
    }

    public Doctor(String doctorId, String name, String specialization, String contactNumber, String email, boolean isAvailable, String dutySchedule, boolean onLeave, String leaveDateStart, String leaveDateEnd) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.email = email;
        this.isAvailable = isAvailable;
        this.dutySchedule = dutySchedule;
        this.onLeave = onLeave;
        this.leaveDateStart = leaveDateStart;
        this.leaveDateEnd = leaveDateEnd;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public String getDutySchedule() {
        return dutySchedule;
    }

    public boolean isOnLeave() {
        return onLeave;
    }

    public String getLeaveDateStart() {
        return leaveDateStart;
    }

    public String getLeaveDateEnd() {
        return leaveDateEnd;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setDutySchedule(String dutySchedule) {
        this.dutySchedule = dutySchedule;
    }

    public void setOnLeave(boolean onLeave) {
        this.onLeave = onLeave;
    }

    public void setLeaveDateStart(String leaveDateStart) {
        this.leaveDateStart = leaveDateStart;
    }

    public void setLeaveDateEnd(String leaveDateEnd) {
        this.leaveDateEnd = leaveDateEnd;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", isAvailable=" + isAvailable +
                ", dutySchedule='" + dutySchedule + '\'' +
                ", onLeave=" + onLeave +
                ", leaveDateStart='" + leaveDateStart + '\'' +
                ", leaveDateEnd='" + leaveDateEnd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Doctor doctor = (Doctor) obj;
        return isAvailable == doctor.isAvailable &&
                onLeave == doctor.onLeave &&
                java.util.Objects.equals(doctorId, doctor.doctorId) &&
                java.util.Objects.equals(name, doctor.name) &&
                java.util.Objects.equals(specialization, doctor.specialization) &&
                java.util.Objects.equals(contactNumber, doctor.contactNumber) &&
                java.util.Objects.equals(email, doctor.email) &&
                java.util.Objects.equals(dutySchedule, doctor.dutySchedule) &&
                java.util.Objects.equals(leaveDateStart, doctor.leaveDateStart) &&
                java.util.Objects.equals(leaveDateEnd, doctor.leaveDateEnd);
    }
    
    @Override
    public int compareTo(Doctor other) {
        return this.doctorId.compareTo(other.doctorId);
    }
}


