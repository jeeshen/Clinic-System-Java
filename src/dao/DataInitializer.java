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
        medicines[20] = new Medicine("MED021", "Paracetamol", "Panadol Active", 5, "31-12-2025", 8.50, "Pain Relief", "Paracetamol", "Analgesic");
        medicines[21] = new Medicine("MED022", "Cephalexin", "Keflex", 35, "15-04-2026", 18.90, "Antibiotic", "Cephalexin", "Antibiotic");
        medicines[22] = new Medicine("MED023", "Diclofenac", "Voltaren", 42, "20-05-2026", 14.70, "Pain Relief", "Diclofenac", "NSAID");
        medicines[23] = new Medicine("MED024", "Ranitidine", "Zantac", 38, "25-06-2026", 19.80, "Acid Reflux", "Ranitidine", "H2 Receptor Antagonist");
        medicines[24] = new Medicine("MED025", "Fexofenadine", "Allegra", 28, "30-07-2026", 21.50, "Allergy Relief", "Fexofenadine", "Antihistamine");
        medicines[25] = new Medicine("MED026", "Glipizide", "Glucotrol", 33, "10-08-2026", 26.80, "Diabetes Management", "Glipizide", "Sulfonylurea");
        medicines[26] = new Medicine("MED027", "Nifedipine", "Adalat", 29, "15-09-2026", 35.40, "Hypertension", "Nifedipine", "Calcium Channel Blocker");
        medicines[27] = new Medicine("MED028", "Budesonide", "Pulmicort", 24, "20-10-2026", 52.30, "Asthma Prevention", "Budesonide", "Corticosteroid");
        medicines[28] = new Medicine("MED029", "Escitalopram", "Lexapro", 18, "25-11-2026", 48.90, "Depression Treatment", "Escitalopram", "SSRI");
        medicines[29] = new Medicine("MED030", "Rosuvastatin", "Crestor", 31, "30-12-2026", 62.70, "Cholesterol Management", "Rosuvastatin", "Statin");
        medicines[30] = new Medicine("MED031", "Naproxen", "Aleve", 26, "15-01-2027", 11.40, "Pain Relief", "Naproxen", "NSAID");
        medicines[31] = new Medicine("MED032", "Metoprolol", "Lopressor", 37, "20-02-2027", 24.60, "Hypertension", "Metoprolol", "Beta Blocker");
        medicines[32] = new Medicine("MED033", "Prednisone", "Deltasone", 22, "25-03-2027", 15.80, "Anti-inflammatory", "Prednisone", "Corticosteroid");
        medicines[33] = new Medicine("MED034", "Tramadol", "Ultram", 19, "30-04-2027", 28.70, "Pain Relief", "Tramadol", "Opioid Analgesic");
        medicines[34] = new Medicine("MED035", "Warfarin", "Coumadin", 16, "15-05-2027", 12.30, "Blood Thinner", "Warfarin", "Anticoagulant");
        medicines[35] = new Medicine("MED036", "Levothyroxine", "Synthroid", 41, "20-06-2027", 18.90, "Thyroid Hormone", "Levothyroxine", "Hormone");
        medicines[36] = new Medicine("MED037", "Hydrochlorothiazide", "Microzide", 34, "25-07-2027", 16.50, "Diuretic", "Hydrochlorothiazide", "Thiazide Diuretic");
        medicines[37] = new Medicine("MED038", "Alprazolam", "Xanax", 13, "30-08-2027", 22.80, "Anxiety Treatment", "Alprazolam", "Benzodiazepine");
        medicines[38] = new Medicine("MED039", "Gabapentin", "Neurontin", 27, "15-09-2027", 31.40, "Nerve Pain", "Gabapentin", "Anticonvulsant");
        medicines[39] = new Medicine("MED040", "Clonazepam", "Klonopin", 15, "20-10-2027", 19.60, "Seizure Control", "Clonazepam", "Benzodiazepine");

        return medicines;
    }
    
    public static Patient[] initializeSamplePatients() {
        Patient[] patients = new Patient[40];

        patients[0] = new Patient(1, "Ahmad bin Abdullah", 35, "Male", "Penicillin", "012-3456789", "123 Jalan Tunku Abdul Rahman, Kuala Lumpur", "01-07-2025", "Hypertension", "Active");
        patients[1] = new Patient(2, "Siti binti Mohamed", 28, "Female", "Paracetamol", "012-3456790", "456 Jalan Sultan Ismail, Petaling Jaya", "02-07-2025", "Diabetes", "Active");
        patients[2] = new Patient(3, "Raj a/l Kumar", 45, "Male", "Sulfa", "012-3456791", "789 Jalan Bukit Bintang, Kuala Lumpur", "03-07-2025", "Asthma", "Active");
        patients[3] = new Patient(4, "Lim Siew Mei", 32, "Female", "None", "012-3456792", "321 Jalan Ampang, Kuala Lumpur", "04-07-2025", "None", "Active");
        patients[4] = new Patient(5, "Tan Ah Kow", 50, "Male", "Codeine", "012-3456793", "654 Jalan Pudu, Kuala Lumpur", "05-07-2025", "Heart Disease", "Active");
        patients[5] = new Patient(6, "Nurul Huda binti Ismail", 26, "Female", "Latex", "012-3456794", "987 Jalan Cheras, Kuala Lumpur", "06-07-2025", "Migraine", "Active");
        patients[6] = new Patient(7, "Krishnan a/l Muthu", 38, "Male", "None", "012-3456795", "147 Jalan Klang Lama, Kuala Lumpur", "07-07-2025", "Depression", "Active");
        patients[7] = new Patient(8, "Wong Mei Ling", 42, "Female", "Iodine", "012-3456796", "258 Jalan Ipoh, Kuala Lumpur", "08-07-2025", "Thyroid Disorder", "Active");
        patients[8] = new Patient(9, "Mohamed Ali bin Hassan", 29, "Male", "None", "012-3456797", "369 Jalan Gombak, Kuala Lumpur", "09-07-2025", "Anxiety", "Active");
        patients[9] = new Patient(10, "Cheah Siew Fong", 33, "Female", "Shellfish", "012-3456798", "741 Jalan Damansara, Petaling Jaya", "10-07-2025", "None", "Active");
        patients[10] = new Patient(11, "Arun a/l Subramaniam", 47, "Male", "None", "012-3456799", "852 Jalan Bangsar, Kuala Lumpur", "11-07-2025", "High Cholesterol", "Active");
        patients[11] = new Patient(12, "Fatimah binti Omar", 31, "Female", "Peanuts", "012-3456800", "963 Jalan TAR, Kuala Lumpur", "12-07-2025", "None", "Active");
        patients[12] = new Patient(13, "Lee Chong Wei", 39, "Male", "None", "012-3456801", "159 Jalan Imbi, Kuala Lumpur", "13-07-2025", "Sleep Apnea", "Active");
        patients[13] = new Patient(14, "Aisha binti Yusof", 25, "Female", "None", "012-3456802", "357 Jalan Raja Chulan, Kuala Lumpur", "14-07-2025", "None", "Active");
        patients[14] = new Patient(15, "Gan Eng Seng", 44, "Male", "Aspirin", "012-3456803", "486 Jalan Tuanku Abdul Rahman, Kuala Lumpur", "15-07-2025", "Gout", "Active");
        patients[15] = new Patient(16, "Zainab binti Ahmad", 36, "Female", "None", "012-3456804", "753 Jalan Raja Laut, Kuala Lumpur", "16-07-2025", "Hypertension", "Active");
        patients[16] = new Patient(17, "Kumar a/l Rajendran", 41, "Male", "Sulfa", "012-3456805", "951 Jalan Sultan, Kuala Lumpur", "17-07-2025", "Diabetes", "Active");
        patients[17] = new Patient(18, "Chan Mei Lin", 27, "Female", "Latex", "012-3456806", "357 Jalan Pahang, Kuala Lumpur", "18-07-2025", "Asthma", "Active");
        patients[18] = new Patient(19, "Ismail bin Omar", 52, "Male", "None", "012-3456807", "159 Jalan Masjid India, Kuala Lumpur", "19-07-2025", "Heart Disease", "Active");
        patients[19] = new Patient(20, "Priya a/p Ramasamy", 34, "Female", "Penicillin", "012-3456808", "753 Jalan Petaling, Kuala Lumpur", "20-07-2025", "Migraine", "Active");
        patients[20] = new Patient(21, "Ong Teck Seng", 48, "Male", "None", "012-3456809", "951 Jalan Chow Kit, Kuala Lumpur", "21-07-2025", "Depression", "Active");
        patients[21] = new Patient(22, "Noraini binti Zainal", 30, "Female", "Iodine", "012-3456810", "357 Jalan Tun Perak, Kuala Lumpur", "22-07-2025", "Thyroid Disorder", "Active");
        patients[22] = new Patient(23, "Muthu a/l Velu", 37, "Male", "None", "012-3456811", "159 Jalan Dang Wangi, Kuala Lumpur", "23-07-2025", "Anxiety", "Active");
        patients[23] = new Patient(24, "Lau Siew Mei", 29, "Female", "Shellfish", "012-3456812", "753 Jalan Tun Razak, Kuala Lumpur", "24-07-2025", "None", "Active");
        patients[24] = new Patient(25, "Hassan bin Ali", 46, "Male", "None", "012-3456813", "951 Jalan Ampang, Kuala Lumpur", "25-07-2025", "High Cholesterol", "Active");
        patients[25] = new Patient(26, "Aminah binti Rashid", 33, "Female", "Aspirin", "012-3456814", "123 Jalan Sentul, Kuala Lumpur", "26-07-2025", "Arthritis", "Active");
        patients[26] = new Patient(27, "Ravi a/l Shankar", 41, "Male", "None", "012-3456815", "456 Jalan Kepong, Kuala Lumpur", "27-07-2025", "Hypertension", "Active");
        patients[27] = new Patient(28, "Lily Tan Mei Hua", 24, "Female", "Shellfish", "012-3456816", "789 Jalan Setapak, Kuala Lumpur", "28-07-2025", "Allergic Rhinitis", "Active");
        patients[28] = new Patient(29, "Azman bin Yusof", 55, "Male", "Penicillin", "012-3456817", "321 Jalan Wangsa Maju, Kuala Lumpur", "29-07-2025", "Diabetes", "Active");
        patients[29] = new Patient(30, "Grace Lim Soo Cheng", 38, "Female", "None", "012-3456818", "654 Jalan Segambut, Kuala Lumpur", "30-07-2025", "Migraine", "Active");
        patients[30] = new Patient(31, "Suresh a/l Krishnan", 43, "Male", "Iodine", "012-3456819", "987 Jalan Batu Caves, Selangor", "31-07-2025", "Thyroid Disorder", "Active");
        patients[31] = new Patient(32, "Farah binti Kamal", 27, "Female", "Latex", "012-3456820", "147 Jalan Rawang, Selangor", "01-08-2025", "Asthma", "Active");
        patients[32] = new Patient(33, "Danny Ng Wei Ming", 49, "Male", "None", "012-3456821", "258 Jalan Kajang, Selangor", "02-08-2025", "Sleep Apnea", "Active");
        patients[33] = new Patient(34, "Khadijah binti Hassan", 31, "Female", "Sulfa", "012-3456822", "369 Jalan Selayang, Selangor", "03-08-2025", "Depression", "Active");
        patients[34] = new Patient(35, "Vincent Loh Chee Keong", 36, "Male", "None", "012-3456823", "741 Jalan Subang, Selangor", "04-08-2025", "High Cholesterol", "Active");
        patients[35] = new Patient(36, "Rohani binti Ibrahim", 42, "Female", "Peanuts", "012-3456824", "852 Jalan Shah Alam, Selangor", "05-08-2025", "Heart Disease", "Active");
        patients[36] = new Patient(37, "Prakash a/l Devi", 28, "Male", "None", "012-3456825", "963 Jalan Klang, Selangor", "06-08-2025", "Anxiety", "Active");
        patients[37] = new Patient(38, "Michelle Wong Ai Ling", 35, "Female", "Codeine", "012-3456826", "159 Jalan Puchong, Selangor", "07-08-2025", "Chronic Pain", "Active");
        patients[38] = new Patient(39, "Hafiz bin Rahman", 40, "Male", "None", "012-3456827", "357 Jalan Cyberjaya, Selangor", "08-08-2025", "Diabetes", "Active");
        patients[39] = new Patient(40, "Stephanie Tan Li Ying", 26, "Female", "Shellfish", "012-3456828", "753 Jalan Putrajaya, Putrajaya", "09-08-2025", "Allergic Dermatitis", "Active");

        return patients;
    }

    public static Doctor[] initializeSampleDoctors() {
        Doctor[] doctors = new Doctor[20];

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
        doctors[12] = new Doctor("DOC013", "Dr. Rachel Lim Hui Ying", "Internal Medicine", "012-3456792", "rachel.lim@chubbyclinic.com.my", true, "Mon-Fri 9AM-5PM", false, "", "");
        doctors[13] = new Doctor("DOC014", "Dr. Kevin Tan Wei Jie", "Gastroenterology", "012-3456793", "kevin.tan@chubbyclinic.com.my", true, "Tue-Sat 8AM-4PM", false, "", "");
        doctors[14] = new Doctor("DOC015", "Dr. Priya Sharma a/p Ravi", "Rheumatology", "012-3456794", "priya.sharma@chubbyclinic.com.my", true, "Wed-Fri 10AM-6PM", false, "", "");
        doctors[15] = new Doctor("DOC016", "Dr. Ahmad Farid bin Hassan", "Pulmonology", "012-3456795", "ahmad.farid@chubbyclinic.com.my", false, "Mon-Thu 9AM-5PM", true, "10-08-2025", "15-08-2025");
        doctors[16] = new Doctor("DOC017", "Dr. Catherine Ng Siew Lan", "Nephrology", "012-3456796", "catherine.ng@chubbyclinic.com.my", true, "Mon-Fri 8AM-4PM", false, "", "");
        doctors[17] = new Doctor("DOC018", "Dr. Rajesh Kumar a/l Devi", "Hematology", "012-3456797", "rajesh.kumar@chubbyclinic.com.my", true, "Tue-Thu 9AM-5PM", false, "", "");
        doctors[18] = new Doctor("DOC019", "Dr. Melissa Chan Ai Ling", "Infectious Disease", "012-3456798", "melissa.chan@chubbyclinic.com.my", true, "Wed-Sat 10AM-6PM", false, "", "");
        doctors[19] = new Doctor("DOC020", "Dr. Hafiz Ismail bin Omar", "Anesthesiology", "012-3456799", "hafiz.ismail@chubbyclinic.com.my", true, "24/7 On-Call", false, "", "");

        return doctors;
    }

    public static Consultation[] initializeSampleConsultations() {
        Consultation[] consultations = new Consultation[35];

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

        return consultations;
    }

    public static Treatment[] initializeSampleTreatments() {
        Treatment[] treatments = new Treatment[30];

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

        return treatments;
    }

    public static PharmacyTransaction[] initializeSampleTransactions() {
        PharmacyTransaction[] transactions = new PharmacyTransaction[40];

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

        return transactions;
    }

    public static Prescription[] initializeSamplePrescriptions() {
        Prescription[] prescriptions = new Prescription[35];

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

        return prescriptions;
    }
} 