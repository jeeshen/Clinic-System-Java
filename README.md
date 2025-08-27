Clinic Management System (Java, console-based)

Overview
This project is a console-based clinic management system built in Java. It demonstrates custom set and queue ADTs, basic CRUD flows, and simple reporting across consultations, treatments, prescriptions, pharmacy inventory, and patients/doctors.

Key modules
- ADT: SetAndQueueInterface, SetQueueArray
- Entities: Patient, Doctor, Consultation, Treatment, Prescription, PrescribedMedicine, Medicine, PharmacyTransaction
- DAO: DataInitializer hardcodes all sample data (220 treatments, 220 prescriptions, full medicine list, transactions)
- Control: PatientManagement, DoctorManagement, ConsultationManagement, TreatmentManagement, PharmacyManagement
- Utility: Input validation and string helpers