package dao;

import entity.Medicine;
import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import entity.Treatment;
import entity.PharmacyTransaction;

public class DataInitializer {
    public static Medicine[] initializeSampleMedicines() {
        Medicine[] medicines = new Medicine[12];
        
        medicines[0] = new Medicine("MED001", "Paracetamol", "Panadol", 50, "31-12-2024", 8.50, "Pain Relief", "Paracetamol", "Analgesic");
        medicines[1] = new Medicine("MED002", "Amoxicillin", "Amoxil", 30, "30-06-2024", 15.80, "Antibiotic", "Amoxicillin", "Antibiotic");
        medicines[2] = new Medicine("MED003", "Ibuprofen", "Advil", 25, "15-09-2024", 12.90, "Pain Relief", "Ibuprofen", "NSAID");
        medicines[3] = new Medicine("MED004", "Omeprazole", "Losec", 15, "20-08-2024", 25.60, "Acid Reflux", "Omeprazole", "Proton Pump Inhibitor");
        medicines[4] = new Medicine("MED005", "Cetirizine", "Zyrtec", 5, "10-12-2024", 18.20, "Allergy Relief", "Cetirizine", "Antihistamine");
        medicines[5] = new Medicine("MED006", "Metformin", "Glucophage", 40, "28-02-2025", 22.40, "Diabetes Management", "Metformin", "Biguanide");
        medicines[6] = new Medicine("MED007", "Amlodipine", "Norvasc", 35, "15-03-2025", 32.80, "Hypertension", "Amlodipine", "Calcium Channel Blocker");
        medicines[7] = new Medicine("MED008", "Salbutamol", "Ventolin", 45, "10-04-2025", 28.90, "Asthma Relief", "Salbutamol", "Bronchodilator");
        medicines[8] = new Medicine("MED009", "Sertraline", "Zoloft", 20, "05-05-2025", 45.60, "Depression Treatment", "Sertraline", "SSRI");
        medicines[9] = new Medicine("MED010", "Atorvastatin", "Lipitor", 30, "20-06-2025", 58.90, "Cholesterol Management", "Atorvastatin", "Statin");
        medicines[10] = new Medicine("MED011", "Aspirin", "Bayer", 8, "15-01-2024", 6.50, "Pain Relief", "Acetylsalicylic Acid", "NSAID");
        medicines[11] = new Medicine("MED012", "Insulin", "Humalog", 12, "30-07-2025", 120.00, "Diabetes Management", "Insulin Lispro", "Insulin");
        
        return medicines;
    }
    
    public static Patient[] initializeSamplePatients() {
        Patient[] patients = new Patient[15];
        
        patients[0] = new Patient(1, "Ahmad bin Abdullah", 35, "Male", "Penicillin", "012-3456789", "123 Jalan Tunku Abdul Rahman, Kuala Lumpur", "01-01-2024", "Hypertension", false, "waiting");
        patients[1] = new Patient(2, "Siti binti Mohamed", 28, "Female", "None", "012-3456790", "456 Jalan Sultan Ismail, Petaling Jaya", "02-01-2024", "Diabetes", false, "waiting");
        patients[2] = new Patient(3, "Raj a/l Kumar", 45, "Male", "Sulfa", "012-3456791", "789 Jalan Bukit Bintang, Kuala Lumpur", "03-01-2024", "Asthma", false, "waiting");
        patients[3] = new Patient(4, "Lim Siew Mei", 32, "Female", "None", "012-3456792", "321 Jalan Ampang, Kuala Lumpur", "04-01-2024", "None", false, "waiting");
        patients[4] = new Patient(5, "Tan Ah Kow", 50, "Male", "Codeine", "012-3456793", "654 Jalan Pudu, Kuala Lumpur", "05-01-2024", "Heart Disease", false, "waiting");
        patients[5] = new Patient(6, "Nurul Huda binti Ismail", 26, "Female", "Latex", "012-3456794", "987 Jalan Cheras, Kuala Lumpur", "06-01-2024", "Migraine", false, "waiting");
        patients[6] = new Patient(7, "Krishnan a/l Muthu", 38, "Male", "None", "012-3456795", "147 Jalan Klang Lama, Kuala Lumpur", "07-01-2024", "Depression", false, "waiting");
        patients[7] = new Patient(8, "Wong Mei Ling", 42, "Female", "Iodine", "012-3456796", "258 Jalan Ipoh, Kuala Lumpur", "08-01-2024", "Thyroid Disorder", false, "waiting");
        patients[8] = new Patient(9, "Mohamed Ali bin Hassan", 29, "Male", "None", "012-3456797", "369 Jalan Gombak, Kuala Lumpur", "09-01-2024", "Anxiety", false, "waiting");
        patients[9] = new Patient(10, "Cheah Siew Fong", 33, "Female", "Shellfish", "012-3456798", "741 Jalan Damansara, Petaling Jaya", "10-01-2024", "None", false, "waiting");
        patients[10] = new Patient(11, "Arun a/l Subramaniam", 47, "Male", "None", "012-3456799", "852 Jalan Bangsar, Kuala Lumpur", "11-01-2024", "High Cholesterol", false, "waiting");
        patients[11] = new Patient(12, "Fatimah binti Omar", 31, "Female", "Peanuts", "012-3456800", "963 Jalan TAR, Kuala Lumpur", "12-01-2024", "None", false, "waiting");
        patients[12] = new Patient(13, "Lee Chong Wei", 39, "Male", "None", "012-3456801", "159 Jalan Imbi, Kuala Lumpur", "13-01-2024", "Sleep Apnea", false, "waiting");
        patients[13] = new Patient(14, "Aisha binti Yusof", 25, "Female", "None", "012-3456802", "357 Jalan Raja Chulan, Kuala Lumpur", "14-01-2024", "None", false, "waiting");
        patients[14] = new Patient(15, "Gan Eng Seng", 44, "Male", "Aspirin", "012-3456803", "486 Jalan Tuanku Abdul Rahman, Kuala Lumpur", "15-01-2024", "Gout", false, "waiting");
        
        return patients;
    }

