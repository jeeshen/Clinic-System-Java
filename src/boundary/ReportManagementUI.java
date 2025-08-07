package boundary;

import control.ReportManagement;
import control.PatientManagement;
import control.DoctorManagement;
import control.ConsultationManagement;
import control.TreatmentManagement;
import java.util.Scanner;
import utility.StringUtility;

public class ReportManagementUI {
    private ReportManagement reportManagement;
    private PatientManagement patientManagement;
    private DoctorManagement doctorManagement;
    private ConsultationManagement consultationManagement;
    private TreatmentManagement treatmentManagement;
    private Scanner scanner;
    
    public ReportManagementUI(ReportManagement reportManagement) {
        this.reportManagement = reportManagement;
        this.scanner = new Scanner(System.in);
    }
    
    public void setDependencies(PatientManagement patientManagement, DoctorManagement doctorManagement, ConsultationManagement consultationManagement, TreatmentManagement treatmentManagement) {
        this.patientManagement = patientManagement;
        this.doctorManagement = doctorManagement;
        this.consultationManagement = consultationManagement;
        this.treatmentManagement = treatmentManagement;
    }
    
    public void generateMedicalReports() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        MEDICAL REPORTS");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . Patient Statistics Report");
            System.out.println("2 . Doctor Performance Report");
            System.out.println("3 . Doctor Availability Report");
            System.out.println("4 . Doctor Specialization Report");
            System.out.println("5 . Consultation Statistics Report");
            System.out.println("6 . Treatment Statistics Report");
            System.out.println("7 . Diagnosis Analysis Report");
            System.out.println("8 . Disease Age Distribution Report");
            System.out.println("9 . Medicine Usage by Disease Report");
            System.out.println("10. Comprehensive Medical Report");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 10);
            
            switch (choice) {
                case 1:
                    if (patientManagement != null) {
                        patientManagement.generatePatientStatisticsReport();
                    } else {
                        System.out.println("Patient management not available!");
                    }
                    break;
                case 2:
                    if (doctorManagement != null) {
                        doctorManagement.generateDoctorPerformanceReport();
                    } else {
                        System.out.println("Doctor management not available!");
                    }
                    break;
                case 3:
                    if (doctorManagement != null) {
                        doctorManagement.generateDoctorAvailabilityReport();
                    } else {
                        System.out.println("Doctor management not available!");
                    }
                    break;
                case 4:
                    if (doctorManagement != null) {
                        doctorManagement.generateDoctorSpecializationReport();
                    } else {
                        System.out.println("Doctor management not available!");
                    }
                    break;
                case 5:
                    if (consultationManagement != null) {
                        consultationManagement.generateConsultationStatisticsReport();
                    } else {
                        System.out.println("Consultation management not available!");
                    }
                    break;
                case 6:
                    if (treatmentManagement != null) {
                        treatmentManagement.generateTreatmentStatisticsReport();
                    } else {
                        System.out.println("Treatment management not available!");
                    }
                    break;
                case 7:
                    if (treatmentManagement != null) {
                        treatmentManagement.generateDiagnosisAnalysisReport();
                    } else {
                        System.out.println("Treatment management not available!");
                    }
                    break;
                case 8:
                    if (patientManagement != null && treatmentManagement != null) {
                        reportManagement.generateAgeVsDiseaseAnalysisReport(patientManagement, treatmentManagement);
                    } else {
                        System.out.println("Patient and Treatment management not available!");
                    }
                    break;
                case 9:
                    if (treatmentManagement != null && patientManagement != null) {
                        reportManagement.generateMedicineUsageByDiseaseReport(treatmentManagement, patientManagement);
                    } else {
                        System.out.println("Treatment and Patient management not available!");
                    }
                    break;
                case 10:
                    if (patientManagement != null && doctorManagement != null && 
                        consultationManagement != null && treatmentManagement != null) {
                        reportManagement.generateComprehensiveMedicalReport(
                            patientManagement, doctorManagement, consultationManagement, treatmentManagement);
                    } else {
                        System.out.println("All management systems not available!");
                    }
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    private int getUserInputInt(int min, int max) {
        int input;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number between " + min + " and " + max + ": ");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine();
            
            if (input < min || input > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (input < min || input > max);
        
        return input;
    }
} 