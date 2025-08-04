package dao;

import entity.Medicine;
import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import entity.Treatment;
import entity.PharmacyTransaction;
import entity.Prescription;
import entity.PrescribedMedicine;
import adt.SetAndQueue;
import adt.SetAndQueueInterface;

public class DataInitializer {
    public static Medicine[] initializeSampleMedicines() {
        Medicine[] medicines = new Medicine[20];
        
        medicines[0] = new Medicine("MED001", "Paracetamol", "Panadol", 50, "31-12-2025", 8.50, "Pain Relief", "Paracetamol", "Analgesic");
        medicines[1] = new Medicine("MED002", "Amoxicillin", "Amoxil", 30, "30-09-2025", 15.80, "Antibiotic", "Amoxicillin", "Antibiotic");
        medicines[2] = new Medicine("MED003", "Ibuprofen", "Advil", 25, "15-10-2025", 12.90, "Pain Relief", "Ibuprofen", "NSAID");
        medicines[3] = new Medicine("MED004", "Omeprazole", "Losec", 15, "20-11-2025", 25.60, "Acid Reflux", "Omeprazole", "Proton Pump Inhibitor");
        medicines[4] = new Medicine("MED005", "Cetirizine", "Zyrtec", 5, "10-12-2025", 18.20, "Allergy Relief", "Cetirizine", "Antihistamine");
        medicines[5] = new Medicine("MED006", "Metformin", "Glucophage", 40, "28-01-2026", 22.40, "Diabetes Management", "Metformin", "Biguanide");
        medicines[6] = new Medicine("MED007", "Amlodipine", "Norvasc", 35, "15-02-2026", 32.80, "Hypertension", "Amlodipine", "Calcium Channel Blocker");
        medicines[7] = new Medicine("MED008", "Salbutamol", "Ventolin", 45, "10-03-2026", 28.90, "Asthma Relief", "Salbutamol", "Bronchodilator");
        medicines[8] = new Medicine("MED009", "Sertraline", "Zoloft", 20, "05-04-2026", 45.60, "Depression Treatment", "Sertraline", "SSRI");
        medicines[9] = new Medicine("MED010", "Atorvastatin", "Lipitor", 30, "20-05-2026", 58.90, "Cholesterol Management", "Atorvastatin", "Statin");
        medicines[10] = new Medicine("MED011", "Aspirin", "Bayer", 8, "15-06-2026", 6.50, "Pain Relief", "Acetylsalicylic Acid", "NSAID");
        medicines[11] = new Medicine("MED012", "Insulin", "Humalog", 12, "30-07-2026", 120.00, "Diabetes Management", "Insulin Lispro", "Insulin");
        medicines[12] = new Medicine("MED013", "Loratadine", "Claritin", 18, "25-08-2026", 16.80, "Allergy Relief", "Loratadine", "Antihistamine");
        medicines[13] = new Medicine("MED014", "Lisinopril", "Zestril", 22, "10-09-2026", 28.40, "Hypertension", "Lisinopril", "ACE Inhibitor");
        medicines[14] = new Medicine("MED015", "Fluoxetine", "Prozac", 15, "05-10-2026", 42.30, "Depression Treatment", "Fluoxetine", "SSRI");
        medicines[15] = new Medicine("MED016", "Simvastatin", "Zocor", 25, "20-11-2026", 35.70, "Cholesterol Management", "Simvastatin", "Statin");
        medicines[16] = new Medicine("MED017", "Montelukast", "Singulair", 12, "15-12-2026", 38.90, "Asthma Prevention", "Montelukast", "Leukotriene Receptor Antagonist");
        medicines[17] = new Medicine("MED018", "Pantoprazole", "Protonix", 20, "10-01-2027", 31.20, "Acid Reflux", "Pantoprazole", "Proton Pump Inhibitor");
        medicines[18] = new Medicine("MED019", "Duloxetine", "Cymbalta", 10, "05-02-2027", 67.80, "Depression Treatment", "Duloxetine", "SNRI");
        medicines[19] = new Medicine("MED020", "Losartan", "Cozaar", 28, "20-03-2027", 29.60, "Hypertension", "Losartan", "Angiotensin Receptor Blocker");
        
        return medicines;
    }
    
