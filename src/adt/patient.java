/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;


/**
 *
 * @author Jason
 */
public class patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String allegy;
    private String contactNumber;
    private String address;
    private String registrationDate;
    private String medicalHistory;
    
    //Optional:For Queue management
    private boolean isInWaiting;
    private String currentStatus; //eg."waiting","In consultation","Completed"

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
    
    

    
    
}
