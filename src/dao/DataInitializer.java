package dao;

import entity.Medicine;
import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import entity.Treatment;
import entity.PharmacyTransaction;
import entity.Prescription;
import entity.PrescribedMedicine;
import adt.SetQueueArray;
import adt.SetAndQueueInterface;

public class DataInitializer {
    public static Medicine[] initializeSampleMedicines() {
        Medicine[] medicines = new Medicine[40];

        medicines[0] = new Medicine("MED001", "Paracetamol", "Panadol", 50, "2025-12-31", 8.50, "Pain Relief", "Paracetamol", "Analgesic");
        medicines[1] = new Medicine("MED002", "Amoxicillin", "Amoxil", 30, "2025-09-30", 15.80, "Antibiotic", "Amoxicillin", "Antibiotic");
        medicines[2] = new Medicine("MED003", "Ibuprofen", "Advil", 25, "2025-10-15", 12.90, "Pain Relief", "Ibuprofen", "NSAID");
        medicines[3] = new Medicine("MED004", "Omeprazole", "Losec", 15, "2025-11-20", 25.60, "Acid Reflux", "Omeprazole", "Proton Pump Inhibitor");
        medicines[4] = new Medicine("MED005", "Cetirizine", "Zyrtec", 5, "2025-12-10", 18.20, "Allergy Relief", "Cetirizine", "Antihistamine");
        medicines[5] = new Medicine("MED006", "Metformin", "Glucophage", 40, "2026-01-28", 22.40, "Diabetes Management", "Metformin", "Biguanide");
        medicines[6] = new Medicine("MED007", "Amlodipine", "Norvasc", 35, "2026-02-15", 32.80, "Hypertension", "Amlodipine", "Calcium Channel Blocker");
        medicines[7] = new Medicine("MED008", "Salbutamol", "Ventolin", 45, "2026-03-10", 28.90, "Asthma Relief", "Salbutamol", "Bronchodilator");
        medicines[8] = new Medicine("MED009", "Sertraline", "Zoloft", 20, "2026-04-05", 45.60, "Depression Treatment", "Sertraline", "SSRI");
        medicines[9] = new Medicine("MED010", "Atorvastatin", "Lipitor", 30, "2026-05-20", 58.90, "Cholesterol Management", "Atorvastatin", "Statin");
        medicines[10] = new Medicine("MED011", "Aspirin", "Bayer", 8, "2026-06-15", 6.50, "Pain Relief", "Acetylsalicylic Acid", "NSAID");
        medicines[11] = new Medicine("MED012", "Insulin", "Humalog", 12, "2026-07-30", 120.00, "Diabetes Management", "Insulin Lispro", "Insulin");
        medicines[12] = new Medicine("MED013", "Loratadine", "Claritin", 18, "2026-08-25", 16.80, "Allergy Relief", "Loratadine", "Antihistamine");
        medicines[13] = new Medicine("MED014", "Lisinopril", "Zestril", 22, "2026-09-10", 28.40, "Hypertension", "Lisinopril", "ACE Inhibitor");
        medicines[14] = new Medicine("MED015", "Fluoxetine", "Prozac", 15, "2026-10-05", 42.30, "Depression Treatment", "Fluoxetine", "SSRI");
        medicines[15] = new Medicine("MED016", "Simvastatin", "Zocor", 25, "2026-11-20", 35.70, "Cholesterol Management", "Simvastatin", "Statin");
        medicines[16] = new Medicine("MED017", "Montelukast", "Singulair", 12, "2026-12-15", 38.90, "Asthma Prevention", "Montelukast", "Leukotriene Receptor Antagonist");
        medicines[17] = new Medicine("MED018", "Pantoprazole", "Protonix", 20, "2027-01-10", 31.20, "Acid Reflux", "Pantoprazole", "Proton Pump Inhibitor");
        medicines[18] = new Medicine("MED019", "Duloxetine", "Cymbalta", 10, "2027-02-05", 67.80, "Depression Treatment", "Duloxetine", "SNRI");
        medicines[19] = new Medicine("MED020", "Losartan", "Cozaar", 28, "2027-03-20", 29.60, "Hypertension", "Losartan", "Angiotensin Receptor Blocker");
        medicines[20] = new Medicine("MED021", "Paracetamol", "Panadol Active", 5, "2025-12-31", 8.50, "Pain Relief", "Paracetamol", "Analgesic");
        medicines[21] = new Medicine("MED022", "Cephalexin", "Keflex", 35, "2026-04-15", 18.90, "Antibiotic", "Cephalexin", "Antibiotic");
        medicines[22] = new Medicine("MED023", "Diclofenac", "Voltaren", 42, "2026-05-20", 14.70, "Pain Relief", "Diclofenac", "NSAID");
        medicines[23] = new Medicine("MED024", "Ranitidine", "Zantac", 38, "2026-06-25", 19.80, "Acid Reflux", "Ranitidine", "H2 Receptor Antagonist");
        medicines[24] = new Medicine("MED025", "Fexofenadine", "Allegra", 28, "2026-07-30", 21.50, "Allergy Relief", "Fexofenadine", "Antihistamine");
        medicines[25] = new Medicine("MED026", "Glipizide", "Glucotrol", 33, "2026-08-10", 26.80, "Diabetes Management", "Glipizide", "Sulfonylurea");
        medicines[26] = new Medicine("MED027", "Nifedipine", "Adalat", 29, "2026-09-15", 35.40, "Hypertension", "Nifedipine", "Calcium Channel Blocker");
        medicines[27] = new Medicine("MED028", "Budesonide", "Pulmicort", 24, "2026-10-20", 52.30, "Asthma Prevention", "Budesonide", "Corticosteroid");
        medicines[28] = new Medicine("MED029", "Escitalopram", "Lexapro", 18, "2026-11-25", 48.90, "Depression Treatment", "Escitalopram", "SSRI");
        medicines[29] = new Medicine("MED030", "Rosuvastatin", "Crestor", 31, "2026-12-30", 62.70, "Cholesterol Management", "Rosuvastatin", "Statin");
        medicines[30] = new Medicine("MED031", "Naproxen", "Aleve", 26, "2027-01-15", 11.40, "Pain Relief", "Naproxen", "NSAID");
        medicines[31] = new Medicine("MED032", "Metoprolol", "Lopressor", 37, "2027-02-20", 24.60, "Hypertension", "Metoprolol", "Beta Blocker");
        medicines[32] = new Medicine("MED033", "Prednisone", "Deltasone", 22, "2027-03-25", 15.80, "Anti-inflammatory", "Prednisone", "Corticosteroid");
        medicines[33] = new Medicine("MED034", "Tramadol", "Ultram", 19, "2027-04-30", 28.70, "Pain Relief", "Tramadol", "Opioid Analgesic");
        medicines[34] = new Medicine("MED035", "Warfarin", "Coumadin", 16, "2027-05-15", 12.30, "Blood Thinner", "Warfarin", "Anticoagulant");
        medicines[35] = new Medicine("MED036", "Levothyroxine", "Synthroid", 41, "2027-06-20", 18.90, "Thyroid Hormone", "Levothyroxine", "Hormone");
        medicines[36] = new Medicine("MED037", "Hydrochlorothiazide", "Microzide", 34, "2027-07-25", 16.50, "Diuretic", "Hydrochlorothiazide", "Thiazide Diuretic");
        medicines[37] = new Medicine("MED038", "Alprazolam", "Xanax", 13, "2027-08-30", 22.80, "Anxiety Treatment", "Alprazolam", "Benzodiazepine");
        medicines[38] = new Medicine("MED039", "Gabapentin", "Neurontin", 27, "2027-09-15", 31.40, "Nerve Pain", "Gabapentin", "Anticonvulsant");
        medicines[39] = new Medicine("MED040", "Clonazepam", "Klonopin", 15, "2027-10-20", 19.60, "Seizure Control", "Clonazepam", "Benzodiazepine");

        return medicines;
    }
    
    public static Patient[] initializeSamplePatients() {
        Patient[] patients = new Patient[40];

        patients[0] = new Patient(1, "Ahmad bin Abdullah", 3, "Male", "Penicillin", "0123456789", "123 Jalan Tunku Abdul Rahman, Kuala Lumpur", "01-07-2025", "Fever", "Active");
        patients[1] = new Patient(2, "Siti binti Mohamed", 7, "Female", "Paracetamol", "0123456790", "456 Jalan Sultan Ismail, Petaling Jaya", "02-07-2025", "Common Cold", "Active");
        patients[2] = new Patient(3, "Raj a/l Kumar", 12, "Male", "Sulfa", "0123456791", "789 Jalan Bukit Bintang, Kuala Lumpur", "03-07-2025", "Asthma", "Active");
        patients[3] = new Patient(4, "Lim Siew Mei", 15, "Female", "None", "0123456792", "321 Jalan Ampang, Kuala Lumpur", "04-07-2025", "None", "Active");
        patients[4] = new Patient(5, "Tan Ah Kow", 17, "Male", "Codeine", "0123456793", "654 Jalan Pudu, Kuala Lumpur", "05-07-2025", "Sports Injury", "Active");
        patients[5] = new Patient(6, "Nurul Huda binti Ismail", 22, "Female", "Latex", "0123456794", "987 Jalan Cheras, Kuala Lumpur", "06-07-2025", "Migraine", "Active");
        patients[6] = new Patient(7, "Krishnan a/l Muthu", 25, "Male", "None", "0123456795", "147 Jalan Klang Lama, Kuala Lumpur", "07-07-2025", "Depression", "Active");
        patients[7] = new Patient(8, "Wong Mei Ling", 28, "Female", "Iodine", "0123456796", "258 Jalan Ipoh, Kuala Lumpur", "08-07-2025", "Thyroid Disorder", "Active");
        patients[8] = new Patient(9, "Mohamed Ali bin Hassan", 31, "Male", "None", "0123456797", "369 Jalan Gombak, Kuala Lumpur", "09-07-2025", "Anxiety", "Active");
        patients[9] = new Patient(10, "Cheah Siew Fong", 35, "Female", "Shellfish", "0123456798", "741 Jalan Damansara, Petaling Jaya", "10-07-2025", "None", "Active");
        patients[10] = new Patient(11, "Arun a/l Subramaniam", 38, "Male", "None", "0123456799", "852 Jalan Bangsar, Kuala Lumpur", "11-07-2025", "High Cholesterol", "Active");
        patients[11] = new Patient(12, "Fatimah binti Omar", 42, "Female", "Peanuts", "0123456800", "963 Jalan TAR, Kuala Lumpur", "12-07-2025", "None", "Active");
        patients[12] = new Patient(13, "Lee Chong Wei", 45, "Male", "None", "0123456801", "159 Jalan Imbi, Kuala Lumpur", "13-07-2025", "Sleep Apnea", "Active");
        patients[13] = new Patient(14, "Aisha binti Yusof", 48, "Female", "None", "0123456802", "357 Jalan Raja Chulan, Kuala Lumpur", "14-07-2025", "None", "Active");
        patients[14] = new Patient(15, "Gan Eng Seng", 52, "Male", "Aspirin", "0123456803", "486 Jalan Tuanku Abdul Rahman, Kuala Lumpur", "15-07-2025", "Gout", "Active");
        patients[15] = new Patient(16, "Zainab binti Ahmad", 55, "Female", "None", "0123456804", "753 Jalan Raja Laut, Kuala Lumpur", "16-07-2025", "Hypertension", "Active");
        patients[16] = new Patient(17, "Kumar a/l Rajendran", 58, "Male", "Sulfa", "0123456805", "951 Jalan Sultan, Kuala Lumpur", "17-07-2025", "Diabetes", "Active");
        patients[17] = new Patient(18, "Chan Mei Lin", 62, "Female", "Latex", "0123456806", "357 Jalan Pahang, Kuala Lumpur", "18-07-2025", "Asthma", "Active");
        patients[18] = new Patient(19, "Ismail bin Omar", 65, "Male", "None", "0123456807", "159 Jalan Masjid India, Kuala Lumpur", "19-07-2025", "Heart Disease", "Active");
        patients[19] = new Patient(20, "Priya a/p Ramasamy", 68, "Female", "Penicillin", "0123456808", "753 Jalan Petaling, Kuala Lumpur", "20-07-2025", "Migraine", "Active");
        patients[20] = new Patient(21, "Ong Teck Seng", 72, "Male", "None", "0123456809", "951 Jalan Chow Kit, Kuala Lumpur", "21-07-2025", "Depression", "Active");
        patients[21] = new Patient(22, "Noraini binti Zainal", 75, "Female", "Iodine", "0123456810", "357 Jalan Tun Perak, Kuala Lumpur", "22-07-2025", "Thyroid Disorder", "Active");
        patients[22] = new Patient(23, "Muthu a/l Velu", 78, "Male", "None", "0123456811", "159 Jalan Dang Wangi, Kuala Lumpur", "23-07-2025", "Anxiety", "Active");
        patients[23] = new Patient(24, "Lau Siew Mei", 82, "Female", "Shellfish", "0123456812", "753 Jalan Tun Razak, Kuala Lumpur", "24-07-2025", "None", "Active");
        patients[24] = new Patient(25, "Hassan bin Ali", 85, "Male", "None", "0123456813", "951 Jalan Ampang, Kuala Lumpur", "25-07-2025", "High Cholesterol", "Active");
        patients[25] = new Patient(26, "Aminah binti Rashid", 88, "Female", "Aspirin", "0123456814", "123 Jalan Sentul, Kuala Lumpur", "26-07-2025", "Arthritis", "Active");
        patients[26] = new Patient(27, "Ravi a/l Shankar", 92, "Male", "None", "0123456815", "456 Jalan Kepong, Kuala Lumpur", "27-07-2025", "Hypertension", "Active");
        patients[27] = new Patient(28, "Lily Tan Mei Hua", 95, "Female", "Shellfish", "0123456816", "789 Jalan Setapak, Kuala Lumpur", "28-07-2025", "Allergic Rhinitis", "Active");
        patients[28] = new Patient(29, "Azman bin Yusof", 19, "Male", "Penicillin", "0123456817", "321 Jalan Wangsa Maju, Kuala Lumpur", "29-07-2025", "Diabetes", "Active");
        patients[29] = new Patient(30, "Grace Lim Soo Cheng", 23, "Female", "None", "0123456818", "654 Jalan Segambut, Kuala Lumpur", "30-07-2025", "Migraine", "Active");
        patients[30] = new Patient(31, "Suresh a/l Krishnan", 26, "Male", "Iodine", "0123456819", "987 Jalan Batu Caves, Selangor", "31-07-2025", "Thyroid Disorder", "Active");
        patients[31] = new Patient(32, "Farah binti Kamal", 29, "Female", "Latex", "0123456820", "147 Jalan Rawang, Selangor", "01-08-2025", "Asthma", "Active");
        patients[32] = new Patient(33, "Danny Ng Wei Ming", 33, "Male", "None", "0123456821", "258 Jalan Kajang, Selangor", "02-08-2025", "Sleep Apnea", "Active");
        patients[33] = new Patient(34, "Khadijah binti Hassan", 36, "Female", "Sulfa", "0123456822", "369 Jalan Selayang, Selangor", "03-08-2025", "Depression", "Active");
        patients[34] = new Patient(35, "Vincent Loh Chee Keong", 39, "Male", "None", "0123456823", "741 Jalan Subang, Selangor", "04-08-2025", "High Cholesterol", "Active");
        patients[35] = new Patient(36, "Rohani binti Ibrahim", 43, "Female", "Peanuts", "0123456824", "852 Jalan Shah Alam, Selangor", "05-08-2025", "Heart Disease", "Active");
        patients[36] = new Patient(37, "Prakash a/l Devi", 47, "Male", "None", "0123456825", "963 Jalan Klang, Selangor", "06-08-2025", "Anxiety", "Active");
        patients[37] = new Patient(38, "Michelle Wong Ai Ling", 51, "Female", "Codeine", "0123456826", "159 Jalan Puchong, Selangor", "07-08-2025", "Chronic Pain", "Active");
        patients[38] = new Patient(39, "Hafiz bin Rahman", 54, "Male", "None", "0123456827", "357 Jalan Cyberjaya, Selangor", "08-08-2025", "Diabetes", "Active");
        patients[39] = new Patient(40, "Stephanie Tan Li Ying", 57, "Female", "Shellfish", "0123456828", "753 Jalan Putrajaya, Putrajaya", "09-08-2025", "Allergic Dermatitis", "Active");

        return patients;
    }

