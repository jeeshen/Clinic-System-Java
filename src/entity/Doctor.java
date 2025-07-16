package entity;

public class Doctor {
    private String doctorId;
    private String name;
    private String specialization; // Faculty (e.g., Cardiology, Pediatrics)
    private String contactNumber;
    private String email;
    private boolean isAvailable;
    private String dutySchedule; // e.g., "Mon-Wed 9AM-5PM"
    private boolean onLeave;
    private String leaveDateStart;
    private String leaveDateEnd;

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
}


