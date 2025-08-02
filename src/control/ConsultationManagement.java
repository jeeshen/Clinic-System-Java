package control;

import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultationManagement {
    private SetAndQueueInterface<Consultation> consultations = new SetAndQueue<>();
    private SetAndQueueInterface<Patient> patients = new SetAndQueue<>();
    private SetAndQueueInterface<Doctor> doctors = new SetAndQueue<>();
    private SetAndQueueInterface<Consultation> appointmentQueue = new SetAndQueue<>(); //queue for appointments
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public ConsultationManagement(SetAndQueueInterface<Consultation> consultations, SetAndQueueInterface<Patient> patients, SetAndQueueInterface<Doctor> doctors) {
        this.consultations = consultations;
        this.patients = patients;
        this.doctors = doctors;
    }
    
    //schedule new consultation
    public boolean scheduleConsultation(Consultation consultation) {
        if (consultation != null && !consultations.contains(consultation)) {
            boolean added = consultations.add(consultation);
            if (added) {
                //add to appointment queue for scheduling
                appointmentQueue.enqueue(consultation);
            }
            return added;
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
    
    //get next appointment from queue
    public Consultation getNextAppointment() {
        return appointmentQueue.getFront();
    }
    
    //process next appointment (dequeue)
    public Consultation processNextAppointment() {
        return appointmentQueue.dequeue();
    }
    
    //check if appointment queue is empty
    public boolean isAppointmentQueueEmpty() {
        return appointmentQueue.isQueueEmpty();
    }
    
    //clear appointment queue
    public void clearAppointmentQueue() {
        appointmentQueue.clearQueue();
    }
    
    //get appointment queue size
    public int getAppointmentQueueSize() {
        return appointmentQueue.size();
    }
    
    //get consultations by status using set operations
    public SetAndQueueInterface<Consultation> getConsultationsByStatusSet(String status) {
        SetAndQueueInterface<Consultation> statusConsultations = new SetAndQueue<>();
        Object[] consultationArray = consultations.toArray();
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getStatus().toLowerCase().contains(status.toLowerCase())) {
                    statusConsultations.add(consultation);
                }
            }
        }
        return statusConsultations;
    }
    
    //get consultations by doctor using set operations
    public SetAndQueueInterface<Consultation> getConsultationsByDoctorSet(String doctorId) {
        SetAndQueueInterface<Consultation> doctorConsultations = new SetAndQueue<>();
        Object[] consultationArray = consultations.toArray();
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getDoctorId().equals(doctorId)) {
                    doctorConsultations.add(consultation);
                }
            }
        }
        return doctorConsultations;
    }
    
    //get consultations by patient using set operations
    public SetAndQueueInterface<Consultation> getConsultationsByPatientSet(String patientId) {
        SetAndQueueInterface<Consultation> patientConsultations = new SetAndQueue<>();
        Object[] consultationArray = consultations.toArray();
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getPatientId().equals(patientId)) {
                    patientConsultations.add(consultation);
                }
            }
        }
        return patientConsultations;
    }
    
    //union: consultations by doctor OR by status
    public SetAndQueueInterface<Consultation> getConsultationsByDoctorOrStatus(String doctorId, String status) {
        SetAndQueueInterface<Consultation> doctorConsultations = getConsultationsByDoctorSet(doctorId);
        SetAndQueueInterface<Consultation> statusConsultations = getConsultationsByStatusSet(status);
        return doctorConsultations.union(statusConsultations);
    }
    
    //intersection: consultations by doctor AND by status
    public SetAndQueueInterface<Consultation> getConsultationsByDoctorAndStatus(String doctorId, String status) {
        SetAndQueueInterface<Consultation> doctorConsultations = getConsultationsByDoctorSet(doctorId);
        SetAndQueueInterface<Consultation> statusConsultations = getConsultationsByStatusSet(status);
        return doctorConsultations.intersection(statusConsultations);
    }
    
    //difference: consultations by doctor but NOT with specific status
    public SetAndQueueInterface<Consultation> getConsultationsByDoctorNotStatus(String doctorId, String status) {
        SetAndQueueInterface<Consultation> doctorConsultations = getConsultationsByDoctorSet(doctorId);
        SetAndQueueInterface<Consultation> statusConsultations = getConsultationsByStatusSet(status);
        return doctorConsultations.difference(statusConsultations);
    }
    
    //check if all doctor consultations are completed (subset operation)
    public boolean areAllDoctorConsultationsCompleted(String doctorId) {
        SetAndQueueInterface<Consultation> doctorConsultations = getConsultationsByDoctorSet(doctorId);
        SetAndQueueInterface<Consultation> completedConsultations = getConsultationsByStatusSet("completed");
        return doctorConsultations.isSubsetOf(completedConsultations);
    }
    
    //get consultations with multiple criteria using intersection
    public SetAndQueueInterface<Consultation> getConsultationsWithMultipleCriteria(String doctorId, String patientId, String status) {
        SetAndQueueInterface<Consultation> doctorConsultations = getConsultationsByDoctorSet(doctorId);
        SetAndQueueInterface<Consultation> patientConsultations = getConsultationsByPatientSet(patientId);
        SetAndQueueInterface<Consultation> statusConsultations = getConsultationsByStatusSet(status);
        
        SetAndQueueInterface<Consultation> temp = doctorConsultations.intersection(patientConsultations);
        return temp.intersection(statusConsultations);
    }
    
    //get urgent consultations (scheduled + specific criteria)
    public SetAndQueueInterface<Consultation> getUrgentConsultations() {
        SetAndQueueInterface<Consultation> scheduledConsultations = getConsultationsByStatusSet("scheduled");
        SetAndQueueInterface<Consultation> urgentConsultations = new SetAndQueue<>();
        
        Object[] consultationArray = consultations.toArray();
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                //check for urgent indicators in notes or other fields
                if (consultation.getNotes() != null && consultation.getNotes().toLowerCase().contains("urgent")) {
                    urgentConsultations.add(consultation);
                }
            }
        }
        
        return scheduledConsultations.intersection(urgentConsultations);
    }
    
    //check if consultation sets are equal
    public boolean areConsultationSetsEqual(Consultation[] set1, Consultation[] set2) {
        SetAndQueueInterface<Consultation> queue1 = new SetAndQueue<>();
        SetAndQueueInterface<Consultation> queue2 = new SetAndQueue<>();
        
        for (Consultation consultation : set1) {
            queue1.add(consultation);
        }
        for (Consultation consultation : set2) {
            queue2.add(consultation);
        }
        
        return queue1.isEqual(queue2);
    }
    
    //get consultation statistics using set operations
    public String getConsultationStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== CONSULTATION STATISTICS ===\n");
        
        SetAndQueueInterface<Consultation> scheduledConsultations = getConsultationsByStatusSet("scheduled");
        SetAndQueueInterface<Consultation> completedConsultations = getConsultationsByStatusSet("completed");
        SetAndQueueInterface<Consultation> cancelledConsultations = getConsultationsByStatusSet("cancelled");
        
        stats.append("Scheduled Consultations: ").append(scheduledConsultations.size()).append("\n");
        stats.append("Completed Consultations: ").append(completedConsultations.size()).append("\n");
        stats.append("Cancelled Consultations: ").append(cancelledConsultations.size()).append("\n");
        
        //union of all status types
        SetAndQueueInterface<Consultation> allStatusConsultations = scheduledConsultations.union(completedConsultations);
        allStatusConsultations = allStatusConsultations.union(cancelledConsultations);
        stats.append("Total Consultations with Status: ").append(allStatusConsultations.size()).append("\n");
        
        return stats.toString();
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

        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        report.append("Appointment Queue Size: ").append(getAppointmentQueueSize()).append("\n");
        report.append("Queue Empty: ").append(isAppointmentQueueEmpty() ? "Yes" : "No").append("\n");
        
        SetAndQueueInterface<Consultation> urgentConsultations = getUrgentConsultations();
        report.append("Urgent Consultations: ").append(urgentConsultations.size()).append("\n");
        
        SetAndQueueInterface<Consultation> scheduledOrCompleted = getConsultationsByStatusSet("scheduled").union(getConsultationsByStatusSet("completed"));
        report.append("Scheduled OR Completed: ").append(scheduledOrCompleted.size()).append("\n");
        
        report.append(getConsultationStatistics());
        
        return report.toString();
    }
    
    //find consultation by ID
    private Consultation findConsultationById(String consultationId) {
        Object[] consultationArray = consultations.toArray();
        
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                Consultation consultation = (Consultation) obj;
                if (consultation.getConsultationId().equalsIgnoreCase(consultationId)) {
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
    
    //check if consultations set is empty
    public boolean isConsultationDatabaseEmpty() {
        return consultations.isEmpty();
    }
    
    //clear all consultations (use with caution)
    public void clearAllConsultations() {
        consultations.clearSet();
        appointmentQueue.clearQueue();
    }
    
    //get total number of consultations
    public int getTotalConsultationCount() {
        return consultations.size();
    }
    
    //check if all consultations contain specific status
    public boolean containsAllConsultationsWithStatus(String status) {
        SetAndQueueInterface<Consultation> allConsultations = new SetAndQueue<>();
        Object[] consultationArray = consultations.toArray();
        for (Object obj : consultationArray) {
            if (obj instanceof Consultation) {
                allConsultations.add((Consultation) obj);
            }
        }
        
        SetAndQueueInterface<Consultation> statusConsultations = getConsultationsByStatusSet(status);
        return allConsultations.containsAll(statusConsultations);
    }
} 