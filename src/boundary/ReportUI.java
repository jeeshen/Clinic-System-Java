package boundary;

import utility.StringUtility;
import java.util.Scanner;
import control.HospitalReportGenerator;

public class ReportUI {
    private Scanner scanner;
    
    public ReportUI() {
        this.scanner = new Scanner(System.in);
    }
    
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n" + StringUtility.repeatString("=", 60));
            System.out.println("    CHUBBY CLINIC COMPREHENSIVE REPORT SYSTEM");
            System.out.println(StringUtility.repeatString("=", 60));
            System.out.println("1  . Patient Management Report");
            System.out.println("2  . Doctor Management Report");
            System.out.println("3  . Consultation Management Report");
            System.out.println("4  . Pharmacy Management Report");
            System.out.println("5  . Treatment Management Report");
            System.out.println("6  . System Summary Report");
            System.out.println("7  . Financial Summary Report");
            System.out.println("8  . Doctor-Patient Load Report");
            System.out.println("9  . Patient Consultation & Treatment Summary Report");
            System.out.println("10 . Generate All Reports");
            System.out.println("0  . Return to Main Menu");
            System.out.println(StringUtility.repeatString("-", 60));
            System.out.print("Enter your choice: ");
            
            int choice = getValidChoice(0, 10);
                
            switch (choice) {
                case 1:
                    displayPatientReport();
                    break;
                case 2:
                    displayDoctorReport();
                    break;
                case 3:
                    displayConsultationReport();
                    break;
                case 4:
                    displayPharmacyReport();
                    break;
                case 5:
                    displayTreatmentReport();
                    break;
                case 6:
                    displaySystemSummaryReport();
                    break;
                case 7:
                    displayFinancialReport();
                    break;
                case 8:
                    displayDoctorPatientLoadReport();
                    break;
                case 9:
                    displayPatientConsultationTreatmentSummaryReport();
                    break;
                case 10:
                    generateAllReports();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayPatientReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING PATIENT MANAGEMENT REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generatePatientReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayDoctorReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING DOCTOR MANAGEMENT REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generateDoctorReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayConsultationReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING CONSULTATION MANAGEMENT REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generateConsultationReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayPharmacyReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING PHARMACY MANAGEMENT REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generatePharmacyReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayTreatmentReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING TREATMENT MANAGEMENT REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generateTreatmentReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displaySystemSummaryReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING SYSTEM SUMMARY REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generateSystemSummaryReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayFinancialReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING FINANCIAL SUMMARY REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generateFinancialReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayDoctorPatientLoadReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING DOCTOR-PATIENT LOAD REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generateDoctorPatientLoadReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayPatientConsultationTreatmentSummaryReport() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING PATIENT CONSULTATION & TREATMENT SUMMARY REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        
        String report = HospitalReportGenerator.generatePatientConsultationTreatmentSummaryReport();
        System.out.println(report);
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void generateAllReports() {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("    GENERATING ALL COMPREHENSIVE REPORTS");
        System.out.println(StringUtility.repeatString("=", 80));
        
        System.out.println("Generating reports... Please wait...\n");
        
        String[] reports = {
            HospitalReportGenerator.generatePatientReport(),
            HospitalReportGenerator.generateDoctorReport(),
            HospitalReportGenerator.generateConsultationReport(),
            HospitalReportGenerator.generatePharmacyReport(),
            HospitalReportGenerator.generateTreatmentReport(),
            HospitalReportGenerator.generateSystemSummaryReport(),
            HospitalReportGenerator.generateFinancialReport(),
            HospitalReportGenerator.generateDoctorPatientLoadReport(),
            HospitalReportGenerator.generatePatientConsultationTreatmentSummaryReport()
        };
        
        String[] reportNames = {
            "PATIENT MANAGEMENT REPORT",
            "DOCTOR MANAGEMENT REPORT", 
            "CONSULTATION MANAGEMENT REPORT",
            "PHARMACY MANAGEMENT REPORT",
            "TREATMENT MANAGEMENT REPORT",
            "SYSTEM SUMMARY REPORT",
            "FINANCIAL SUMMARY REPORT",
            "DOCTOR-PATIENT LOAD REPORT",
            "PATIENT CONSULTATION & TREATMENT SUMMARY REPORT"
        };
        
        for (int i = 0; i < reports.length; i++) {
            System.out.println("\n" + StringUtility.repeatString("=", 80));
            System.out.println("    " + reportNames[i]);
            System.out.println(StringUtility.repeatString("=", 80));
            System.out.println(reports[i]);
            
            if (i < reports.length - 1) {
                System.out.println("\nPress Enter to view next report...");
                scanner.nextLine();
            }
        }
        
        System.out.println("\nAll reports generated successfully!");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private int getValidChoice(int min, int max) {
        int choice;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number between " + min + " and " + max + ": ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice < min || choice > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (choice < min || choice > max);
        
        return choice;
    }
} 