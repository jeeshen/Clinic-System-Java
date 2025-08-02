package control;

import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import entity.Medicine;
import entity.Treatment;
import entity.PharmacyTransaction;
import dao.DataInitializer;
import utility.StringUtility;
import adt.SetAndQueue;
import adt.SetAndQueueInterface;
import java.util.Date;
import java.text.SimpleDateFormat;

public class HospitalReportGenerator {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static String generatePatientReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC PATIENT MANAGEMENT REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //patient data table
        Patient[] patients = DataInitializer.initializeSamplePatients();
        report.append("PATIENT DETAILS TABLE:\n");
        report.append(String.format("%-8s %-25s %-8s %-10s %-15s %-20s\n", 
            "ID", "Name", "Age", "Gender", "Phone", "Medical Condition"));
        report.append(StringUtility.repeatString("-", 90)).append("\n");
        
        int totalPatients = 0;
        int maleCount = 0, femaleCount = 0;
        int adultCount = 0, childCount = 0;
        SetAndQueueInterface<String> conditions = new SetAndQueue<>();
        
        for (Patient patient : patients) {
            totalPatients++;
            report.append(String.format("%-8d %-25s %-8d %-10s %-15s %-20s\n",
                patient.getId(), patient.getName(), patient.getAge(), 
                patient.getGender(), patient.getContactNumber(), patient.getMedicalHistory()));
            
            //count statistics
            if (patient.getGender().equalsIgnoreCase("Male")) maleCount++;
            else femaleCount++;
            
            if (patient.getAge() >= 18) adultCount++;
            else childCount++;
            
            String condition = patient.getMedicalHistory();
            conditions.add(condition);
        }
        
        //summary statistics
        report.append("\nSUMMARY STATISTICS:\n");
        report.append("• Total Number of Patients: ").append(totalPatients).append("\n");
        report.append("• Male Patients: ").append(maleCount).append("\n");
        report.append("• Female Patients: ").append(femaleCount).append("\n");
        report.append("• Adult Patients (18+): ").append(adultCount).append("\n");
        report.append("• Child Patients (<18): ").append(childCount).append("\n");
        
        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create sets for different patient categories
        SetAndQueueInterface<String> malePatients = new SetAndQueue<>();
        SetAndQueueInterface<String> femalePatients = new SetAndQueue<>();
        SetAndQueueInterface<String> adultPatients = new SetAndQueue<>();
        SetAndQueueInterface<String> childPatients = new SetAndQueue<>();
        
        for (Patient patient : patients) {
            if (patient.getGender().equalsIgnoreCase("Male")) {
                malePatients.add(patient.getName());
            } else {
                femalePatients.add(patient.getName());
            }
            
            if (patient.getAge() >= 18) {
                adultPatients.add(patient.getName());
            } else {
                childPatients.add(patient.getName());
            }
        }
        
        //union operations
        SetAndQueueInterface<String> allPatients = malePatients.union(femalePatients);
        report.append("Total Unique Patients (Union): ").append(allPatients.size()).append("\n");
        
        //intersection operations
        SetAndQueueInterface<String> adultMales = adultPatients.intersection(malePatients);
        report.append("Adult Male Patients (Intersection): ").append(adultMales.size()).append("\n");
        
        //difference operations
        SetAndQueueInterface<String> adultFemales = adultPatients.difference(malePatients);
        report.append("Adult Female Patients (Difference): ").append(adultFemales.size()).append("\n");
        
        //subset operations
        boolean areAllMalesAdults = malePatients.isSubsetOf(adultPatients);
        report.append("Are All Males Adults (Subset): ").append(areAllMalesAdults ? "Yes" : "No").append("\n");
        
        //equality operations
        boolean arePatientSetsEqual = malePatients.isEqual(femalePatients);
        report.append("Are Male and Female Sets Equal: ").append(arePatientSetsEqual ? "Yes" : "No").append("\n");
        
        //contains operations
        boolean containsAllAdults = allPatients.containsAll(adultPatients);
        report.append("Contains All Adults: ").append(containsAllAdults ? "Yes" : "No").append("\n");
        
        //graphical representation
        report.append("\nGRAPHICAL REPRESENTATION OF PATIENT DATA:\n");
        report.append("Gender Distribution:\n");
        report.append(createBarChart("Male", maleCount, totalPatients, 20));
        report.append(createBarChart("Female", femaleCount, totalPatients, 20));
        
        report.append("\nAge Group Distribution:\n");
        report.append(createBarChart("Adults (18+)", adultCount, totalPatients, 20));
        report.append(createBarChart("Children (<18)", childCount, totalPatients, 20));
        
        //medical conditions summary
        report.append("\nMEDICAL CONDITIONS SUMMARY:\n");
        Object[] conditionArray = conditions.toArray();
        for (Object obj : conditionArray) {
            if (obj instanceof String) {
                report.append("• ").append((String)obj).append(" patients\n");
            }
        }
        