    public static Doctor[] initializeSampleDoctors() {
        Doctor[] doctors = new Doctor[20];

        doctors[0] = new Doctor("DOC001", "Dr. Sarah Chen Mei Ling", "Cardiology", "0123456780", "sarah.chen@chubbyclinic.com.my", true, "Mon-Wed 9AM-5PM", false, "", "");
        doctors[1] = new Doctor("DOC002", "Dr. Robert Kim Ah Kow", "Pediatrics", "0123456781", "robert.kim@chubbyclinic.com.my", true, "Tue-Thu 8AM-4PM", false, "", "");
        doctors[2] = new Doctor("DOC003", "Dr. Lisa Wong Siew Mei", "Neurology", "0123456782", "lisa.wong@chubbyclinic.com.my", true, "Wed-Fri 10AM-6PM", false, "", "");
        doctors[3] = new Doctor("DOC004", "Dr. James Lee Chong Wei", "Orthopedics", "0123456783", "james.lee@chubbyclinic.com.my", false, "Mon-Fri 9AM-5PM", true, "15-07-2025", "20-07-2025");
        doctors[4] = new Doctor("DOC005", "Dr. Maria Garcia binti Abdullah", "Endocrinology", "0123456784", "maria.garcia@chubbyclinic.com.my", true, "Mon-Fri 8AM-4PM", false, "", "");
        doctors[5] = new Doctor("DOC006", "Dr. David Wilson a/l Kumar", "Psychiatry", "0123456785", "david.wilson@chubbyclinic.com.my", true, "Tue-Sat 9AM-5PM", false, "", "");
        doctors[6] = new Doctor("DOC007", "Dr. Jennifer Brown Mei Fong", "Dermatology", "0123456786", "jennifer.brown@chubbyclinic.com.my", true, "Mon-Thu 10AM-6PM", false, "", "");
        doctors[7] = new Doctor("DOC008", "Dr. Michael Taylor bin Mohamed", "Emergency Medicine", "0123456787", "michael.taylor@chubbyclinic.com.my", true, "24/7 Shifts", false, "", "");
        doctors[8] = new Doctor("DOC009", "Dr. Amanda Lim Siew Lin", "Oncology", "0123456788", "amanda.lim@chubbyclinic.com.my", true, "Mon-Fri 9AM-5PM", false, "", "");
        doctors[9] = new Doctor("DOC010", "Dr. Christopher Tan Ah Beng", "Radiology", "0123456789", "christopher.tan@chubbyclinic.com.my", true, "Mon-Fri 8AM-4PM", false, "", "");
        doctors[10] = new Doctor("DOC011", "Dr. Emily Wong Mei Ling", "Obstetrics & Gynecology", "0123456790", "emily.wong@chubbyclinic.com.my", true, "Tue-Sat 9AM-5PM", false, "", "");
        doctors[11] = new Doctor("DOC012", "Dr. Benjamin Raj a/l Kumar", "Urology", "0123456791", "benjamin.raj@chubbyclinic.com.my", true, "Mon-Thu 10AM-6PM", false, "", "");
        doctors[12] = new Doctor("DOC013", "Dr. Rachel Lim Hui Ying", "Internal Medicine", "0123456792", "rachel.lim@chubbyclinic.com.my", true, "Mon-Fri 9AM-5PM", false, "", "");
        doctors[13] = new Doctor("DOC014", "Dr. Kevin Tan Wei Jie", "Gastroenterology", "0123456793", "kevin.tan@chubbyclinic.com.my", true, "Tue-Sat 8AM-4PM", false, "", "");
        doctors[14] = new Doctor("DOC015", "Dr. Priya Sharma a/p Ravi", "Rheumatology", "0123456794", "priya.sharma@chubbyclinic.com.my", true, "Wed-Fri 10AM-6PM", false, "", "");
        doctors[15] = new Doctor("DOC016", "Dr. Ahmad Farid bin Hassan", "Pulmonology", "0123456795", "ahmad.farid@chubbyclinic.com.my", false, "Mon-Thu 9AM-5PM", true, "10-08-2025", "15-08-2025");
        doctors[16] = new Doctor("DOC017", "Dr. Catherine Ng Siew Lan", "Nephrology", "0123456796", "catherine.ng@chubbyclinic.com.my", true, "Mon-Fri 8AM-4PM", false, "", "");
        doctors[17] = new Doctor("DOC018", "Dr. Rajesh Kumar a/l Devi", "Hematology", "0123456797", "rajesh.kumar@chubbyclinic.com.my", true, "Tue-Thu 9AM-5PM", false, "", "");
        doctors[18] = new Doctor("DOC019", "Dr. Melissa Chan Ai Ling", "Infectious Disease", "0123456798", "melissa.chan@chubbyclinic.com.my", true, "Wed-Sat 10AM-6PM", false, "", "");
        doctors[19] = new Doctor("DOC020", "Dr. Hafiz Ismail bin Omar", "Anesthesiology", "0123456799", "hafiz.ismail@chubbyclinic.com.my", true, "24/7 On-Call", false, "", "");

        return doctors;
    }

