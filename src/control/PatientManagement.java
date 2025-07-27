package control;

import entity.Patient;
import adt.SetAndQueue;

public class PatientManagement {
    private SetAndQueue<Patient> patientSet = new SetAndQueue<>();    
    private SetAndQueue<Patient> queue = new SetAndQueue<>();     

    private int nextId = 1;

    //part for register patient
    public int registerPatient(Patient patient) {
        patient.setId(nextId++);
        boolean added = patientSet.add(patient);
        return added ? patient.getId() : -1; 
    }

    //get how many patient
    public int getPatientCount() {
        return patientSet.size();
    }

    //get patient at index
    public Patient getPatientAt(int index) {
        Patient[] arr = patientSet.toArray();
        if (index < 0 || index >= arr.length) return null;
        return arr[index];
    }

    //find the patient by id
    public Patient getPatientRecord(int id) {
        Patient[] arr = patientSet.toArray();
        for (Patient p : arr) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    //delete patient by id
    public boolean deletePatientRecord(int id) {
        Patient[] arr = patientSet.toArray();
        for (Patient p : arr) {
            if (p.getId() == id) {
                patientSet.remove(p);
                // Also remove from queue if present
                queue.remove(p);
                return true;
            }
        }
        return false;
    }

    //add patient id to queue
    public boolean manageQueue(int id) {
        Patient p = getPatientRecord(id);
        if (p == null) return false;
        if (queue.contains(p)) return false;
        queue.enqueue(p);
        p.setIsInWaiting(true);
        p.setCurrentStatus("waiting");
        return true;
    }

    //get next patient
    public Patient getNextPatientInQueue() {
        Patient p = queue.dequeue();
        if (p == null) return null;
        p.setIsInWaiting(false);
        p.setCurrentStatus("in consultation");
        return p;
    }

    //remove patient from the queue by id
    public boolean removePatientFromQueue(int id) {
        Patient[] arr = queue.toArray();
        for (Patient p : arr) {
            if (p.getId() == id) {
                queue.remove(p);
                p.setIsInWaiting(false);
                p.setCurrentStatus("completed");
                return true;
            }
        }
        return false;
    }

    //list all patient in queue by id
    public int getQueueSize() {
        return queue.size();
    }

    public Patient getQueuePatientAt(int index) {
        Patient[] arr = queue.toArray();
        if (index < 0 || index >= arr.length) return null;
        return arr[index];
    }

    //for seach patient
    public int getSearchPatientCount(String criteria) {
        int count = 0;
        Patient[] arr = patientSet.toArray();
        for (Patient p : arr) {
            if (String.valueOf(p.getId()).equals(criteria)
                || p.getName().toLowerCase().contains(criteria.toLowerCase())
                || p.getContactNumber().contains(criteria)) {
                count++;
            }
        }
        return count;
    }

    public Patient getSearchPatientAt(String criteria, int index) {
        int count = 0;
        Patient[] arr = patientSet.toArray();
        for (Patient p : arr) {
            if (String.valueOf(p.getId()).equals(criteria)
                || p.getName().toLowerCase().contains(criteria.toLowerCase())
                || p.getContactNumber().contains(criteria)) {
                if (count == index) return p;
                count++;
            }
        }
        return null;
    }
}
