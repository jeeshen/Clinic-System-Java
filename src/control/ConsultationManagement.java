package control;

import adt.SetAndQueueInterface;
import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultationManagement {
    private SetAndQueueInterface<Consultation> consultations;
    private SetAndQueueInterface<Patient> patients;
    private SetAndQueueInterface<Doctor> doctors;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public ConsultationManagement(SetAndQueueInterface<Consultation> consultations, SetAndQueueInterface<Patient> patients, SetAndQueueInterface<Doctor> doctors) {
        this.consultations = consultations;
        this.patients = patients;
        this.doctors = doctors;
    }
    
    //schedule new consultation
    public boolean scheduleConsultation(Consultation consultation) {
        if (consultation != null && !consultations.contains(consultation)) {
            return consultations.add(consultation);
        }
        return false;
    }
    
    //update consultation status
    public boolean updateConsultationStatus(String consultationId, String status) {
        Consultation consultation = findConsultationById(consultationId);
        if (consultation != null) {
            consultation.setStatus(status);
            return true;
        }
        return false;
    }
    
    //update consultation notes
    public boolean updateConsultationNotes(String consultationId, String notes) {
        Consultation consultation = findConsultationById(consultationId);
        if (consultation != null) {
            consultation.setNotes(notes);
            return true;
        }
        return false;
    }
    
    //schedule follow-up appointment
    public boolean scheduleFollowUp(String consultationId, String nextAppointmentDate) {
        Consultation consultation = findConsultationById(consultationId);
        if (consultation != null) {
            consultation.setNextAppointmentDate(nextAppointmentDate);
            return true;
        }
        return false;
    }
    
    //get all consultations
    public Consultation[] getAllConsultations() {
        Object[] consultationArray = consultations.toArray();
        Consultation[] consultationList = new Consultation[consultationArray.length];
        
        for (int i = 0; i < consultationArray.length; i++) {
            if (consultationArray[i] instanceof Consultation) {
                consultationList[i] = (Consultation) consultationArray[i];
            }
        }
        return consultationList;
    }
    
    //get consultations by status
    public Consultation[] getConsultationsByStatus(String status) {
        Object[] consultationArray = consultations.toArray();
        Consultation[] tempResults = new Consultation[consultationArray.length];
        int count = 0;
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getStatus().toLowerCase().contains(status.toLowerCase())) {
                    tempResults[count++] = consultation;
                }
            }
        }
        
        Consultation[] results = new Consultation[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get consultations by doctor
    public Consultation[] getConsultationsByDoctor(String doctorId) {
        Object[] consultationArray = consultations.toArray();
        Consultation[] tempResults = new Consultation[consultationArray.length];
        int count = 0;
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getDoctorId().equals(doctorId)) {
                    tempResults[count++] = consultation;
                }
            }
        }
        
        Consultation[] results = new Consultation[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get consultations by patient
    public Consultation[] getConsultationsByPatient(String patientId) {
        Object[] consultationArray = consultations.toArray();
        Consultation[] tempResults = new Consultation[consultationArray.length];
        int count = 0;
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getPatientId().equals(patientId)) {
                    tempResults[count++] = consultation;
                }
            }
        }
        
        Consultation[] results = new Consultation[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get scheduled consultations (not completed)
    public Consultation[] getScheduledConsultations() {
        Object[] consultationArray = consultations.toArray();
        Consultation[] tempResults = new Consultation[consultationArray.length];
        int count = 0;
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getStatus().toLowerCase().contains("scheduled")) {
                    tempResults[count++] = consultation;
                }
            }
        }
        
        Consultation[] results = new Consultation[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //get completed consultations
    public Consultation[] getCompletedConsultations() {
        Object[] consultationArray = consultations.toArray();
        Consultation[] tempResults = new Consultation[consultationArray.length];
        int count = 0;
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getStatus().toLowerCase().contains("completed")) {
                    tempResults[count++] = consultation;
                }
            }
        }
        
        Consultation[] results = new Consultation[count];
        for (int i = 0; i < count; i++) {
            results[i] = tempResults[i];
        }
        return results;
    }
    
    //generate consultation report
    public String generateConsultationReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== CONSULTATION MANAGEMENT REPORT ===\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n\n");
        
        Object[] consultationArray = consultations.toArray();
        int totalConsultations = 0;
        int scheduledConsultations = 0;
        int completedConsultations = 0;
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                totalConsultations++;
                
                report.append(String.format("ID: %s | Patient: %s | Doctor: %s | Date: %s | Status: %s\n", 
                    consultation.getConsultationId(), consultation.getPatientId(), 
                    consultation.getDoctorId(), consultation.getConsultationDate(),
                    consultation.getStatus()));
                
                if (consultation.getStatus().toLowerCase().contains("scheduled")) {
                    scheduledConsultations++;
                }
                if (consultation.getStatus().toLowerCase().contains("completed")) {
                    completedConsultations++;
                }
            }
        }
        
        report.append("\n=== SUMMARY ===\n");
        report.append("Total Consultations: ").append(totalConsultations).append("\n");
        report.append("Scheduled Consultations: ").append(scheduledConsultations).append("\n");
        report.append("Completed Consultations: ").append(completedConsultations).append("\n");
        
        return report.toString();
    }
    
    //find consultation by ID
    private Consultation findConsultationById(String consultationId) {
        Object[] consultationArray = consultations.toArray();
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getConsultationId().equals(consultationId)) {
                    return consultation;
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