    public static Consultation[] initializeSampleConsultations() {
        Consultation[] consultations = new Consultation[220];

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
        consultations[20] = new Consultation("CON021", "21", "DOC013", "02-08-2025", "Completed", "Internal medicine consultation");
        consultations[21] = new Consultation("CON022", "22", "DOC014", "03-08-2025", "Completed", "Gastroenterology examination");
        consultations[22] = new Consultation("CON023", "23", "DOC015", "04-08-2025", "Completed", "Rheumatology assessment");
        consultations[23] = new Consultation("CON024", "24", "DOC017", "05-08-2025", "Completed", "Nephrology consultation");
        consultations[24] = new Consultation("CON025", "25", "DOC018", "06-08-2025", "Completed", "Hematology examination");
        consultations[25] = new Consultation("CON026", "26", "DOC019", "07-08-2025", "Completed", "Infectious disease consultation");
        consultations[26] = new Consultation("CON027", "27", "DOC001", "08-08-2025", "Completed", "Follow-up cardiac assessment");
        consultations[27] = new Consultation("CON028", "28", "DOC007", "09-08-2025", "Completed", "Dermatology follow-up");
        consultations[28] = new Consultation("CON029", "29", "DOC005", "10-08-2025", "Completed", "Endocrinology consultation");
        consultations[29] = new Consultation("CON030", "30", "DOC013", "11-08-2025", "Completed", "General health checkup");
        consultations[30] = new Consultation("CON031", "31", "DOC006", "12-08-2025", "Completed", "Mental health follow-up");
        consultations[31] = new Consultation("CON032", "32", "DOC002", "13-08-2025", "Completed", "Pediatric vaccination");
        consultations[32] = new Consultation("CON033", "33", "DOC008", "14-08-2025", "Completed", "Emergency treatment");
        consultations[33] = new Consultation("CON034", "34", "DOC014", "15-08-2025", "Completed", "Digestive system examination");
        consultations[34] = new Consultation("CON035", "35", "DOC015", "16-08-2025", "Completed", "Joint pain assessment");
        
        // Additional consultations to reach 200+
        consultations[35] = new Consultation("CON036", "36", "DOC001", "17-08-2025", "Completed", "Cardiovascular follow-up examination");
        consultations[36] = new Consultation("CON037", "37", "DOC002", "18-08-2025", "Completed", "Pediatric growth assessment");
        consultations[37] = new Consultation("CON038", "38", "DOC003", "19-08-2025", "Completed", "Neurological screening for headaches");
        consultations[38] = new Consultation("CON039", "39", "DOC004", "20-08-2025", "Completed", "Orthopedic consultation for back pain");
        consultations[39] = new Consultation("CON040", "40", "DOC005", "21-08-2025", "Completed", "Endocrine system evaluation");
        consultations[40] = new Consultation("CON041", "1", "DOC006", "22-08-2025", "Completed", "Mental health follow-up session");
        consultations[41] = new Consultation("CON042", "1", "DOC007", "23-08-2025", "Completed", "Dermatological skin check");
        consultations[42] = new Consultation("CON043", "1", "DOC008", "24-08-2025", "Completed", "Emergency medicine consultation");
        consultations[43] = new Consultation("CON044", "1", "DOC009", "25-08-2025", "Completed", "Oncology screening appointment");
        consultations[44] = new Consultation("CON045", "2", "DOC010", "26-08-2025", "Completed", "Radiological examination review");
        consultations[45] = new Consultation("CON046", "2", "DOC011", "27-08-2025", "Completed", "Gynecological wellness check");
        consultations[46] = new Consultation("CON047", "2", "DOC012", "28-08-2025", "Completed", "Urological assessment");
        consultations[47] = new Consultation("CON048", "2", "DOC013", "29-08-2025", "Completed", "Internal medicine consultation");
        consultations[48] = new Consultation("CON049", "3", "DOC014", "30-08-2025", "Completed", "Gastroenterological examination");
        consultations[49] = new Consultation("CON050", "3", "DOC015", "31-08-2025", "Completed", "Rheumatological joint assessment");
        consultations[50] = new Consultation("CON051", "3", "DOC016", "01-09-2025", "Completed", "Pulmonology breathing test");
        consultations[51] = new Consultation("CON052", "3", "DOC017", "02-09-2025", "Completed", "Nephrology kidney function test");
        consultations[52] = new Consultation("CON053", "4", "DOC018", "03-09-2025", "Completed", "Hematology blood work review");
        consultations[53] = new Consultation("CON054", "4", "DOC019", "04-09-2025", "Completed", "Infectious disease consultation");
        consultations[54] = new Consultation("CON055", "4", "DOC020", "05-09-2025", "Completed", "Anesthesiology pre-operative assessment");
        consultations[55] = new Consultation("CON056", "4", "DOC001", "06-09-2025", "Completed", "Cardiac stress test evaluation");
        consultations[56] = new Consultation("CON057", "3", "DOC002", "07-09-2025", "Completed", "Pediatric immunization consultation");
        consultations[57] = new Consultation("CON058", "8", "DOC003", "08-09-2025", "Completed", "Neurological cognitive assessment");
        consultations[58] = new Consultation("CON059", "15", "DOC005", "09-09-2025", "Completed", "Diabetes management review");
        consultations[59] = new Consultation("CON060", "20", "DOC006", "10-09-2025", "Completed", "Psychiatric medication review");
        consultations[60] = new Consultation("CON061", "6", "DOC007", "11-09-2025", "Completed", "Dermatology mole examination");
        consultations[61] = new Consultation("CON062", "9", "DOC008", "12-09-2025", "Completed", "Emergency trauma assessment");
        consultations[62] = new Consultation("CON063", "14", "DOC009", "13-09-2025", "Completed", "Cancer follow-up consultation");
        consultations[63] = new Consultation("CON064", "16", "DOC010", "14-09-2025", "Completed", "Imaging results discussion");
        consultations[64] = new Consultation("CON065", "7", "DOC011", "15-09-2025", "Completed", "Prenatal care consultation");
        consultations[65] = new Consultation("CON066", "7", "DOC012", "16-09-2025", "Completed", "Prostate examination");
        consultations[66] = new Consultation("CON067", "8", "DOC013", "17-09-2025", "Completed", "General health screening");
        consultations[67] = new Consultation("CON068", "9", "DOC014", "18-09-2025", "Completed", "Digestive health consultation");
        consultations[68] = new Consultation("CON069", "5", "DOC015", "19-09-2025", "Completed", "Arthritis management review");
        consultations[69] = new Consultation("CON070", "5", "DOC016", "20-09-2025", "Completed", "Respiratory function assessment");
        consultations[70] = new Consultation("CON071", "5", "DOC017", "21-09-2025", "Completed", "Kidney health evaluation");
        consultations[71] = new Consultation("CON072", "5", "DOC018", "22-09-2025", "Completed", "Blood disorder consultation");
        consultations[72] = new Consultation("CON073", "6", "DOC019", "23-09-2025", "Completed", "Infection treatment follow-up");
        consultations[73] = new Consultation("CON074", "6", "DOC020", "24-09-2025", "Completed", "Pain management consultation");
        consultations[74] = new Consultation("CON075", "6", "DOC001", "25-09-2025", "Completed", "Heart rhythm monitoring");
        consultations[75] = new Consultation("CON076", "6", "DOC002", "26-09-2025", "Completed", "Child development assessment");
        consultations[76] = new Consultation("CON077", "2", "DOC003", "27-09-2025", "Completed", "Memory and cognition test");
        consultations[77] = new Consultation("CON078", "7", "DOC005", "28-09-2025", "Completed", "Thyroid function evaluation");
        consultations[78] = new Consultation("CON079", "11", "DOC006", "29-09-2025", "Completed", "Anxiety disorder treatment");
        consultations[79] = new Consultation("CON080", "18", "DOC007", "30-09-2025", "Completed", "Skin cancer screening");
        consultations[80] = new Consultation("CON081", "1", "DOC008", "01-10-2025", "Completed", "Emergency chest pain evaluation");
        consultations[81] = new Consultation("CON082", "4", "DOC009", "02-10-2025", "Completed", "Tumor marker assessment");
        consultations[82] = new Consultation("CON083", "23", "DOC010", "03-10-2025", "Completed", "X-ray interpretation session");
        consultations[83] = new Consultation("CON084", "1", "DOC011", "04-10-2025", "Completed", "Reproductive health consultation");
        consultations[84] = new Consultation("CON085", "5", "DOC012", "05-10-2025", "Completed", "Bladder health assessment");
        consultations[85] = new Consultation("CON086", "24", "DOC013", "06-10-2025", "Completed", "Preventive medicine consultation");
        consultations[86] = new Consultation("CON087", "26", "DOC014", "07-10-2025", "Completed", "Liver function evaluation");
        consultations[87] = new Consultation("CON088", "5", "DOC015", "08-10-2025", "Completed", "Joint mobility assessment");
        consultations[88] = new Consultation("CON089", "10", "DOC016", "09-10-2025", "Completed", "Asthma control evaluation");
        consultations[89] = new Consultation("CON090", "10", "DOC017", "10-10-2025", "Completed", "Dialysis consultation");
        consultations[90] = new Consultation("CON091", "10", "DOC018", "11-10-2025", "Completed", "Anemia treatment review");
        consultations[91] = new Consultation("CON092", "10", "DOC019", "12-10-2025", "Completed", "Vaccination consultation");
        consultations[92] = new Consultation("CON093", "11", "DOC020", "13-10-2025", "Completed", "Surgical consultation");
        consultations[93] = new Consultation("CON094", "11", "DOC001", "14-10-2025", "Completed", "Hypertension medication adjustment");
        consultations[94] = new Consultation("CON095", "11", "DOC002", "15-10-2025", "Completed", "Growth hormone assessment");
        consultations[95] = new Consultation("CON096", "11", "DOC003", "16-10-2025", "Completed", "Seizure disorder evaluation");
        consultations[96] = new Consultation("CON097", "3", "DOC005", "17-10-2025", "Completed", "Insulin therapy consultation");
        consultations[97] = new Consultation("CON098", "8", "DOC006", "18-10-2025", "Completed", "Depression therapy session");
        consultations[98] = new Consultation("CON099", "15", "DOC007", "19-10-2025", "Completed", "Acne treatment consultation");
        consultations[99] = new Consultation("CON100", "20", "DOC008", "20-10-2025", "Completed", "Accident injury assessment");
        consultations[100] = new Consultation("CON101", "1", "DOC009", "21-10-2025", "Completed", "Chemotherapy consultation");
        consultations[101] = new Consultation("CON102", "3", "DOC010", "22-10-2025", "Completed", "MRI scan review");
        consultations[102] = new Consultation("CON103", "32", "DOC011", "23-10-2025", "Completed", "Menopause management");
        consultations[103] = new Consultation("CON104", "1", "DOC012", "24-10-2025", "Completed", "Kidney stone consultation");
        consultations[104] = new Consultation("CON105", "12", "DOC013", "25-10-2025", "Completed", "Cholesterol management");
        consultations[105] = new Consultation("CON106", "12", "DOC014", "26-10-2025", "Completed", "Inflammatory bowel disease");
        consultations[106] = new Consultation("CON107", "12", "DOC015", "27-10-2025", "Completed", "Osteoporosis screening");
        consultations[107] = new Consultation("CON108", "12", "DOC016", "28-10-2025", "Completed", "COPD management review");
        consultations[108] = new Consultation("CON109", "5", "DOC017", "29-10-2025", "Completed", "Chronic kidney disease");
        consultations[109] = new Consultation("CON110", "13", "DOC018", "30-10-2025", "Completed", "Leukemia follow-up");
        consultations[110] = new Consultation("CON111", "14", "DOC019", "31-10-2025", "Completed", "Hepatitis screening");
        consultations[111] = new Consultation("CON112", "5", "DOC020", "01-11-2025", "Completed", "Post-operative check");
        consultations[112] = new Consultation("CON113", "12", "DOC001", "02-11-2025", "Completed", "Arrhythmia monitoring");
        consultations[113] = new Consultation("CON114", "15", "DOC002", "03-11-2025", "Completed", "Developmental milestone check");
        consultations[114] = new Consultation("CON115", "16", "DOC003", "04-11-2025", "Completed", "Parkinson's disease assessment");
        consultations[115] = new Consultation("CON116", "12", "DOC005", "05-11-2025", "Completed", "Adrenal function test");
        consultations[116] = new Consultation("CON117", "2", "DOC006", "06-11-2025", "Completed", "Bipolar disorder management");
        consultations[117] = new Consultation("CON118", "7", "DOC007", "07-11-2025", "Completed", "Psoriasis treatment review");
        consultations[118] = new Consultation("CON119", "11", "DOC008", "08-11-2025", "Completed", "Cardiac arrest consultation");
        consultations[119] = new Consultation("CON120", "18", "DOC009", "09-11-2025", "Completed", "Radiation therapy planning");
        consultations[120] = new Consultation("CON121", "1", "DOC010", "10-11-2025", "Completed", "CT scan interpretation");
        consultations[121] = new Consultation("CON122", "1", "DOC011", "11-11-2025", "Completed", "Fertility consultation");
        consultations[122] = new Consultation("CON123", "1", "DOC012", "12-11-2025", "Completed", "Erectile dysfunction treatment");
        consultations[123] = new Consultation("CON124", "1", "DOC013", "13-11-2025", "Completed", "Metabolic syndrome evaluation");
        consultations[124] = new Consultation("CON125", "5", "DOC014", "14-11-2025", "Completed", "Peptic ulcer management");
        consultations[125] = new Consultation("CON126", "5", "DOC015", "15-11-2025", "Completed", "Fibromyalgia consultation");
        consultations[126] = new Consultation("CON127", "5", "DOC016", "16-11-2025", "Completed", "Sleep apnea evaluation");
        consultations[127] = new Consultation("CON128", "5", "DOC017", "17-11-2025", "Completed", "Hypertensive nephropathy");
        consultations[128] = new Consultation("CON129", "17", "DOC018", "18-11-2025", "Completed", "Thrombocytopenia treatment");
        consultations[129] = new Consultation("CON130", "18", "DOC019", "19-11-2025", "Completed", "Antibiotic resistance consultation");
        consultations[130] = new Consultation("CON131", "19", "DOC020", "20-11-2025", "Completed", "Chronic pain management");
        consultations[131] = new Consultation("CON132", "20", "DOC001", "21-11-2025", "Completed", "Valve replacement follow-up");
        consultations[132] = new Consultation("CON133", "21", "DOC002", "22-11-2025", "Completed", "Autism spectrum screening");
        consultations[133] = new Consultation("CON134", "22", "DOC003", "23-11-2025", "Completed", "Multiple sclerosis evaluation");
        consultations[134] = new Consultation("CON135", "23", "DOC005", "24-11-2025", "Completed", "Pituitary gland assessment");
        consultations[135] = new Consultation("CON136", "24", "DOC006", "25-11-2025", "Completed", "PTSD therapy session");
        consultations[136] = new Consultation("CON137", "11", "DOC007", "26-11-2025", "Completed", "Melanoma screening");
        consultations[137] = new Consultation("CON138", "8", "DOC008", "27-11-2025", "Completed", "Stroke rehabilitation");
        consultations[138] = new Consultation("CON139", "15", "DOC009", "28-11-2025", "Completed", "Immunotherapy consultation");
        consultations[139] = new Consultation("CON140", "20", "DOC010", "29-11-2025", "Completed", "Ultrasound examination");
        consultations[140] = new Consultation("CON141", "1", "DOC011", "30-11-2025", "Completed", "Endometriosis management");
        consultations[141] = new Consultation("CON142", "1", "DOC012", "01-12-2025", "Completed", "Benign prostatic hyperplasia");
        consultations[142] = new Consultation("CON143", "1", "DOC013", "02-12-2025", "Completed", "Vitamin deficiency assessment");
        consultations[143] = new Consultation("CON144", "1", "DOC014", "03-12-2025", "Completed", "Gallbladder disease consultation");
        consultations[144] = new Consultation("CON145", "25", "DOC015", "04-12-2025", "Completed", "Lupus management review");
        consultations[145] = new Consultation("CON146", "26", "DOC016", "05-12-2025", "Completed", "Pulmonary embolism treatment");
        consultations[146] = new Consultation("CON147", "27", "DOC017", "06-12-2025", "Completed", "Polycystic kidney disease");
        consultations[147] = new Consultation("CON148", "28", "DOC018", "07-12-2025", "Completed", "Hemophilia management");
        consultations[148] = new Consultation("CON149", "29", "DOC019", "08-12-2025", "Completed", "Tuberculosis screening");
        consultations[149] = new Consultation("CON150", "30", "DOC020", "09-12-2025", "Completed", "Regional anesthesia consultation");
        consultations[150] = new Consultation("CON151", "11", "DOC001", "10-12-2025", "Completed", "Congestive heart failure");
        consultations[151] = new Consultation("CON152", "32", "DOC002", "11-12-2025", "Completed", "ADHD assessment");
        consultations[152] = new Consultation("CON153", "33", "DOC003", "12-12-2025", "Completed", "Alzheimer's disease evaluation");
        consultations[153] = new Consultation("CON154", "34", "DOC005", "13-12-2025", "Completed", "Gestational diabetes management");
        consultations[154] = new Consultation("CON155", "35", "DOC006", "14-12-2025", "Completed", "Obsessive-compulsive disorder");
        consultations[155] = new Consultation("CON156", "36", "DOC007", "15-12-2025", "Completed", "Rosacea treatment consultation");
        consultations[156] = new Consultation("CON157", "2", "DOC008", "16-12-2025", "Completed", "Anaphylaxis management");
        consultations[157] = new Consultation("CON158", "7", "DOC009", "17-12-2025", "Completed", "Bone marrow biopsy consultation");
        consultations[158] = new Consultation("CON159", "11", "DOC010", "18-12-2025", "Completed", "Nuclear medicine scan");
        consultations[159] = new Consultation("CON160", "18", "DOC011", "19-12-2025", "Completed", "Ovarian cyst management");
        consultations[160] = new Consultation("CON161", "1", "DOC012", "20-12-2025", "Completed", "Testicular cancer screening");
        consultations[161] = new Consultation("CON162", "2", "DOC013", "21-12-2025", "Completed", "Autoimmune disease consultation");
        consultations[162] = new Consultation("CON163", "3", "DOC014", "22-12-2025", "Completed", "Celiac disease management");
        consultations[163] = new Consultation("CON164", "4", "DOC015", "23-12-2025", "Completed", "Gout management review");
        consultations[164] = new Consultation("CON165", "5", "DOC016", "24-12-2025", "Completed", "Interstitial lung disease");
        consultations[165] = new Consultation("CON166", "6", "DOC017", "25-12-2025", "Completed", "Diabetic nephropathy");
        consultations[166] = new Consultation("CON167", "7", "DOC018", "26-12-2025", "Completed", "Sickle cell disease");
        consultations[167] = new Consultation("CON168", "8", "DOC019", "27-12-2025", "Completed", "HIV consultation");
        consultations[168] = new Consultation("CON169", "9", "DOC020", "28-12-2025", "Completed", "Epidural injection consultation");
        consultations[169] = new Consultation("CON170", "10", "DOC001", "29-12-2025", "Completed", "Peripheral artery disease");
        consultations[170] = new Consultation("CON171", "11", "DOC002", "30-12-2025", "Completed", "Cystic fibrosis management");
        consultations[171] = new Consultation("CON172", "2", "DOC003", "31-12-2025", "Completed", "Huntington's disease consultation");
        consultations[172] = new Consultation("CON173", "13", "DOC005", "01-01-2026", "Completed", "Polycystic ovary syndrome");
        consultations[173] = new Consultation("CON174", "14", "DOC006", "02-01-2026", "Completed", "Eating disorder consultation");
        consultations[174] = new Consultation("CON175", "15", "DOC007", "03-01-2026", "Completed", "Vitiligo treatment review");
        consultations[175] = new Consultation("CON176", "16", "DOC008", "04-01-2026", "Completed", "Burn injury assessment");
        consultations[176] = new Consultation("CON177", "17", "DOC009", "05-01-2026", "Completed", "Palliative care consultation");
        consultations[177] = new Consultation("CON178", "18", "DOC010", "06-01-2026", "Completed", "Mammography interpretation");
        consultations[178] = new Consultation("CON179", "19", "DOC011", "07-01-2026", "Completed", "Uterine fibroid consultation");
        consultations[179] = new Consultation("CON180", "20", "DOC012", "08-01-2026", "Completed", "Vasectomy consultation");
        consultations[180] = new Consultation("CON181", "21", "DOC013", "09-01-2026", "Completed", "Iron deficiency anemia");
        consultations[181] = new Consultation("CON182", "22", "DOC014", "10-01-2026", "Completed", "Hepatitis C treatment");
        consultations[182] = new Consultation("CON183", "23", "DOC015", "11-01-2026", "Completed", "Carpal tunnel syndrome");
        consultations[183] = new Consultation("CON184", "24", "DOC016", "12-01-2026", "Completed", "Chronic bronchitis");
        consultations[184] = new Consultation("CON185", "25", "DOC017", "13-01-2026", "Completed", "Acute kidney injury");
        consultations[185] = new Consultation("CON186", "26", "DOC018", "14-01-2026", "Completed", "Platelet disorder consultation");
        consultations[186] = new Consultation("CON187", "27", "DOC019", "15-01-2026", "Completed", "Malaria treatment");
        consultations[187] = new Consultation("CON188", "28", "DOC020", "16-01-2026", "Completed", "Spinal anesthesia consultation");
        consultations[188] = new Consultation("CON189", "29", "DOC001", "17-01-2026", "Completed", "Mitral valve prolapse");
        consultations[189] = new Consultation("CON190", "30", "DOC002", "18-01-2026", "Completed", "Cerebral palsy management");
        consultations[190] = new Consultation("CON191", "31", "DOC003", "19-01-2026", "Completed", "Migraine prevention consultation");
        consultations[191] = new Consultation("CON192", "32", "DOC005", "20-01-2026", "Completed", "Cushing's syndrome evaluation");
        consultations[192] = new Consultation("CON193", "33", "DOC006", "21-01-2026", "Completed", "Panic disorder treatment");
        consultations[193] = new Consultation("CON194", "34", "DOC007", "22-01-2026", "Completed", "Basal cell carcinoma");
        consultations[194] = new Consultation("CON195", "35", "DOC008", "23-01-2026", "Completed", "Hypothermia treatment");
        consultations[195] = new Consultation("CON196", "36", "DOC009", "24-01-2026", "Completed", "Lymphoma consultation");
        consultations[196] = new Consultation("CON197", "37", "DOC010", "25-01-2026", "Completed", "Bone density scan");
        consultations[197] = new Consultation("CON198", "38", "DOC011", "26-01-2026", "Completed", "Pelvic inflammatory disease");
        consultations[198] = new Consultation("CON199", "39", "DOC012", "27-01-2026", "Completed", "Penile cancer screening");
        consultations[199] = new Consultation("CON200", "40", "DOC013", "28-01-2026", "Completed", "Chronic fatigue syndrome");
        consultations[200] = new Consultation("CON201", "1", "DOC014", "29-01-2026", "Completed", "Pancreatic disorder consultation");
        consultations[201] = new Consultation("CON202", "2", "DOC015", "30-01-2026", "Completed", "Tendonitis treatment");
        consultations[202] = new Consultation("CON203", "3", "DOC016", "31-01-2026", "Completed", "Pneumothorax management");
        consultations[203] = new Consultation("CON204", "4", "DOC017", "01-02-2026", "Completed", "Glomerulonephritis consultation");
        consultations[204] = new Consultation("CON205", "5", "DOC018", "02-02-2026", "Completed", "Aplastic anemia treatment");
        consultations[205] = new Consultation("CON206", "6", "DOC019", "03-02-2026", "Completed", "Dengue fever management");
        consultations[206] = new Consultation("CON207", "7", "DOC020", "04-02-2026", "Completed", "Nerve block consultation");
        consultations[207] = new Consultation("CON208", "8", "DOC001", "05-02-2026", "Completed", "Aortic stenosis evaluation");
        consultations[208] = new Consultation("CON209", "9", "DOC002", "06-02-2026", "Completed", "Down syndrome consultation");
        consultations[209] = new Consultation("CON210", "10", "DOC003", "07-02-2026", "Completed", "Trigeminal neuralgia");
        consultations[210] = new Consultation("CON211", "11", "DOC005", "08-02-2026", "Completed", "Addison's disease management");
        consultations[211] = new Consultation("CON212", "12", "DOC006", "09-02-2026", "Completed", "Schizophrenia treatment");
        consultations[212] = new Consultation("CON213", "13", "DOC007", "10-02-2026", "Completed", "Hidradenitis suppurativa");
        consultations[213] = new Consultation("CON214", "14", "DOC008", "11-02-2026", "Completed", "Sepsis management");
        consultations[214] = new Consultation("CON215", "15", "DOC009", "12-02-2026", "Completed", "Mesothelioma consultation");
        consultations[215] = new Consultation("CON216", "16", "DOC010", "13-02-2026", "Completed", "PET scan interpretation");
        consultations[216] = new Consultation("CON217", "17", "DOC011", "14-02-2026", "Completed", "Ectopic pregnancy management");
        consultations[217] = new Consultation("CON218", "18", "DOC012", "15-02-2026", "Completed", "Hydrocele treatment");
        consultations[218] = new Consultation("CON219", "19", "DOC013", "16-02-2026", "Completed", "Osteomalacia consultation");
        consultations[219] = new Consultation("CON220", "20", "DOC014", "17-02-2026", "Completed", "Esophageal cancer screening");

        return consultations;
    }

