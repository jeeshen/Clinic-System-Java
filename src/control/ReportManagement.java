package control;

import java.util.Scanner;

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
    
    public void viewSystemStatus(PatientManagement patientManagement, DoctorManagement doctorManagement, ConsultationManagement consultationManagement, TreatmentManagement treatmentManagement) {
        System.out.println("\n" + repeatString("=", 60));
        System.out.println("        SYSTEM STATUS");
        System.out.println(repeatString("=", 60));
        
        System.out.println("📊 Clinic Statistics:");
        System.out.println("• Total Patients: " + patientManagement.getTotalPatientCount());
        System.out.println("• Total Doctors: " + doctorManagement.getTotalDoctorCount());
        System.out.println("• Doctors on Duty: " + doctorManagement.getDoctorsOnDutyCount());
        System.out.println("• Patients in Queue: " + patientManagement.getQueueSize());
        System.out.println("• Total Consultations: " + consultationManagement.getTotalConsultationCount());
        System.out.println("• Total Prescriptions: " + treatmentManagement.getTotalPrescriptionCount());
        
        System.out.println("\n💰 Financial Summary:");
        double totalRevenue = treatmentManagement.getTotalRevenue();
        int paidPrescriptions = treatmentManagement.getPaidPrescriptionCount();
        System.out.println("• Total Revenue: RM " + String.format("%.2f", totalRevenue));
        System.out.println("• Paid Prescriptions: " + paidPrescriptions);
        System.out.println("• Unpaid Prescriptions: " + (treatmentManagement.getTotalPrescriptionCount() - paidPrescriptions));
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
} 