    public static Patient[] initializeSamplePatients() {
        Patient[] patients = new Patient[25];
        
        patients[0] = new Patient(1, "Ahmad bin Abdullah", 35, "Male", "Penicillin", "012-3456789", "123 Jalan Tunku Abdul Rahman, Kuala Lumpur", "01-07-2025", "Hypertension", false, "Waiting");
        patients[1] = new Patient(2, "Siti binti Mohamed", 28, "Female", "None", "012-3456790", "456 Jalan Sultan Ismail, Petaling Jaya", "02-07-2025", "Diabetes", false, "Waiting");
        patients[2] = new Patient(3, "Raj a/l Kumar", 45, "Male", "Sulfa", "012-3456791", "789 Jalan Bukit Bintang, Kuala Lumpur", "03-07-2025", "Asthma", false, "Waiting");
        patients[3] = new Patient(4, "Lim Siew Mei", 32, "Female", "None", "012-3456792", "321 Jalan Ampang, Kuala Lumpur", "04-07-2025", "None", false, "Waiting");
        patients[4] = new Patient(5, "Tan Ah Kow", 50, "Male", "Codeine", "012-3456793", "654 Jalan Pudu, Kuala Lumpur", "05-07-2025", "Heart Disease", false, "Waiting");
        patients[5] = new Patient(6, "Nurul Huda binti Ismail", 26, "Female", "Latex", "012-3456794", "987 Jalan Cheras, Kuala Lumpur", "06-07-2025", "Migraine", false, "Waiting");
        patients[6] = new Patient(7, "Krishnan a/l Muthu", 38, "Male", "None", "012-3456795", "147 Jalan Klang Lama, Kuala Lumpur", "07-07-2025", "Depression", false, "Waiting");
        patients[7] = new Patient(8, "Wong Mei Ling", 42, "Female", "Iodine", "012-3456796", "258 Jalan Ipoh, Kuala Lumpur", "08-07-2025", "Thyroid Disorder", false, "Waiting");
        patients[8] = new Patient(9, "Mohamed Ali bin Hassan", 29, "Male", "None", "012-3456797", "369 Jalan Gombak, Kuala Lumpur", "09-07-2025", "Anxiety", false, "Waiting");
        patients[9] = new Patient(10, "Cheah Siew Fong", 33, "Female", "Shellfish", "012-3456798", "741 Jalan Damansara, Petaling Jaya", "10-07-2025", "None", false, "Waiting");
        patients[10] = new Patient(11, "Arun a/l Subramaniam", 47, "Male", "None", "012-3456799", "852 Jalan Bangsar, Kuala Lumpur", "11-07-2025", "High Cholesterol", false, "Waiting");
        patients[11] = new Patient(12, "Fatimah binti Omar", 31, "Female", "Peanuts", "012-3456800", "963 Jalan TAR, Kuala Lumpur", "12-07-2025", "None", false, "Waiting");
        patients[12] = new Patient(13, "Lee Chong Wei", 39, "Male", "None", "012-3456801", "159 Jalan Imbi, Kuala Lumpur", "13-07-2025", "Sleep Apnea", false, "Waiting");
        patients[13] = new Patient(14, "Aisha binti Yusof", 25, "Female", "None", "012-3456802", "357 Jalan Raja Chulan, Kuala Lumpur", "14-07-2025", "None", false, "Waiting");
        patients[14] = new Patient(15, "Gan Eng Seng", 44, "Male", "Aspirin", "012-3456803", "486 Jalan Tuanku Abdul Rahman, Kuala Lumpur", "15-07-2025", "Gout", false, "Waiting");
        patients[15] = new Patient(16, "Zainab binti Ahmad", 36, "Female", "None", "012-3456804", "753 Jalan Raja Laut, Kuala Lumpur", "16-07-2025", "Hypertension", false, "Waiting");
        patients[16] = new Patient(17, "Kumar a/l Rajendran", 41, "Male", "Sulfa", "012-3456805", "951 Jalan Sultan, Kuala Lumpur", "17-07-2025", "Diabetes", false, "Waiting");
        patients[17] = new Patient(18, "Chan Mei Lin", 27, "Female", "Latex", "012-3456806", "357 Jalan Pahang, Kuala Lumpur", "18-07-2025", "Asthma", false, "Waiting");
        patients[18] = new Patient(19, "Ismail bin Omar", 52, "Male", "None", "012-3456807", "159 Jalan Masjid India, Kuala Lumpur", "19-07-2025", "Heart Disease", false, "Waiting");
        patients[19] = new Patient(20, "Priya a/p Ramasamy", 34, "Female", "Penicillin", "012-3456808", "753 Jalan Petaling, Kuala Lumpur", "20-07-2025", "Migraine", false, "Waiting");
        patients[20] = new Patient(21, "Ong Teck Seng", 48, "Male", "None", "012-3456809", "951 Jalan Chow Kit, Kuala Lumpur", "21-07-2025", "Depression", false, "Waiting");
        patients[21] = new Patient(22, "Noraini binti Zainal", 30, "Female", "Iodine", "012-3456810", "357 Jalan Tun Perak, Kuala Lumpur", "22-07-2025", "Thyroid Disorder", false, "Waiting");
        patients[22] = new Patient(23, "Muthu a/l Velu", 37, "Male", "None", "012-3456811", "159 Jalan Dang Wangi, Kuala Lumpur", "23-07-2025", "Anxiety", false, "Waiting");
        patients[23] = new Patient(24, "Lau Siew Mei", 29, "Female", "Shellfish", "012-3456812", "753 Jalan Tun Razak, Kuala Lumpur", "24-07-2025", "None", false, "Waiting");
        patients[24] = new Patient(25, "Hassan bin Ali", 46, "Male", "None", "012-3456813", "951 Jalan Ampang, Kuala Lumpur", "25-07-2025", "High Cholesterol", false, "Waiting");
        
        return patients;
    }

