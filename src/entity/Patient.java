package entity;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;

public class Patient implements Comparable<Patient> {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String allegy;
    private String contactNumber;
    private String address;
    private String registrationDate;
    private String medicalHistory;
    private String status;
    private SetAndQueueInterface<Consultation> consultations = new SetAndQueue<>();
    private SetAndQueueInterface<Treatment> treatments = new SetAndQueue<>();
    private SetAndQueueInterface<Prescription> prescriptions = new SetAndQueue<>();
    private SetAndQueueInterface<PharmacyTransaction> pharmacyTransactions = new SetAndQueue<>();

    public Patient() {
        this(0, "", 0, "", "", "", "", "", "", "");
    }

    public Patient(int id, String name, int age, String gender, String allegy, String contactNumber, String address, String registrationDate, String medicalHistory, String status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.allegy = allegy;
        this.contactNumber = contactNumber;
        this.address = address;
        this.registrationDate = registrationDate;
        this.medicalHistory = medicalHistory;
        this.status = status;
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

    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) {
        this.status = status; 
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

    public SetAndQueueInterface<Consultation> getConsultations() {
        return consultations;
    }

    public SetAndQueueInterface<Treatment> getTreatments() {
        return treatments;
    }

    public SetAndQueueInterface<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public SetAndQueueInterface<PharmacyTransaction> getPharmacyTransactions() {
        return pharmacyTransactions;
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
                ", status='" + status + '\'' +
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
        final Patient other = (Patient) obj;
        if (!java.util.Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.id, other.id);
    }
}
