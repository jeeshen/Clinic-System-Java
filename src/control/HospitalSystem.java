package control;

import entity.Consultation;
import entity.Doctor;
import entity.Medicine;
import entity.Patient;
import entity.PharmacyTransaction;
import entity.Treatment;

import java.util.Scanner;

public class HospitalSystem {
    private Doctor[] doctorList;
    private Patient[] patientList;
    private Consultation[] consultations;
    private Medicine[] medicineList;
    private Patient[] waitingQueue;

    private int doctorCount;
    private int patientCount;
    private int consultationCount;
    private int medicineCount;
    private int queueSize;

    private Scanner scanner;

    public HospitalSystem(int doctorCapacity, int patientCapacity, int consultationCapacity, int medicineCapacity, int queueCapacity) {
        doctorList = new Doctor[doctorCapacity];
        patientList = new Patient[patientCapacity];
        consultations = new Consultation[consultationCapacity];
        medicineList = new Medicine[medicineCapacity];
        waitingQueue = new Patient[queueCapacity];

        doctorCount = 0;
        patientCount = 0;
        consultationCount = 0;
        medicineCount = 0;
        queueSize = 0;

        scanner = new Scanner(System.in);
    }

    public void addDoctor(Doctor doctor) {
        if (doctorCount < doctorList.length) {
            doctorList[doctorCount++] = doctor;
            System.out.println("Doctor added successfully.");
        } else {
            System.out.println("Doctor list is full.");
        }
    }

    public void addPatient(Patient patient) {
        if (patientCount < patientList.length) {
            patientList[patientCount++] = patient;
            System.out.println("Patient added successfully.");
        } else {
            System.out.println("Patient list is full.");
        }
    }

    public void enqueuePatient(Patient patient) {
        if (queueSize < waitingQueue.length) {
            waitingQueue[queueSize++] = patient;
            System.out.println("Patient enqueued successfully.");
        } else {
            System.out.println("Waiting queue is full.");
        }
    }

    public Patient dequeuePatient() {
        if (queueSize == 0) {
            System.out.println("No patients in queue.");
            return null;
        }
        Patient first = waitingQueue[0];
        for (int i = 1; i < queueSize; i++) {
            waitingQueue[i - 1] = waitingQueue[i];
        }
        waitingQueue[--queueSize] = null;
        System.out.println("Patient dequeued: " + first.getName());
        return first;
    }

    public void addMedicine(Medicine medicine) {
        if (medicineCount < medicineList.length) {
            medicineList[medicineCount++] = medicine;
            System.out.println("Medicine added successfully.");
        } else {
            System.out.println("Medicine list is full.");
        }
    }

    public Medicine[] recommendAlternatives(String purpose, String activeIngredient) {
        Medicine[] alternatives = new Medicine[medicineCount];
        int count = 0;

        for (int i = 0; i < medicineCount; i++) {
            Medicine med = medicineList[i];
            if (med.getPurpose().equalsIgnoreCase(purpose)
                && !med.getActiveIngredient().equalsIgnoreCase(activeIngredient)
                && med.getStockQuantity() > 0) {
                alternatives[count++] = med;
            }
        }

        Medicine[] result = new Medicine[count];
        for (int i = 0; i < count; i++) {
            result[i] = alternatives[i];
        }
        return result;
    }

    // --------- Main Screen Controller/UI Loop ---------
    public void run() {
        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = getUserInputInt();

            switch (choice) {
                case 1:
                    addDoctorFlow();
                    break;
                case 2:
                    addPatientFlow();
                    break;
                case 3:
                    enqueuePatientFlow();
                    break;
                case 4:
                    dequeuePatient();
                    break;
                case 5:
                    listDoctors();
                    break;
                case 6:
                    listPatients();
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
            System.out.println();
        }
    }

    private void showMainMenu() {
        System.out.println("===== Hospital System Main Menu =====");
        System.out.println("1. Add Doctor");
        System.out.println("2. Add Patient");
        System.out.println("3. Enqueue Patient");
        System.out.println("4. Dequeue Patient");
        System.out.println("5. List Doctors");
        System.out.println("6. List Patients");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserInputInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private String getUserInputString() {
        scanner.nextLine(); // consume leftover newline
        return scanner.nextLine();
    }

    private void addDoctorFlow() {
        System.out.print("Enter doctor's name: ");
        String name = getUserInputString();
        System.out.print("Enter doctor's specialization: ");
        String specialization = getUserInputString();

        Doctor doctor = new Doctor(name, specialization); // Assuming such constructor exists
        addDoctor(doctor);
    }

    private void addPatientFlow() {
        System.out.print("Enter patient's name: ");
        String name = getUserInputString();
        System.out.print("Enter patient's IC/ID: ");
        String id = getUserInputString();

        Patient patient = new Patient(name, id); // Assuming such constructor exists
        addPatient(patient);
    }

    private void enqueuePatientFlow() {
        if (patientCount == 0) {
            System.out.println("No patients available to enqueue.");
            return;
        }

        listPatients();

        System.out.print("Enter patient number to enqueue: ");
        int index = getUserInputInt();
        if (index < 1 || index > patientCount) {
            System.out.println("Invalid patient number.");
            return;
        }

        enqueuePatient(patientList[index - 1]);
    }

    private void listDoctors() {
        System.out.println("Doctors List:");
        if (doctorCount == 0) {
            System.out.println("No doctors found.");
            return;
        }
        for (int i = 0; i < doctorCount; i++) {
            Doctor doc = doctorList[i];
            System.out.printf("%d. %s - Specialization: %s%n", i + 1, doc.getName(), doc.getSpecialization());
        }
    }

    private void listPatients() {
        System.out.println("Patients List:");
        if (patientCount == 0) {
            System.out.println("No patients found.");
            return;
        }
        for (int i = 0; i < patientCount; i++) {
            Patient p = patientList[i];
            System.out.printf("%d. %s (ID: %s)%n", i + 1, p.getName(), p.getId());
        }
    }

    // -------- Main method --------
    public static void main(String[] args) {
        HospitalSystem hospitalSystem = new HospitalSystem(10, 20, 50, 30, 10);
        hospitalSystem.run();
    }
}
