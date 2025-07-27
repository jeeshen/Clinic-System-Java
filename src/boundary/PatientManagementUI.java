package boundary;

import java.util.Scanner;
import entity.Patient;
import control.PatientManagement;

public class PatientManagementUI {
    Scanner scanner = new Scanner(System.in);
    PatientManagement controller = new PatientManagement();

    public int getMenuChoice() {
        System.out.println("\n=====Hospital System Main Menu=====");
        System.out.println("1. Register new patient");
        System.out.println("2. List all patients");
        System.out.println("3. View patient record");
        System.out.println("4. Update patient record");
        System.out.println("5. Delete patient record");
        System.out.println("6. Add patient to queue");
        System.out.println("7. Get next patient in queue");
        System.out.println("8. Remove patient from queue");
        System.out.println("9. Search patient");
        System.out.println("0. Exit");
        System.out.print("   Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }
    
    public void registerPatient(){
        System.out.println("=====Register New Patient=====");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Allergy: ");
        String allergy = scanner.nextLine();
        System.out.print("Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Registration Date (YYYY-MM-DD): ");
        String registrationDate = scanner.nextLine();
        System.out.print("Medical History: ");
        String medicalHistory = scanner.nextLine();
        Patient p = new Patient(0, name, age, gender, allergy, contactNumber, address, registrationDate, medicalHistory, false, "Registered");
        int id = controller.registerPatient(p);
        System.out.println("Patient registered successfully with ID: " + id); 
    }
    
    public void listPatients() {
        int patientCount = controller.getPatientCount();
        System.out.println("=====List All Patients=====");
        if (patientCount == 0) {
            System.out.println("No patients registered.");
        } else {
            for (int i = 0; i < patientCount; i++) {
                Patient p = controller.getPatientAt(i);
                if (p != null) {
                    System.out.println(
                        "ID: " + p.getId() +
                        ", Name: " + p.getName() +
                        ", Age: " + p.getAge() +
                        ", Gender: " + p.getGender() +
                        ", Allergy: " + p.getAllegy() +
                        ", Contact: " + p.getContactNumber() +
                        ", Address: " + p.getAddress() +
                        ", Registration Date: " + p.getRegistrationDate() +
                        ", Medical History: " + p.getMedicalHistory() +
                        ", Waiting: " + p.isIsInWaiting() +
                        ", Status: " + p.getCurrentStatus()
                    );
                }
            }
        }
    }

    public void getPatientRecord() {
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        Patient p = controller.getPatientRecord(id);
        if (p == null) {
            System.out.println("Patient not found.");
        } else {
            System.out.println("\nPatient Record:");
            System.out.println("Patient name: " + p.getName());
            System.out.println("Patient age: " + p.getAge());
            System.out.println("Patient gender: " + p.getGender());
            System.out.println("Patient allergy: " + p.getAllegy());
            System.out.println("Patient contact number: " + p.getContactNumber());
            System.out.println("Patient address: " + p.getAddress());
            System.out.println("Patient Registration Date: " + p.getRegistrationDate());
            System.out.println("Patient medical history: " + p.getMedicalHistory());
        }
    }
        
    public void updatePatientRecord() {
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        Patient p = controller.getPatientRecord(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }
        boolean done = false;
        while (!done) {
            System.out.println("\nSelect the field to update:");
            System.out.println("1. Name (" + p.getName() + ")");
            System.out.println("2. Age (" + p.getAge() + ")");
            System.out.println("3. Gender (" + p.getGender() + ")");
            System.out.println("4. Allergy (" + p.getAllegy() + ")");
            System.out.println("5. Contact Number (" + p.getContactNumber() + ")");
            System.out.println("6. Address (" + p.getAddress() + ")");
            System.out.println("7. Registration Date (" + p.getRegistrationDate() + ")");
            System.out.println("8. Medical History (" + p.getMedicalHistory() + ")");
            System.out.println("9. Current Status (" + p.getCurrentStatus() + ")");
            System.out.println("0. Finish editing");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt(); scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine();
                    p.setName(name);
                    System.out.println("Name updated.");
                    break;
                case 2:
                    System.out.print("Enter new age: ");
                    int age = scanner.nextInt(); scanner.nextLine();
                    p.setAge(age);
                    System.out.println("Age updated.");
                    break;
                case 3:
                    System.out.print("Enter new gender: ");
                    String gender = scanner.nextLine();
                    p.setGender(gender);
                    System.out.println("Gender updated.");
                    break;
                case 4:
                    System.out.print("Enter new allergy: ");
                    String allergy = scanner.nextLine();
                    p.setAllegy(allergy);
                    System.out.println("Allergy updated.");
                    break;
                case 5:
                    System.out.print("Enter new contact number: ");
                    String contactNumber = scanner.nextLine();
                    p.setContactNumber(contactNumber);
                    System.out.println("Contact Number updated.");
                    break;
                case 6:
                    System.out.print("Enter new address: ");
                    String address = scanner.nextLine();
                    p.setAddress(address);
                    System.out.println("Address updated.");
                    break;
                case 7:
                    System.out.print("Enter new registration date: ");
                    String regDate = scanner.nextLine();
                    p.setRegistrationDate(regDate);
                    System.out.println("Registration Date updated.");
                    break;
                case 8:
                    System.out.print("Enter new medical history: ");
                    String medHistory = scanner.nextLine();
                    p.setMedicalHistory(medHistory);
                    System.out.println("Medical History updated.");
                    break;
                case 9:
                    System.out.print("Enter new current status: ");
                    String status = scanner.nextLine();
                    p.setCurrentStatus(status);
                    System.out.println("Current Status updated.");
                    break;
                case 0:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        System.out.println("Patient record updated.");
    }
    
    public void deletePatientRecord() {
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        boolean ok = controller.deletePatientRecord(id);
        if (ok) System.out.println("Patient deleted.");
        else System.out.println("Patient not found.");
    }

    public void addPatientToQueue() {
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        boolean ok = controller.manageQueue(id);
        if (ok) System.out.println("Patient added to queue.");
        else System.out.println("Patient not found or already in queue.");
    }

    public void getNextPatientInQueue() {
        Patient p = controller.getNextPatientInQueue();
        if (p == null) System.out.println("No patient in queue.");
        else System.out.println("Next patient: " + p.getName() + " (ID: " + p.getId() + ")");
    }

    public void removePatientFromQueue() {
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt(); scanner.nextLine();
        boolean ok = controller.removePatientFromQueue(id);
        if (ok) System.out.println("Patient removed from queue.");
        else System.out.println("Patient not in queue.");
    }
    
    public void searchPatient() {
        System.out.print("Enter name, ID, or contact to search: ");
        String criteria = scanner.nextLine();
        int count = controller.getSearchPatientCount(criteria);
        if (count == 0) {
            System.out.println("No patients found.");
        } else {
            for (int i = 0; i < count; i++) {
                Patient p = controller.getSearchPatientAt(criteria, i);
                if (p != null) {
                    System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Contact: " + p.getContactNumber());
                }
            }
        }
    }
}
