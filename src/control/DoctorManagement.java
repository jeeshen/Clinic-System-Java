package control;

import adt.SetAndQueueInterface;
import entity.Doctor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorManagement {
    private SetAndQueueInterface<Doctor> doctors;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public DoctorManagement(SetAndQueueInterface<Doctor> doctors) {
        this.doctors = doctors;
    }
    
    //add new doctor to the system
    public boolean addDoctor(Doctor doctor) {
        if (doctor != null && !doctors.contains(doctor)) {
            return doctors.add(doctor);
        }
        return false;
    }
    
    //remove doctor from the system
    public boolean removeDoctor(String doctorId) {
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            return doctors.remove(doctor);
        }
        return false;
    }
    
    //update doctor information
    public boolean updateDoctor(Doctor updatedDoctor) {
        Doctor existingDoctor = findDoctorById(updatedDoctor.getDoctorId());
        if (existingDoctor != null) {
            doctors.remove(existingDoctor);
            return doctors.add(updatedDoctor);
        }
        return false;
    }
    
    //get all doctors
    public Doctor[] getAllDoctors() {
        Object[] doctorArray = doctors.toArray();
        Doctor[] doctorList = new Doctor[doctorArray.length];
        
        for (int i = 0; i < doctorArray.length; i++) {
            if (doctorArray[i] instanceof Doctor) {
                doctorList[i] = (Doctor) doctorArray[i];
            }
        }
        return doctorList;
    }
    
    //search doctors by specialization
    public Doctor[] searchDoctorsBySpecialization(String specialization) {
        Object[] doctorArray = doctors.toArray();
        Doctor[] tempResults = new Doctor[doctorArray.length];
        int count = 0;
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.getSpecialization().toLowerCase().contains(specialization.toLowerCase())) {
                    tempResults[count++] = doctor;
                }
            }
        }
        
        Doctor[] results = new Doctor[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //search doctors by name
    public Doctor[] searchDoctorsByName(String name) {
        Object[] doctorArray = doctors.toArray();
        Doctor[] tempResults = new Doctor[doctorArray.length];
        int count = 0;
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.getName().toLowerCase().contains(name.toLowerCase())) {
                    tempResults[count++] = doctor;
                }
            }
        }
        
        Doctor[] results = new Doctor[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get available doctors (not on leave and available)
    public Doctor[] getAvailableDoctors() {
        Object[] doctorArray = doctors.toArray();
        Doctor[] tempResults = new Doctor[doctorArray.length];
        int count = 0;
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.isIsAvailable() && !doctor.isOnLeave()) {
                    tempResults[count++] = doctor;
                }
            }
        }
        
        Doctor[] results = new Doctor[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get doctors on leave
    public Doctor[] getDoctorsOnLeave() {
        Object[] doctorArray = doctors.toArray();
        Doctor[] tempResults = new Doctor[doctorArray.length];
        int count = 0;
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.isOnLeave()) {
                    tempResults[count++] = doctor;
                }
            }
        }
        
        Doctor[] results = new Doctor[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //update doctor availability
    public boolean updateDoctorAvailability(String doctorId, boolean isAvailable) {
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            doctor.setIsAvailable(isAvailable);
            return true;
        }
        return false;
    }
    
    //update doctor leave status
    public boolean updateDoctorLeave(String doctorId, boolean onLeave, String leaveStart, String leaveEnd) {
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            doctor.setOnLeave(onLeave);
            doctor.setLeaveDateStart(leaveStart);
            doctor.setLeaveDateEnd(leaveEnd);
            return true;
        }
        return false;
    }
    
    //update doctor duty schedule
    public boolean updateDutySchedule(String doctorId, String dutySchedule) {
        Doctor doctor = findDoctorById(doctorId);
        if (doctor != null) {
            doctor.setDutySchedule(dutySchedule);
            return true;
        }
        return false;
    }
    
    //generate doctor report
    public String generateDoctorReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== DOCTOR MANAGEMENT REPORT ===\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");
        
        Object[] doctorArray = doctors.toArray();
        int totalDoctors = 0;
        int availableDoctors = 0;
        int onLeaveDoctors = 0;
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                totalDoctors++;
                
                report.append(String.format("ID: %s | Name: %s | Specialization: %s | Available: %s | On Leave: %s\n", 
                    doctor.getDoctorId(), doctor.getName(), 
                    doctor.getSpecialization(), doctor.isIsAvailable() ? "Yes" : "No",
                    doctor.isOnLeave() ? "Yes" : "No"));
                
                if (doctor.isIsAvailable() && !doctor.isOnLeave()) {
                    availableDoctors++;
                }
                if (doctor.isOnLeave()) {
                    onLeaveDoctors++;
                }
            }
        }
        
        report.append("\n=== SUMMARY ===\n");
        report.append("Total Doctors: ").append(totalDoctors).append("\n");
        report.append("Available Doctors: ").append(availableDoctors).append("\n");
        report.append("Doctors on Leave: ").append(onLeaveDoctors).append("\n");
        
        return report.toString();
    }
    
    //find doctor by ID
    private Doctor findDoctorById(String doctorId) {
        Object[] doctorArray = doctors.toArray();
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.getDoctorId().equals(doctorId)) {
                    return doctor;
                }
            }
        }
        return null;
    }
    
    //check if doctor exists
    public boolean doctorExists(String doctorId) {
        return findDoctorById(doctorId) != null;
    }
} 