    public static Doctor[] initializeSampleDoctors() {
        Doctor[] doctors = new Doctor[12];
        
        doctors[0] = new Doctor("DOC001", "Dr. Sarah Chen Mei Ling", "Cardiology", "012-3456780", "sarah.chen@chubbyclinic.com.my", true, "Mon-Wed 9AM-5PM", false, "", "");
        doctors[1] = new Doctor("DOC002", "Dr. Robert Kim Ah Kow", "Pediatrics", "012-3456781", "robert.kim@chubbyclinic.com.my", true, "Tue-Thu 8AM-4PM", false, "", "");
        doctors[2] = new Doctor("DOC003", "Dr. Lisa Wong Siew Mei", "Neurology", "012-3456782", "lisa.wong@chubbyclinic.com.my", true, "Wed-Fri 10AM-6PM", false, "", "");
        doctors[3] = new Doctor("DOC004", "Dr. James Lee Chong Wei", "Orthopedics", "012-3456783", "james.lee@chubbyclinic.com.my", false, "Mon-Fri 9AM-5PM", true, "15-07-2025", "20-07-2025");
        doctors[4] = new Doctor("DOC005", "Dr. Maria Garcia binti Abdullah", "Endocrinology", "012-3456784", "maria.garcia@chubbyclinic.com.my", true, "Mon-Fri 8AM-4PM", false, "", "");
        doctors[5] = new Doctor("DOC006", "Dr. David Wilson a/l Kumar", "Psychiatry", "012-3456785", "david.wilson@chubbyclinic.com.my", true, "Tue-Sat 9AM-5PM", false, "", "");
        doctors[6] = new Doctor("DOC007", "Dr. Jennifer Brown Mei Fong", "Dermatology", "012-3456786", "jennifer.brown@chubbyclinic.com.my", true, "Mon-Thu 10AM-6PM", false, "", "");
        doctors[7] = new Doctor("DOC008", "Dr. Michael Taylor bin Mohamed", "Emergency Medicine", "012-3456787", "michael.taylor@chubbyclinic.com.my", true, "24/7 Shifts", false, "", "");
        doctors[8] = new Doctor("DOC009", "Dr. Amanda Lim Siew Lin", "Oncology", "012-3456788", "amanda.lim@chubbyclinic.com.my", true, "Mon-Fri 9AM-5PM", false, "", "");
        doctors[9] = new Doctor("DOC010", "Dr. Christopher Tan Ah Beng", "Radiology", "012-3456789", "christopher.tan@chubbyclinic.com.my", true, "Mon-Fri 8AM-4PM", false, "", "");
        doctors[10] = new Doctor("DOC011", "Dr. Emily Wong Mei Ling", "Obstetrics & Gynecology", "012-3456790", "emily.wong@chubbyclinic.com.my", true, "Tue-Sat 9AM-5PM", false, "", "");
        doctors[11] = new Doctor("DOC012", "Dr. Benjamin Raj a/l Kumar", "Urology", "012-3456791", "benjamin.raj@chubbyclinic.com.my", true, "Mon-Thu 10AM-6PM", false, "", "");
        
        return doctors;
    }

