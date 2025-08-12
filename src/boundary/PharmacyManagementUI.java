package boundary;

import control.PharmacyManagement;
import control.TreatmentManagement;
import utility.StringUtility;
import java.util.Scanner;

public class PharmacyManagementUI {
    private PharmacyManagement pharmacyManagement;
    private TreatmentManagement treatmentManagement;
    private Scanner scanner;
    
    public PharmacyManagementUI(PharmacyManagement pharmacyManagement) {
        this.pharmacyManagement = pharmacyManagement;
        this.scanner = new Scanner(System.in);
    }
    
    public void setDependencies(TreatmentManagement treatmentManagement) {
        this.treatmentManagement = treatmentManagement;
    }
    
    public void managePharmacyOperations() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        PHARMACY OPERATIONS MANAGEMENT");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . View All Medicines (Sorted by ID)");
            System.out.println("2 . Search Medicine by ID");
            System.out.println("3 . Search Medicine by Name");
            System.out.println("4 . Update Medicine Information");
            System.out.println("5 . Update Medicine Stock");
            System.out.println("6 . Remove Medicine");
            System.out.println("7 . Dispense Medicines");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 7);
            
            switch (choice) {
                case 1:
                    pharmacyManagement.displayAllMedicinesSorted();
                    break;
                case 2:
                    pharmacyManagement.searchMedicineById();
                    break;
                case 3:
                    pharmacyManagement.searchMedicineByName();
                    break;
                case 4:
                    pharmacyManagement.updateMedicineInfo();
                    break;
                case 5:
                    pharmacyManagement.updateMedicineStock();
                    break;
                case 6:
                    pharmacyManagement.removeMedicine();
                    break;
                case 7:
                    pharmacyManagement.dispenseMedicines();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    public void generatePharmacyReports() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + StringUtility.repeatString("=", 50));
            System.out.println("        PHARMACY REPORTS & INVENTORY");
            System.out.println(StringUtility.repeatString("=", 50));
            System.out.println("1 . Medicine Stock Report");
            System.out.println("2 . Medicine Category Report");
            System.out.println("3 . Dispensing Statistics Report");
            System.out.println("4 . Inventory Value Report");
            System.out.println("0 . Back to Main Menu");
            System.out.println(StringUtility.repeatString("-", 50));
            System.out.print("Enter your choice: ");
            
            int choice = getUserInputInt(0, 4);
            
            switch (choice) {
                case 1:
                    pharmacyManagement.generateMedicineStockReport();
                    break;
                case 2:
                    pharmacyManagement.generateMedicineCategoryReport();
                    break;
                case 3:
                    pharmacyManagement.generateDispensingStatisticsReport();
                    break;
                case 4:
                    pharmacyManagement.generateInventoryValueReport();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
    public void processPayment() {
        if (treatmentManagement == null) {
            System.out.println("Treatment management not available!");
            return;
        }
        
        treatmentManagement.processPayment();
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
