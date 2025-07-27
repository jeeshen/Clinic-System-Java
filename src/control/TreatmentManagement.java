package control;

import adt.SetAndQueueInterface;
import entity.Treatment;
import entity.Patient;
import entity.Doctor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TreatmentManagement {
    private SetAndQueueInterface<Treatment> treatments;
    private SetAndQueueInterface<Patient> patients;
    private SetAndQueueInterface<Doctor> doctors;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public TreatmentManagement(SetAndQueueInterface<Treatment> treatments,
                             SetAndQueueInterface<Patient> patients,
                             SetAndQueueInterface<Doctor> doctors) {
        this.treatments = treatments;
        this.patients = patients;
        this.doctors = doctors;
    }
    
    //add new treatment record
    public boolean addTreatment(Treatment treatment) {
        if (treatment != null && !treatments.contains(treatment)) {
            return treatments.add(treatment);
        }
        return false;
    }
    
    //update treatment diagnosis
    public boolean updateDiagnosis(String treatmentId, String diagnosis) {
        Treatment treatment = findTreatmentById(treatmentId);
        if (treatment != null) {
            treatment.setDiagnosis(diagnosis);
            return true;
        }
        return false;
    }
    
    //update prescribed medications
    public boolean updatePrescribedMedications(String treatmentId, String medications) {
        Treatment treatment = findTreatmentById(treatmentId);
        if (treatment != null) {
            treatment.setPrescribedMedications(medications);
            return true;
        }
        return false;
    }
    
    //schedule follow-up appointment
    public boolean scheduleFollowUp(String treatmentId, String followUpDate) {
        Treatment treatment = findTreatmentById(treatmentId);
        if (treatment != null) {
            treatment.setFollowUpDate(followUpDate);
            return true;
        }
        return false;
    }
    
    //get all treatments
    public Treatment[] getAllTreatments() {
        Object[] treatmentArray = treatments.toArray();
        Treatment[] treatmentList = new Treatment[treatmentArray.length];
        
        for (int i = 0; i < treatmentArray.length; i++) {
            if (treatmentArray[i] instanceof Treatment) {
                treatmentList[i] = (Treatment) treatmentArray[i];
            }
        }
        return treatmentList;
    }
    
    //get treatments by patient
    public Treatment[] getTreatmentsByPatient(String patientId) {
        Object[] treatmentArray = treatments.toArray();
        Treatment[] tempResults = new Treatment[treatmentArray.length];
        int count = 0;
        
        for (Object obj : treatmentArray) {
            if (obj instanceof Treatment) {
                Treatment treatment = (Treatment) obj;
                if (treatment.getPatientId().equals(patientId)) {
                    tempResults[count++] = treatment;
                }
            }
        }
        
        Treatment[] results = new Treatment[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get treatments by doctor
    public Treatment[] getTreatmentsByDoctor(String doctorId) {
        Object[] treatmentArray = treatments.toArray();
        Treatment[] tempResults = new Treatment[treatmentArray.length];
        int count = 0;
        
        for (Object obj : treatmentArray) {
            if (obj instanceof Treatment) {
                Treatment treatment = (Treatment) obj;
                if (treatment.getDoctorId().equals(doctorId)) {
                    tempResults[count++] = treatment;
                }
            }
        }
        
        Treatment[] results = new Treatment[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //search treatments by diagnosis
    public Treatment[] searchTreatmentsByDiagnosis(String diagnosis) {
        Object[] treatmentArray = treatments.toArray();
        Treatment[] tempResults = new Treatment[treatmentArray.length];
        int count = 0;
        
        for (Object obj : treatmentArray) {
            if (obj instanceof Treatment) {
                Treatment treatment = (Treatment) obj;
                if (treatment.getDiagnosis().toLowerCase().contains(diagnosis.toLowerCase())) {
                    tempResults[count++] = treatment;
                }
            }
        }
        
        Treatment[] results = new Treatment[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get treatments with follow-up appointments
    public Treatment[] getTreatmentsWithFollowUp() {
        Object[] treatmentArray = treatments.toArray();
        Treatment[] tempResults = new Treatment[treatmentArray.length];
        int count = 0;
        
        for (Object obj : treatmentArray) {
            if (obj instanceof Treatment) {
                Treatment treatment = (Treatment) obj;
                if (treatment.getFollowUpDate() != null && !treatment.getFollowUpDate().isEmpty()) {
                    tempResults[count++] = treatment;
                }
            }
        }
        
        Treatment[] results = new Treatment[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get recent treatments (within last 30 days)
    public Treatment[] getRecentTreatments() {
        Object[] treatmentArray = treatments.toArray();
        Treatment[] tempResults = new Treatment[treatmentArray.length];
        int count = 0;
        
        // Simple check for recent treatments (in real implementation, parse dates)
        for (Object obj : treatmentArray) {
            if (obj instanceof Treatment) {
                Treatment treatment = (Treatment) obj;
                if (treatment.getTreatmentDate() != null && treatment.getTreatmentDate().contains("2024")) {
                    tempResults[count++] = treatment;
                }
            }
        }
        
        Treatment[] results = new Treatment[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //generate treatment report
    public String generateTreatmentReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== MEDICAL TREATMENT MANAGEMENT REPORT ===\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");
        
        Object[] treatmentArray = treatments.toArray();
        int totalTreatments = 0;
        int treatmentsWithFollowUp = 0;
        
        for (Object obj : treatmentArray) {
            if (obj instanceof Treatment) {
                Treatment treatment = (Treatment) obj;
                totalTreatments++;
                
                report.append(String.format("ID: %s | Patient: %s | Doctor: %s | Diagnosis: %s | Date: %s\n", 
                    treatment.getTreatmentId(), treatment.getPatientId(), 
                    treatment.getDoctorId(), treatment.getDiagnosis(),
                    treatment.getTreatmentDate()));
                
                if (treatment.getFollowUpDate() != null && !treatment.getFollowUpDate().isEmpty()) {
                    treatmentsWithFollowUp++;
                }
            }
        }
        
        report.append("\n=== SUMMARY ===\n");
        report.append("Total Treatments: ").append(totalTreatments).append("\n");
        report.append("Treatments with Follow-up: ").append(treatmentsWithFollowUp).append("\n");
        
        return report.toString();
    }
    
    //generate patient treatment history
    public String generatePatientTreatmentHistory(String patientId) {
        StringBuilder report = new StringBuilder();
        report.append("=== PATIENT TREATMENT HISTORY ===\n");
        report.append("Patient ID: ").append(patientId).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");
        
        Treatment[] patientTreatments = getTreatmentsByPatient(patientId);
        
        if (patientTreatments.length == 0) {
            report.append("No treatment records found for this patient.\n");
        } else {
            for (Treatment treatment : patientTreatments) {
                report.append(String.format("Treatment ID: %s\n", treatment.getTreatmentId()));
                report.append(String.format("Date: %s\n", treatment.getTreatmentDate()));
                report.append(String.format("Doctor: %s\n", treatment.getDoctorId()));
                report.append(String.format("Diagnosis: %s\n", treatment.getDiagnosis()));
                report.append(String.format("Medications: %s\n", treatment.getPrescribedMedications()));
                if (treatment.getFollowUpDate() != null && !treatment.getFollowUpDate().isEmpty()) {
                    report.append(String.format("Follow-up: %s\n", treatment.getFollowUpDate()));
                }
                report.append("----------------------------------------").append("\n");
            }
        }
        
        return report.toString();
    }
    
    //find treatment by ID
    private Treatment findTreatmentById(String treatmentId) {
        Object[] treatmentArray = treatments.toArray();
        
        for (Object obj : treatmentArray) {
            if (obj instanceof Treatment) {
                Treatment treatment = (Treatment) obj;
                if (treatment.getTreatmentId().equals(treatmentId)) {
                    return treatment;
                }
            }
        }
        return null;
    }
    
    //check if patient exists
    public boolean patientExists(String patientId) {
        Object[] patientArray = patients.toArray();
        
        for (Object obj : patientArray) {
            if (obj instanceof Patient) {
                Patient patient = (Patient) obj;
                if (patient.getId() == Integer.parseInt(patientId)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //check if doctor exists
    public boolean doctorExists(String doctorId) {
        Object[] doctorArray = doctors.toArray();
        
        for (Object obj : doctorArray) {
            if (obj instanceof Doctor) {
                Doctor doctor = (Doctor) obj;
                if (doctor.getDoctorId().equals(doctorId)) {
                    return true;
                }
            }
        }
        return false;
    }
} 