    public static Treatment[] initializeSampleTreatments() {
        Treatment[] treatments = new Treatment[210];

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
        treatments[15] = new Treatment("TRE016", "19", "DOC013", "Gastritis", "MED018", "02-08-2025");
        treatments[16] = new Treatment("TRE017", "20", "DOC014", "Inflammatory Bowel Disease", "MED033", "03-08-2025");
        treatments[17] = new Treatment("TRE018", "21", "DOC015", "Rheumatoid Arthritis", "MED031", "04-08-2025");
        treatments[18] = new Treatment("TRE019", "22", "DOC017", "Chronic Kidney Disease", "MED014", "05-08-2025");
        treatments[19] = new Treatment("TRE020", "23", "DOC018", "Anemia", "MED012", "06-08-2025");
        treatments[20] = new Treatment("TRE021", "24", "DOC019", "Pneumonia", "MED022", "07-08-2025");
        treatments[21] = new Treatment("TRE022", "25", "DOC001", "Atrial Fibrillation", "MED032", "08-08-2025");
        treatments[22] = new Treatment("TRE023", "26", "DOC007", "Psoriasis", "MED028", "09-08-2025");
        treatments[23] = new Treatment("TRE024", "27", "DOC005", "Hyperthyroidism", "MED036", "10-08-2025");
        treatments[24] = new Treatment("TRE025", "28", "DOC013", "Peptic Ulcer", "MED024", "11-08-2025");
        treatments[25] = new Treatment("TRE026", "29", "DOC006", "Bipolar Disorder", "MED029", "12-08-2025");
        treatments[26] = new Treatment("TRE027", "30", "DOC002", "Allergic Asthma", "MED025", "13-08-2025");
        treatments[27] = new Treatment("TRE028", "31", "DOC008", "Acute Myocardial Infarction", "MED035", "14-08-2025");
        treatments[28] = new Treatment("TRE029", "32", "DOC014", "Crohn's Disease", "MED033", "15-08-2025");
        treatments[29] = new Treatment("TRE030", "33", "DOC015", "Osteoarthritis", "MED034", "16-08-2025");
        
        // Additional treatments to reach 200+
        treatments[30] = new Treatment("TRE031", "34", "DOC016", "Pulmonary Hypertension", "MED020", "17-08-2025");
        treatments[31] = new Treatment("TRE032", "35", "DOC017", "Nephrotic Syndrome", "MED032", "18-08-2025");
        treatments[32] = new Treatment("TRE033", "36", "DOC018", "Hemolytic Anemia", "MED012", "19-08-2025");
        treatments[33] = new Treatment("TRE034", "37", "DOC019", "Meningitis", "MED002", "20-08-2025");
        treatments[34] = new Treatment("TRE035", "38", "DOC020", "Chronic Pain Syndrome", "MED034", "21-08-2025");
        treatments[35] = new Treatment("TRE036", "39", "DOC001", "Coronary Artery Disease", "MED010", "22-08-2025");
        treatments[36] = new Treatment("TRE037", "40", "DOC002", "Attention Deficit Disorder", "MED015", "23-08-2025");
        treatments[37] = new Treatment("TRE038", "1", "DOC003", "Epilepsy", "MED039", "24-08-2025");
        treatments[38] = new Treatment("TRE039", "2", "DOC005", "Adrenal Insufficiency", "MED033", "25-08-2025");
        treatments[39] = new Treatment("TRE040", "3", "DOC006", "Generalized Anxiety Disorder", "MED038", "26-08-2025");
        treatments[40] = new Treatment("TRE041", "4", "DOC007", "Seborrheic Dermatitis", "MED005", "27-08-2025");
        treatments[41] = new Treatment("TRE042", "5", "DOC008", "Acute Respiratory Distress", "MED008", "28-08-2025");
        treatments[42] = new Treatment("TRE043", "6", "DOC009", "Breast Cancer", "MED019", "29-08-2025");
        treatments[43] = new Treatment("TRE044", "7", "DOC010", "Bone Fracture", "MED001", "30-08-2025");
        treatments[44] = new Treatment("TRE045", "8", "DOC011", "Endometriosis", "MED004", "31-08-2025");
        treatments[45] = new Treatment("TRE046", "9", "DOC012", "Prostatitis", "MED022", "01-09-2025");
        treatments[46] = new Treatment("TRE047", "10", "DOC013", "Hyperlipidemia", "MED016", "02-09-2025");
        treatments[47] = new Treatment("TRE048", "11", "DOC014", "Gastroenteritis", "MED024", "03-09-2025");
        treatments[48] = new Treatment("TRE049", "12", "DOC015", "Systemic Lupus Erythematosus", "MED033", "04-09-2025");
        treatments[49] = new Treatment("TRE050", "13", "DOC016", "Chronic Obstructive Pulmonary Disease", "MED028", "05-09-2025");
        treatments[50] = new Treatment("TRE051", "14", "DOC017", "Acute Renal Failure", "MED037", "06-09-2025");
        treatments[51] = new Treatment("TRE052", "15", "DOC018", "Iron Deficiency Anemia", "MED012", "07-09-2025");
        treatments[52] = new Treatment("TRE053", "16", "DOC019", "Viral Hepatitis", "MED018", "08-09-2025");
        treatments[53] = new Treatment("TRE054", "17", "DOC020", "Postoperative Pain", "MED034", "09-09-2025");
        treatments[54] = new Treatment("TRE055", "18", "DOC001", "Myocardial Infarction", "MED035", "10-09-2025");
        treatments[55] = new Treatment("TRE056", "19", "DOC002", "Developmental Delay", "MED036", "11-09-2025");
        treatments[56] = new Treatment("TRE057", "20", "DOC003", "Stroke Recovery", "MED001", "12-09-2025");
        treatments[57] = new Treatment("TRE058", "21", "DOC005", "Type 1 Diabetes", "MED012", "13-09-2025");
        treatments[58] = new Treatment("TRE059", "22", "DOC006", "Bipolar Disorder", "MED029", "14-09-2025");
        treatments[59] = new Treatment("TRE060", "23", "DOC007", "Skin Cancer", "MED001", "15-09-2025");
        treatments[60] = new Treatment("TRE061", "24", "DOC008", "Septic Shock", "MED002", "16-09-2025");
        treatments[61] = new Treatment("TRE062", "25", "DOC009", "Lung Cancer", "MED019", "17-09-2025");
        treatments[62] = new Treatment("TRE063", "26", "DOC010", "Osteoporosis", "MED001", "18-09-2025");
        treatments[63] = new Treatment("TRE064", "27", "DOC011", "Ovarian Cancer", "MED019", "19-09-2025");
        treatments[64] = new Treatment("TRE065", "28", "DOC012", "Bladder Cancer", "MED019", "20-09-2025");
        treatments[65] = new Treatment("TRE066", "29", "DOC013", "Metabolic Syndrome", "MED006", "21-09-2025");
        treatments[66] = new Treatment("TRE067", "30", "DOC014", "Liver Cirrhosis", "MED037", "22-09-2025");
        treatments[67] = new Treatment("TRE068", "31", "DOC015", "Rheumatoid Arthritis", "MED031", "23-09-2025");
        treatments[68] = new Treatment("TRE069", "32", "DOC016", "Pneumonia", "MED002", "24-09-2025");
        treatments[69] = new Treatment("TRE070", "33", "DOC017", "Chronic Kidney Disease", "MED014", "25-09-2025");
        treatments[70] = new Treatment("TRE071", "34", "DOC018", "Leukemia", "MED019", "26-09-2025");
        treatments[71] = new Treatment("TRE072", "35", "DOC019", "Tuberculosis", "MED002", "27-09-2025");
        treatments[72] = new Treatment("TRE073", "36", "DOC020", "Fibromyalgia", "MED034", "28-09-2025");
        treatments[73] = new Treatment("TRE074", "37", "DOC001", "Atrial Fibrillation", "MED035", "29-09-2025");
        treatments[74] = new Treatment("TRE075", "38", "DOC002", "Autism Spectrum Disorder", "MED015", "30-09-2025");
        treatments[75] = new Treatment("TRE076", "39", "DOC003", "Parkinson's Disease", "MED036", "01-10-2025");
        treatments[76] = new Treatment("TRE077", "40", "DOC005", "Thyroid Cancer", "MED036", "02-10-2025");
        treatments[77] = new Treatment("TRE078", "1", "DOC006", "Post-Traumatic Stress Disorder", "MED009", "03-10-2025");
        treatments[78] = new Treatment("TRE079", "2", "DOC007", "Melanoma", "MED001", "04-10-2025");
        treatments[79] = new Treatment("TRE080", "3", "DOC008", "Cardiac Arrest", "MED001", "05-10-2025");
        treatments[80] = new Treatment("TRE081", "4", "DOC009", "Colon Cancer", "MED019", "06-10-2025");
        treatments[81] = new Treatment("TRE082", "5", "DOC010", "Spinal Injury", "MED033", "07-10-2025");
        treatments[82] = new Treatment("TRE083", "6", "DOC011", "Cervical Cancer", "MED019", "08-10-2025");
        treatments[83] = new Treatment("TRE084", "7", "DOC012", "Kidney Stones", "MED001", "09-10-2025");
        treatments[84] = new Treatment("TRE085", "8", "DOC013", "Obesity", "MED006", "10-10-2025");
        treatments[85] = new Treatment("TRE086", "9", "DOC014", "Inflammatory Bowel Disease", "MED033", "11-10-2025");
        treatments[86] = new Treatment("TRE087", "10", "DOC015", "Ankylosing Spondylitis", "MED031", "12-10-2025");
        treatments[87] = new Treatment("TRE088", "11", "DOC016", "Asthma", "MED008", "13-10-2025");
        treatments[88] = new Treatment("TRE089", "12", "DOC017", "Polycystic Kidney Disease", "MED014", "14-10-2025");
        treatments[89] = new Treatment("TRE090", "13", "DOC018", "Sickle Cell Disease", "MED001", "15-10-2025");
        treatments[90] = new Treatment("TRE091", "14", "DOC019", "HIV/AIDS", "MED002", "16-10-2025");
        treatments[91] = new Treatment("TRE092", "15", "DOC020", "Chronic Pain", "MED034", "17-10-2025");
        treatments[92] = new Treatment("TRE093", "16", "DOC001", "Heart Failure", "MED007", "18-10-2025");
        treatments[93] = new Treatment("TRE094", "17", "DOC002", "Growth Hormone Deficiency", "MED036", "19-10-2025");
        treatments[94] = new Treatment("TRE095", "18", "DOC003", "Multiple Sclerosis", "MED033", "20-10-2025");
        treatments[95] = new Treatment("TRE096", "19", "DOC005", "Gestational Diabetes", "MED012", "21-10-2025");
        treatments[96] = new Treatment("TRE097", "20", "DOC006", "Major Depressive Disorder", "MED015", "22-10-2025");
        treatments[97] = new Treatment("TRE098", "21", "DOC007", "Acne Vulgaris", "MED005", "23-10-2025");
        treatments[98] = new Treatment("TRE099", "22", "DOC008", "Anaphylaxis", "MED001", "24-10-2025");
        treatments[99] = new Treatment("TRE100", "23", "DOC009", "Pancreatic Cancer", "MED019", "25-10-2025");
        treatments[100] = new Treatment("TRE101", "24", "DOC010", "Hip Fracture", "MED001", "26-10-2025");
        treatments[101] = new Treatment("TRE102", "25", "DOC011", "Polycystic Ovary Syndrome", "MED006", "27-10-2025");
        treatments[102] = new Treatment("TRE103", "26", "DOC012", "Erectile Dysfunction", "MED020", "28-10-2025");
        treatments[103] = new Treatment("TRE104", "27", "DOC013", "Vitamin D Deficiency", "MED001", "29-10-2025");
        treatments[104] = new Treatment("TRE105", "28", "DOC014", "Peptic Ulcer Disease", "MED024", "30-10-2025");
        treatments[105] = new Treatment("TRE106", "29", "DOC015", "Osteoarthritis", "MED031", "31-10-2025");
        treatments[106] = new Treatment("TRE107", "30", "DOC016", "Sleep Apnea", "MED001", "01-11-2025");
        treatments[107] = new Treatment("TRE108", "31", "DOC017", "Diabetic Nephropathy", "MED014", "02-11-2025");
        treatments[108] = new Treatment("TRE109", "32", "DOC018", "Thrombocytopenia", "MED033", "03-11-2025");
        treatments[109] = new Treatment("TRE110", "33", "DOC019", "Malaria", "MED002", "04-11-2025");
        treatments[110] = new Treatment("TRE111", "34", "DOC020", "Neuropathic Pain", "MED039", "05-11-2025");
        treatments[111] = new Treatment("TRE112", "35", "DOC001", "Valvular Heart Disease", "MED035", "06-11-2025");
        treatments[112] = new Treatment("TRE113", "36", "DOC002", "Cerebral Palsy", "MED033", "07-11-2025");
        treatments[113] = new Treatment("TRE114", "37", "DOC003", "Alzheimer's Disease", "MED036", "08-11-2025");
        treatments[114] = new Treatment("TRE115", "38", "DOC005", "Hyperthyroidism", "MED036", "09-11-2025");
        treatments[115] = new Treatment("TRE116", "39", "DOC006", "Obsessive-Compulsive Disorder", "MED009", "10-11-2025");
        treatments[116] = new Treatment("TRE117", "40", "DOC007", "Psoriasis", "MED028", "11-11-2025");
        treatments[117] = new Treatment("TRE118", "1", "DOC008", "Burns", "MED001", "12-11-2025");
        treatments[118] = new Treatment("TRE119", "2", "DOC009", "Brain Tumor", "MED019", "13-11-2025");
        treatments[119] = new Treatment("TRE120", "3", "DOC010", "Compression Fracture", "MED001", "14-11-2025");
        treatments[120] = new Treatment("TRE121", "4", "DOC011", "Menopause", "MED004", "15-11-2025");
        treatments[121] = new Treatment("TRE122", "5", "DOC012", "Benign Prostatic Hyperplasia", "MED020", "16-11-2025");
        treatments[122] = new Treatment("TRE123", "6", "DOC013", "Chronic Fatigue Syndrome", "MED001", "17-11-2025");
        treatments[123] = new Treatment("TRE124", "7", "DOC014", "Gallstones", "MED001", "18-11-2025");
        treatments[124] = new Treatment("TRE125", "8", "DOC015", "Gout", "MED031", "19-11-2025");
        treatments[125] = new Treatment("TRE126", "9", "DOC016", "Interstitial Lung Disease", "MED033", "20-11-2025");
        treatments[126] = new Treatment("TRE127", "10", "DOC017", "Hypertensive Nephropathy", "MED014", "21-11-2025");
        treatments[127] = new Treatment("TRE128", "11", "DOC018", "Hemophilia", "MED035", "22-11-2025");
        treatments[128] = new Treatment("TRE129", "12", "DOC019", "Dengue Fever", "MED001", "23-11-2025");
        treatments[129] = new Treatment("TRE130", "13", "DOC020", "Spinal Stenosis", "MED034", "24-11-2025");
        treatments[130] = new Treatment("TRE131", "14", "DOC001", "Peripheral Artery Disease", "MED007", "25-11-2025");
        treatments[131] = new Treatment("TRE132", "15", "DOC002", "Cystic Fibrosis", "MED008", "26-11-2025");
        treatments[132] = new Treatment("TRE133", "16", "DOC003", "Huntington's Disease", "MED036", "27-11-2025");
        treatments[133] = new Treatment("TRE134", "17", "DOC005", "Cushing's Syndrome", "MED033", "28-11-2025");
        treatments[134] = new Treatment("TRE135", "18", "DOC006", "Panic Disorder", "MED038", "29-11-2025");
        treatments[135] = new Treatment("TRE136", "19", "DOC007", "Basal Cell Carcinoma", "MED001", "30-11-2025");
        treatments[136] = new Treatment("TRE137", "20", "DOC008", "Hypothermia", "MED001", "01-12-2025");
        treatments[137] = new Treatment("TRE138", "21", "DOC009", "Lymphoma", "MED019", "02-12-2025");
        treatments[138] = new Treatment("TRE139", "22", "DOC010", "Bone Metastases", "MED001", "03-12-2025");
        treatments[139] = new Treatment("TRE140", "23", "DOC011", "Pelvic Inflammatory Disease", "MED002", "04-12-2025");
        treatments[140] = new Treatment("TRE141", "24", "DOC012", "Testicular Cancer", "MED019", "05-12-2025");
        treatments[141] = new Treatment("TRE142", "25", "DOC013", "Autoimmune Hepatitis", "MED033", "06-12-2025");
        treatments[142] = new Treatment("TRE143", "26", "DOC014", "Celiac Disease", "MED001", "07-12-2025");
        treatments[143] = new Treatment("TRE144", "27", "DOC015", "Carpal Tunnel Syndrome", "MED031", "08-12-2025");
        treatments[144] = new Treatment("TRE145", "28", "DOC016", "Pulmonary Embolism", "MED035", "09-12-2025");
        treatments[145] = new Treatment("TRE146", "29", "DOC017", "Acute Kidney Injury", "MED037", "10-12-2025");
        treatments[146] = new Treatment("TRE147", "30", "DOC018", "Aplastic Anemia", "MED033", "11-12-2025");
        treatments[147] = new Treatment("TRE148", "31", "DOC019", "Chikungunya", "MED001", "12-12-2025");
        treatments[148] = new Treatment("TRE149", "32", "DOC020", "Complex Regional Pain Syndrome", "MED039", "13-12-2025");
        treatments[149] = new Treatment("TRE150", "33", "DOC001", "Aortic Stenosis", "MED035", "14-12-2025");
        treatments[150] = new Treatment("TRE151", "34", "DOC002", "Down Syndrome", "MED036", "15-12-2025");
        treatments[151] = new Treatment("TRE152", "35", "DOC003", "Trigeminal Neuralgia", "MED039", "16-12-2025");
        treatments[152] = new Treatment("TRE153", "36", "DOC005", "Addison's Disease", "MED033", "17-12-2025");
        treatments[153] = new Treatment("TRE154", "37", "DOC006", "Schizophrenia", "MED015", "18-12-2025");
        treatments[154] = new Treatment("TRE155", "38", "DOC007", "Hidradenitis Suppurativa", "MED002", "19-12-2025");
        treatments[155] = new Treatment("TRE156", "39", "DOC008", "Sepsis", "MED002", "20-12-2025");
        treatments[156] = new Treatment("TRE157", "40", "DOC009", "Mesothelioma", "MED019", "21-12-2025");
        treatments[157] = new Treatment("TRE158", "1", "DOC010", "Bone Density Loss", "MED001", "22-12-2025");
        treatments[158] = new Treatment("TRE159", "2", "DOC011", "Uterine Fibroids", "MED004", "23-12-2025");
        treatments[159] = new Treatment("TRE160", "3", "DOC012", "Hydrocele", "MED001", "24-12-2025");
        treatments[160] = new Treatment("TRE161", "4", "DOC013", "Osteomalacia", "MED001", "25-12-2025");
        treatments[161] = new Treatment("TRE162", "5", "DOC014", "Esophageal Cancer", "MED019", "26-12-2025");
        treatments[162] = new Treatment("TRE163", "6", "DOC015", "Tendonitis", "MED031", "27-12-2025");
        treatments[163] = new Treatment("TRE164", "7", "DOC016", "Pneumothorax", "MED001", "28-12-2025");
        treatments[164] = new Treatment("TRE165", "8", "DOC017", "Glomerulonephritis", "MED014", "29-12-2025");
        treatments[165] = new Treatment("TRE166", "9", "DOC018", "Platelet Dysfunction", "MED033", "30-12-2025");
        treatments[166] = new Treatment("TRE167", "10", "DOC019", "Zika Virus", "MED001", "31-12-2025");
        treatments[167] = new Treatment("TRE168", "11", "DOC020", "Chronic Back Pain", "MED034", "01-01-2026");
        treatments[168] = new Treatment("TRE169", "12", "DOC001", "Mitral Valve Prolapse", "MED007", "02-01-2026");
        treatments[169] = new Treatment("TRE170", "13", "DOC002", "Attention Deficit Hyperactivity Disorder", "MED015", "03-01-2026");
        treatments[170] = new Treatment("TRE171", "14", "DOC003", "Migraine Prophylaxis", "MED003", "04-01-2026");
        treatments[171] = new Treatment("TRE172", "15", "DOC005", "Polycystic Ovary Syndrome", "MED006", "05-01-2026");
        treatments[172] = new Treatment("TRE173", "16", "DOC006", "Eating Disorders", "MED015", "06-01-2026");
        treatments[173] = new Treatment("TRE174", "17", "DOC007", "Vitiligo", "MED033", "07-01-2026");
        treatments[174] = new Treatment("TRE175", "18", "DOC008", "Burn Injuries", "MED001", "08-01-2026");
        treatments[175] = new Treatment("TRE176", "19", "DOC009", "Palliative Care", "MED034", "09-01-2026");
        treatments[176] = new Treatment("TRE177", "20", "DOC010", "Soft Tissue Injury", "MED031", "10-01-2026");
        treatments[177] = new Treatment("TRE178", "21", "DOC011", "Ovarian Cysts", "MED004", "11-01-2026");
        treatments[178] = new Treatment("TRE179", "22", "DOC012", "Vasectomy Recovery", "MED001", "12-01-2026");
        treatments[179] = new Treatment("TRE180", "23", "DOC013", "Iron Deficiency", "MED012", "13-01-2026");
        treatments[180] = new Treatment("TRE181", "24", "DOC014", "Hepatitis C", "MED002", "14-01-2026");
        treatments[181] = new Treatment("TRE182", "25", "DOC015", "Joint Replacement Recovery", "MED031", "15-01-2026");
        treatments[182] = new Treatment("TRE183", "26", "DOC016", "Chronic Bronchitis", "MED008", "16-01-2026");
        treatments[183] = new Treatment("TRE184", "27", "DOC017", "Renal Calculi", "MED001", "17-01-2026");
        treatments[184] = new Treatment("TRE185", "28", "DOC018", "Blood Coagulation Disorder", "MED035", "18-01-2026");
        treatments[185] = new Treatment("TRE186", "29", "DOC019", "Viral Infection", "MED001", "19-01-2026");
        treatments[186] = new Treatment("TRE187", "30", "DOC020", "Post-Surgical Pain", "MED034", "20-01-2026");
        treatments[187] = new Treatment("TRE188", "31", "DOC001", "Coronary Stent Follow-up", "MED035", "21-01-2026");
        treatments[188] = new Treatment("TRE189", "32", "DOC002", "Developmental Disorders", "MED036", "22-01-2026");
        treatments[189] = new Treatment("TRE190", "33", "DOC003", "Cognitive Impairment", "MED036", "23-01-2026");
        treatments[190] = new Treatment("TRE191", "34", "DOC005", "Hormonal Imbalance", "MED036", "24-01-2026");
        treatments[191] = new Treatment("TRE192", "35", "DOC006", "Substance Abuse Recovery", "MED015", "25-01-2026");
        treatments[192] = new Treatment("TRE193", "36", "DOC007", "Skin Allergies", "MED005", "26-01-2026");
        treatments[193] = new Treatment("TRE194", "37", "DOC008", "Electrolyte Imbalance", "MED037", "27-01-2026");
        treatments[194] = new Treatment("TRE195", "38", "DOC009", "Radiation Side Effects", "MED001", "28-01-2026");
        treatments[195] = new Treatment("TRE196", "39", "DOC010", "Muscle Strain", "MED031", "29-01-2026");
        treatments[196] = new Treatment("TRE197", "40", "DOC011", "Reproductive Health Issues", "MED004", "30-01-2026");
        treatments[197] = new Treatment("TRE198", "1", "DOC012", "Urinary Incontinence", "MED020", "31-01-2026");
        treatments[198] = new Treatment("TRE199", "2", "DOC013", "Nutritional Deficiency", "MED001", "01-02-2026");
        treatments[199] = new Treatment("TRE200", "3", "DOC014", "Digestive Disorders", "MED018", "02-02-2026");
        treatments[200] = new Treatment("TRE201", "4", "DOC015", "Muscle Weakness", "MED001", "03-02-2026");
        treatments[201] = new Treatment("TRE202", "5", "DOC016", "Respiratory Infection", "MED002", "04-02-2026");
        treatments[202] = new Treatment("TRE203", "6", "DOC017", "Fluid Retention", "MED037", "05-02-2026");
        treatments[203] = new Treatment("TRE204", "7", "DOC018", "Blood Pressure Irregularities", "MED007", "06-02-2026");
        treatments[204] = new Treatment("TRE205", "8", "DOC019", "Infectious Disease Prevention", "MED002", "07-02-2026");
        treatments[205] = new Treatment("TRE206", "9", "DOC020", "Nerve Compression", "MED039", "08-02-2026");
        treatments[206] = new Treatment("TRE207", "10", "DOC001", "Heart Rhythm Disorders", "MED032", "09-02-2026");
        treatments[207] = new Treatment("TRE208", "11", "DOC002", "Learning Disabilities", "MED015", "10-02-2026");
        treatments[208] = new Treatment("TRE209", "12", "DOC003", "Movement Disorders", "MED036", "11-02-2026");
        treatments[209] = new Treatment("TRE210", "13", "DOC005", "Metabolic Disorders", "MED006", "12-02-2026");

        return treatments;
    }

