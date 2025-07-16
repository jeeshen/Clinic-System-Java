package control;
import entity.*;

//main class
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
    }

    // Example method skeletons
    public void addDoctor(Doctor doctor) {
        if (doctorCount < doctorList.length) {
            doctorList[doctorCount++] = doctor;
        }
    }

    public void addPatient(Patient patient) {
        if (patientCount < patientList.length) {
            patientList[patientCount++] = patient;
        }
    }

    public void enqueuePatient(Patient patient) {
        if (queueSize < waitingQueue.length) {
            waitingQueue[queueSize++] = patient;
        }
    }

    public Patient dequeuePatient() {
        if (queueSize == 0) return null;
        Patient first = waitingQueue[0];
        for (int i = 1; i < queueSize; i++) {
            waitingQueue[i - 1] = waitingQueue[i];
        }
        waitingQueue[--queueSize] = null;
        return first;
    }

    public void addMedicine(Medicine medicine) {
        if (medicineCount < medicineList.length) {
            medicineList[medicineCount++] = medicine;
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

        // Create exact-sized result array
        Medicine[] result = new Medicine[count];
        for (int i = 0; i < count; i++) {
            result[i] = alternatives[i];
        }
        return result;
    }

    // You can later add:
    // - autoAssignDoctor()
    // - replaceDoctorOnLeave()
    // - recommendMedicineAlternative()
}