    public static Consultation[] initializeSampleConsultations() {
        Consultation[] consultations = new Consultation[20];
        
        consultations[0] = new Consultation("CON001", "1", "DOC001", "10-07-2025", "Completed", "Patient shows signs of hypertension");
        consultations[1] = new Consultation("CON002", "2", "DOC002", "12-07-2025", "Completed", "Regular checkup");
        consultations[2] = new Consultation("CON003", "3", "DOC003", "15-07-2025", "Completed", "Neurological examination completed");
        consultations[3] = new Consultation("CON004", "4", "DOC005", "16-07-2025", "Completed", "Diabetes management consultation");
        consultations[4] = new Consultation("CON005", "5", "DOC001", "17-07-2025", "Completed", "Cardiac assessment");
        consultations[5] = new Consultation("CON006", "6", "DOC006", "18-07-2025", "Completed", "Mental health evaluation");
        consultations[6] = new Consultation("CON007", "7", "DOC007", "19-07-2025", "Completed", "Skin condition review");
        consultations[7] = new Consultation("CON008", "8", "DOC002", "20-07-2025", "Completed", "Pediatric consultation");
        consultations[8] = new Consultation("CON009", "9", "DOC006", "21-07-2025", "Completed", "Anxiety treatment");
        consultations[9] = new Consultation("CON010", "10", "DOC005", "21-07-2025", "Completed", "Thyroid function test review");
        consultations[10] = new Consultation("CON011", "11", "DOC001", "23-07-2025", "Completed", "Cholesterol management");
        consultations[11] = new Consultation("CON012", "12", "DOC003", "24-07-2025", "Completed", "Neurological examination");
        consultations[12] = new Consultation("CON013", "13", "DOC007", "25-07-2025", "Completed", "Dermatological examination");
        consultations[13] = new Consultation("CON014", "14", "DOC008", "26-07-2025", "Completed", "Emergency consultation");
        consultations[14] = new Consultation("CON015", "15", "DOC009", "27-07-2025", "Completed", "Oncology consultation");
        consultations[15] = new Consultation("CON016", "16", "DOC010", "28-07-2025", "Completed", "Radiological assessment");
        consultations[16] = new Consultation("CON017", "17", "DOC011", "29-07-2025", "Completed", "Gynecological examination");
        consultations[17] = new Consultation("CON018", "18", "DOC012", "30-07-2025", "Completed", "Urological consultation");
        consultations[18] = new Consultation("CON019", "19", "DOC001", "31-07-2025", "Completed", "Cardiac examination");
        consultations[19] = new Consultation("CON020", "20", "DOC006", "01-08-2025", "Completed", "Psychiatric evaluation");
        
        return consultations;
    }