    public static Doctor[] initializeSampleDoctors() {
        Doctor[] doctors = new Doctor[8];
        
        doctors[0] = new Doctor("DOC001", "Dr. Sarah Chen Mei Ling", "Cardiology", "012-3456780", "sarah.chen@chubbyclinic.com.my", true, "Mon-Wed 9AM-5PM", false, "", "");
        doctors[1] = new Doctor("DOC002", "Dr. Robert Kim Ah Kow", "Pediatrics", "012-3456781", "robert.kim@chubbyclinic.com.my", true, "Tue-Thu 8AM-4PM", false, "", "");
        doctors[2] = new Doctor("DOC003", "Dr. Lisa Wong Siew Mei", "Neurology", "012-3456782", "lisa.wong@chubbyclinic.com.my", true, "Wed-Fri 10AM-6PM", false, "", "");
        doctors[3] = new Doctor("DOC004", "Dr. James Lee Chong Wei", "Orthopedics", "012-3456783", "james.lee@chubbyclinic.com.my", false, "Mon-Fri 9AM-5PM", true, "15-01-2024", "20-01-2024");
        doctors[4] = new Doctor("DOC005", "Dr. Maria Garcia binti Abdullah", "Endocrinology", "012-3456784", "maria.garcia@chubbyclinic.com.my", true, "Mon-Fri 8AM-4PM", false, "", "");
        doctors[5] = new Doctor("DOC006", "Dr. David Wilson a/l Kumar", "Psychiatry", "012-3456785", "david.wilson@chubbyclinic.com.my", true, "Tue-Sat 9AM-5PM", false, "", "");
        doctors[6] = new Doctor("DOC007", "Dr. Jennifer Brown Mei Fong", "Dermatology", "012-3456786", "jennifer.brown@chubbyclinic.com.my", true, "Mon-Thu 10AM-6PM", false, "", "");
        doctors[7] = new Doctor("DOC008", "Dr. Michael Taylor bin Mohamed", "Emergency Medicine", "012-3456787", "michael.taylor@chubbyclinic.com.my", true, "24/7 Shifts", false, "", "");
        
        return doctors;
    }

