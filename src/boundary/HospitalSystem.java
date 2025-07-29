package boundary;

import adt.SetAndQueueInterface;
import control.ConsultationManagement;
import control.DoctorManagement;
import control.PatientManagement;
import control.PharmacyManagement;
import control.TreatmentManagement;
import adt.SetAndQueue;
import entity.Medicine;
import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import entity.Treatment;
import entity.PharmacyTransaction;
import dao.DataInitializer;
import utility.StringUtility;
import java.util.Scanner;

public class HospitalSystem {
    private SetAndQueueInterface<Medicine> medicineInventory;
    private SetAndQueueInterface<PharmacyTransaction> transactions;
    private SetAndQueueInterface<Patient> patients;
    private SetAndQueueInterface<Doctor> doctors;
    private SetAndQueueInterface<Consultation> consultations;
    private SetAndQueueInterface<Treatment> treatments;
    
    private PharmacyManagement pharmacyManagement;
    private PatientManagement patientManagement;
    private DoctorManagement doctorManagement;
    private ConsultationManagement consultationManagement;
    private TreatmentManagement treatmentManagement;
    private Scanner scanner;
    
    public HospitalSystem() {
        //initialize data structures
        medicineInventory = new SetAndQueue<>();
        transactions = new SetAndQueue<>();
        patients = new SetAndQueue<>();
        doctors = new SetAndQueue<>();
        consultations = new SetAndQueue<>();
        treatments = new SetAndQueue<>();
        
        //initialize management systems
        pharmacyManagement = new PharmacyManagement(medicineInventory, transactions);
        patientManagement = new PatientManagement(patients);
        doctorManagement = new DoctorManagement(doctors);
        consultationManagement = new ConsultationManagement(consultations, patients, doctors);
        treatmentManagement = new TreatmentManagement(treatments, patients, doctors);
        
        //initialize scanner
        scanner = new Scanner(System.in);
        
        //load sample data
        loadSampleData();
    }
    
    private void loadSampleData() {
        //load medicines
        Medicine[] sampleMedicines = DataInitializer.initializeSampleMedicines();
        for (Medicine medicine : sampleMedicines) {
            pharmacyManagement.addNewMedicine(medicine);
        }
        
        //load patients
        Patient[] samplePatients = DataInitializer.initializeSamplePatients();
        for (Patient patient : samplePatients) {
            patientManagement.addPatient(patient);
        }
        
        //load doctors
        Doctor[] sampleDoctors = DataInitializer.initializeSampleDoctors();
        for (Doctor doctor : sampleDoctors) {
            doctorManagement.addDoctor(doctor);
        }
        
        //load consultations
        Consultation[] sampleConsultations = DataInitializer.initializeSampleConsultations();
        for (Consultation consultation : sampleConsultations) {
            consultationManagement.scheduleConsultation(consultation);
        }
        
        //load treatments
        Treatment[] sampleTreatments = DataInitializer.initializeSampleTreatments();
        for (Treatment treatment : sampleTreatments) {
            treatmentManagement.addTreatment(treatment);
        }
        
        //load transactions
        PharmacyTransaction[] sampleTransactions = DataInitializer.initializeSampleTransactions();
        for (PharmacyTransaction transaction : sampleTransactions) {
            transactions.add(transaction);
        }
        
        System.out.println("Chubby Clinic data loaded successfully!");
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getUserInputInt(0, 7);
            
            switch (choice) {
                case 1:
                    openPatientManagementModule();
                    break;
                case 2:
                    openDoctorManagementModule();
                    break;
                case 3:
                    openConsultationModule();
                    break;
                case 4:
                    openPharmacyModule();
                    break;
                case 5:
                    openTreatmentModule();
                    break;
                case 6:
                    viewSystemStatistics();
                    break;
                case 7:
                    openComprehensiveReportSystem();
                    break;
                case 0:
                    System.out.println("Thank you for using Chubby Clinic Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            System.out.println();
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + StringUtility.repeatString("=", 50));
        System.out.println("    CHUBBY CLINIC MANAGEMENT SYSTEM");
        System.out.println(StringUtility.repeatString("=", 50));
        System.out.println("1 . Patient Management");
        System.out.println("2 . Doctor Management");
        System.out.println("3 . Consultation Management");
        System.out.println("4 . Pharmacy Management");
        System.out.println("5 . Medical Treatment Management");
        System.out.println("6 . System Statistics");
        System.out.println("7 . Comprehensive Reports");
        System.out.println("0 . Exit System");
        System.out.println(StringUtility.repeatString("-", 50));
        System.out.print("Enter your choice: ");
    }
    
    private void openPatientManagementModule() {
        PatientManagementUI patientUI = new PatientManagementUI(patientManagement);
        patientUI.displayMainMenu();
    }
    
    private void openDoctorManagementModule() {
        DoctorManagementUI doctorUI = new DoctorManagementUI(doctorManagement);
        doctorUI.displayMainMenu();
    }
    
    private void openConsultationModule() {
        ConsultationManagementUI consultationUI = new ConsultationManagementUI(consultationManagement, patientManagement, doctorManagement);
        consultationUI.displayMainMenu();
    }
    
    private void openPharmacyModule() {
        PharmacyUI pharmacyUI = new PharmacyUI(pharmacyManagement);
        pharmacyUI.displayMainMenu();
    }
    
    private void openTreatmentModule() {
        TreatmentManagementUI treatmentUI = new TreatmentManagementUI(treatmentManagement, patientManagement, doctorManagement);
        treatmentUI.displayMainMenu();
    }
    
    private void viewSystemStatistics() {
        System.out.println("\n" + StringUtility.repeatString("=", 40));
        System.out.println("    SYSTEM STATISTICS");
        System.out.println(StringUtility.repeatString("=", 40));
        
        System.out.println("📊 Chubby Clinic Statistics:");
        System.out.println("• Total Medicines: " + medicineInventory.size());
        System.out.println("• Total Patients: " + patients.size());
        System.out.println("• Total Doctors: " + doctors.size());
        System.out.println("• Total Consultations: " + consultations.size());
        System.out.println("• Total Treatments: " + treatments.size());
        System.out.println("• Total Transactions: " + transactions.size());
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void openComprehensiveReportSystem() {
        ReportUI reportUI = new ReportUI();
        reportUI.displayMainMenu();
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
    
    //getters for other modules to access data
    public PharmacyManagement getPharmacyManagement() {
        return pharmacyManagement;
    }
    
    public PatientManagement getPatientManagement() {
        return patientManagement;
    }
    
    public DoctorManagement getDoctorManagement() {
        return doctorManagement;
    }
    
    public ConsultationManagement getConsultationManagement() {
        return consultationManagement;
    }
    
    public TreatmentManagement getTreatmentManagement() {
        return treatmentManagement;
    }
    
    public SetAndQueueInterface<Patient> getPatients() {
        return patients;
    }
    
    public SetAndQueueInterface<Doctor> getDoctors() {
        return doctors;
    }
    
    public SetAndQueueInterface<Consultation> getConsultations() {
        return consultations;
    }
    
    public SetAndQueueInterface<Treatment> getTreatments() {
        return treatments;
    }

    public static void main(String[] args) {
        HospitalSystem hospitalSystem = new HospitalSystem();
        hospitalSystem.run();
    }
}