    public static Treatment[] initializeSampleTreatments() {
        Treatment[] treatments = new Treatment[15];
        
        treatments[0] = new Treatment("TRE001", "1", "DOC001", "Hypertension", "MED007", "10-07-2025");
        treatments[1] = new Treatment("TRE002", "3", "DOC003", "Migraine", "MED003", "15-07-2025");
        treatments[2] = new Treatment("TRE003", "2", "DOC005", "Diabetes Type 2", "MED006", "16-07-2025");
        treatments[3] = new Treatment("TRE004", "6", "DOC006", "Depression", "MED009", "18-07-2025");
        treatments[4] = new Treatment("TRE005", "4", "DOC005", "Hypothyroidism", "MED004", "19-07-2025");
        treatments[5] = new Treatment("TRE006", "8", "DOC002", "Childhood Asthma", "MED008", "20-07-2025");
        treatments[6] = new Treatment("TRE007", "9", "DOC006", "Anxiety Disorder", "MED009", "21-07-2025");
        treatments[7] = new Treatment("TRE008", "11", "DOC001", "High Cholesterol", "MED010", "23-07-2025");
        treatments[8] = new Treatment("TRE009", "12", "DOC007", "Eczema", "MED005", "24-07-2025");
        treatments[9] = new Treatment("TRE010", "13", "DOC008", "Acute Bronchitis", "MED002", "25-07-2025");
        treatments[10] = new Treatment("TRE011", "14", "DOC009", "Cancer Screening", "MED001", "26-07-2025");
        treatments[11] = new Treatment("TRE012", "15", "DOC010", "Fracture Assessment", "MED003", "27-07-2025");
        treatments[12] = new Treatment("TRE013", "16", "DOC011", "Pregnancy Care", "MED004", "28-07-2025");
        treatments[13] = new Treatment("TRE014", "17", "DOC012", "Urinary Tract Infection", "MED002", "29-07-2025");
        treatments[14] = new Treatment("TRE015", "18", "DOC001", "Heart Disease Management", "MED007", "30-07-2025");
        
        return treatments;
    }

    public static PharmacyTransaction[] initializeSampleTransactions() {
        PharmacyTransaction[] transactions = new PharmacyTransaction[25];
        
        transactions[0] = new PharmacyTransaction("TXN001", "1", "MED007", 2, "10-07-2025");
        transactions[1] = new PharmacyTransaction("TXN002", "3", "MED003", 1, "15-07-2025");
        transactions[2] = new PharmacyTransaction("TXN003", "2", "MED006", 3, "16-07-2025");
        transactions[3] = new PharmacyTransaction("TXN004", "6", "MED009", 1, "18-07-2025");
        transactions[4] = new PharmacyTransaction("TXN005", "4", "MED004", 2, "19-07-2025");
        transactions[5] = new PharmacyTransaction("TXN006", "8", "MED008", 1, "20-07-2025");
        transactions[6] = new PharmacyTransaction("TXN007", "9", "MED009", 2, "21-07-2025");
        transactions[7] = new PharmacyTransaction("TXN008", "11", "MED010", 1, "23-07-2025");
        transactions[8] = new PharmacyTransaction("TXN009", "12", "MED003", 1, "24-07-2025");
        transactions[9] = new PharmacyTransaction("TXN010", "5", "MED007", 1, "25-07-2025");
        transactions[10] = new PharmacyTransaction("TXN011", "7", "MED001", 2, "26-07-2025");
        transactions[11] = new PharmacyTransaction("TXN012", "10", "MED005", 1, "27-07-2025");
        transactions[12] = new PharmacyTransaction("TXN013", "13", "MED002", 1, "28-07-2025");
        transactions[13] = new PharmacyTransaction("TXN014", "14", "MED001", 1, "29-07-2025");
        transactions[14] = new PharmacyTransaction("TXN015", "15", "MED003", 2, "30-07-2025");
        transactions[15] = new PharmacyTransaction("TXN016", "16", "MED013", 1, "31-07-2025");
        transactions[16] = new PharmacyTransaction("TXN017", "17", "MED014", 2, "01-08-2025");
        transactions[17] = new PharmacyTransaction("TXN018", "18", "MED015", 1, "02-08-2025");
        transactions[18] = new PharmacyTransaction("TXN019", "19", "MED016", 1, "03-08-2025");
        transactions[19] = new PharmacyTransaction("TXN020", "20", "MED017", 1, "04-08-2025");
        transactions[20] = new PharmacyTransaction("TXN021", "21", "MED018", 2, "05-08-2025");
        transactions[21] = new PharmacyTransaction("TXN022", "22", "MED019", 1, "06-08-2025");
        transactions[22] = new PharmacyTransaction("TXN023", "23", "MED020", 1, "07-08-2025");
        transactions[23] = new PharmacyTransaction("TXN024", "24", "MED011", 2, "08-08-2025");
        transactions[24] = new PharmacyTransaction("TXN025", "25", "MED012", 1, "09-08-2025");
        
        return transactions;
    }