    public static PharmacyTransaction[] initializeSampleTransactions() {
        PharmacyTransaction[] transactions = new PharmacyTransaction[220];

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
        transactions[25] = new PharmacyTransaction("TXN026", "26", "MED022", 1, "10-08-2025");
        transactions[26] = new PharmacyTransaction("TXN027", "27", "MED023", 2, "11-08-2025");
        transactions[27] = new PharmacyTransaction("TXN028", "28", "MED024", 1, "12-08-2025");
        transactions[28] = new PharmacyTransaction("TXN029", "29", "MED025", 1, "13-08-2025");
        transactions[29] = new PharmacyTransaction("TXN030", "30", "MED026", 2, "14-08-2025");
        transactions[30] = new PharmacyTransaction("TXN031", "31", "MED027", 1, "15-08-2025");
        transactions[31] = new PharmacyTransaction("TXN032", "32", "MED028", 1, "16-08-2025");
        transactions[32] = new PharmacyTransaction("TXN033", "33", "MED029", 2, "17-08-2025");
        transactions[33] = new PharmacyTransaction("TXN034", "34", "MED030", 1, "18-08-2025");
        transactions[34] = new PharmacyTransaction("TXN035", "35", "MED031", 1, "19-08-2025");
        transactions[35] = new PharmacyTransaction("TXN036", "36", "MED032", 2, "20-08-2025");
        transactions[36] = new PharmacyTransaction("TXN037", "37", "MED033", 1, "21-08-2025");
        transactions[37] = new PharmacyTransaction("TXN038", "38", "MED034", 1, "22-08-2025");
        transactions[38] = new PharmacyTransaction("TXN039", "39", "MED035", 2, "23-08-2025");
        transactions[39] = new PharmacyTransaction("TXN040", "40", "MED036", 1, "24-08-2025");
        
        // Additional pharmacy transactions to reach 200+
        transactions[40] = new PharmacyTransaction("TXN041", "1", "MED001", 3, "25-08-2025");
        transactions[41] = new PharmacyTransaction("TXN042", "2", "MED002", 2, "26-08-2025");
        transactions[42] = new PharmacyTransaction("TXN043", "3", "MED003", 1, "27-08-2025");
        transactions[43] = new PharmacyTransaction("TXN044", "4", "MED004", 2, "28-08-2025");
        transactions[44] = new PharmacyTransaction("TXN045", "5", "MED005", 1, "29-08-2025");
        transactions[45] = new PharmacyTransaction("TXN046", "6", "MED006", 3, "30-08-2025");
        transactions[46] = new PharmacyTransaction("TXN047", "7", "MED007", 1, "31-08-2025");
        transactions[47] = new PharmacyTransaction("TXN048", "8", "MED008", 2, "01-09-2025");
        transactions[48] = new PharmacyTransaction("TXN049", "9", "MED009", 1, "02-09-2025");
        transactions[49] = new PharmacyTransaction("TXN050", "10", "MED010", 2, "03-09-2025");
        transactions[50] = new PharmacyTransaction("TXN051", "11", "MED011", 3, "04-09-2025");
        transactions[51] = new PharmacyTransaction("TXN052", "12", "MED012", 1, "05-09-2025");
        transactions[52] = new PharmacyTransaction("TXN053", "13", "MED013", 2, "06-09-2025");
        transactions[53] = new PharmacyTransaction("TXN054", "14", "MED014", 1, "07-09-2025");
        transactions[54] = new PharmacyTransaction("TXN055", "15", "MED015", 2, "08-09-2025");
        transactions[55] = new PharmacyTransaction("TXN056", "16", "MED016", 1, "09-09-2025");
        transactions[56] = new PharmacyTransaction("TXN057", "17", "MED017", 3, "10-09-2025");
        transactions[57] = new PharmacyTransaction("TXN058", "18", "MED018", 2, "11-09-2025");
        transactions[58] = new PharmacyTransaction("TXN059", "19", "MED019", 1, "12-09-2025");
        transactions[59] = new PharmacyTransaction("TXN060", "20", "MED020", 2, "13-09-2025");
        transactions[60] = new PharmacyTransaction("TXN061", "21", "MED021", 1, "14-09-2025");
        transactions[61] = new PharmacyTransaction("TXN062", "22", "MED022", 3, "15-09-2025");
        transactions[62] = new PharmacyTransaction("TXN063", "23", "MED023", 2, "16-09-2025");
        transactions[63] = new PharmacyTransaction("TXN064", "24", "MED024", 1, "17-09-2025");
        transactions[64] = new PharmacyTransaction("TXN065", "25", "MED025", 2, "18-09-2025");
        transactions[65] = new PharmacyTransaction("TXN066", "26", "MED026", 1, "19-09-2025");
        transactions[66] = new PharmacyTransaction("TXN067", "27", "MED027", 3, "20-09-2025");
        transactions[67] = new PharmacyTransaction("TXN068", "28", "MED028", 1, "21-09-2025");
        transactions[68] = new PharmacyTransaction("TXN069", "29", "MED029", 2, "22-09-2025");
        transactions[69] = new PharmacyTransaction("TXN070", "30", "MED030", 1, "23-09-2025");
        transactions[70] = new PharmacyTransaction("TXN071", "31", "MED031", 2, "24-09-2025");
        transactions[71] = new PharmacyTransaction("TXN072", "32", "MED032", 3, "25-09-2025");
        transactions[72] = new PharmacyTransaction("TXN073", "33", "MED033", 1, "26-09-2025");
        transactions[73] = new PharmacyTransaction("TXN074", "34", "MED034", 2, "27-09-2025");
        transactions[74] = new PharmacyTransaction("TXN075", "35", "MED035", 1, "28-09-2025");
        transactions[75] = new PharmacyTransaction("TXN076", "36", "MED036", 3, "29-09-2025");
        transactions[76] = new PharmacyTransaction("TXN077", "37", "MED037", 2, "30-09-2025");
        transactions[77] = new PharmacyTransaction("TXN078", "38", "MED038", 1, "01-10-2025");
        transactions[78] = new PharmacyTransaction("TXN079", "39", "MED039", 2, "02-10-2025");
        transactions[79] = new PharmacyTransaction("TXN080", "40", "MED040", 1, "03-10-2025");
        transactions[80] = new PharmacyTransaction("TXN081", "1", "MED001", 2, "04-10-2025");
        transactions[81] = new PharmacyTransaction("TXN082", "2", "MED002", 3, "05-10-2025");
        transactions[82] = new PharmacyTransaction("TXN083", "3", "MED003", 1, "06-10-2025");
        transactions[83] = new PharmacyTransaction("TXN084", "4", "MED004", 2, "07-10-2025");
        transactions[84] = new PharmacyTransaction("TXN085", "5", "MED005", 1, "08-10-2025");
        transactions[85] = new PharmacyTransaction("TXN086", "6", "MED006", 3, "09-10-2025");
        transactions[86] = new PharmacyTransaction("TXN087", "7", "MED007", 2, "10-10-2025");
        transactions[87] = new PharmacyTransaction("TXN088", "8", "MED008", 1, "11-10-2025");
        transactions[88] = new PharmacyTransaction("TXN089", "9", "MED009", 2, "12-10-2025");
        transactions[89] = new PharmacyTransaction("TXN090", "10", "MED010", 1, "13-10-2025");
        transactions[90] = new PharmacyTransaction("TXN091", "11", "MED011", 3, "14-10-2025");
        transactions[91] = new PharmacyTransaction("TXN092", "12", "MED012", 2, "15-10-2025");
        transactions[92] = new PharmacyTransaction("TXN093", "13", "MED013", 1, "16-10-2025");
        transactions[93] = new PharmacyTransaction("TXN094", "14", "MED014", 2, "17-10-2025");
        transactions[94] = new PharmacyTransaction("TXN095", "15", "MED015", 3, "18-10-2025");
        transactions[95] = new PharmacyTransaction("TXN096", "16", "MED016", 1, "19-10-2025");
        transactions[96] = new PharmacyTransaction("TXN097", "17", "MED017", 2, "20-10-2025");
        transactions[97] = new PharmacyTransaction("TXN098", "18", "MED018", 1, "21-10-2025");
        transactions[98] = new PharmacyTransaction("TXN099", "19", "MED019", 3, "22-10-2025");
        transactions[99] = new PharmacyTransaction("TXN100", "20", "MED020", 2, "23-10-2025");
        transactions[100] = new PharmacyTransaction("TXN101", "21", "MED021", 1, "24-10-2025");
        transactions[101] = new PharmacyTransaction("TXN102", "22", "MED022", 2, "25-10-2025");
        transactions[102] = new PharmacyTransaction("TXN103", "23", "MED023", 3, "26-10-2025");
        transactions[103] = new PharmacyTransaction("TXN104", "24", "MED024", 1, "27-10-2025");
        transactions[104] = new PharmacyTransaction("TXN105", "25", "MED025", 2, "28-10-2025");
        transactions[105] = new PharmacyTransaction("TXN106", "26", "MED026", 1, "29-10-2025");
        transactions[106] = new PharmacyTransaction("TXN107", "27", "MED027", 3, "30-10-2025");
        transactions[107] = new PharmacyTransaction("TXN108", "28", "MED028", 2, "31-10-2025");
        transactions[108] = new PharmacyTransaction("TXN109", "29", "MED029", 1, "01-11-2025");
        transactions[109] = new PharmacyTransaction("TXN110", "30", "MED030", 2, "02-11-2025");
        transactions[110] = new PharmacyTransaction("TXN111", "31", "MED031", 3, "03-11-2025");
        transactions[111] = new PharmacyTransaction("TXN112", "32", "MED032", 1, "04-11-2025");
        transactions[112] = new PharmacyTransaction("TXN113", "33", "MED033", 2, "05-11-2025");
        transactions[113] = new PharmacyTransaction("TXN114", "34", "MED034", 1, "06-11-2025");
        transactions[114] = new PharmacyTransaction("TXN115", "35", "MED035", 3, "07-11-2025");
        transactions[115] = new PharmacyTransaction("TXN116", "36", "MED036", 2, "08-11-2025");
        transactions[116] = new PharmacyTransaction("TXN117", "37", "MED037", 1, "09-11-2025");
        transactions[117] = new PharmacyTransaction("TXN118", "38", "MED038", 2, "10-11-2025");
        transactions[118] = new PharmacyTransaction("TXN119", "39", "MED039", 3, "11-11-2025");
        transactions[119] = new PharmacyTransaction("TXN120", "40", "MED040", 1, "12-11-2025");
        transactions[120] = new PharmacyTransaction("TXN121", "1", "MED001", 2, "13-11-2025");
        transactions[121] = new PharmacyTransaction("TXN122", "2", "MED002", 1, "14-11-2025");
        transactions[122] = new PharmacyTransaction("TXN123", "3", "MED003", 3, "15-11-2025");
        transactions[123] = new PharmacyTransaction("TXN124", "4", "MED004", 2, "16-11-2025");
        transactions[124] = new PharmacyTransaction("TXN125", "5", "MED005", 1, "17-11-2025");
        transactions[125] = new PharmacyTransaction("TXN126", "6", "MED006", 2, "18-11-2025");
        transactions[126] = new PharmacyTransaction("TXN127", "7", "MED007", 3, "19-11-2025");
        transactions[127] = new PharmacyTransaction("TXN128", "8", "MED008", 1, "20-11-2025");
        transactions[128] = new PharmacyTransaction("TXN129", "9", "MED009", 2, "21-11-2025");
        transactions[129] = new PharmacyTransaction("TXN130", "10", "MED010", 1, "22-11-2025");
        transactions[130] = new PharmacyTransaction("TXN131", "11", "MED011", 3, "23-11-2025");
        transactions[131] = new PharmacyTransaction("TXN132", "12", "MED012", 2, "24-11-2025");
        transactions[132] = new PharmacyTransaction("TXN133", "13", "MED013", 1, "25-11-2025");
        transactions[133] = new PharmacyTransaction("TXN134", "14", "MED014", 2, "26-11-2025");
        transactions[134] = new PharmacyTransaction("TXN135", "15", "MED015", 3, "27-11-2025");
        transactions[135] = new PharmacyTransaction("TXN136", "16", "MED016", 1, "28-11-2025");
        transactions[136] = new PharmacyTransaction("TXN137", "17", "MED017", 2, "29-11-2025");
        transactions[137] = new PharmacyTransaction("TXN138", "18", "MED018", 1, "30-11-2025");
        transactions[138] = new PharmacyTransaction("TXN139", "19", "MED019", 3, "01-12-2025");
        transactions[139] = new PharmacyTransaction("TXN140", "20", "MED020", 2, "02-12-2025");
        transactions[140] = new PharmacyTransaction("TXN141", "21", "MED021", 1, "03-12-2025");
        transactions[141] = new PharmacyTransaction("TXN142", "22", "MED022", 2, "04-12-2025");
        transactions[142] = new PharmacyTransaction("TXN143", "23", "MED023", 3, "05-12-2025");
        transactions[143] = new PharmacyTransaction("TXN144", "24", "MED024", 1, "06-12-2025");
        transactions[144] = new PharmacyTransaction("TXN145", "25", "MED025", 2, "07-12-2025");
        transactions[145] = new PharmacyTransaction("TXN146", "26", "MED026", 1, "08-12-2025");
        transactions[146] = new PharmacyTransaction("TXN147", "27", "MED027", 3, "09-12-2025");
        transactions[147] = new PharmacyTransaction("TXN148", "28", "MED028", 2, "10-12-2025");
        transactions[148] = new PharmacyTransaction("TXN149", "29", "MED029", 1, "11-12-2025");
        transactions[149] = new PharmacyTransaction("TXN150", "30", "MED030", 2, "12-12-2025");
        transactions[150] = new PharmacyTransaction("TXN151", "31", "MED031", 3, "13-12-2025");
        transactions[151] = new PharmacyTransaction("TXN152", "32", "MED032", 1, "14-12-2025");
        transactions[152] = new PharmacyTransaction("TXN153", "33", "MED033", 2, "15-12-2025");
        transactions[153] = new PharmacyTransaction("TXN154", "34", "MED034", 1, "16-12-2025");
        transactions[154] = new PharmacyTransaction("TXN155", "35", "MED035", 3, "17-12-2025");
        transactions[155] = new PharmacyTransaction("TXN156", "36", "MED036", 2, "18-12-2025");
        transactions[156] = new PharmacyTransaction("TXN157", "37", "MED037", 1, "19-12-2025");
        transactions[157] = new PharmacyTransaction("TXN158", "38", "MED038", 2, "20-12-2025");
        transactions[158] = new PharmacyTransaction("TXN159", "39", "MED039", 3, "21-12-2025");
        transactions[159] = new PharmacyTransaction("TXN160", "40", "MED040", 1, "22-12-2025");
        transactions[160] = new PharmacyTransaction("TXN161", "1", "MED001", 2, "23-12-2025");
        transactions[161] = new PharmacyTransaction("TXN162", "2", "MED002", 1, "24-12-2025");
        transactions[162] = new PharmacyTransaction("TXN163", "3", "MED003", 3, "25-12-2025");
        transactions[163] = new PharmacyTransaction("TXN164", "4", "MED004", 2, "26-12-2025");
        transactions[164] = new PharmacyTransaction("TXN165", "5", "MED005", 1, "27-12-2025");
        transactions[165] = new PharmacyTransaction("TXN166", "6", "MED006", 2, "28-12-2025");
        transactions[166] = new PharmacyTransaction("TXN167", "7", "MED007", 3, "29-12-2025");
        transactions[167] = new PharmacyTransaction("TXN168", "8", "MED008", 1, "30-12-2025");
        transactions[168] = new PharmacyTransaction("TXN169", "9", "MED009", 2, "31-12-2025");
        transactions[169] = new PharmacyTransaction("TXN170", "10", "MED010", 1, "01-01-2026");
        transactions[170] = new PharmacyTransaction("TXN171", "11", "MED011", 3, "02-01-2026");
        transactions[171] = new PharmacyTransaction("TXN172", "12", "MED012", 2, "03-01-2026");
        transactions[172] = new PharmacyTransaction("TXN173", "13", "MED013", 1, "04-01-2026");
        transactions[173] = new PharmacyTransaction("TXN174", "14", "MED014", 2, "05-01-2026");
        transactions[174] = new PharmacyTransaction("TXN175", "15", "MED015", 3, "06-01-2026");
        transactions[175] = new PharmacyTransaction("TXN176", "16", "MED016", 1, "07-01-2026");
        transactions[176] = new PharmacyTransaction("TXN177", "17", "MED017", 2, "08-01-2026");
        transactions[177] = new PharmacyTransaction("TXN178", "18", "MED018", 1, "09-01-2026");
        transactions[178] = new PharmacyTransaction("TXN179", "19", "MED019", 3, "10-01-2026");
        transactions[179] = new PharmacyTransaction("TXN180", "20", "MED020", 2, "11-01-2026");
        transactions[180] = new PharmacyTransaction("TXN181", "21", "MED021", 1, "12-01-2026");
        transactions[181] = new PharmacyTransaction("TXN182", "22", "MED022", 2, "13-01-2026");
        transactions[182] = new PharmacyTransaction("TXN183", "23", "MED023", 3, "14-01-2026");
        transactions[183] = new PharmacyTransaction("TXN184", "24", "MED024", 1, "15-01-2026");
        transactions[184] = new PharmacyTransaction("TXN185", "25", "MED025", 2, "16-01-2026");
        transactions[185] = new PharmacyTransaction("TXN186", "26", "MED026", 1, "17-01-2026");
        transactions[186] = new PharmacyTransaction("TXN187", "27", "MED027", 3, "18-01-2026");
        transactions[187] = new PharmacyTransaction("TXN188", "28", "MED028", 2, "19-01-2026");
        transactions[188] = new PharmacyTransaction("TXN189", "29", "MED029", 1, "20-01-2026");
        transactions[189] = new PharmacyTransaction("TXN190", "30", "MED030", 2, "21-01-2026");
        transactions[190] = new PharmacyTransaction("TXN191", "31", "MED031", 3, "22-01-2026");
        transactions[191] = new PharmacyTransaction("TXN192", "32", "MED032", 1, "23-01-2026");
        transactions[192] = new PharmacyTransaction("TXN193", "33", "MED033", 2, "24-01-2026");
        transactions[193] = new PharmacyTransaction("TXN194", "34", "MED034", 1, "25-01-2026");
        transactions[194] = new PharmacyTransaction("TXN195", "35", "MED035", 3, "26-01-2026");
        transactions[195] = new PharmacyTransaction("TXN196", "36", "MED036", 2, "27-01-2026");
        transactions[196] = new PharmacyTransaction("TXN197", "37", "MED037", 1, "28-01-2026");
        transactions[197] = new PharmacyTransaction("TXN198", "38", "MED038", 2, "29-01-2026");
        transactions[198] = new PharmacyTransaction("TXN199", "39", "MED039", 3, "30-01-2026");
        transactions[199] = new PharmacyTransaction("TXN200", "40", "MED040", 1, "31-01-2026");
        transactions[200] = new PharmacyTransaction("TXN201", "1", "MED001", 2, "01-02-2026");
        transactions[201] = new PharmacyTransaction("TXN202", "2", "MED002", 1, "02-02-2026");
        transactions[202] = new PharmacyTransaction("TXN203", "3", "MED003", 3, "03-02-2026");
        transactions[203] = new PharmacyTransaction("TXN204", "4", "MED004", 2, "04-02-2026");
        transactions[204] = new PharmacyTransaction("TXN205", "5", "MED005", 1, "05-02-2026");
        transactions[205] = new PharmacyTransaction("TXN206", "6", "MED006", 2, "06-02-2026");
        transactions[206] = new PharmacyTransaction("TXN207", "7", "MED007", 3, "07-02-2026");
        transactions[207] = new PharmacyTransaction("TXN208", "8", "MED008", 1, "08-02-2026");
        transactions[208] = new PharmacyTransaction("TXN209", "9", "MED009", 2, "09-02-2026");
        transactions[209] = new PharmacyTransaction("TXN210", "10", "MED010", 1, "10-02-2026");
        transactions[210] = new PharmacyTransaction("TXN211", "11", "MED011", 3, "11-02-2026");
        transactions[211] = new PharmacyTransaction("TXN212", "12", "MED012", 2, "12-02-2026");
        transactions[212] = new PharmacyTransaction("TXN213", "13", "MED013", 1, "13-02-2026");
        transactions[213] = new PharmacyTransaction("TXN214", "14", "MED014", 2, "14-02-2026");
        transactions[214] = new PharmacyTransaction("TXN215", "15", "MED015", 3, "15-02-2026");
        transactions[215] = new PharmacyTransaction("TXN216", "16", "MED016", 1, "16-02-2026");
        transactions[216] = new PharmacyTransaction("TXN217", "17", "MED017", 2, "17-02-2026");
        transactions[217] = new PharmacyTransaction("TXN218", "18", "MED018", 1, "18-02-2026");
        transactions[218] = new PharmacyTransaction("TXN219", "19", "MED019", 3, "19-02-2026");
        transactions[219] = new PharmacyTransaction("TXN220", "20", "MED020", 2, "20-02-2026");

        return transactions;
    }