    public static Consultation[] initializeSampleConsultations() {
        Consultation[] consultations = new Consultation[12];
        
        consultations[0] = new Consultation("CON001", "1", "DOC001", "10-01-2024", "completed", "Patient shows signs of hypertension", "20-01-2024");
        consultations[1] = new Consultation("CON002", "2", "DOC002", "12-01-2024", "scheduled", "Regular checkup", "25-01-2024");
        consultations[2] = new Consultation("CON003", "3", "DOC003", "15-01-2024", "completed", "Neurological examination completed", "30-01-2024");
        consultations[3] = new Consultation("CON004", "4", "DOC005", "16-01-2024", "completed", "Diabetes management consultation", "26-01-2024");
        consultations[4] = new Consultation("CON005", "5", "DOC001", "17-01-2024", "scheduled", "Cardiac assessment", "27-01-2024");
        consultations[5] = new Consultation("CON006", "6", "DOC006", "18-01-2024", "completed", "Mental health evaluation", "28-01-2024");
        consultations[6] = new Consultation("CON007", "7", "DOC007", "19-01-2024", "scheduled", "Skin condition review", "29-01-2024");
        consultations[7] = new Consultation("CON008", "8", "DOC002", "20-01-2024", "completed", "Pediatric consultation", "30-01-2024");
        consultations[8] = new Consultation("CON009", "9", "DOC006", "21-01-2024", "scheduled", "Anxiety treatment follow-up", "31-01-2024");
        consultations[9] = new Consultation("CON010", "10", "DOC005", "22-01-2024", "completed", "Thyroid function test review", "01-02-2024");
        consultations[10] = new Consultation("CON011", "11", "DOC001", "23-01-2024", "scheduled", "Cholesterol management", "02-02-2024");
        consultations[11] = new Consultation("CON012", "12", "DOC003", "24-01-2024", "completed", "Neurological follow-up", "03-02-2024");
        
        return consultations;
    }

    public static Treatment[] initializeSampleTreatments() {
        Treatment[] treatments = new Treatment[8];
        
        treatments[0] = new Treatment("TRE001", "1", "DOC001", "Hypertension", "MED007", "10-01-2024", "20-01-2024");
        treatments[1] = new Treatment("TRE002", "3", "DOC003", "Migraine", "MED003", "15-01-2024", "30-01-2024");
        treatments[2] = new Treatment("TRE003", "2", "DOC005", "Diabetes Type 2", "MED006", "16-01-2024", "26-01-2024");
        treatments[3] = new Treatment("TRE004", "6", "DOC006", "Depression", "MED009", "18-01-2024", "28-01-2024");
        treatments[4] = new Treatment("TRE005", "4", "DOC005", "Hypothyroidism", "MED004", "19-01-2024", "29-01-2024");
        treatments[5] = new Treatment("TRE006", "8", "DOC002", "Childhood Asthma", "MED008", "20-01-2024", "30-01-2024");
        treatments[6] = new Treatment("TRE007", "9", "DOC006", "Anxiety Disorder", "MED009", "21-01-2024", "31-01-2024");
        treatments[7] = new Treatment("TRE008", "11", "DOC001", "High Cholesterol", "MED010", "23-01-2024", "02-02-2024");
        
        return treatments;
    }

    public static PharmacyTransaction[] initializeSampleTransactions() {
        PharmacyTransaction[] transactions = new PharmacyTransaction[15];
        
        transactions[0] = new PharmacyTransaction("TXN001", "1", "MED007", 2, "10-01-2024");
        transactions[1] = new PharmacyTransaction("TXN002", "3", "MED003", 1, "15-01-2024");
        transactions[2] = new PharmacyTransaction("TXN003", "2", "MED006", 3, "16-01-2024");
        transactions[3] = new PharmacyTransaction("TXN004", "6", "MED009", 1, "18-01-2024");
        transactions[4] = new PharmacyTransaction("TXN005", "4", "MED004", 2, "19-01-2024");
        transactions[5] = new PharmacyTransaction("TXN006", "8", "MED008", 1, "20-01-2024");
        transactions[6] = new PharmacyTransaction("TXN007", "9", "MED009", 2, "21-01-2024");
        transactions[7] = new PharmacyTransaction("TXN008", "11", "MED010", 1, "23-01-2024");
        transactions[8] = new PharmacyTransaction("TXN009", "12", "MED003", 1, "24-01-2024");
        transactions[9] = new PharmacyTransaction("TXN010", "5", "MED007", 1, "25-01-2024");
        transactions[10] = new PharmacyTransaction("TXN011", "7", "MED001", 2, "26-01-2024");
        transactions[11] = new PharmacyTransaction("TXN012", "10", "MED005", 1, "27-01-2024");
        transactions[12] = new PharmacyTransaction("TXN013", "13", "MED002", 1, "28-01-2024");
        transactions[13] = new PharmacyTransaction("TXN014", "14", "MED001", 1, "29-01-2024");
        transactions[14] = new PharmacyTransaction("TXN015", "15", "MED003", 2, "30-01-2024");
        
        return transactions;
    }
} 