    public static Prescription[] initializeSamplePrescriptions() {
        Prescription[] prescriptions = new Prescription[20];

        SetAndQueueInterface<PrescribedMedicine> pm1 = new SetAndQueue<>();
        pm1.add(new PrescribedMedicine("PM001", "PRE001", "MED007", "Amlodipine", 30, "1 tablet daily", "Take in the morning", 32.80, 984.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm2 = new SetAndQueue<>();
        pm2.add(new PrescribedMedicine("PM002", "PRE002", "MED003", "Ibuprofen", 20, "1 tablet every 6 hours", "Take with food", 12.90, 258.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm3 = new SetAndQueue<>();
        pm3.add(new PrescribedMedicine("PM003", "PRE003", "MED006", "Metformin", 60, "1 tablet twice daily", "Take with meals", 22.40, 1344.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm4 = new SetAndQueue<>();
        pm4.add(new PrescribedMedicine("PM004", "PRE004", "MED009", "Sertraline", 30, "1 tablet daily", "Take in the morning", 45.60, 1368.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm5 = new SetAndQueue<>();
        pm5.add(new PrescribedMedicine("PM005", "PRE005", "MED004", "Omeprazole", 30, "1 tablet daily", "Take before breakfast", 25.60, 768.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm6 = new SetAndQueue<>();
        pm6.add(new PrescribedMedicine("PM006", "PRE006", "MED008", "Salbutamol", 1, "2 puffs as needed", "Use during asthma attacks", 28.90, 28.90, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm7 = new SetAndQueue<>();
        pm7.add(new PrescribedMedicine("PM007", "PRE007", "MED009", "Sertraline", 60, "1 tablet daily", "Take in the morning", 45.60, 2736.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm8 = new SetAndQueue<>();
        pm8.add(new PrescribedMedicine("PM008", "PRE008", "MED010", "Atorvastatin", 30, "1 tablet daily", "Take in the evening", 58.90, 1767.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm9 = new SetAndQueue<>();
        pm9.add(new PrescribedMedicine("PM009", "PRE009", "MED013", "Loratadine", 30, "1 tablet daily", "Take as needed for allergies", 16.80, 504.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm10 = new SetAndQueue<>();
        pm10.add(new PrescribedMedicine("PM010", "PRE010", "MED014", "Lisinopril", 30, "1 tablet daily", "Take in the morning", 28.40, 852.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm11 = new SetAndQueue<>();
        pm11.add(new PrescribedMedicine("PM011", "PRE011", "MED015", "Fluoxetine", 30, "1 tablet daily", "Take in the morning", 42.30, 1269.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm12 = new SetAndQueue<>();
        pm12.add(new PrescribedMedicine("PM012", "PRE012", "MED016", "Simvastatin", 30, "1 tablet daily", "Take in the evening", 35.70, 1071.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm13 = new SetAndQueue<>();
        pm13.add(new PrescribedMedicine("PM013", "PRE013", "MED017", "Montelukast", 30, "1 tablet daily", "Take in the evening", 38.90, 1167.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm14 = new SetAndQueue<>();
        pm14.add(new PrescribedMedicine("PM014", "PRE014", "MED018", "Pantoprazole", 30, "1 tablet daily", "Take before breakfast", 31.20, 936.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm15 = new SetAndQueue<>();
        pm15.add(new PrescribedMedicine("PM015", "PRE015", "MED019", "Duloxetine", 30, "1 tablet daily", "Take in the morning", 67.80, 2034.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm16 = new SetAndQueue<>();
        pm16.add(new PrescribedMedicine("PM016", "PRE016", "MED020", "Losartan", 30, "1 tablet daily", "Take in the morning", 29.60, 888.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm17 = new SetAndQueue<>();
        pm17.add(new PrescribedMedicine("PM017", "PRE017", "MED001", "Paracetamol", 20, "1-2 tablets every 4-6 hours", "Take as needed for pain", 8.50, 170.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm18 = new SetAndQueue<>();
        pm18.add(new PrescribedMedicine("PM018", "PRE018", "MED002", "Amoxicillin", 21, "1 capsule three times daily", "Take with food", 15.80, 331.80, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm19 = new SetAndQueue<>();
        pm19.add(new PrescribedMedicine("PM019", "PRE019", "MED005", "Cetirizine", 30, "1 tablet daily", "Take in the evening", 18.20, 546.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm20 = new SetAndQueue<>();
        pm20.add(new PrescribedMedicine("PM020", "PRE020", "MED011", "Aspirin", 30, "1 tablet daily", "Take with food", 6.50, 195.00, false));
        
        prescriptions[0] = new Prescription("PRE001", "CON001", "1", "DOC001", "Hypertension", pm1, "10-07-2025", "active", 984.00, true);
        prescriptions[1] = new Prescription("PRE002", "CON003", "3", "DOC003", "Migraine", pm2, "15-07-2025", "active", 258.00, true);
        prescriptions[2] = new Prescription("PRE003", "CON004", "2", "DOC005", "Diabetes Type 2", pm3, "16-07-2025", "active", 1344.00, true);
        prescriptions[3] = new Prescription("PRE004", "CON006", "6", "DOC006", "Depression", pm4, "18-07-2025", "active", 1368.00, true);
        prescriptions[4] = new Prescription("PRE005", "CON010", "4", "DOC005", "Hypothyroidism", pm5, "22-07-2025", "active", 768.00, true);
        prescriptions[5] = new Prescription("PRE006", "CON008", "8", "DOC002", "Childhood Asthma", pm6, "20-07-2025", "active", 28.90, true);
        prescriptions[6] = new Prescription("PRE007", "CON009", "9", "DOC006", "Anxiety Disorder", pm7, "21-07-2025", "active", 2736.00, false);
        prescriptions[7] = new Prescription("PRE008", "CON011", "11", "DOC001", "High Cholesterol", pm8, "23-07-2025", "active", 1767.00, true);
        prescriptions[8] = new Prescription("PRE009", "CON012", "12", "DOC007", "Allergic Rhinitis", pm9, "24-07-2025", "active", 504.00, true);
        prescriptions[9] = new Prescription("PRE010", "CON013", "13", "DOC008", "Hypertension", pm10, "25-07-2025", "active", 852.00, false);
        prescriptions[10] = new Prescription("PRE011", "CON014", "14", "DOC009", "Depression", pm11, "26-07-2025", "active", 1269.00, true);
        prescriptions[11] = new Prescription("PRE012", "CON015", "15", "DOC010", "High Cholesterol", pm12, "27-07-2025", "active", 1071.00, true);
        prescriptions[12] = new Prescription("PRE013", "CON016", "16", "DOC011", "Asthma Prevention", pm13, "28-07-2025", "active", 1167.00, false);
        prescriptions[13] = new Prescription("PRE014", "CON017", "17", "DOC012", "Gastroesophageal Reflux", pm14, "29-07-2025", "active", 936.00, true);
        prescriptions[14] = new Prescription("PRE015", "CON018", "18", "DOC001", "Depression", pm15, "30-07-2025", "active", 2034.00, false);
        prescriptions[15] = new Prescription("PRE016", "CON019", "19", "DOC002", "Hypertension", pm16, "31-07-2025", "active", 888.00, true);
        prescriptions[16] = new Prescription("PRE017", "CON020", "20", "DOC006", "Pain Management", pm17, "01-08-2025", "active", 170.00, true);
        prescriptions[17] = new Prescription("PRE018", "CON002", "2", "DOC002", "Bacterial Infection", pm18, "12-07-2025", "completed", 331.80, true);
        prescriptions[18] = new Prescription("PRE019", "CON005", "5", "DOC001", "Allergic Reaction", pm19, "17-07-2025", "completed", 546.00, true);
        prescriptions[19] = new Prescription("PRE020", "CON007", "7", "DOC007", "Cardiovascular Prevention", pm20, "19-07-2025", "completed", 195.00, true);
        
        return prescriptions;
    }
} 