    public static Prescription[] initializeSamplePrescriptions() {
        Prescription[] prescriptions = new Prescription[210];

        SetAndQueueInterface<PrescribedMedicine> pm1 = new SetQueueArray<>();
        pm1.add(new PrescribedMedicine("PM001", "PRE001", "MED007", "Amlodipine", 30, "1 tablet daily", "Take in the morning", 32.80, 984.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm2 = new SetQueueArray<>();
        pm2.add(new PrescribedMedicine("PM002", "PRE002", "MED003", "Ibuprofen", 20, "1 tablet every 6 hours", "Take with food", 12.90, 258.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm3 = new SetQueueArray<>();
        pm3.add(new PrescribedMedicine("PM003", "PRE003", "MED006", "Metformin", 60, "1 tablet twice daily", "Take with meals", 22.40, 1344.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm4 = new SetQueueArray<>();
        pm4.add(new PrescribedMedicine("PM004", "PRE004", "MED009", "Sertraline", 30, "1 tablet daily", "Take in the morning", 45.60, 1368.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm5 = new SetQueueArray<>();
        pm5.add(new PrescribedMedicine("PM005", "PRE005", "MED004", "Omeprazole", 30, "1 tablet daily", "Take before breakfast", 25.60, 768.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm6 = new SetQueueArray<>();
        pm6.add(new PrescribedMedicine("PM006", "PRE006", "MED008", "Salbutamol", 1, "2 puffs as needed", "Use during asthma attacks", 28.90, 28.90, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm7 = new SetQueueArray<>();
        pm7.add(new PrescribedMedicine("PM007", "PRE007", "MED009", "Sertraline", 60, "1 tablet daily", "Take in the morning", 45.60, 2736.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm8 = new SetQueueArray<>();
        pm8.add(new PrescribedMedicine("PM008", "PRE008", "MED010", "Atorvastatin", 30, "1 tablet daily", "Take in the evening", 58.90, 1767.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm9 = new SetQueueArray<>();
        pm9.add(new PrescribedMedicine("PM009", "PRE009", "MED013", "Loratadine", 30, "1 tablet daily", "Take as needed for allergies", 16.80, 504.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm10 = new SetQueueArray<>();
        pm10.add(new PrescribedMedicine("PM010", "PRE010", "MED014", "Lisinopril", 30, "1 tablet daily", "Take in the morning", 28.40, 852.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm11 = new SetQueueArray<>();
        pm11.add(new PrescribedMedicine("PM011", "PRE011", "MED015", "Fluoxetine", 30, "1 tablet daily", "Take in the morning", 42.30, 1269.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm12 = new SetQueueArray<>();
        pm12.add(new PrescribedMedicine("PM012", "PRE012", "MED016", "Simvastatin", 30, "1 tablet daily", "Take in the evening", 35.70, 1071.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm13 = new SetQueueArray<>();
        pm13.add(new PrescribedMedicine("PM013", "PRE013", "MED017", "Montelukast", 30, "1 tablet daily", "Take in the evening", 38.90, 1167.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm14 = new SetQueueArray<>();
        pm14.add(new PrescribedMedicine("PM014", "PRE014", "MED018", "Pantoprazole", 30, "1 tablet daily", "Take before breakfast", 31.20, 936.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm15 = new SetQueueArray<>();
        pm15.add(new PrescribedMedicine("PM015", "PRE015", "MED019", "Duloxetine", 30, "1 tablet daily", "Take in the morning", 67.80, 2034.00, false));
        
        SetAndQueueInterface<PrescribedMedicine> pm16 = new SetQueueArray<>();
        pm16.add(new PrescribedMedicine("PM016", "PRE016", "MED020", "Losartan", 30, "1 tablet daily", "Take in the morning", 29.60, 888.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm17 = new SetQueueArray<>();
        pm17.add(new PrescribedMedicine("PM017", "PRE017", "MED001", "Paracetamol", 20, "1-2 tablets every 4-6 hours", "Take as needed for pain", 8.50, 170.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm18 = new SetQueueArray<>();
        pm18.add(new PrescribedMedicine("PM018", "PRE018", "MED002", "Amoxicillin", 21, "1 capsule three times daily", "Take with food", 15.80, 331.80, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm19 = new SetQueueArray<>();
        pm19.add(new PrescribedMedicine("PM019", "PRE019", "MED005", "Cetirizine", 30, "1 tablet daily", "Take in the evening", 18.20, 546.00, true));
        
        SetAndQueueInterface<PrescribedMedicine> pm20 = new SetQueueArray<>();
        pm20.add(new PrescribedMedicine("PM020", "PRE020", "MED011", "Aspirin", 30, "1 tablet daily", "Take with food", 6.50, 195.00, false));

        SetAndQueueInterface<PrescribedMedicine> pm21 = new SetQueueArray<>();
        pm21.add(new PrescribedMedicine("PM021", "PRE021", "MED022", "Cephalexin", 14, "1 capsule four times daily", "Take with food", 18.90, 264.60, true));

        SetAndQueueInterface<PrescribedMedicine> pm22 = new SetQueueArray<>();
        pm22.add(new PrescribedMedicine("PM022", "PRE022", "MED023", "Diclofenac", 30, "1 tablet twice daily", "Take with food", 14.70, 441.00, false));

        SetAndQueueInterface<PrescribedMedicine> pm23 = new SetQueueArray<>();
        pm23.add(new PrescribedMedicine("PM023", "PRE023", "MED024", "Ranitidine", 30, "1 tablet twice daily", "Take before meals", 19.80, 594.00, true));

        SetAndQueueInterface<PrescribedMedicine> pm24 = new SetQueueArray<>();
        pm24.add(new PrescribedMedicine("PM024", "PRE024", "MED025", "Fexofenadine", 30, "1 tablet daily", "Take in the morning", 21.50, 645.00, true));

        SetAndQueueInterface<PrescribedMedicine> pm25 = new SetQueueArray<>();
        pm25.add(new PrescribedMedicine("PM025", "PRE025", "MED026", "Glipizide", 30, "1 tablet twice daily", "Take with meals", 26.80, 804.00, false));

        SetAndQueueInterface<PrescribedMedicine> pm26 = new SetQueueArray<>();
        pm26.add(new PrescribedMedicine("PM026", "PRE026", "MED027", "Nifedipine", 30, "1 tablet daily", "Take in the morning", 35.40, 1062.00, true));

        SetAndQueueInterface<PrescribedMedicine> pm27 = new SetQueueArray<>();
        pm27.add(new PrescribedMedicine("PM027", "PRE027", "MED028", "Budesonide", 1, "2 puffs twice daily", "Rinse mouth after use", 52.30, 52.30, true));

        SetAndQueueInterface<PrescribedMedicine> pm28 = new SetQueueArray<>();
        pm28.add(new PrescribedMedicine("PM028", "PRE028", "MED029", "Escitalopram", 30, "1 tablet daily", "Take in the morning", 48.90, 1467.00, false));

        SetAndQueueInterface<PrescribedMedicine> pm29 = new SetQueueArray<>();
        pm29.add(new PrescribedMedicine("PM029", "PRE029", "MED030", "Rosuvastatin", 30, "1 tablet daily", "Take in the evening", 62.70, 1881.00, true));

        SetAndQueueInterface<PrescribedMedicine> pm30 = new SetQueueArray<>();
        pm30.add(new PrescribedMedicine("PM030", "PRE030", "MED031", "Naproxen", 20, "1 tablet twice daily", "Take with food", 11.40, 228.00, true));

        SetAndQueueInterface<PrescribedMedicine> pm31 = new SetQueueArray<>();
        pm31.add(new PrescribedMedicine("PM031", "PRE031", "MED032", "Metoprolol", 30, "1 tablet twice daily", "Take with food", 24.60, 738.00, false));

        SetAndQueueInterface<PrescribedMedicine> pm32 = new SetQueueArray<>();
        pm32.add(new PrescribedMedicine("PM032", "PRE032", "MED033", "Prednisone", 10, "1 tablet daily", "Take with food", 15.80, 158.00, true));

        SetAndQueueInterface<PrescribedMedicine> pm33 = new SetQueueArray<>();
        pm33.add(new PrescribedMedicine("PM033", "PRE033", "MED034", "Tramadol", 20, "1 tablet every 6 hours", "Take as needed for pain", 28.70, 574.00, true));

        SetAndQueueInterface<PrescribedMedicine> pm34 = new SetQueueArray<>();
        pm34.add(new PrescribedMedicine("PM034", "PRE034", "MED035", "Warfarin", 30, "1 tablet daily", "Monitor INR regularly", 12.30, 369.00, false));

        SetAndQueueInterface<PrescribedMedicine> pm35 = new SetQueueArray<>();
        pm35.add(new PrescribedMedicine("PM035", "PRE035", "MED036", "Levothyroxine", 30, "1 tablet daily", "Take on empty stomach", 18.90, 567.00, true));
        
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
        prescriptions[20] = new Prescription("PRE021", "CON021", "26", "DOC013", "Bacterial Infection", pm21, "02-08-2025", "active", 264.60, true);
        prescriptions[21] = new Prescription("PRE022", "CON022", "27", "DOC014", "Arthritis", pm22, "03-08-2025", "active", 441.00, false);
        prescriptions[22] = new Prescription("PRE023", "CON023", "28", "DOC015", "Gastritis", pm23, "04-08-2025", "active", 594.00, true);
        prescriptions[23] = new Prescription("PRE024", "CON024", "29", "DOC017", "Allergic Rhinitis", pm24, "05-08-2025", "active", 645.00, true);
        prescriptions[24] = new Prescription("PRE025", "CON025", "30", "DOC018", "Diabetes Type 2", pm25, "06-08-2025", "active", 804.00, false);
        prescriptions[25] = new Prescription("PRE026", "CON026", "31", "DOC019", "Hypertension", pm26, "07-08-2025", "active", 1062.00, true);
        prescriptions[26] = new Prescription("PRE027", "CON027", "32", "DOC001", "Asthma", pm27, "08-08-2025", "active", 52.30, true);
        prescriptions[27] = new Prescription("PRE028", "CON028", "33", "DOC007", "Depression", pm28, "09-08-2025", "active", 1467.00, false);
        prescriptions[28] = new Prescription("PRE029", "CON029", "34", "DOC005", "High Cholesterol", pm29, "10-08-2025", "active", 1881.00, true);
        prescriptions[29] = new Prescription("PRE030", "CON030", "35", "DOC013", "Joint Pain", pm30, "11-08-2025", "active", 228.00, true);
        prescriptions[30] = new Prescription("PRE031", "CON031", "36", "DOC006", "Hypertension", pm31, "12-08-2025", "active", 738.00, false);
        prescriptions[31] = new Prescription("PRE032", "CON032", "37", "DOC002", "Inflammatory Condition", pm32, "13-08-2025", "active", 158.00, true);
        prescriptions[32] = new Prescription("PRE033", "CON033", "38", "DOC008", "Chronic Pain", pm33, "14-08-2025", "active", 574.00, true);
        prescriptions[33] = new Prescription("PRE034", "CON034", "39", "DOC014", "Atrial Fibrillation", pm34, "15-08-2025", "active", 369.00, false);
        prescriptions[34] = new Prescription("PRE035", "CON035", "40", "DOC015", "Hypothyroidism", pm35, "16-08-2025", "active", 567.00, true);
        
        // Additional prescriptions to reach 200+
        for (int i = 35; i < 210; i++) {
            SetAndQueueInterface<PrescribedMedicine> pm = new SetQueueArray<>();
            String prescriptionId = "PRE" + String.format("%03d", i + 1);
            String consultationId = "CON" + String.format("%03d", ((i - 35) % 185) + 36);
            String patientId = String.valueOf(((i - 35) % 40) + 1);
            String doctorId = "DOC" + String.format("%03d", ((i - 35) % 20) + 1);
            String medicineId = "MED" + String.format("%03d", ((i - 35) % 40) + 1);
            String prescribedMedicineId = "PM" + String.format("%03d", i + 1);
            
            // Vary medicine names and dosages
            String[] medicineNames = {"Paracetamol", "Amoxicillin", "Ibuprofen", "Omeprazole", "Cetirizine", 
                                    "Metformin", "Amlodipine", "Salbutamol", "Sertraline", "Atorvastatin"};
            String medicineName = medicineNames[i % medicineNames.length];
            
            int quantity = 20 + (i % 3) * 10; // 20, 30, or 40
            double unitPrice = 10.0 + (i % 10) * 5.0; // Varies from 10.0 to 55.0
            double totalCost = quantity * unitPrice;
            
            String[] dosages = {"1 tablet daily", "2 tablets daily", "1 tablet twice daily", "1 capsule daily", "2 capsules daily"};
            String dosage = dosages[i % dosages.length];
            
            String[] instructions = {"Take with food", "Take on empty stomach", "Take in the morning", "Take in the evening", "Take as needed"};
            String instruction = instructions[i % instructions.length];
            
            boolean isPaid = (i % 3) != 0; // Most prescriptions are paid
            
            pm.add(new PrescribedMedicine(prescribedMedicineId, prescriptionId, medicineId, medicineName, 
                                        quantity, dosage, instruction, unitPrice, totalCost, isPaid));
            
            String[] diagnoses = {"Hypertension", "Diabetes", "Infection", "Pain Management", "Allergy", 
                                "Depression", "High Cholesterol", "Asthma", "Arthritis", "Acid Reflux"};
            String diagnosis = diagnoses[i % diagnoses.length];
            
            String[] statuses = {"active", "completed", "cancelled"};
            String status = statuses[i % 10 < 7 ? 0 : (i % 10 < 9 ? 1 : 2)]; // 70% active, 20% completed, 10% cancelled
            
            // Calculate date (spread over several months)
            int dayOffset = (i - 35) * 2; // 2 days apart
            int day = 17 + (dayOffset % 30); // Start from day 17
            int month = 8 + (dayOffset / 30); // Start from August
            int year = 2025;
            
            if (month > 12) {
                month = month - 12;
                year = 2026;
            }
            if (day > 30) day = 30;
            
            String date = String.format("%02d-%02d-%d", day, month, year);
            
            prescriptions[i] = new Prescription(prescriptionId, consultationId, patientId, doctorId, 
                                              diagnosis, pm, date, status, totalCost, isPaid);
        }

        return prescriptions;
    }
} 