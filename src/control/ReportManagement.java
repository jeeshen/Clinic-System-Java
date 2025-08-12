package control;

import java.util.Scanner;
import adt.SetAndQueueInterface;
import adt.SetAndQueue;
import utility.StringUtility;

public class ReportManagement {
    private Scanner scanner;
    
    public ReportManagement() {
        scanner = new Scanner(System.in);
    }
    
    public void generateComprehensiveMedicalReport(PatientManagement patientManagement, DoctorManagement doctorManagement, ConsultationManagement consultationManagement, TreatmentManagement treatmentManagement) {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("        COMPREHENSIVE MEDICAL REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 80));

        int totalPatients = patientManagement.getTotalPatientCount();
        int totalDoctors = doctorManagement.getTotalDoctorCount();
        int totalConsultations = consultationManagement.getTotalConsultationCount();
        int totalPrescriptions = treatmentManagement.getTotalPrescriptionCount();
        double totalRevenue = treatmentManagement.getTotalRevenue();
        int paidPrescriptions = treatmentManagement.getPaidPrescriptionCount();
        int queueSize = patientManagement.getQueueSize();
        int doctorsOnDuty = doctorManagement.getDoctorsOnDutyCount();

        String[] headers = {"Metric", "Value"};
        Object[][] rows = {
            {"Total Patients", totalPatients},
            {"Total Doctors", totalDoctors},
            {"Total Consultations", totalConsultations},
            {"Total Prescriptions", totalPrescriptions},
            {"Total Revenue", String.format("RM %.2f", totalRevenue)},
            {"Paid Prescriptions", paidPrescriptions},
            {"Doctors on Duty", doctorsOnDuty},
            {"Patients in Queue", queueSize}
        };
        System.out.println("\nSYSTEM OVERVIEW:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));
        System.out.println("\nSUMMARY:");
        System.out.println("• Total Patients: " + totalPatients);
        System.out.println("• Total Doctors: " + totalDoctors);
        System.out.println("• Total Consultations: " + totalConsultations);
        System.out.println("• Total Prescriptions: " + totalPrescriptions);
        System.out.println("• Total Revenue: RM " + String.format("%.2f", totalRevenue));
        System.out.println("• Paid Prescriptions: " + paidPrescriptions);
        System.out.println("• Doctors on Duty: " + doctorsOnDuty);
        System.out.println("• Patients in Queue: " + queueSize);
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void generateAgeVsDiseaseAnalysisReport(PatientManagement patientManagement, TreatmentManagement treatmentManagement) {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("        DISEASE AGE DISTRIBUTION REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 80));

        Object[] patientsArray = patientManagement.getAllPatients();
        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
        int[] ageGroups = {0, 20, 30, 40, 50, 60, 100};
        String[] ageGroupNames = {"0-19", "20-29", "30-39", "40-49", "50-59", "60+"};
        int[] ageGroupCounts = new int[ageGroups.length - 1];
        SetAndQueueInterface<String>[] diseaseByAgeGroup = new SetAndQueue[ageGroups.length - 1];
        for (int i = 0; i < diseaseByAgeGroup.length; i++) diseaseByAgeGroup[i] = new SetAndQueue<>();
        SetAndQueueInterface<String> allDiseases = new SetAndQueue<>();
        for (Object prescriptionObj : prescriptionsArray) {
            entity.Prescription prescription = (entity.Prescription) prescriptionObj;
            String diagnosis = prescription.getDiagnosis();
            String patientId = prescription.getPatientId();
            int patientAge = 0;
            for (Object patientObj : patientsArray) {
                entity.Patient patient = (entity.Patient) patientObj;
                if (String.valueOf(patient.getId()).equals(patientId)) {
                    patientAge = patient.getAge();
                    break;
                }
            }
            int ageGroupIndex = 0;
            for (int i = 0; i < ageGroups.length - 1; i++) {
                if (patientAge >= ageGroups[i] && patientAge < ageGroups[i + 1]) {
                    ageGroupIndex = i;
                    break;
                }
            }
            ageGroupCounts[ageGroupIndex]++;
            allDiseases.add(diagnosis); //adt method
            diseaseByAgeGroup[ageGroupIndex].add(diagnosis); //adt method
        }
        int maxAgeCount = 0;
        for (int i = 0; i < ageGroupNames.length; i++) {
            if (ageGroupCounts[i] > maxAgeCount) maxAgeCount = ageGroupCounts[i];
        }
        System.out.println("\nAGE GROUP DISTRIBUTION:");

        int barWidth = 30;
        for (int i = 0; i < ageGroupNames.length; i++) {
            System.out.printf("%-30s   [%s] %d patients\n", ageGroupNames[i], StringUtility.greenBarChart(ageGroupCounts[i], maxAgeCount, barWidth), ageGroupCounts[i]);
        }
        System.out.println("\nDISEASES BY AGE GROUP:");
        for (int i = 0; i < ageGroupNames.length; i++) {
            if (!diseaseByAgeGroup[i].isEmpty()) { //adt method
                System.out.println(ageGroupNames[i] + ":");;
                Object[] diseasesArray = diseaseByAgeGroup[i].toArray(); //adt method
                Object[][] diseaseRows = new Object[diseasesArray.length][2];
                int maxCases = 0;
                for (int j = 0; j < diseasesArray.length; j++) {
                    String diag = (String) diseasesArray[j];
                    int count = countDiseaseInAgeGroup(diag, prescriptionsArray, patientsArray, ageGroups[i], ageGroups[i + 1]);
                    diseaseRows[j][0] = diag;
                    diseaseRows[j][1] = count;
                    if (count > maxCases) maxCases = count;
                }
                for (int j = 0; j < diseasesArray.length; j++) {
                    String diag = (String) diseasesArray[j];
                    int count = countDiseaseInAgeGroup(diag, prescriptionsArray, patientsArray, ageGroups[i], ageGroups[i + 1]);
                    System.out.printf("  %-30s [%s] %d cases\n", diag, StringUtility.greenBarChart(count, maxCases, barWidth), count);
                }
            }
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Patients: " + patientsArray.length);
        System.out.println("• Total Diagnoses: " + allDiseases.size()); //adt method
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private int countDiseaseInAgeGroup(String disease, Object[] prescriptionsArray, Object[] patientsArray, int minAge, int maxAge) {
        int count = 0;
        for (Object prescriptionObj : prescriptionsArray) {
            entity.Prescription prescription = (entity.Prescription) prescriptionObj;
            if (prescription.getDiagnosis().equals(disease)) {
                String patientId = prescription.getPatientId();
                for (Object patientObj : patientsArray) {
                    entity.Patient patient = (entity.Patient) patientObj;
                    if (String.valueOf(patient.getId()).equals(patientId)) {
                        int age = patient.getAge();
                        if (age >= minAge && age < maxAge) {
                            count++;
                        }
                        break;
                    }
                }
            }
        }
        return count;
    }
    
    public void generateMedicineUsageByDiseaseReport(TreatmentManagement treatmentManagement, PatientManagement patientManagement) {
        System.out.println("\n" + StringUtility.repeatString("=", 80));
        System.out.println("        MEDICINE USAGE BY DISEASE REPORT");
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Generated at: " + StringUtility.getCurrentDateTime());
        System.out.println(StringUtility.repeatString("-", 80));

        Object[] prescriptionsArray = treatmentManagement.getAllPrescriptions();
        SetAndQueueInterface<String> medicineNames = new SetAndQueue<>();
        SetAndQueueInterface<String>[] medicineToPatients = new SetAndQueue[100];
        SetAndQueueInterface<String>[] medicineToDiseases = new SetAndQueue[100];
        int maxPatients = 0;
        int medicineIndex = 0;
        for (Object obj : prescriptionsArray) {
            entity.Prescription prescription = (entity.Prescription) obj;
            String diagnosis = prescription.getDiagnosis();
            String patientId = prescription.getPatientId();
            entity.Patient patient = patientManagement.findPatientById(Integer.parseInt(patientId));
            if (patient == null) continue;
            adt.SetAndQueueInterface<entity.PrescribedMedicine> meds = prescription.getPrescribedMedicines();
            Object[] medsArray = meds.toArray(); //adt method
            for (Object medObj : medsArray) {
                entity.PrescribedMedicine med = (entity.PrescribedMedicine) medObj;
                String medName = med.getMedicineName();
                //find or create index for this medicine
                int medIdx = -1;
                Object[] existingMeds = medicineNames.toArray(); //adt method
                for (int i = 0; i < existingMeds.length; i++) {
                    if (existingMeds[i].equals(medName)) {
                        medIdx = i;
                        break;
                    }
                }
                if (medIdx == -1) {
                    medIdx = medicineIndex++;
                    medicineNames.add(medName); //adt method
                    medicineToPatients[medIdx] = new SetAndQueue<>();
                    medicineToDiseases[medIdx] = new SetAndQueue<>();
                }
                //track unique patients and diseases
                medicineToPatients[medIdx].add(patientId); //adt method
                medicineToDiseases[medIdx].add(diagnosis); //adt method
            }
        }

        String[] headers = {"Medicine", "Unique Patients", "Diseases"};
        Object[] medNamesArray = medicineNames.toArray(); //adt method
        Object[][] rows = new Object[medNamesArray.length][headers.length];
        for (int i = 0; i < medNamesArray.length; i++) {
            String medName = (String) medNamesArray[i];
            rows[i][0] = medName;
            rows[i][1] = medicineToPatients[i].size(); //adt method
            //convert diseases set to comma-separated string
            Object[] diseasesArray = medicineToDiseases[i].toArray(); //adt method
            StringBuilder diseasesStr = new StringBuilder();
            for (int j = 0; j < diseasesArray.length; j++) {
                if (j > 0) diseasesStr.append(", ");
                diseasesStr.append(diseasesArray[j]);
            }
            rows[i][2] = diseasesStr.toString();
            if (medicineToPatients[i].size() > maxPatients) { //adt method
                maxPatients = medicineToPatients[i].size(); //adt method
            }
        }
        System.out.println("\nMEDICINE USAGE TABLE:");
        System.out.print(StringUtility.formatTableWithDividers(headers, rows));

        System.out.println("\nPATIENT COUNT PER MEDICINE:");
        int barWidth = 30;
        for (int i = 0; i < medNamesArray.length; i++) {
            String medName = (String) medNamesArray[i];
            int count = medicineToPatients[i].size(); //adt method
            System.out.printf("%-20s [%s] %d patients\n", medName, StringUtility.greenBarChart(count, maxPatients, barWidth), count);
        }

        System.out.println("\nSUMMARY:");
        System.out.println("• Total Medicines: " + medNamesArray.length);
        System.out.println("• Max Unique Patients for a Medicine: " + maxPatients);
        System.out.println(StringUtility.repeatString("=", 80));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
} 