package entity;

public class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String allegy;
    private String contactNumber;
    private String address;
    private String registrationDate;
    private String medicalHistory;
    private boolean isInWaiting;
    private String currentStatus; //waiting, in consultation, completed

    public Patient() {
        this(0, "", 0, "", "", "", "", "", "", false, "");
    }

    public Patient(int id, String name, int age, String gender, String allegy, String contactNumber, String address, String registrationDate, String medicalHistory, boolean isInWaiting, String currentStatus) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.allegy = allegy;
        this.contactNumber = contactNumber;
        this.address = address;
        this.registrationDate = registrationDate;
        this.medicalHistory = medicalHistory;
        this.isInWaiting = isInWaiting;
        this.currentStatus = currentStatus;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAllegy() {
        return allegy;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public boolean isIsInWaiting() {
        return isInWaiting;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAllegy(String allegy) {
        this.allegy = allegy;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setIsInWaiting(boolean isInWaiting) {
        this.isInWaiting = isInWaiting;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", allegy='" + allegy + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", isInWaiting=" + isInWaiting +
                ", currentStatus='" + currentStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient patient = (Patient) obj;
        return id == patient.id &&
                age == patient.age &&
                isInWaiting == patient.isInWaiting &&
                java.util.Objects.equals(name, patient.name) &&
                java.util.Objects.equals(gender, patient.gender) &&
                java.util.Objects.equals(allegy, patient.allegy) &&
                java.util.Objects.equals(contactNumber, patient.contactNumber) &&
                java.util.Objects.equals(address, patient.address) &&
                java.util.Objects.equals(registrationDate, patient.registrationDate) &&
                java.util.Objects.equals(medicalHistory, patient.medicalHistory) &&
                java.util.Objects.equals(currentStatus, patient.currentStatus);
    }
}
