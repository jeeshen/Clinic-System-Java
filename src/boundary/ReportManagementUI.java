package boundary;

import control.PatientManagement;
import control.DoctorManagement;
import control.ConsultationManagement;
import control.TreatmentManagement;
import control.PharmacyManagement;
import java.util.Scanner;
import utility.StringUtility;

public class ReportManagementUI {
    private PatientManagement patientManagement;
    private DoctorManagement doctorManagement;
    private ConsultationManagement consultationManagement;
    private TreatmentManagement treatmentManagement;
    private PharmacyManagement pharmacyManagement;
    private Scanner scanner;
    
    public ReportManagementUI() {
        this.scanner = new Scanner(System.in);
    }
    
    public void setDependencies(PatientManagement patientManagement, DoctorManagement doctorManagement, 
                               ConsultationManagement consultationManagement, TreatmentManagement treatmentManagement,
                               PharmacyManagement pharmacyManagement) {
        this.patientManagement = patientManagement;
        this.doctorManagement = doctorManagement;
        this.consultationManagement = consultationManagement;
        this.treatmentManagement = treatmentManagement;
        this.pharmacyManagement = pharmacyManagement;
    }
    
    public void generateAllReports() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + StringUtility.repeatString("=", 60));
            System.out.println("        COMPREHENSIVE REPORTS & ANALYTICS SYSTEM");
            System.out.println(StringUtility.repeatString("=", 60));
            System.out.println("PATIENT REPORTS:");
            System.out.println("1 . Patient Demographics & Disease Analysis Report");
            System.out.println("2 . Patient Treatment Analysis Report");
            System.out.println();
            System.out.println("DOCTOR REPORTS:");
            System.out.println("3 . Doctor Performance Report");
            System.out.println("4 . Doctor Specialization Analysis Report");
            System.out.println();
            System.out.println("CONSULTATION REPORTS:");
            System.out.println("5 . Consultation Prescription Analysis Report");
            System.out.println("6 . Consultation Diagnosis Analysis Report");
            System.out.println();
            System.out.println("TREATMENT REPORTS:");
            System.out.println("7 . Treatment Prescription Analysis Report");
            System.out.println("8 . Treatment Prescription Efficiency Report");
            System.out.println();
            System.out.println("PHARMACY REPORTS:");
            System.out.println("9 . Pharmacy Inventory & Usage Report");
            System.out.println("10. Pharmacy Financial & Category Report");
            System.out.println("11. Medicine Stock Report");
            System.out.println("12. Dispensing Statistics Report");
            System.out.println();
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 60));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 12);
            
            switch (choice) {
                // Patient Reports
                case 1:
                    if (patientManagement != null) {
                        patientManagement.generatePatientDemographicsAndDiseaseAnalysisReport();
                    } else {
                        System.out.println("Patient management not available!");
                    }
                    break;
                case 2:
                    if (patientManagement != null) {
                        patientManagement.generatePatientTreatmentOutcomesReport();
                    } else {
                        System.out.println("Patient management not available!");
                    }
                    break;
                    
                // Doctor Reports
                case 3:
                    if (doctorManagement != null) {
                        doctorManagement.generateDoctorPerformanceAndWorkloadReport();
                    } else {
                        System.out.println("Doctor management not available!");
                    }
                    break;
                case 4:
                    if (doctorManagement != null) {
                        doctorManagement.generateDoctorSpecializationAndAvailabilityReport();
                    } else {
                        System.out.println("Doctor management not available!");
                    }
                    break;
                    
                // Consultation Reports
                case 5:
                    if (consultationManagement != null) {
                        consultationManagement.generateConsultationVolumeAndTrendsReport();
                    } else {
                        System.out.println("Consultation management not available!");
                    }
                    break;
                case 6:
                    if (consultationManagement != null) {
                        consultationManagement.generateConsultationOutcomesAndDiagnosisReport();
                    } else {
                        System.out.println("Consultation management not available!");
                    }
                    break;
                    
                // Treatment Reports
                case 7:
                    if (treatmentManagement != null) {
                        treatmentManagement.generatePatientMedicineAdherenceReport();
                    } else {
                        System.out.println("Treatment management not available!");
                    }
                    break;
                case 8:
                    if (treatmentManagement != null) {
                        treatmentManagement.generateDoctorPrescriptionEfficiencyReport();
                    } else {
                        System.out.println("Treatment management not available!");
                    }
                    break;
                    
                // Pharmacy Reports
                case 9:
                    if (pharmacyManagement != null) {
                        pharmacyManagement.generatePharmacyInventoryAndUsageReport();
                    } else {
                        System.out.println("Pharmacy management not available!");
                    }
                    break;
                case 10:
                    if (pharmacyManagement != null) {
                        pharmacyManagement.generatePharmacyFinancialAndCategoryReport();
                    } else {
                        System.out.println("Pharmacy management not available!");
                    }
                    break;
                case 11:
                    if (pharmacyManagement != null) {
                        pharmacyManagement.generateMedicineStockReport();
                    } else {
                        System.out.println("Pharmacy management not available!");
                    }
                    break;
                case 12:
                    if (pharmacyManagement != null) {
                        pharmacyManagement.generateDispensingStatisticsReport();
                    } else {
                        System.out.println("Pharmacy management not available!");
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
