package control;

import adt.SetAndQueueInterface;
import entity.Patient;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientManagement {
    private SetAndQueueInterface<Patient> patients;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public PatientManagement(SetAndQueueInterface<Patient> patients) {
        this.patients = patients;
    }
    
    //add new patient to the system
    public boolean addPatient(Patient patient) {
        if (patient != null && !patients.contains(patient)) {
            return patients.add(patient);
        }
        return false;
    }
    
    //remove patient from the system
    public boolean removePatient(int patientId) {
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            return patients.remove(patient);
        }
        return false;
    }
    
    //update patient information
    public boolean updatePatient(Patient updatedPatient) {
        Patient existingPatient = findPatientById(updatedPatient.getId());
        if (existingPatient != null) {
            patients.remove(existingPatient);
            return patients.add(updatedPatient);
        }
        return false;
    }
    
    //get all patients
    public Patient[] getAllPatients() {
        Object[] patientArray = patients.toArray();
        Patient[] patientList = new Patient[patientArray.length];
        
        for (int i = 0; i < patientArray.length; i++) {
            if (patientArray[i] instanceof Patient) {
                patientList[i] = (Patient) patientArray[i];
            }
        }
        return patientList;
    }
    
    //search patients by name
    public Patient[] searchPatientsByName(String name) {
        Object[] patientArray = patients.toArray();
        Patient[] tempResults = new Patient[patientArray.length];
        int count = 0;
        
        for (Object obj : patientArray) {
            if (obj instanceof Patient) {
                Patient patient = (Patient) obj;
                if (patient.getName().toLowerCase().contains(name.toLowerCase())) {
                    tempResults[count++] = patient;
                }
            }
        }
        
        Patient[] results = new Patient[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //search patients by phone number
    public Patient[] searchPatientsByPhone(String phone) {
        Object[] patientArray = patients.toArray();
        Patient[] tempResults = new Patient[patientArray.length];
        int count = 0;
        
        for (Object obj : patientArray) {
            if (obj instanceof Patient) {
                Patient patient = (Patient) obj;
                if (patient.getContactNumber().contains(phone)) {
                    tempResults[count++] = patient;
                }
            }
        }
        
        Patient[] results = new Patient[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get patients by age group
    public Patient[] getPatientsByAgeGroup(int minAge, int maxAge) {
        Object[] patientArray = patients.toArray();
        Patient[] tempResults = new Patient[patientArray.length];
        int count = 0;
        
        for (Object obj : patientArray) {
            if (obj instanceof Patient) {
                Patient patient = (Patient) obj;
                if (patient.getAge() >= minAge && patient.getAge() <= maxAge) {
                    tempResults[count++] = patient;
                }
            }
        }
        
        Patient[] results = new Patient[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get patients by gender
    public Patient[] getPatientsByGender(String gender) {
        Object[] patientArray = patients.toArray();
        Patient[] tempResults = new Patient[patientArray.length];
        int count = 0;
        
        for (Object obj : patientArray) {
            if (obj instanceof Patient) {
                Patient patient = (Patient) obj;
                if (patient.getGender().toLowerCase().equals(gender.toLowerCase())) {
                    tempResults[count++] = patient;
                }
            }
        }
        
        Patient[] results = new Patient[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //update patient contact information
    public boolean updatePatientContact(int patientId, String phone, String address) {
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            patient.setContactNumber(phone);
            patient.setAddress(address);
            return true;
        }
        return false;
    }
    
    //update patient medical information
    public boolean updatePatientMedicalInfo(int patientId, String allegy) {
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            patient.setAllegy(allegy);
            return true;
        }
        return false;
    }
    
    //generate patient report
    public String generatePatientReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== PATIENT MANAGEMENT REPORT ===\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");
        
        Object[] patientArray = patients.toArray();
        int totalPatients = 0;
        int malePatients = 0;
        int femalePatients = 0;
        int adultPatients = 0;
        int childPatients = 0;
        
        for (Object obj : patientArray) {
            if (obj instanceof Patient) {
                Patient patient = (Patient) obj;
                totalPatients++;
                
                report.append(String.format("ID: %d | Name: %s | Age: %d | Gender: %s | Phone: %s\n", 
                    patient.getId(), patient.getName(), 
                    patient.getAge(), patient.getGender(), patient.getContactNumber()));
                
                if (patient.getGender().toLowerCase().equals("male")) {
                    malePatients++;
                } else if (patient.getGender().toLowerCase().equals("female")) {
                    femalePatients++;
                }
                
                if (patient.getAge() >= 18) {
                    adultPatients++;
                } else {
                    childPatients++;
                }
            }
        }
        
        report.append("\n=== SUMMARY ===\n");
        report.append("Total Patients: ").append(totalPatients).append("\n");
        report.append("Male Patients: ").append(malePatients).append("\n");
        report.append("Female Patients: ").append(femalePatients).append("\n");
        report.append("Adult Patients (18+): ").append(adultPatients).append("\n");
        report.append("Child Patients (<18): ").append(childPatients).append("\n");
        
        return report.toString();
    }
    
    //find patient by ID
    private Patient findPatientById(int patientId) {
        Object[] patientArray = patients.toArray();
        
        for (Object obj : patientArray) {
            if (obj instanceof Patient) {
                Patient patient = (Patient) obj;
                if (patient.getId() == patientId) {
                    return patient;
                }
            }
        }
        return null;
    }
    
    //check if patient exists
    public boolean patientExists(int patientId) {
        return findPatientById(patientId) != null;
    }
}