        report.append("\nEND OF PATIENT REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generateDoctorReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC DOCTOR MANAGEMENT REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //doctor data table
        Doctor[] doctors = DataInitializer.initializeSampleDoctors();
        report.append("DOCTOR DETAILS TABLE:\n");
        report.append(String.format("%-8s %-30s %-15s %-12s %-15s\n", 
            "ID", "Name", "Specialization", "Available", "On Leave"));
        report.append(StringUtility.repeatString("-", 90)).append("\n");
        
        int totalDoctors = 0;
        int availableCount = 0, onLeaveCount = 0;
        SetAndQueueInterface<String> specializations = new SetAndQueue<>();
        
        for (Doctor doctor : doctors) {
            totalDoctors++;
            report.append(String.format("%-8s %-30s %-15s %-12s %-15s\n",
                doctor.getDoctorId(), doctor.getName(), doctor.getSpecialization(),
                doctor.isIsAvailable() ? "Yes" : "No", doctor.isOnLeave() ? "Yes" : "No"));
            
            //count statistics
            if (doctor.isIsAvailable()) availableCount++;
            if (doctor.isOnLeave()) onLeaveCount++;
            
            String specialization = doctor.getSpecialization();
            specializations.add(specialization);
        }
        
        //summary statistics
        report.append("\nSUMMARY STATISTICS:\n");
        report.append("• Total Number of Doctors: ").append(totalDoctors).append("\n");
        report.append("• Available Doctors: ").append(availableCount).append("\n");
        report.append("• Doctors on Leave: ").append(onLeaveCount).append("\n");
        
        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create sets for different doctor categories
        SetAndQueueInterface<String> availableDoctors = new SetAndQueue<>();
        SetAndQueueInterface<String> onLeaveDoctors = new SetAndQueue<>();
        SetAndQueueInterface<String> cardiologists = new SetAndQueue<>();
        SetAndQueueInterface<String> emergencyDoctors = new SetAndQueue<>();
        
        for (Doctor doctor : doctors) {
            if (doctor.isIsAvailable()) {
                availableDoctors.add(doctor.getName());
            }
            if (doctor.isOnLeave()) {
                onLeaveDoctors.add(doctor.getName());
            }
            if (doctor.getSpecialization().toLowerCase().contains("cardiology")) {
                cardiologists.add(doctor.getName());
            }
            if (doctor.getSpecialization().toLowerCase().contains("emergency")) {
                emergencyDoctors.add(doctor.getName());
            }
        }
        
        //union operations
        SetAndQueueInterface<String> allDoctors = availableDoctors.union(onLeaveDoctors);
        report.append("Total Unique Doctors (Union): ").append(allDoctors.size()).append("\n");
        
        //intersection operations
        SetAndQueueInterface<String> availableCardiologists = availableDoctors.intersection(cardiologists);
        report.append("Available Cardiologists (Intersection): ").append(availableCardiologists.size()).append("\n");
        
        //difference operations
        SetAndQueueInterface<String> availableNotOnLeave = availableDoctors.difference(onLeaveDoctors);
        report.append("Available Not On Leave (Difference): ").append(availableNotOnLeave.size()).append("\n");
        
        //subset operations
        boolean areAllCardiologistsAvailable = cardiologists.isSubsetOf(availableDoctors);
        report.append("Are All Cardiologists Available (Subset): ").append(areAllCardiologistsAvailable ? "Yes" : "No").append("\n");
        
        //equality operations
        boolean areDoctorSetsEqual = availableDoctors.isEqual(onLeaveDoctors);
        report.append("Are Available and On Leave Sets Equal: ").append(areDoctorSetsEqual ? "Yes" : "No").append("\n");
        
        //contains operations
        boolean containsAllSpecialists = allDoctors.containsAll(cardiologists);
        report.append("Contains All Cardiologists: ").append(containsAllSpecialists ? "Yes" : "No").append("\n");
        
        //graphical representation
        report.append("\nGRAPHICAL REPRESENTATION OF DOCTOR DATA:\n");
        report.append("Availability Status:\n");
        report.append(createBarChart("Available", availableCount, totalDoctors, 20));
        report.append(createBarChart("On Leave", onLeaveCount, totalDoctors, 20));
        
        report.append("\nSpecialization Distribution:\n");
        Object[] specializationArray = specializations.toArray();
        for (Object obj : specializationArray) {
            if (obj instanceof String) {
                report.append(createBarChart((String)obj, 1, totalDoctors, 20));
            }
        }
        
        report.append("\nEND OF DOCTOR REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generateConsultationReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC CONSULTATION MANAGEMENT REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //consultation data table
        Consultation[] consultations = DataInitializer.initializeSampleConsultations();
        report.append("CONSULTATION DETAILS TABLE:\n");
        report.append(String.format("%-10s %-10s %-10s %-12s %-15s %-15s\n", 
            "ID", "Patient", "Doctor", "Date", "Status", "Next Appointment"));
        report.append(StringUtility.repeatString("-", 90)).append("\n");
        
        int totalConsultations = 0;
        int scheduledCount = 0, completedCount = 0;
        SetAndQueueInterface<String> doctorWorkload = new SetAndQueue<>();
        
        for (Consultation consultation : consultations) {
            totalConsultations++;
            report.append(String.format("%-10s %-10s %-10s %-12s %-15s %-15s\n",
                consultation.getConsultationId(), consultation.getPatientId(), 
                consultation.getDoctorId(), consultation.getConsultationDate(),
                consultation.getStatus(), consultation.getNextAppointmentDate() != null ? 
                consultation.getNextAppointmentDate() : "None"));
            
            //count statistics
            if (consultation.getStatus().equalsIgnoreCase("scheduled")) scheduledCount++;
            if (consultation.getStatus().equalsIgnoreCase("completed")) completedCount++;
            
            String doctorId = consultation.getDoctorId();
            doctorWorkload.add(doctorId);
        }
        
        //summary statistics
        report.append("\nSUMMARY STATISTICS:\n");
        report.append("• Total Number of Consultations: ").append(totalConsultations).append("\n");
        report.append("• Scheduled Consultations: ").append(scheduledCount).append("\n");
        report.append("• Completed Consultations: ").append(completedCount).append("\n");

        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create queue for appointment processing simulation
        SetAndQueueInterface<Consultation> appointmentQueue = new SetAndQueue<>();
        SetAndQueueInterface<String> scheduledConsultations = new SetAndQueue<>();
        SetAndQueueInterface<String> completedConsultations = new SetAndQueue<>();
        SetAndQueueInterface<String> urgentConsultations = new SetAndQueue<>();
        
        for (Consultation consultation : consultations) {
            //enqueue consultations for processing
            appointmentQueue.enqueue(consultation);
            
            if (consultation.getStatus().equalsIgnoreCase("scheduled")) {
                scheduledConsultations.add(consultation.getConsultationId());
            }
            if (consultation.getStatus().equalsIgnoreCase("completed")) {
                completedConsultations.add(consultation.getConsultationId());
            }
            if (consultation.getNotes() != null && consultation.getNotes().toLowerCase().contains("urgent")) {
                urgentConsultations.add(consultation.getConsultationId());
            }
        }
        
        //queue operations
        report.append("Appointment Queue Size: ").append(appointmentQueue.size()).append("\n");
        report.append("Queue Empty: ").append(appointmentQueue.isQueueEmpty() ? "Yes" : "No").append("\n");
        
        //process first appointment
        Consultation nextAppointment = appointmentQueue.getFront();
        if (nextAppointment != null) {
            report.append("Next Appointment: ").append(nextAppointment.getConsultationId()).append("\n");
        }
        
        //set operations
        SetAndQueueInterface<String> allConsultations = scheduledConsultations.union(completedConsultations);
        report.append("Total Unique Consultations (Union): ").append(allConsultations.size()).append("\n");
        
        SetAndQueueInterface<String> urgentAndScheduled = urgentConsultations.intersection(scheduledConsultations);
        report.append("Urgent Scheduled Consultations (Intersection): ").append(urgentAndScheduled.size()).append("\n");
        
        SetAndQueueInterface<String> scheduledNotCompleted = scheduledConsultations.difference(completedConsultations);
        report.append("Scheduled Not Completed (Difference): ").append(scheduledNotCompleted.size()).append("\n");
        
        boolean areAllScheduledCompleted = scheduledConsultations.isSubsetOf(completedConsultations);
        report.append("Are All Scheduled Completed (Subset): ").append(areAllScheduledCompleted ? "Yes" : "No").append("\n");
        
        //graphical representation
        report.append("\nGRAPHICAL REPRESENTATION OF CONSULTATION DATA:\n");
        report.append("Consultation Status:\n");
        report.append(createBarChart("Scheduled", scheduledCount, totalConsultations, 20));
        report.append(createBarChart("Completed", completedCount, totalConsultations, 20));
        
        report.append("\nDoctor Workload Distribution:\n");
        Object[] doctorArray = doctorWorkload.toArray();
        for (Object obj : doctorArray) {
            if (obj instanceof String) {
                report.append(createBarChart("Dr. " + (String)obj, 1, totalConsultations, 20));
            }
        }
        
        report.append("\nEND OF CONSULTATION REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generatePharmacyReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC PHARMACY MANAGEMENT REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //medicine data table
        Medicine[] medicines = DataInitializer.initializeSampleMedicines();
        report.append("MEDICINE INVENTORY TABLE:\n");
        report.append(String.format("%-8s %-20s %-15s %-8s %-10s %-15s %-15s\n", 
            "ID", "Name", "Brand", "Stock", "Price", "Purpose", "Category"));
        report.append(StringUtility.repeatString("-", 100)).append("\n");
        
        int totalMedicines = 0;
        int lowStockCount = 0;
        double totalValue = 0;
        SetAndQueueInterface<String> categories = new SetAndQueue<>();
        SetAndQueueInterface<String> purposes = new SetAndQueue<>();
        
        for (Medicine medicine : medicines) {
            totalMedicines++;
            report.append(String.format("%-8s %-20s %-15s %-8d %-10.2f %-15s %-15s\n",
                medicine.getMedicineId(), medicine.getName(), medicine.getBrand(),
                medicine.getStockQuantity(), medicine.getPrice(), medicine.getPurpose(), 
                medicine.getCategory()));
            
            //count statistics
            if (medicine.getStockQuantity() < 10) lowStockCount++;
            totalValue += medicine.getStockQuantity() * medicine.getPrice();
            
            String category = medicine.getCategory();
            categories.add(category);
            
            String purpose = medicine.getPurpose();
            purposes.add(purpose);
        }
        
        //summary statistics
        report.append("\nSUMMARY STATISTICS:\n");
        report.append("• Total Number of Medicines: ").append(totalMedicines).append("\n");
        report.append("• Low Stock Items (< 10): ").append(lowStockCount).append("\n");
        report.append("• Total Inventory Value: RM").append(String.format("%.2f", totalValue)).append("\n");
        
        //advanced adt analytics with queue operations
        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        // Create queue for medicine dispensing simulation
        SetAndQueueInterface<Medicine> dispensingQueue = new SetAndQueue<>();
        SetAndQueueInterface<String> lowStockMedicines = new SetAndQueue<>();
        SetAndQueueInterface<String> essentialMedicines = new SetAndQueue<>();
        SetAndQueueInterface<String> antibioticMedicines = new SetAndQueue<>();
        
        for (Medicine medicine : medicines) {
            //enqueue medicines for dispensing
            dispensingQueue.enqueue(medicine);
            
            if (medicine.getStockQuantity() < 10) {
                lowStockMedicines.add(medicine.getMedicineId());
            }
            if (medicine.getCategory().toLowerCase().contains("antibiotic")) {
                essentialMedicines.add(medicine.getMedicineId());
            }
            if (medicine.getPurpose().toLowerCase().contains("antibiotic")) {
                antibioticMedicines.add(medicine.getMedicineId());
            }
        }
        
        //queue operations
        report.append("Dispensing Queue Size: ").append(dispensingQueue.size()).append("\n");
        report.append("Queue Empty: ").append(dispensingQueue.isQueueEmpty() ? "Yes" : "No").append("\n");
        
        //process next medicine
        Medicine nextMedicine = dispensingQueue.getFront();
        if (nextMedicine != null) {
            report.append("Next Medicine to Dispense: ").append(nextMedicine.getName()).append("\n");
        }
        
        //set operations
        SetAndQueueInterface<String> allMedicines = lowStockMedicines.union(essentialMedicines);
        report.append("Total Unique Medicines (Union): ").append(allMedicines.size()).append("\n");
        
        SetAndQueueInterface<String> lowStockAndEssential = lowStockMedicines.intersection(essentialMedicines);
        report.append("Low Stock Essential Medicines (Intersection): ").append(lowStockAndEssential.size()).append("\n");
        
        SetAndQueueInterface<String> essentialNotLowStock = essentialMedicines.difference(lowStockMedicines);
        report.append("Essential Not Low Stock (Difference): ").append(essentialNotLowStock.size()).append("\n");
        
        boolean areAllEssentialLowStock = essentialMedicines.isSubsetOf(lowStockMedicines);
        report.append("Are All Essential Low Stock (Subset): ").append(areAllEssentialLowStock ? "Yes" : "No").append("\n");
        
        //graphical representation
        report.append("\nGRAPHICAL REPRESENTATION OF PHARMACY DATA:\n");
        report.append("Stock Levels:\n");
        report.append(createBarChart("Low Stock (<10)", lowStockCount, totalMedicines, 20));
        report.append(createBarChart("Adequate Stock (≥10)", totalMedicines - lowStockCount, totalMedicines, 20));
        
        report.append("\nMedicine Categories:\n");
        Object[] categoryArray = categories.toArray();
        for (Object obj : categoryArray) {
            if (obj instanceof String) {
                report.append(createBarChart((String)obj, 1, totalMedicines, 20));
            }
        }
        
        report.append("\nMedicine Purposes:\n");
        Object[] purposeArray = purposes.toArray();
        for (Object obj : purposeArray) {
            if (obj instanceof String) {
                report.append(createBarChart((String)obj, 1, totalMedicines, 20));
            }
        }
        
        report.append("\nEND OF PHARMACY REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generateTreatmentReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC TREATMENT MANAGEMENT REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //treatment data table
        Treatment[] treatments = DataInitializer.initializeSampleTreatments();
        report.append("TREATMENT DETAILS TABLE:\n");
        report.append(String.format("%-8s %-10s %-10s %-20s %-15s %-12s %-12s\n", 
            "ID", "Patient", "Doctor", "Diagnosis", "Medication", "Start Date", "Follow-up"));
        report.append(StringUtility.repeatString("-", 100)).append("\n");
        
        int totalTreatments = 0;
        int withFollowUpCount = 0;
        SetAndQueueInterface<String> diagnoses = new SetAndQueue<>();
        SetAndQueueInterface<String> doctorWorkload = new SetAndQueue<>();
        
        for (Treatment treatment : treatments) {
            totalTreatments++;
            report.append(String.format("%-8s %-10s %-10s %-20s %-15s %-12s %-12s\n",
                treatment.getTreatmentId(), treatment.getPatientId(), treatment.getDoctorId(),
                treatment.getDiagnosis(), treatment.getPrescribedMedications(),
                treatment.getTreatmentDate(), treatment.getFollowUpDate() != null ? 
                treatment.getFollowUpDate() : "None"));
            
            //count statistics
            if (treatment.getFollowUpDate() != null && !treatment.getFollowUpDate().isEmpty()) {
                withFollowUpCount++;
            }
            
            String diagnosis = treatment.getDiagnosis();
            diagnoses.add(diagnosis);
            
            String doctorId = treatment.getDoctorId();
            doctorWorkload.add(doctorId);
        }
        
        //summary statistics
        report.append("\nSUMMARY STATISTICS:\n");
        report.append("• Total Number of Treatments: ").append(totalTreatments).append("\n");
        report.append("• Treatments with Follow-up: ").append(withFollowUpCount).append("\n");
        report.append("• Treatments without Follow-up: ").append(totalTreatments - withFollowUpCount).append("\n");
        
        //advanced adt analytics
        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create sets for different treatment categories
        SetAndQueueInterface<String> followUpTreatments = new SetAndQueue<>();
        SetAndQueueInterface<String> urgentTreatments = new SetAndQueue<>();
        SetAndQueueInterface<String> cardiologyTreatments = new SetAndQueue<>();
        SetAndQueueInterface<String> emergencyTreatments = new SetAndQueue<>();
        
        for (Treatment treatment : treatments) {
            if (treatment.getFollowUpDate() != null && !treatment.getFollowUpDate().isEmpty()) {
                followUpTreatments.add(treatment.getTreatmentId());
            }
            if (treatment.getDiagnosis().toLowerCase().contains("urgent")) {
                urgentTreatments.add(treatment.getTreatmentId());
            }
            if (treatment.getDiagnosis().toLowerCase().contains("cardiology")) {
                cardiologyTreatments.add(treatment.getTreatmentId());
            }
            if (treatment.getDiagnosis().toLowerCase().contains("emergency")) {
                emergencyTreatments.add(treatment.getTreatmentId());
            }
        }
        
        //set operations
        SetAndQueueInterface<String> allTreatments = followUpTreatments.union(urgentTreatments);
        report.append("Total Unique Treatments (Union): ").append(allTreatments.size()).append("\n");
        
        SetAndQueueInterface<String> urgentWithFollowUp = urgentTreatments.intersection(followUpTreatments);
        report.append("Urgent Treatments with Follow-up (Intersection): ").append(urgentWithFollowUp.size()).append("\n");
        
        SetAndQueueInterface<String> urgentNotFollowUp = urgentTreatments.difference(followUpTreatments);
        report.append("Urgent Treatments without Follow-up (Difference): ").append(urgentNotFollowUp.size()).append("\n");
        
        boolean areAllUrgentWithFollowUp = urgentTreatments.isSubsetOf(followUpTreatments);
        report.append("Are All Urgent Treatments with Follow-up (Subset): ").append(areAllUrgentWithFollowUp ? "Yes" : "No").append("\n");
        
        //equality operations
        boolean areTreatmentSetsEqual = followUpTreatments.isEqual(urgentTreatments);
        report.append("Are Follow-up and Urgent Sets Equal: ").append(areTreatmentSetsEqual ? "Yes" : "No").append("\n");
        
        //contains operations
        boolean containsAllFollowUp = allTreatments.containsAll(followUpTreatments);
        report.append("Contains All Follow-up Treatments: ").append(containsAllFollowUp ? "Yes" : "No").append("\n");
        
        //graphical representation
        report.append("\nGRAPHICAL REPRESENTATION OF TREATMENT DATA:\n");
        report.append("Follow-up Status:\n");
        report.append(createBarChart("With Follow-up", withFollowUpCount, totalTreatments, 20));
        report.append(createBarChart("Without Follow-up", totalTreatments - withFollowUpCount, totalTreatments, 20));
        
        report.append("\nDiagnosis Distribution:\n");
        Object[] diagnosisArray = diagnoses.toArray();
        for (Object obj : diagnosisArray) {
            if (obj instanceof String) {
                report.append(createBarChart((String)obj, 1, totalTreatments, 20));
            }
        }
        
        report.append("\nDoctor Treatment Workload:\n");
        Object[] doctorArray = doctorWorkload.toArray();
        for (Object obj : doctorArray) {
            if (obj instanceof String) {
                report.append(createBarChart("Dr. " + (String)obj, 1, totalTreatments, 20));
            }
        }
        
        report.append("\nEND OF TREATMENT REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generateSystemSummaryReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC SYSTEM SUMMARY REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //system overview table
        Patient[] patients = DataInitializer.initializeSamplePatients();
        Doctor[] doctors = DataInitializer.initializeSampleDoctors();
        Consultation[] consultations = DataInitializer.initializeSampleConsultations();
        Medicine[] medicines = DataInitializer.initializeSampleMedicines();
        Treatment[] treatments = DataInitializer.initializeSampleTreatments();
        PharmacyTransaction[] transactions = DataInitializer.initializeSampleTransactions();
        
        report.append("SYSTEM OVERVIEW TABLE:\n");
        report.append(String.format("%-25s %-15s %-20s\n", "Module", "Count", "Status"));
        report.append(StringUtility.repeatString("-", 60)).append("\n");
        report.append(String.format("%-25s %-15d %-20s\n", "Patients", patients.length, "Active"));
        report.append(String.format("%-25s %-15d %-20s\n", "Doctors", doctors.length, "Active"));
        report.append(String.format("%-25s %-15d %-20s\n", "Consultations", consultations.length, "Active"));
        report.append(String.format("%-25s %-15d %-20s\n", "Medicines", medicines.length, "Active"));
        report.append(String.format("%-25s %-15d %-20s\n", "Treatments", treatments.length, "Active"));
        report.append(String.format("%-25s %-15d %-20s\n", "Transactions", transactions.length, "Active"));
        
        //comprehensive adt analytics
        report.append("\n=== COMPREHENSIVE ADT ANALYTICS ===\n");
        
        //create comprehensive sets for cross-module analysis
        SetAndQueueInterface<String> allPatientNames = new SetAndQueue<>();
        SetAndQueueInterface<String> allDoctorNames = new SetAndQueue<>();
        SetAndQueueInterface<String> allConsultationIds = new SetAndQueue<>();
        SetAndQueueInterface<String> allMedicineIds = new SetAndQueue<>();
        SetAndQueueInterface<String> allTreatmentIds = new SetAndQueue<>();
        
        for (Patient patient : patients) {
            allPatientNames.add(patient.getName());
        }
        for (Doctor doctor : doctors) {
            allDoctorNames.add(doctor.getName());
        }
        for (Consultation consultation : consultations) {
            allConsultationIds.add(consultation.getConsultationId());
        }
        for (Medicine medicine : medicines) {
            allMedicineIds.add(medicine.getMedicineId());
        }
        for (Treatment treatment : treatments) {
            allTreatmentIds.add(treatment.getTreatmentId());
        }
        
        //cross-module set operations
        report.append("Total Unique Patient Names: ").append(allPatientNames.size()).append("\n");
        report.append("Total Unique Doctor Names: ").append(allDoctorNames.size()).append("\n");
        report.append("Total Unique Consultation IDs: ").append(allConsultationIds.size()).append("\n");
        report.append("Total Unique Medicine IDs: ").append(allMedicineIds.size()).append("\n");
        report.append("Total Unique Treatment IDs: ").append(allTreatmentIds.size()).append("\n");
        
        //union of all entities
        SetAndQueueInterface<String> allEntities = allPatientNames.union(allDoctorNames);
        allEntities = allEntities.union(allConsultationIds);
        allEntities = allEntities.union(allMedicineIds);
        allEntities = allEntities.union(allTreatmentIds);
        report.append("Total Unique Entities Across All Modules: ").append(allEntities.size()).append("\n");
        
        //key performance indicators
        int availableDoctors = 0;
        for (Doctor doctor : doctors) {
            if (doctor.isIsAvailable()) availableDoctors++;
        }
        
        int completedConsultations = 0;
        for (Consultation consultation : consultations) {
            if (consultation.getStatus().equalsIgnoreCase("completed")) completedConsultations++;
        }
        
        int lowStockMedicines = 0;
        double totalInventoryValue = 0;
        for (Medicine medicine : medicines) {
            if (medicine.getStockQuantity() < 10) lowStockMedicines++;
            totalInventoryValue += medicine.getStockQuantity() * medicine.getPrice();
        }
        
        report.append("\nKEY PERFORMANCE INDICATORS:\n");
        report.append("• Doctor Availability Rate: ").append(String.format("%.1f%%", (double)availableDoctors/doctors.length*100)).append("\n");
        report.append("• Consultation Completion Rate: ").append(String.format("%.1f%%", (double)completedConsultations/consultations.length*100)).append("\n");
        report.append("• Low Stock Alert Rate: ").append(String.format("%.1f%%", (double)lowStockMedicines/medicines.length*100)).append("\n");
        report.append("• Total Inventory Value: RM").append(String.format("%.2f", totalInventoryValue)).append("\n");
        
        //system health status
        report.append("\nSYSTEM HEALTH STATUS:\n");
        report.append("• Patient Management: ").append(patients.length > 0 ? "✅ Operational" : "❌ No Data").append("\n");
        report.append("• Doctor Management: ").append(doctors.length > 0 ? "✅ Operational" : "❌ No Data").append("\n");
        report.append("• Consultation System: ").append(consultations.length > 0 ? "✅ Operational" : "❌ No Data").append("\n");
        report.append("• Pharmacy System: ").append(medicines.length > 0 ? "✅ Operational" : "❌ No Data").append("\n");
        report.append("• Treatment System: ").append(treatments.length > 0 ? "✅ Operational" : "❌ No Data").append("\n");
        report.append("• Transaction System: ").append(transactions.length > 0 ? "✅ Operational" : "❌ No Data").append("\n");
        
        report.append("\nEND OF SYSTEM SUMMARY REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generateFinancialReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC FINANCIAL SUMMARY REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //financial data table
        Medicine[] medicines = DataInitializer.initializeSampleMedicines();
        PharmacyTransaction[] transactions = DataInitializer.initializeSampleTransactions();
        
        report.append("FINANCIAL OVERVIEW TABLE:\n");
        report.append(String.format("%-15s %-15s %-15s %-15s\n", "Category", "Count", "Total Value", "Average Value"));
        report.append(StringUtility.repeatString("-", 60)).append("\n");
        
        double totalInventoryValue = 0;
        double totalTransactionValue = 0;
        SetAndQueueInterface<String> categories = new SetAndQueue<>();
        
        for (Medicine medicine : medicines) {
            double value = medicine.getStockQuantity() * medicine.getPrice();
            totalInventoryValue += value;
            String category = medicine.getCategory();
            categories.add(category);
        }
        
        for (PharmacyTransaction transaction : transactions) {
            //find medicine price for transaction
            for (Medicine medicine : medicines) {
                if (medicine.getMedicineId().equals(transaction.getMedicineId())) {
                    totalTransactionValue += transaction.getQuantityDispensed() * medicine.getPrice();
                    break;
                }
            }
        }
        
        report.append(String.format("%-15s %-15d %-15.2f %-15.2f\n", 
            "Inventory", medicines.length, totalInventoryValue, totalInventoryValue/medicines.length));
        report.append(String.format("%-15s %-15d %-15.2f %-15.2f\n", 
            "Transactions", transactions.length, totalTransactionValue, totalTransactionValue/transactions.length));
        
        //advanced financial adt analytics
        report.append("\n=== ADVANCED FINANCIAL ADT ANALYTICS ===\n");
        
        //create sets for financial analysis
        SetAndQueueInterface<String> highValueMedicines = new SetAndQueue<>();
        SetAndQueueInterface<String> lowValueMedicines = new SetAndQueue<>();
        SetAndQueueInterface<String> expensiveCategories = new SetAndQueue<>();
        SetAndQueueInterface<String> affordableCategories = new SetAndQueue<>();
        
        double averagePrice = totalInventoryValue / medicines.length;
        
        for (Medicine medicine : medicines) {
            if (medicine.getPrice() > averagePrice) {
                highValueMedicines.add(medicine.getMedicineId());
            } else {
                lowValueMedicines.add(medicine.getMedicineId());
            }
            
            if (medicine.getPrice() > 50.0) {
                expensiveCategories.add(medicine.getCategory());
            } else {
                affordableCategories.add(medicine.getCategory());
            }
        }
        
        //financial set operations
        SetAndQueueInterface<String> allValueMedicines = highValueMedicines.union(lowValueMedicines);
        report.append("Total Unique Value Categories (Union): ").append(allValueMedicines.size()).append("\n");
        
        SetAndQueueInterface<String> expensiveHighValue = expensiveCategories.intersection(highValueMedicines);
        report.append("Expensive High-Value Medicines (Intersection): ").append(expensiveHighValue.size()).append("\n");
        
        SetAndQueueInterface<String> affordableNotHighValue = affordableCategories.difference(highValueMedicines);
        report.append("Affordable Not High-Value (Difference): ").append(affordableNotHighValue.size()).append("\n");
        
        boolean areAllExpensiveHighValue = expensiveCategories.isSubsetOf(highValueMedicines);
        report.append("Are All Expensive High-Value (Subset): ").append(areAllExpensiveHighValue ? "Yes" : "No").append("\n");
        
        //category breakdown
        report.append("\nINVENTORY VALUE BY CATEGORY:\n");
        Object[] categoryArray = categories.toArray();
        for (Object obj : categoryArray) {
            if (obj instanceof String) {
                report.append(String.format("• %s: RM%.2f\n", (String)obj, totalInventoryValue/categories.size()));
            }
        }
        
        //financial metrics
        report.append("\nFINANCIAL METRICS:\n");
        report.append("• Total Inventory Value: RM").append(String.format("%.2f", totalInventoryValue)).append("\n");
        report.append("• Total Transaction Value: RM").append(String.format("%.2f", totalTransactionValue)).append("\n");
        report.append("• Average Medicine Price: RM").append(String.format("%.2f", totalInventoryValue/medicines.length)).append("\n");
        report.append("• Average Transaction Value: RM").append(String.format("%.2f", totalTransactionValue/transactions.length)).append("\n");
        
        //graphical representation
        report.append("\nGRAPHICAL REPRESENTATION OF FINANCIAL DATA:\n");
        report.append("Inventory Value Distribution:\n");
        for (Object obj : categoryArray) {
            if (obj instanceof String) {
                double percentage = (totalInventoryValue/categories.size() / totalInventoryValue) * 100;
                report.append(createBarChart((String)obj, (int)percentage, 100, 20));
            }
        }
        
        report.append("\nEND OF FINANCIAL REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generatePatientDoctorConsultationReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC PATIENT-DOCTOR-CONSULTATION RELATIONSHIP REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //initialize all data
        Patient[] patients = DataInitializer.initializeSamplePatients();
        Doctor[] doctors = DataInitializer.initializeSampleDoctors();
        Consultation[] consultations = DataInitializer.initializeSampleConsultations();
        
        //create relationship mappings using ADT
        SetAndQueueInterface<String> patientNames = new SetAndQueue<>();
        SetAndQueueInterface<String> doctorNames = new SetAndQueue<>();
        SetAndQueueInterface<String> doctorSpecializations = new SetAndQueue<>();
        SetAndQueueInterface<String> consultationStatuses = new SetAndQueue<>();
        
        //build patient and doctor mappings
        for (Patient patient : patients) {
            patientNames.add(patient.getName());
        }
        
        for (Doctor doctor : doctors) {
            doctorNames.add(doctor.getName());
            doctorSpecializations.add(doctor.getSpecialization());
        }
        
        //relationship analysis table
        report.append("PATIENT-DOCTOR CONSULTATION RELATIONSHIP TABLE:\n");
        report.append(String.format("%-10s %-20s %-15s %-20s %-15s %-12s\n", 
            "Patient ID", "Patient Name", "Doctor ID", "Doctor Name", "Specialization", "Status"));
        report.append(StringUtility.repeatString("-", 100)).append("\n");
        
        //create ADT sets for analysis
        SetAndQueueInterface<String> doctorWorkload = new SetAndQueue<>();
        SetAndQueueInterface<String> patientVisitCount = new SetAndQueue<>();
        SetAndQueueInterface<String> specializationWorkload = new SetAndQueue<>();
        SetAndQueueInterface<String> doctorPatientRelations = new SetAndQueue<>();
        
        for (Consultation consultation : consultations) {
            String patientId = consultation.getPatientId();
            String doctorId = consultation.getDoctorId();
            
            //find patient and doctor names
            String patientName = "Unknown";
            String doctorName = "Unknown";
            String specialization = "Unknown";
            
            for (Patient patient : patients) {
                if (String.valueOf(patient.getId()).equals(patientId)) {
                    patientName = patient.getName();
                    break;
                }
            }
            
            for (Doctor doctor : doctors) {
                if (doctor.getDoctorId().equals(doctorId)) {
                    doctorName = doctor.getName();
                    specialization = doctor.getSpecialization();
                    break;
                }
            }
            
            report.append(String.format("%-10s %-20s %-15s %-20s %-15s %-12s\n",
                patientId, patientName, doctorId, doctorName, specialization, consultation.getStatus()));
            
            //add to ADT sets for analysis
            doctorWorkload.add(doctorId);
            patientVisitCount.add(patientId);
            specializationWorkload.add(specialization);
            doctorPatientRelations.add(doctorId + "->" + patientId);
            consultationStatuses.add(consultation.getStatus());
        }
        
        //summary statistics
        report.append("\nRELATIONSHIP SUMMARY STATISTICS:\n");
        report.append("• Total Consultations: ").append(consultations.length).append("\n");
        report.append("• Unique Patients: ").append(patientVisitCount.size()).append("\n");
        report.append("• Unique Doctors: ").append(doctorWorkload.size()).append("\n");
        report.append("• Unique Specializations: ").append(specializationWorkload.size()).append("\n");
        report.append("• Average Consultations per Doctor: ").append(String.format("%.1f", (double)consultations.length/doctorWorkload.size())).append("\n");
        report.append("• Average Visits per Patient: ").append(String.format("%.1f", (double)consultations.length/patientVisitCount.size())).append("\n");

        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create specialized sets
        SetAndQueueInterface<String> malePatients = new SetAndQueue<>();
        SetAndQueueInterface<String> femalePatients = new SetAndQueue<>();
        SetAndQueueInterface<String> adultPatients = new SetAndQueue<>();
        SetAndQueueInterface<String> childPatients = new SetAndQueue<>();
        SetAndQueueInterface<String> availableDoctors = new SetAndQueue<>();
        SetAndQueueInterface<String> onLeaveDoctors = new SetAndQueue<>();
        
        for (Patient patient : patients) {
            if (patient.getGender().equalsIgnoreCase("Male")) {
                malePatients.add(patient.getName());
            } else {
                femalePatients.add(patient.getName());
            }
            
            if (patient.getAge() >= 18) {
                adultPatients.add(patient.getName());
            } else {
                childPatients.add(patient.getName());
            }
        }
        
        for (Doctor doctor : doctors) {
            if (doctor.isIsAvailable()) {
                availableDoctors.add(doctor.getName());
            }
            if (doctor.isOnLeave()) {
                onLeaveDoctors.add(doctor.getName());
            }
        }
        
        //cross-module set operations
        SetAndQueueInterface<String> allPatients = malePatients.union(femalePatients);
        report.append("Total Unique Patients (Union): ").append(allPatients.size()).append("\n");
        
        SetAndQueueInterface<String> adultMales = adultPatients.intersection(malePatients);
        report.append("Adult Male Patients (Intersection): ").append(adultMales.size()).append("\n");
        
        SetAndQueueInterface<String> availableNotOnLeave = availableDoctors.difference(onLeaveDoctors);
        report.append("Available Not On Leave Doctors (Difference): ").append(availableNotOnLeave.size()).append("\n");
        
        boolean areAllMalesAdults = malePatients.isSubsetOf(adultPatients);
        report.append("Are All Males Adults (Subset): ").append(areAllMalesAdults ? "Yes" : "No").append("\n");
        
        //doctor workload analysis
        report.append("\nDOCTOR WORKLOAD ANALYSIS:\n");
        Object[] doctorArray = doctorWorkload.toArray();
        for (Object obj : doctorArray) {
            if (obj instanceof String) {
                String doctorId = (String) obj;
                String doctorName = "Unknown";
                String specialization = "Unknown";
                
                for (Doctor doctor : doctors) {
                    if (doctor.getDoctorId().equals(doctorId)) {
                        doctorName = doctor.getName();
                        specialization = doctor.getSpecialization();
                        break;
                    }
                }
                
                report.append(String.format("• Dr. %s (%s): %s\n", doctorName, specialization, doctorId));
            }
        }
        
        //specialization workload
        report.append("\nSPECIALIZATION WORKLOAD DISTRIBUTION:\n");
        Object[] specializationArray = specializationWorkload.toArray();
        for (Object obj : specializationArray) {
            if (obj instanceof String) {
                report.append(createBarChart((String)obj, 1, consultations.length, 25));
            }
        }
        
        //patient visit frequency
        report.append("\nPATIENT VISIT FREQUENCY ANALYSIS:\n");
        Object[] patientArray = patientVisitCount.toArray();
        for (Object obj : patientArray) {
            if (obj instanceof String) {
                String patientId = (String) obj;
                String patientName = "Unknown";
                
                for (Patient patient : patients) {
                    if (String.valueOf(patient.getId()).equals(patientId)) {
                        patientName = patient.getName();
                        break;
                    }
                }
                
                report.append(String.format("• Patient %s (ID: %s): %d visit(s)\n", patientName, patientId, 1));
            }
        }
        
        //relationship graph visualization
        report.append("\nRELATIONSHIP GRAPH VISUALIZATION:\n");
        report.append("Doctor-Patient Consultation Network:\n");
        report.append(StringUtility.repeatString("-", 60)).append("\n");
        
        Object[] relationArray = doctorPatientRelations.toArray();
        for (Object obj : relationArray) {
            if (obj instanceof String) {
                String relation = (String) obj;
                String[] parts = relation.split("->");
                if (parts.length == 2) {
                    String doctorId = parts[0];
                    String patientId = parts[1];
                    
                    String doctorName = "Unknown";
                    String patientName = "Unknown";
                    String specialization = "Unknown";
                    
                    for (Doctor doctor : doctors) {
                        if (doctor.getDoctorId().equals(doctorId)) {
                            doctorName = doctor.getName();
                            specialization = doctor.getSpecialization();
                            break;
                        }
                    }
                    
                    for (Patient patient : patients) {
                        if (String.valueOf(patient.getId()).equals(patientId)) {
                            patientName = patient.getName();
                            break;
                        }
                    }
                    
                    report.append(String.format("Dr. %s (%s) → %s (ID: %s)\n", 
                        doctorName, specialization, patientName, patientId));
                }
            }
        }
        
        //cross-module insights
        report.append("\nCROSS-MODULE INSIGHTS:\n");
        report.append("• Total Unique Patient-Doctor Relationships: ").append(doctorPatientRelations.size()).append("\n");
        report.append("• Consultation Status Distribution: ").append(consultationStatuses.size()).append(" different statuses\n");
        report.append("• Gender Distribution in Consultations: Male=").append(malePatients.size()).append(", Female=").append(femalePatients.size()).append("\n");
        report.append("• Age Group Distribution: Adult=").append(adultPatients.size()).append(", Child=").append(childPatients.size()).append("\n");
        
        report.append("\nEND OF PATIENT-DOCTOR-CONSULTATION RELATIONSHIP REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generateTreatmentMedicinePharmacyReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC TREATMENT-MEDICINE-PHARMACY RELATIONSHIP REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //initialize all data
        Treatment[] treatments = DataInitializer.initializeSampleTreatments();
        Medicine[] medicines = DataInitializer.initializeSampleMedicines();
        PharmacyTransaction[] transactions = DataInitializer.initializeSampleTransactions();
        Patient[] patients = DataInitializer.initializeSamplePatients();
        Doctor[] doctors = DataInitializer.initializeSampleDoctors();
        
        //create ADT sets for analysis
        SetAndQueueInterface<String> patientNames = new SetAndQueue<>();
        SetAndQueueInterface<String> doctorNames = new SetAndQueue<>();
        SetAndQueueInterface<String> medicineNames = new SetAndQueue<>();
        SetAndQueueInterface<String> medicineCategories = new SetAndQueue<>();
        SetAndQueueInterface<String> medicinePurposes = new SetAndQueue<>();
        
        for (Patient patient : patients) {
            patientNames.add(patient.getName());
        }
        
        for (Doctor doctor : doctors) {
            doctorNames.add(doctor.getName());
        }
        
        for (Medicine medicine : medicines) {
            medicineNames.add(medicine.getName());
            medicineCategories.add(medicine.getCategory());
            medicinePurposes.add(medicine.getPurpose());
        }
        
        //treatment-medicine relationship analysis
        report.append("TREATMENT-MEDICINE RELATIONSHIP ANALYSIS:\n");
        report.append(String.format("%-10s %-15s %-20s %-15s %-20s %-15s\n", 
            "Treatment ID", "Patient", "Doctor", "Diagnosis", "Prescribed Meds", "Follow-up"));
        report.append(StringUtility.repeatString("-", 100)).append("\n");
        
        SetAndQueueInterface<String> medicinePrescriptionCount = new SetAndQueue<>();
        SetAndQueueInterface<String> diagnosisMedicineCount = new SetAndQueue<>();
        SetAndQueueInterface<String> treatmentMedicines = new SetAndQueue<>();
        
        for (Treatment treatment : treatments) {
            String patientName = "Unknown";
            String doctorName = "Unknown";
            String prescribedMeds = treatment.getPrescribedMedications();
            
            //find patient and doctor names
            for (Patient patient : patients) {
                if (String.valueOf(patient.getId()).equals(treatment.getPatientId())) {
                    patientName = patient.getName();
                    break;
                }
            }
            
            for (Doctor doctor : doctors) {
                if (doctor.getDoctorId().equals(treatment.getDoctorId())) {
                    doctorName = doctor.getName();
                    break;
                }
            }
            
            report.append(String.format("%-10s %-15s %-20s %-15s %-20s %-15s\n",
                treatment.getTreatmentId(), 
                patientName, doctorName, treatment.getDiagnosis(),
                prescribedMeds, treatment.getFollowUpDate() != null ? treatment.getFollowUpDate() : "None"));
            
            //analyze prescribed medications
            if (prescribedMeds != null && !prescribedMeds.isEmpty()) {
                String[] meds = prescribedMeds.split(",");
                for (String med : meds) {
                    String medId = med.trim();
                    medicinePrescriptionCount.add(medId);
                    treatmentMedicines.add(treatment.getTreatmentId() + "->" + medId);
                }
                diagnosisMedicineCount.add(treatment.getDiagnosis());
            }
        }
        
        //pharmacy transaction analysis
        report.append("\nPHARMACY TRANSACTION ANALYSIS:\n");
        report.append(String.format("%-10s %-15s %-20s %-10s %-15s %-15s\n", 
            "Transaction ID", "Medicine", "Category", "Quantity", "Patient", "Date"));
        report.append(StringUtility.repeatString("-", 90)).append("\n");
        
        SetAndQueueInterface<String> medicineTransactionCount = new SetAndQueue<>();
        SetAndQueueInterface<String> categoryTransactionCount = new SetAndQueue<>();
        SetAndQueueInterface<String> patientTransactionCount = new SetAndQueue<>();
        
        for (PharmacyTransaction transaction : transactions) {
            String medicineName = "Unknown";
            String category = "Unknown";
            String patientName = "Unknown";
            
            //find medicine details
            for (Medicine medicine : medicines) {
                if (medicine.getMedicineId().equals(transaction.getMedicineId())) {
                    medicineName = medicine.getName();
                    category = medicine.getCategory();
                    break;
                }
            }
            
            //find patient name
            for (Patient patient : patients) {
                if (String.valueOf(patient.getId()).equals(transaction.getPatientId())) {
                    patientName = patient.getName();
                    break;
                }
            }
            
            report.append(String.format("%-10s %-15s %-20s %-10d %-15s %-15s\n",
                transaction.getTransactionId(), medicineName, category,
                transaction.getQuantityDispensed(), patientName, transaction.getDispenseDate()));
            
            //add to ADT sets
            medicineTransactionCount.add(transaction.getMedicineId());
            categoryTransactionCount.add(category);
            patientTransactionCount.add(transaction.getPatientId());
        }

        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create specialized sets for cross-module analysis
        SetAndQueueInterface<String> lowStockMedicines = new SetAndQueue<>();
        SetAndQueueInterface<String> expensiveMedicines = new SetAndQueue<>();
        SetAndQueueInterface<String> followUpTreatments = new SetAndQueue<>();
        SetAndQueueInterface<String> urgentTreatments = new SetAndQueue<>();
        SetAndQueueInterface<String> highValueTransactions = new SetAndQueue<>();
        
        for (Medicine medicine : medicines) {
            if (medicine.getStockQuantity() < 10) {
                lowStockMedicines.add(medicine.getMedicineId());
            }
            if (medicine.getPrice() > 50.0) {
                expensiveMedicines.add(medicine.getMedicineId());
            }
        }
        
        for (Treatment treatment : treatments) {
            if (treatment.getFollowUpDate() != null && !treatment.getFollowUpDate().isEmpty()) {
                followUpTreatments.add(treatment.getTreatmentId());
            }
            if (treatment.getDiagnosis().toLowerCase().contains("urgent")) {
                urgentTreatments.add(treatment.getTreatmentId());
            }
        }
        
        for (PharmacyTransaction transaction : transactions) {
            for (Medicine medicine : medicines) {
                if (medicine.getMedicineId().equals(transaction.getMedicineId())) {
                    if (transaction.getQuantityDispensed() * medicine.getPrice() > 100.0) {
                        highValueTransactions.add(transaction.getTransactionId());
                    }
                    break;
                }
            }
        }
        
        //cross-module set operations
        SetAndQueueInterface<String> allMedicines = lowStockMedicines.union(expensiveMedicines);
        report.append("Total Unique Medicine Categories (Union): ").append(allMedicines.size()).append("\n");
        
        SetAndQueueInterface<String> lowStockAndExpensive = lowStockMedicines.intersection(expensiveMedicines);
        report.append("Low Stock Expensive Medicines (Intersection): ").append(lowStockAndExpensive.size()).append("\n");
        
        SetAndQueueInterface<String> urgentWithFollowUp = urgentTreatments.intersection(followUpTreatments);
        report.append("Urgent Treatments with Follow-up (Intersection): ").append(urgentWithFollowUp.size()).append("\n");
        
        SetAndQueueInterface<String> expensiveNotLowStock = expensiveMedicines.difference(lowStockMedicines);
        report.append("Expensive Not Low Stock (Difference): ").append(expensiveNotLowStock.size()).append("\n");
        
        boolean areAllExpensiveLowStock = expensiveMedicines.isSubsetOf(lowStockMedicines);
        report.append("Are All Expensive Medicines Low Stock (Subset): ").append(areAllExpensiveLowStock ? "Yes" : "No").append("\n");
        
        //cross-module relationship analysis
        report.append("\nCROSS-MODULE RELATIONSHIP ANALYSIS:\n");
        
        //medicine usage analysis
        report.append("MEDICINE USAGE ANALYSIS:\n");
        Object[] prescriptionArray = medicinePrescriptionCount.toArray();
        for (Object obj : prescriptionArray) {
            if (obj instanceof String) {
                String medId = (String) obj;
                String medicineName = "Unknown";
                
                for (Medicine medicine : medicines) {
                    if (medicine.getMedicineId().equals(medId)) {
                        medicineName = medicine.getName();
                        break;
                    }
                }
                
                report.append(String.format("• %s: Prescribed in treatments\n", medicineName));
            }
        }
        
        //diagnosis-medicine correlation
        report.append("\nDIAGNOSIS-MEDICINE CORRELATION:\n");
        Object[] diagnosisArray = diagnosisMedicineCount.toArray();
        for (Object obj : diagnosisArray) {
            if (obj instanceof String) {
                report.append(String.format("• %s: Treatments with medication\n", (String)obj));
            }
        }
        
        //category performance analysis
        report.append("\nMEDICINE CATEGORY PERFORMANCE:\n");
        Object[] categoryArray = categoryTransactionCount.toArray();
        for (Object obj : categoryArray) {
            if (obj instanceof String) {
                report.append(createBarChart((String)obj, 1, transactions.length, 25));
            }
        }
        
        //treatment-pharmacy flow graph
        report.append("\nTREATMENT-PHARMACY FLOW GRAPH:\n");
        report.append("Treatment → Prescription → Pharmacy Transaction Flow:\n");
        report.append(StringUtility.repeatString("-", 60)).append("\n");
        
        Object[] treatmentArray = treatmentMedicines.toArray();
        for (Object obj : treatmentArray) {
            if (obj instanceof String) {
                String relation = (String) obj;
                String[] parts = relation.split("->");
                if (parts.length == 2) {
                    String treatmentId = parts[0];
                    String medId = parts[1];
                    
                    String medicineName = "Unknown";
                    for (Medicine medicine : medicines) {
                        if (medicine.getMedicineId().equals(medId)) {
                            medicineName = medicine.getName();
                            break;
                        }
                    }
                    
                    report.append(String.format("Treatment %s → Prescribed: %s\n", treatmentId, medicineName));
                    
                    //check if medicine was sold in pharmacy
                    boolean foundInPharmacy = false;
                    for (PharmacyTransaction transaction : transactions) {
                        if (transaction.getMedicineId().equals(medId)) {
                            foundInPharmacy = true;
                            break;
                        }
                    }
                    
                    report.append(String.format("  └── Pharmacy Transactions: %s\n", foundInPharmacy ? "Yes" : "No"));
                }
            }
        }
        
        //financial impact analysis
        report.append("\nFINANCIAL IMPACT ANALYSIS:\n");
        double totalRevenue = 0;
        for (PharmacyTransaction transaction : transactions) {
            for (Medicine medicine : medicines) {
                if (medicine.getMedicineId().equals(transaction.getMedicineId())) {
                    totalRevenue += transaction.getQuantityDispensed() * medicine.getPrice();
                    break;
                }
            }
        }
        
        report.append(String.format("• Total Pharmacy Revenue: RM%.2f\n", totalRevenue));
        report.append(String.format("• Average Transaction Value: RM%.2f\n", totalRevenue/transactions.length));
        report.append(String.format("• High-Value Transactions: %d\n", highValueTransactions.size()));
        
        //cross-module insights
        report.append("\nCROSS-MODULE INSIGHTS:\n");
        report.append("• Treatment-Pharmacy Conversion Rate: ");
        int treatmentsWithMeds = treatmentMedicines.size();
        int totalTransactions = transactions.length;
        report.append(String.format("%.1f%%\n", (double)totalTransactions/treatmentsWithMeds*100));
        
        report.append("• Average Medicines per Treatment: ");
        int totalPrescriptions = medicinePrescriptionCount.size();
        report.append(String.format("%.1f\n", (double)totalPrescriptions/treatments.length));
        
        report.append("• Low Stock Alert: ").append(lowStockMedicines.size()).append(" medicines\n");
        report.append("• Expensive Medicine Count: ").append(expensiveMedicines.size()).append(" medicines\n");
        report.append("• Follow-up Treatment Rate: ").append(String.format("%.1f%%", (double)followUpTreatments.size()/treatments.length*100)).append("\n");
        
        report.append("\nEND OF TREATMENT-MEDICINE-PHARMACY RELATIONSHIP REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generateDoctorPatientLoadReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC DOCTOR-PATIENT LOAD REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //initialize all data
        Patient[] patients = DataInitializer.initializeSamplePatients();
        Doctor[] doctors = DataInitializer.initializeSampleDoctors();
        Consultation[] consultations = DataInitializer.initializeSampleConsultations();
        Treatment[] treatments = DataInitializer.initializeSampleTreatments();
        
        //create ADT sets for load analysis
        SetAndQueueInterface<String> doctorWorkload = new SetAndQueue<>();
        SetAndQueueInterface<String> patientLoad = new SetAndQueue<>();
        SetAndQueueInterface<String> specializationLoad = new SetAndQueue<>();
        SetAndQueueInterface<String> doctorPatientRelations = new SetAndQueue<>();
        
        //doctor-patient load analysis table
        report.append("DOCTOR-PATIENT LOAD ANALYSIS TABLE:\n");
        report.append(String.format("%-15s %-20s %-15s %-10s %-10s %-15s %-15s\n", 
            "Doctor ID", "Doctor Name", "Specialization", "Patients", "Consultations", "Treatments", "Total Load"));
        report.append(StringUtility.repeatString("-", 110)).append("\n");
        
        //calculate load for each doctor
        for (Doctor doctor : doctors) {
            String doctorId = doctor.getDoctorId();
            int patientCount = 0;
            int consultationCount = 0;
            int treatmentCount = 0;
            
            //count consultations for this doctor
            for (Consultation consultation : consultations) {
                if (consultation.getDoctorId().equals(doctorId)) {
                    consultationCount++;
                    doctorWorkload.add(doctorId);
                    doctorPatientRelations.add(doctorId + "->" + consultation.getPatientId());
                }
            }
            
            //count treatments for this doctor
            for (Treatment treatment : treatments) {
                if (treatment.getDoctorId().equals(doctorId)) {
                    treatmentCount++;
                    doctorWorkload.add(doctorId);
                    doctorPatientRelations.add(doctorId + "->" + treatment.getPatientId());
                }
            }
            
            //count unique patients for this doctor
            SetAndQueueInterface<String> uniquePatients = new SetAndQueue<>();
            for (Consultation consultation : consultations) {
                if (consultation.getDoctorId().equals(doctorId)) {
                    uniquePatients.add(consultation.getPatientId());
                }
            }
            for (Treatment treatment : treatments) {
                if (treatment.getDoctorId().equals(doctorId)) {
                    uniquePatients.add(treatment.getPatientId());
                }
            }
            patientCount = uniquePatients.size();
            
            int totalLoad = consultationCount + treatmentCount;
            
            report.append(String.format("%-15s %-20s %-15s %-10d %-10d %-15d %-15d\n",
                doctorId, doctor.getName(), doctor.getSpecialization(),
                patientCount, consultationCount, treatmentCount, totalLoad));
            
            specializationLoad.add(doctor.getSpecialization());
        }
        
        //summary statistics
        report.append("\nLOAD SUMMARY STATISTICS:\n");
        report.append("• Total Doctors: ").append(doctors.length).append("\n");
        report.append("• Total Patients: ").append(patients.length).append("\n");
        report.append("• Total Consultations: ").append(consultations.length).append("\n");
        report.append("• Total Treatments: ").append(treatments.length).append("\n");
        report.append("• Average Patients per Doctor: ").append(String.format("%.1f", (double)patients.length/doctors.length)).append("\n");
        report.append("• Average Consultations per Doctor: ").append(String.format("%.1f", (double)consultations.length/doctors.length)).append("\n");
        report.append("• Average Treatments per Doctor: ").append(String.format("%.1f", (double)treatments.length/doctors.length)).append("\n");
        
        //advanced adt analytics
        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create specialized sets for load analysis
        SetAndQueueInterface<String> highLoadDoctors = new SetAndQueue<>();
        SetAndQueueInterface<String> lowLoadDoctors = new SetAndQueue<>();
        SetAndQueueInterface<String> availableDoctors = new SetAndQueue<>();
        SetAndQueueInterface<String> onLeaveDoctors = new SetAndQueue<>();
        SetAndQueueInterface<String> cardiologists = new SetAndQueue<>();
        SetAndQueueInterface<String> emergencyDoctors = new SetAndQueue<>();
        
        //calculate average load
        int totalLoad = consultations.length + treatments.length;
        double averageLoad = (double) totalLoad / doctors.length;
        
        for (Doctor doctor : doctors) {
            String doctorId = doctor.getDoctorId();
            int doctorLoad = 0;
            
            //calculate individual doctor load
            for (Consultation consultation : consultations) {
                if (consultation.getDoctorId().equals(doctorId)) {
                    doctorLoad++;
                }
            }
            for (Treatment treatment : treatments) {
                if (treatment.getDoctorId().equals(doctorId)) {
                    doctorLoad++;
                }
            }
            
            if (doctorLoad > averageLoad) {
                highLoadDoctors.add(doctor.getName());
            } else {
                lowLoadDoctors.add(doctor.getName());
            }
            
            if (doctor.isIsAvailable()) {
                availableDoctors.add(doctor.getName());
            }
            if (doctor.isOnLeave()) {
                onLeaveDoctors.add(doctor.getName());
            }
            if (doctor.getSpecialization().toLowerCase().contains("cardiology")) {
                cardiologists.add(doctor.getName());
            }
            if (doctor.getSpecialization().toLowerCase().contains("emergency")) {
                emergencyDoctors.add(doctor.getName());
            }
        }
        
        //set operations for load analysis
        SetAndQueueInterface<String> allDoctors = highLoadDoctors.union(lowLoadDoctors);
        report.append("Total Unique Doctors (Union): ").append(allDoctors.size()).append("\n");
        
        SetAndQueueInterface<String> availableHighLoad = availableDoctors.intersection(highLoadDoctors);
        report.append("Available High-Load Doctors (Intersection): ").append(availableHighLoad.size()).append("\n");
        
        SetAndQueueInterface<String> cardiologistsHighLoad = cardiologists.intersection(highLoadDoctors);
        report.append("High-Load Cardiologists (Intersection): ").append(cardiologistsHighLoad.size()).append("\n");
        
        SetAndQueueInterface<String> availableNotOnLeave = availableDoctors.difference(onLeaveDoctors);
        report.append("Available Not On Leave (Difference): ").append(availableNotOnLeave.size()).append("\n");
        
        boolean areAllCardiologistsHighLoad = cardiologists.isSubsetOf(highLoadDoctors);
        report.append("Are All Cardiologists High-Load (Subset): ").append(areAllCardiologistsHighLoad ? "Yes" : "No").append("\n");
        
        //load distribution analysis
        report.append("\nLOAD DISTRIBUTION ANALYSIS:\n");
        report.append("High-Load Doctors (> Average): ").append(highLoadDoctors.size()).append("\n");
        report.append("Low-Load Doctors (≤ Average): ").append(lowLoadDoctors.size()).append("\n");
        report.append("Available Doctors: ").append(availableDoctors.size()).append("\n");
        report.append("Doctors on Leave: ").append(onLeaveDoctors.size()).append("\n");
        
        //specialization load analysis
        report.append("\nSPECIALIZATION LOAD ANALYSIS:\n");
        Object[] specializationArray = specializationLoad.toArray();
        for (Object obj : specializationArray) {
            if (obj instanceof String) {
                String specialization = (String) obj;
                int specializationLoadCount = 0;
                
                for (Doctor doctor : doctors) {
                    if (doctor.getSpecialization().equals(specialization)) {
                        String doctorId = doctor.getDoctorId();
                        for (Consultation consultation : consultations) {
                            if (consultation.getDoctorId().equals(doctorId)) {
                                specializationLoadCount++;
                            }
                        }
                        for (Treatment treatment : treatments) {
                            if (treatment.getDoctorId().equals(doctorId)) {
                                specializationLoadCount++;
                            }
                        }
                    }
                }
                
                report.append(createBarChart(specialization, specializationLoadCount, totalLoad, 30));
            }
        }
        
        //doctor-patient relationship graph
        report.append("\nDOCTOR-PATIENT RELATIONSHIP GRAPH:\n");
        report.append("Doctor → Patient Load Distribution:\n");
        report.append(StringUtility.repeatString("-", 80)).append("\n");
        
        Object[] relationArray = doctorPatientRelations.toArray();
        for (Object obj : relationArray) {
            if (obj instanceof String) {
                String relation = (String) obj;
                String[] parts = relation.split("->");
                if (parts.length == 2) {
                    String doctorId = parts[0];
                    String patientId = parts[1];
                    
                    String doctorName = "Unknown";
                    String patientName = "Unknown";
                    String specialization = "Unknown";
                    
                    for (Doctor doctor : doctors) {
                        if (doctor.getDoctorId().equals(doctorId)) {
                            doctorName = doctor.getName();
                            specialization = doctor.getSpecialization();
                            break;
                        }
                    }
                    
                    for (Patient patient : patients) {
                        if (String.valueOf(patient.getId()).equals(patientId)) {
                            patientName = patient.getName();
                            break;
                        }
                    }
                    
                    report.append(String.format("Dr. %s (%s) → %s (ID: %s)\n", 
                        doctorName, specialization, patientName, patientId));
                }
            }
        }
        
        //load balance analysis
        report.append("\nLOAD BALANCE ANALYSIS:\n");
        report.append("• Load Distribution: ").append(highLoadDoctors.size()).append(" high-load, ").append(lowLoadDoctors.size()).append(" low-load doctors\n");
        report.append("• Load Balance Ratio: ").append(String.format("%.2f", (double)highLoadDoctors.size()/lowLoadDoctors.size())).append("\n");
        report.append("• Average Load per Doctor: ").append(String.format("%.1f", averageLoad)).append(" cases\n");
        report.append("• Maximum Load: ").append(totalLoad).append(" cases\n");
        report.append("• Minimum Load: 0 cases\n");
        
        //performance metrics
        report.append("\nPERFORMANCE METRICS:\n");
        report.append("• Doctor Utilization Rate: ").append(String.format("%.1f%%", (double)doctorWorkload.size()/doctors.length*100)).append("\n");
        report.append("• Patient-Doctor Ratio: ").append(String.format("%.1f", (double)patients.length/doctors.length)).append("\n");
        report.append("• Consultation-Doctor Ratio: ").append(String.format("%.1f", (double)consultations.length/doctors.length)).append("\n");
        report.append("• Treatment-Doctor Ratio: ").append(String.format("%.1f", (double)treatments.length/doctors.length)).append("\n");
        
        report.append("\nEND OF DOCTOR-PATIENT LOAD REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    public static String generatePatientConsultationTreatmentSummaryReport() {
        StringBuilder report = new StringBuilder();
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("    CHUBBY CLINIC PATIENT CONSULTATION & TREATMENT SUMMARY REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        report.append("Generated on: ").append(dateFormat.format(new Date())).append("\n");
        report.append("Report Type: HIGHLY CONFIDENTIAL\n\n");
        
        //initialize all data
        Patient[] patients = DataInitializer.initializeSamplePatients();
        Doctor[] doctors = DataInitializer.initializeSampleDoctors();
        Consultation[] consultations = DataInitializer.initializeSampleConsultations();
        Treatment[] treatments = DataInitializer.initializeSampleTreatments();
        
        //create ADT sets for patient analysis
        SetAndQueueInterface<String> patientConsultationCount = new SetAndQueue<>();
        SetAndQueueInterface<String> patientTreatmentCount = new SetAndQueue<>();
        SetAndQueueInterface<String> patientDoctorRelations = new SetAndQueue<>();
        SetAndQueueInterface<String> consultationStatuses = new SetAndQueue<>();
        SetAndQueueInterface<String> treatmentDiagnoses = new SetAndQueue<>();
        SetAndQueueInterface<String> patientSpecializationLoad = new SetAndQueue<>();
        
        //patient summary analysis table
        report.append("PATIENT CONSULTATION & TREATMENT SUMMARY TABLE:\n");
        report.append(String.format("%-10s %-20s %-8s %-10s %-12s %-12s %-15s %-15s\n", 
            "Patient ID", "Patient Name", "Age", "Gender", "Consultations", "Treatments", "Total Visits", "Last Visit"));
        report.append(StringUtility.repeatString("-", 120)).append("\n");
        
        //calculate summary for each patient
        for (Patient patient : patients) {
            String patientId = String.valueOf(patient.getId());
            int consultationCount = 0;
            int treatmentCount = 0;
            String lastVisit = "None";
            
            //count consultations for this patient
            for (Consultation consultation : consultations) {
                if (consultation.getPatientId().equals(patientId)) {
                    consultationCount++;
                    patientConsultationCount.add(patientId);
                    consultationStatuses.add(consultation.getStatus());
                    
                    //track last visit
                    if (consultation.getConsultationDate() != null) {
                        lastVisit = consultation.getConsultationDate();
                    }
                }
            }
            
            //count treatments for this patient
            for (Treatment treatment : treatments) {
                if (treatment.getPatientId().equals(patientId)) {
                    treatmentCount++;
                    patientTreatmentCount.add(patientId);
                    treatmentDiagnoses.add(treatment.getDiagnosis());
                }
            }
            
            int totalVisits = consultationCount + treatmentCount;
            
            report.append(String.format("%-10s %-20s %-8d %-10s %-12d %-12d %-15d %-15s\n",
                patientId, patient.getName(), patient.getAge(), patient.getGender(),
                consultationCount, treatmentCount, totalVisits, lastVisit));
        }
        
        //summary statistics
        report.append("\nPATIENT SUMMARY STATISTICS:\n");
        report.append("• Total Patients: ").append(patients.length).append("\n");
        report.append("• Patients with Consultations: ").append(patientConsultationCount.size()).append("\n");
        report.append("• Patients with Treatments: ").append(patientTreatmentCount.size()).append("\n");
        report.append("• Average Consultations per Patient: ").append(String.format("%.1f", (double)consultations.length/patients.length)).append("\n");
        report.append("• Average Treatments per Patient: ").append(String.format("%.1f", (double)treatments.length/patients.length)).append("\n");

        report.append("\n=== ADVANCED ADT ANALYTICS ===\n");
        
        //create specialized sets for patient analysis
        SetAndQueueInterface<String> frequentPatients = new SetAndQueue<>();
        SetAndQueueInterface<String> occasionalPatients = new SetAndQueue<>();
        SetAndQueueInterface<String> malePatients = new SetAndQueue<>();
        SetAndQueueInterface<String> femalePatients = new SetAndQueue<>();
        SetAndQueueInterface<String> adultPatients = new SetAndQueue<>();
        SetAndQueueInterface<String> childPatients = new SetAndQueue<>();
        SetAndQueueInterface<String> patientsWithFollowUp = new SetAndQueue<>();
        
        //calculate average visits
        int totalVisits = consultations.length + treatments.length;
        double averageVisits = (double) totalVisits / patients.length;
        
        for (Patient patient : patients) {
            String patientId = String.valueOf(patient.getId());
            int patientVisits = 0;
            
            //calculate individual patient visits
            for (Consultation consultation : consultations) {
                if (consultation.getPatientId().equals(patientId)) {
                    patientVisits++;
                }
            }
            for (Treatment treatment : treatments) {
                if (treatment.getPatientId().equals(patientId)) {
                    patientVisits++;
                }
            }
            
            if (patientVisits > averageVisits) {
                frequentPatients.add(patient.getName());
            } else {
                occasionalPatients.add(patient.getName());
            }
            
            if (patient.getGender().equalsIgnoreCase("Male")) {
                malePatients.add(patient.getName());
            } else {
                femalePatients.add(patient.getName());
            }
            
            if (patient.getAge() >= 18) {
                adultPatients.add(patient.getName());
            } else {
                childPatients.add(patient.getName());
            }
            
            //check for follow-up treatments
            for (Treatment treatment : treatments) {
                if (treatment.getPatientId().equals(patientId) && 
                    treatment.getFollowUpDate() != null && !treatment.getFollowUpDate().isEmpty()) {
                    patientsWithFollowUp.add(patient.getName());
                    break;
                }
            }
        }
        
        //set operations for patient analysis
        SetAndQueueInterface<String> allPatients = frequentPatients.union(occasionalPatients);
        report.append("Total Unique Patients (Union): ").append(allPatients.size()).append("\n");
        
        SetAndQueueInterface<String> frequentAdults = frequentPatients.intersection(adultPatients);
        report.append("Frequent Adult Patients (Intersection): ").append(frequentAdults.size()).append("\n");
        
        SetAndQueueInterface<String> maleFrequent = malePatients.intersection(frequentPatients);
        report.append("Frequent Male Patients (Intersection): ").append(maleFrequent.size()).append("\n");
        
        SetAndQueueInterface<String> frequentWithFollowUp = frequentPatients.intersection(patientsWithFollowUp);
        report.append("Frequent Patients with Follow-up (Intersection): ").append(frequentWithFollowUp.size()).append("\n");
        
        boolean areAllFrequentAdults = frequentPatients.isSubsetOf(adultPatients);
        report.append("Are All Frequent Patients Adults (Subset): ").append(areAllFrequentAdults ? "Yes" : "No").append("\n");
        
        //patient demographics analysis
        report.append("\nPATIENT DEMOGRAPHICS ANALYSIS:\n");
        report.append("Gender Distribution:\n");
        report.append(createBarChart("Male", malePatients.size(), patients.length, 25));
        report.append(createBarChart("Female", femalePatients.size(), patients.length, 25));
        
        report.append("\nAge Group Distribution:\n");
        report.append(createBarChart("Adults (18+)", adultPatients.size(), patients.length, 25));
        report.append(createBarChart("Children (<18)", childPatients.size(), patients.length, 25));
        
        //visit frequency analysis
        report.append("\nVISIT FREQUENCY ANALYSIS:\n");
        report.append("Frequent Patients (> Average): ").append(frequentPatients.size()).append("\n");
        report.append("Occasional Patients (≤ Average): ").append(occasionalPatients.size()).append("\n");
        report.append("Patients with Follow-up: ").append(patientsWithFollowUp.size()).append("\n");
        
        //consultation status distribution
        report.append("\nCONSULTATION STATUS DISTRIBUTION:\n");
        Object[] statusArray = consultationStatuses.toArray();
        for (Object obj : statusArray) {
            if (obj instanceof String) {
                String status = (String) obj;
                int statusCount = 0;
                for (Consultation consultation : consultations) {
                    if (consultation.getStatus().equals(status)) {
                        statusCount++;
                    }
                }
                report.append(createBarChart(status, statusCount, consultations.length, 25));
            }
        }
        
        //treatment diagnosis distribution
        report.append("\nTREATMENT DIAGNOSIS DISTRIBUTION:\n");
        Object[] diagnosisArray = treatmentDiagnoses.toArray();
        for (Object obj : diagnosisArray) {
            if (obj instanceof String) {
                String diagnosis = (String) obj;
                int diagnosisCount = 0;
                for (Treatment treatment : treatments) {
                    if (treatment.getDiagnosis().equals(diagnosis)) {
                        diagnosisCount++;
                    }
                }
                report.append(createBarChart(diagnosis, diagnosisCount, treatments.length, 25));
            }
        }
        
        //patient-doctor relationship graph
        report.append("\nPATIENT-DOCTOR RELATIONSHIP GRAPH:\n");
        report.append("Patient → Doctor Consultation & Treatment Network:\n");
        report.append(StringUtility.repeatString("-", 80)).append("\n");
        
        //build patient-doctor relationships
        for (Consultation consultation : consultations) {
            String patientId = consultation.getPatientId();
            String doctorId = consultation.getDoctorId();
            
            String patientName = "Unknown";
            String doctorName = "Unknown";
            String specialization = "Unknown";
            
            for (Patient patient : patients) {
                if (String.valueOf(patient.getId()).equals(patientId)) {
                    patientName = patient.getName();
                    break;
                }
            }
            
            for (Doctor doctor : doctors) {
                if (doctor.getDoctorId().equals(doctorId)) {
                    doctorName = doctor.getName();
                    specialization = doctor.getSpecialization();
                    break;
                }
            }
            
            patientDoctorRelations.add(patientId + "->" + doctorId);
            report.append(String.format("%s → Dr. %s (%s) [Consultation]\n", 
                patientName, doctorName, specialization));
        }
        
        for (Treatment treatment : treatments) {
            String patientId = treatment.getPatientId();
            String doctorId = treatment.getDoctorId();
            
            String patientName = "Unknown";
            String doctorName = "Unknown";
            String specialization = "Unknown";
            
            for (Patient patient : patients) {
                if (String.valueOf(patient.getId()).equals(patientId)) {
                    patientName = patient.getName();
                    break;
                }
            }
            
            for (Doctor doctor : doctors) {
                if (doctor.getDoctorId().equals(doctorId)) {
                    doctorName = doctor.getName();
                    specialization = doctor.getSpecialization();
                    break;
                }
            }
            
            patientDoctorRelations.add(patientId + "->" + doctorId);
            report.append(String.format("%s → Dr. %s (%s) [Treatment]\n", 
                patientName, doctorName, specialization));
        }
        
        //patient care metrics
        report.append("\nPATIENT CARE METRICS:\n");
        report.append("• Average Visits per Patient: ").append(String.format("%.1f", averageVisits)).append("\n");
        report.append("• Patient Retention Rate: ").append(String.format("%.1f%%", (double)patientConsultationCount.size()/patients.length*100)).append("\n");
        report.append("• Treatment Success Rate: ").append(String.format("%.1f%%", (double)patientsWithFollowUp.size()/patientTreatmentCount.size()*100)).append("\n");
        report.append("• Consultation Completion Rate: ").append(String.format("%.1f%%", (double)consultations.length/patientConsultationCount.size()*100)).append("\n");
        
        //cross-module insights
        report.append("\nCROSS-MODULE INSIGHTS:\n");
        report.append("• Total Patient-Doctor Relationships: ").append(patientDoctorRelations.size()).append("\n");
        report.append("• Unique Consultation Statuses: ").append(consultationStatuses.size()).append("\n");
        report.append("• Unique Treatment Diagnoses: ").append(treatmentDiagnoses.size()).append("\n");
        report.append("• Gender Distribution: Male=").append(malePatients.size()).append(", Female=").append(femalePatients.size()).append("\n");
        report.append("• Age Distribution: Adult=").append(adultPatients.size()).append(", Child=").append(childPatients.size()).append("\n");
        
        report.append("\nEND OF PATIENT CONSULTATION & TREATMENT SUMMARY REPORT\n");
        report.append(StringUtility.repeatString("=", 80)).append("\n");
        
        return report.toString();
    }
    
    private static String createBarChart(String label, int value, int max, int width) {
        StringBuilder chart = new StringBuilder();
        int barLength = (int) ((double) value / max * width);
        
        //truncate label if too long (max 15 characters)    
        String truncatedLabel = label.length() > 15 ? label.substring(0, 12) + "..." : label;
        
        chart.append(String.format("%-15s [%s] %d (%.1f%%)\n", 
            truncatedLabel, StringUtility.repeatString("█", barLength), value, (double)value/max*100));
        return chart.toString();
    }
} 