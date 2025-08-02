package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Doctor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorManagement {
    private SetAndQueueInterface<Doctor> doctors = new SetAndQueue<>();
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
    
    //get doctors by specialization using set operations
    public SetAndQueueInterface<Doctor> getDoctorsBySpecializationSet(String specialization) {
        SetAndQueueInterface<Doctor> specializationDoctors = new SetAndQueue<>();
        Object[] doctorArray = doctors.toArray();
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.getSpecialization().toLowerCase().contains(specialization.toLowerCase())) {
                    specializationDoctors.add(doctor);
                }
            }
        }
        return specializationDoctors;
    }
    
    //get available doctors using set operations
    public SetAndQueueInterface<Doctor> getAvailableDoctorsSet() {
        SetAndQueueInterface<Doctor> availableDoctors = new SetAndQueue<>();
        Object[] doctorArray = doctors.toArray();
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.isIsAvailable() && !doctor.isOnLeave()) {
                    availableDoctors.add(doctor);
                }
            }
        }
        return availableDoctors;
    }
    
    //get doctors on leave using set operations
    public SetAndQueueInterface<Doctor> getDoctorsOnLeaveSet() {
        SetAndQueueInterface<Doctor> leaveDoctors = new SetAndQueue<>();
        Object[] doctorArray = doctors.toArray();
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.isOnLeave()) {
                    leaveDoctors.add(doctor);
                }
            }
        }
        return leaveDoctors;
    }
    
    //union: available doctors OR doctors with specific specialization
    public SetAndQueueInterface<Doctor> getAvailableOrSpecializedDoctors(String specialization) {
        SetAndQueueInterface<Doctor> availableDoctors = getAvailableDoctorsSet();
        SetAndQueueInterface<Doctor> specializedDoctors = getDoctorsBySpecializationSet(specialization);
        return availableDoctors.union(specializedDoctors);
    }
    
    //intersection: available doctors AND doctors with specific specialization
    public SetAndQueueInterface<Doctor> getAvailableAndSpecializedDoctors(String specialization) {
        SetAndQueueInterface<Doctor> availableDoctors = getAvailableDoctorsSet();
        SetAndQueueInterface<Doctor> specializedDoctors = getDoctorsBySpecializationSet(specialization);
        return availableDoctors.intersection(specializedDoctors);
    }
    
    //difference: available doctors but NOT on leave
    public SetAndQueueInterface<Doctor> getAvailableNotOnLeaveDoctors() {
        SetAndQueueInterface<Doctor> availableDoctors = getAvailableDoctorsSet();
        SetAndQueueInterface<Doctor> leaveDoctors = getDoctorsOnLeaveSet();
        return availableDoctors.difference(leaveDoctors);
    }
    
    //check if all specialized doctors are available (subset operation)
    public boolean areAllSpecializedDoctorsAvailable(String specialization) {
        SetAndQueueInterface<Doctor> specializedDoctors = getDoctorsBySpecializationSet(specialization);
        SetAndQueueInterface<Doctor> availableDoctors = getAvailableDoctorsSet();
        return specializedDoctors.isSubsetOf(availableDoctors);
    }
    
    //get doctors with multiple specializations using union
    public SetAndQueueInterface<Doctor> getDoctorsWithMultipleSpecializations(String[] specializations) {
        SetAndQueueInterface<Doctor> combinedDoctors = new SetAndQueue<>();
        
        for (String specialization : specializations) {
            SetAndQueueInterface<Doctor> specializationDoctors = getDoctorsBySpecializationSet(specialization);
            combinedDoctors.addAll(specializationDoctors);
        }
        return combinedDoctors;
    }
    
    //check if two doctor groups are equal (same doctors)
    public boolean areDoctorGroupsEqual(Doctor[] group1, Doctor[] group2) {
        SetAndQueueInterface<Doctor> set1 = new SetAndQueue<>();
        SetAndQueueInterface<Doctor> set2 = new SetAndQueue<>();
        
        for (Doctor doctor : group1) {
            set1.add(doctor);
        }
        for (Doctor doctor : group2) {
            set2.add(doctor);
        }
        
        return set1.isEqual(set2);
    }
    
    //get doctors with specific characteristics using intersection
    public SetAndQueueInterface<Doctor> getDoctorsWithMultipleCriteria(String specialization, boolean available, boolean onLeave) {
        SetAndQueueInterface<Doctor> specializationDoctors = getDoctorsBySpecializationSet(specialization);
        SetAndQueueInterface<Doctor> availabilityDoctors = new SetAndQueue<>();
        
        Object[] doctorArray = doctors.toArray();
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.isIsAvailable() == available && doctor.isOnLeave() == onLeave) {
                    availabilityDoctors.add(doctor);
                }
            }
        }
        
        return specializationDoctors.intersection(availabilityDoctors);
    }
    
    //get emergency doctors (available, not on leave, with emergency specializations)
    public SetAndQueueInterface<Doctor> getEmergencyDoctors() {
        String[] emergencySpecializations = {"emergency", "trauma", "critical care", "intensive care"};
        SetAndQueueInterface<Doctor> emergencySpecialists = getDoctorsWithMultipleSpecializations(emergencySpecializations);
        SetAndQueueInterface<Doctor> availableDoctors = getAvailableDoctorsSet();
        return emergencySpecialists.intersection(availableDoctors);
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

        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        SetAndQueueInterface<Doctor> cardiologists = getDoctorsBySpecializationSet("cardiology");
        report.append("Cardiologists: ").append(cardiologists.size()).append("\n");
        
        SetAndQueueInterface<Doctor> availableCardiologists = getAvailableAndSpecializedDoctors("cardiology");
        report.append("Available Cardiologists: ").append(availableCardiologists.size()).append("\n");
        
        SetAndQueueInterface<Doctor> emergencyDoctors = getEmergencyDoctors();
        report.append("Emergency Doctors: ").append(emergencyDoctors.size()).append("\n");
        
        SetAndQueueInterface<Doctor> availableOrCardio = getAvailableOrSpecializedDoctors("cardiology");
        report.append("Available OR Cardiologists: ").append(availableOrCardio.size()).append("\n");
        
        boolean allCardiologistsAvailable = areAllSpecializedDoctorsAvailable("cardiology");
        report.append("All Cardiologists Available: ").append(allCardiologistsAvailable ? "Yes" : "No").append("\n");
        
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
    
    //check if doctors set is empty
    public boolean isDoctorDatabaseEmpty() {
        return doctors.isEmpty();
    }
    
    //clear all doctors (use with caution)
    public void clearAllDoctors() {
        doctors.clearSet();
    }
    
    //get total number of doctors
    public int getTotalDoctorCount() {
        return doctors.size();
    }
    
    //check if all doctors contain specific specialization
    public boolean containsAllDoctorsWithSpecialization(String specialization) {
        SetAndQueueInterface<Doctor> allDoctors = new SetAndQueue<>();
        Object[] doctorArray = doctors.toArray();
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                allDoctors.add((Doctor) obj);
            }
        }
        
        SetAndQueueInterface<Doctor> specializedDoctors = getDoctorsBySpecializationSet(specialization);
        return allDoctors.containsAll(specializedDoctors);
    }
} 