package control;

import java.util.Scanner;
import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import entity.Prescription;

public class ReportManagement {
    private Scanner scanner;
    
    public ReportManagement() {
        scanner = new Scanner(System.in);
    }
    
    public void generateComprehensiveMedicalReport(PatientManagement patientManagement, DoctorManagement doctorManagement, ConsultationManagement consultationManagement, TreatmentManagement treatmentManagement) {
        System.out.println("\n" + repeatString("=", 80));
        System.out.println("        COMPREHENSIVE MEDICAL REPORT");
        System.out.println(repeatString("=", 80));
        
        System.out.println("📊 SYSTEM OVERVIEW:");
        System.out.println("• Total Patients: " + patientManagement.getTotalPatientCount());
        System.out.println("• Total Doctors: " + doctorManagement.getTotalDoctorCount());
        System.out.println("• Total Consultations: " + consultationManagement.getTotalConsultationCount());
        System.out.println("• Total Prescriptions: " + treatmentManagement.getTotalPrescriptionCount());
        System.out.println("• Total Revenue: RM " + String.format("%.2f", treatmentManagement.getTotalRevenue()));
        System.out.println("• Paid Prescriptions: " + treatmentManagement.getPaidPrescriptionCount());
        
        System.out.println("\n📈 PERFORMANCE METRICS:");
        double completionRate = (double) treatmentManagement.getPaidPrescriptionCount() / 
                               treatmentManagement.getTotalPrescriptionCount() * 100;
        System.out.println("• Prescription Completion Rate: " + String.format("%.1f", completionRate) + "%");
        
        System.out.println("\n🏥 OPERATIONAL STATUS:");
        System.out.println("• Doctors on Duty: " + doctorManagement.getDoctorsOnDutyCount());
        System.out.println("• Patients in Queue: " + patientManagement.getQueueSize());
        
        System.out.println(repeatString("=", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateAgeVsDiseaseAnalysisReport(PatientManagement patientManagement, TreatmentManagement treatmentManagement) {
        System.out.println("\n" + repeatString("=", 80));
        System.out.println("        AGE VS DISEASE ANALYSIS REPORT");
        System.out.println(repeatString("=", 80));
        
        Object[] patientsArray = patientManagement.getAllPatients();
        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
        
        //create age groups
        int[] ageGroups = {0, 20, 30, 40, 50, 60, 100}; // 0-19, 20-29, 30-39, 40-49, 50-59, 60+
        String[] ageGroupNames = {"0-19", "20-29", "30-39", "40-49", "50-59", "60+"};
        int[] ageGroupCounts = new int[ageGroups.length - 1];
        
        //track diseases by age group
        SetAndQueueInterface<String> allDiseases = new SetAndQueue<>();
        int[][] diseaseByAgeGroup = new int[ageGroups.length - 1][100]; //assuming max 100 diseases
        String[] diseaseNames = new String[100];
        int diseaseIndex = 0;
        
        //analyze prescriptions and patients
        for (Object prescriptionObj : prescriptionsArray) {
            Prescription prescription = (Prescription) prescriptionObj;
            String diagnosis = prescription.getDiagnosis();
            String patientId = prescription.getPatientId();
            
            //find patient age
            int patientAge = 0;
            for (Object patientObj : patientsArray) {
                Patient patient = (Patient) patientObj;
                if (String.valueOf(patient.getId()).equals(patientId)) {
                    patientAge = patient.getAge();
                    break;
                }
            }
            
            //determine age group
            int ageGroupIndex = 0;
            for (int i = 0; i < ageGroups.length - 1; i++) {
                if (patientAge >= ageGroups[i] && patientAge < ageGroups[i + 1]) {
                    ageGroupIndex = i;
                    break;
                }
            }
            
            ageGroupCounts[ageGroupIndex]++;
            allDiseases.add(diagnosis);
            
            //track disease by age group
            boolean found = false;
            for (int i = 0; i < diseaseIndex; i++) {
                if (diseaseNames[i].equals(diagnosis)) {
                    diseaseByAgeGroup[ageGroupIndex][i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                diseaseNames[diseaseIndex] = diagnosis;
                diseaseByAgeGroup[ageGroupIndex][diseaseIndex] = 1;
                diseaseIndex++;
            }
        }
        
        System.out.println("📊 AGE GROUP DISTRIBUTION:");
        for (int i = 0; i < ageGroupNames.length; i++) {
            int bars = (int) Math.round((double) ageGroupCounts[i] / getMaxValue(ageGroupCounts) * 25);
            System.out.printf("%-8s: [%s] %d patients (%.1f%%)\n", 
                ageGroupNames[i], createColoredBar(BLUE_BG, bars, 25), ageGroupCounts[i], 
                (double)ageGroupCounts[i]/getTotalPatients(patientsArray)*100);
        }
        
        System.out.println("\n🏥 DISEASE PATTERNS BY AGE GROUP:");
        for (int i = 0; i < ageGroupNames.length; i++) {
            if (ageGroupCounts[i] > 0) {
                System.out.println("\n" + ageGroupNames[i] + " Age Group:");
                for (int j = 0; j < diseaseIndex; j++) {
                    if (diseaseByAgeGroup[i][j] > 0) {
                        int bars = (int) Math.round((double) diseaseByAgeGroup[i][j] / getMaxValue(diseaseByAgeGroup[i]) * 25);
                        System.out.printf("  %-30s: [%s] %d cases\n", 
                            diseaseNames[j], createColoredBar(BLUE_BG, bars, 25), diseaseByAgeGroup[i][j]);
                    }
                }
            }
        }
        
        System.out.println("");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateDoctorVsWorkloadAnalysisReport(DoctorManagement doctorManagement, ConsultationManagement consultationManagement) {
        System.out.println("\n" + repeatString("=", 80));
        System.out.println("        DOCTOR VS WORKLOAD ANALYSIS REPORT");
        System.out.println(repeatString("=", 80));
        
        Object[] doctorsArray = doctorManagement.getAllDoctors();
        Object[] consultationsArray = consultationManagement.getAllConsultations();
        
        //track doctor workload
        int[] doctorWorkload = new int[doctorsArray.length];
        String[] doctorNames = new String[doctorsArray.length];
        
        //initialize doctor names
        for (int i = 0; i < doctorsArray.length; i++) {
            Doctor doctor = (Doctor) doctorsArray[i];
            doctorNames[i] = doctor.getName();
        }
        
        //count consultations per doctor
        for (Object consultationObj : consultationsArray) {
            Consultation consultation = (Consultation) consultationObj;
            String doctorId = consultation.getDoctorId();
            
            for (int i = 0; i < doctorsArray.length; i++) {
                Doctor doctor = (Doctor) doctorsArray[i];
                if (doctor.getDoctorId().equals(doctorId)) {
                    doctorWorkload[i]++;
                    break;
                }
            }
        }
        
        System.out.println("👨‍⚕️ DOCTOR WORKLOAD ANALYSIS:");
        int maxWorkload = getMaxValue(doctorWorkload);
        
        for (int i = 0; i < doctorsArray.length; i++) {
            if (doctorWorkload[i] > 0) {
                int bars = (int) Math.round((double) doctorWorkload[i] / maxWorkload * 25);
                System.out.printf("%-40s: [%s] %d consultations (%.1f%%)\n", 
                    doctorNames[i], createColoredBar(BLUE_BG, bars, 25), doctorWorkload[i], 
                    (double)doctorWorkload[i]/getTotalConsultations(consultationsArray)*100);
            }
        }
        
        //workload categories
        System.out.println("\n📈 WORKLOAD CATEGORIES:");
        int highWorkload = 0, mediumWorkload = 0, lowWorkload = 0;
        for (int workload : doctorWorkload) {
            if (workload >= 5) highWorkload++;
            else if (workload >= 2) mediumWorkload++;
            else if (workload > 0) lowWorkload++;
        }
        
        System.out.println("• High Workload (≥5 consultations): " + highWorkload + " doctors");
        System.out.println("• Medium Workload (2-4 consultations): " + mediumWorkload + " doctors");
        System.out.println("• Low Workload (1 consultation): " + lowWorkload + " doctors");
        
        System.out.println("");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private int getMaxValue(int[] array) {
        int max = 0;
        for (int value : array) {
            if (value > max) max = value;
        }
        return max;
    }
    
    private int getTotalPatients(Object[] patientsArray) {
        return patientsArray.length;
    }
    
    private int getTotalConsultations(Object[] consultationsArray) {
        return consultationsArray.length;
    }
    
    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BG = "\u001B[44m";
    
    private String createColoredBar(String color, int barCount, int totalWidth) {
        return color + repeatString(" ", barCount) + RESET + repeatString(" ", totalWidth - barCount);
    }
    
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
} 