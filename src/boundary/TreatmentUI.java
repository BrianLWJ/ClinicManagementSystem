package boundary;
/**
 *
 * @author User
 */

import adt.QueueInterface;
import control.TreatmentControl;
import control.ConsultationControl;
import control.PatientControl;
import control.DoctorControl;
import control.MedicineControl;
import entity.Treatment;
import entity.TreatmentRecord;
import entity.Consultation;
import entity.Patient;
import entity.Doctor;
import entity.Medicine;
import entity.TreatmentMedicine;
import java.util.Scanner;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class TreatmentUI {
    
    private TreatmentControl control;
    private PatientControl patientControl;
    private DoctorControl doctorControl;
    private ConsultationControl consultationControl;
    private MedicineControl medicineControl;

    private final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    
    public TreatmentUI(TreatmentControl control) throws ParseException{
        this.control = control;
        this.patientControl = control.getPatientControl();
        this.doctorControl = control.getDoctorControl();
        this.consultationControl = control.getConsultationControl();
        this.medicineControl = control.getMedicineControl();
        addSampleTreatments();
    }
    
    private Scanner scanner = new Scanner(System.in);
    
    private void addSampleTreatments() throws ParseException {
        // Example Medicines (treatment-only)
        medicineControl.addMedicine(new Medicine("M004", "Paracetamol", "Tablet", 100, 0.30,
                date.parse("2026-01-01"), 20, 0, null));
        medicineControl.addMedicine(new Medicine("M005", "Vitamin C", "Tablet", 80, 0.25,
                  date.parse("2025-12-31"), 15, 0, null));
        medicineControl.addMedicine(new Medicine("M006", "Amoxicillin", "Capsule", 60, 0.45,
                date.parse("2026-07-15"), 10, 0, null));
        medicineControl.addMedicine(new Medicine("M007", "Ibuprofen", "Tablet", 90, 0.35,
                date.parse("2026-09-30"), 15, 0, null));
        medicineControl.addMedicine(new Medicine("M008", "Cough Syrup", "Syrup", 40, 1.50,
                date.parse("2026-03-10"), 10, 0, null));

        // Example Treatment 1: Cold & Flu Treatment
        Treatment coldFlu = new Treatment("T001", "Cold & Flu Treatment", "Relieve cold and flu symptoms");
        coldFlu.addTreatmentMedicine(new TreatmentMedicine(medicineControl.findMedicineById("M004"), 2, "Take 2 tablets every 6 hours"));
        coldFlu.addTreatmentMedicine(new TreatmentMedicine(medicineControl.findMedicineById("M005"), 1, "Take 1 tablet daily after meal"));

        // Example Treatment 2: Bacterial Infection Treatment
        Treatment bacterialInfection = new Treatment("T002", "Bacterial Infection", "Antibiotic course for infection");
        bacterialInfection.addTreatmentMedicine(new TreatmentMedicine(medicineControl.findMedicineById("M006"), 1, "Take 1 capsule every 8 hours for 7 days"));

        // Example Treatment 3: Pain & Inflammation Treatment
        Treatment painRelief = new Treatment("T003", "Pain & Inflammation", "Reduce pain and inflammation");
        painRelief.addTreatmentMedicine(new TreatmentMedicine(medicineControl.findMedicineById("M007"), 1, "Take 1 tablet every 12 hours"));
        painRelief.addTreatmentMedicine(new TreatmentMedicine(medicineControl.findMedicineById("M008"), 10, "Take 10ml of syrup 3 times a day"));

        // Add all treatments to the system
        control.addTreatment(coldFlu);
        control.addTreatment(bacterialInfection);
        control.addTreatment(painRelief);
    }
    
    public void run() {
        int choice;
        do {
            System.out.println("\n=== Treatment Management Menu ===");
            System.out.println("1. Add Treatment");
            System.out.println("2. Remove Treatment");
            System.out.println("3. List Treatments");
            System.out.println("4. Update Treatment");
            System.out.println("5. Search Treatment");
            System.out.println("6. Add Treatment Record");
            System.out.println("7. Remove Treatment Record");
            System.out.println("8. List Treatment Records");
            System.out.println("9. Update Treatment Record");
            System.out.println("10.Report : Find Records By Doctor");
            System.out.println("11.Report : Find Records By Patient");
            System.out.println("12.Search Treatment Record");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> addTreatment();
                case 2 -> removeTreatment();
                case 3 -> control.listTreatments();
                case 4 -> updateTreatment();
                case 5 -> searchTreatment();
                case 6 -> addTreatmentRecord();
                case 7 -> removeTreatmentRecord();
                case 8 -> control.listTreatmentRecords();
                case 9 -> updateTreatmentRecord();
                case 10 -> reportRecordsByDoctor();
                case 11 -> reportRecordsByPatient();
                case 12 -> searchTreatmentRecord();
                case 0 -> System.out.println("Exiting...\n");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // === Treatment ===
    private void addTreatment() {
        String id = control.generateTreatmentID();
        System.out.println("Generated Treatment ID: " + id);

        System.out.print("Enter Treatment Type: ");
        String type = scanner.nextLine();

        System.out.print("Enter Treatment Description: ");
        String desc = scanner.nextLine();

        Treatment treatment = new Treatment(id, type, desc);

        // === Add Medicines ===
        String more;
        do {
            System.out.println("=== Available Medicine ===");
            for (Medicine m : medicineControl.getAllMedicines()) {
                System.out.printf("ID: %s, Name: %-15s, Quantity: %d\n", m.getMedicineID(), m.getName(), m.getStockQty());
            }

            Medicine medicine = null;
            do {
                System.out.print("\nEnter Medicine ID: ");
                String medID = scanner.nextLine();
                medicine = medicineControl.findMedicineById(medID);

                if (medicine == null) {
                    System.out.println("No medicine with ID " + medID + " found. Please try again.");
                }
            } while (medicine == null);

            int qty;
            do {
                System.out.print("Amount of Medicine Needed: ");
                qty = getIntInput();

                if (qty <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                } else if (qty > medicine.getStockQty()) {
                    System.out.println("Not enough stock, currently have " + medicine.getStockQty() + " left. Please enter again.");
                } else {
                    break;
                }
            } while (true);

            System.out.print("Duration: ");
            String usage = scanner.nextLine();

            treatment.addTreatmentMedicine(new TreatmentMedicine(medicine, qty, usage));

            medicine.setStockQty(medicine.getStockQty() - qty);

            System.out.print("Add another medicine? (Y/N): ");
            more = scanner.nextLine();
        } while (more.equalsIgnoreCase("Y"));

        control.addTreatment(treatment);
        System.out.println("\nTreatment added successfully with ID " + id);
    }

    private void removeTreatment() {
        System.out.print("Enter Treatment ID to remove: ");
        String id = scanner.nextLine();
        if (control.removeTreatment(id)) {
            System.out.println("Treatment removed!");
        } else {
            System.out.println("Treatment not found.");
        }
    }
    
    private void updateTreatment() {
        System.out.print("Enter Treatment ID to update: ");
        String id = scanner.nextLine();
        Treatment treatment = control.findTreatmentByID(id);

        if (treatment == null) {
            System.out.println("Treatment not found.");
            return;
        }

        System.out.println("Updating Treatment: " + treatment.getTreatmentID() + " - " + treatment.getTreatmentType());

        // Update type
        System.out.print("Enter new Treatment Type (leave blank to keep '" + treatment.getTreatmentType() + "'): ");
        String newType = scanner.nextLine();
        if (!newType.isBlank()) {
            treatment.setTreatmentType(newType);
        }

        // Update description
        System.out.print("Enter new Description (leave blank to keep current): ");
        String newDesc = scanner.nextLine();
        if (!newDesc.isBlank()) {
            treatment.setTreatmentDesc(newDesc);
        }

        // Update medicines
        System.out.print("Do you want to update medicines? (Y/N): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            // Loop through existing medicines to update
            for (TreatmentMedicine tm : treatment.getTreatmentMedicines()) {
                Medicine med = tm.getMedicine();

                System.out.println("\nUpdating medicine: " + med.getMedicineID() + " - " + med.getName());
                System.out.println("Current qty: " + tm.getQuantityNeeded() + " | Current usage: " + tm.getUsageNotes());

                // Restore stock first
                med.setStockQty(med.getStockQty() + tm.getQuantityNeeded());

                // Ask for new qty
                int qty;
                do {
                    System.out.print("Enter new quantity (or 0 to keep same " + tm.getQuantityNeeded() + "): ");
                    qty = getIntInput();

                    if (qty == 0) {
                        qty = tm.getQuantityNeeded(); // keep same
                        break;
                    } else if (qty < 0) {
                        System.out.println("Quantity must not be negative.");
                    } else if (qty > med.getStockQty()) {
                        System.out.println("Not enough stock, currently have " + med.getStockQty() + " left.");
                    } else {
                        break;
                    }
                } while (true);

                // Ask for new usage
                System.out.print("Enter new usage instructions (leave blank to keep '" + tm.getUsageNotes() + "'): ");
                String usage = scanner.nextLine();
                if (usage.isBlank()) {
                    usage = tm.getUsageNotes(); // keep old
                }

                // Update treatment medicine
                tm.setQuantityNeeded(qty);
                tm.setUsageNotes(usage);

                // Deduct stock
                med.setStockQty(med.getStockQty() - qty);

                System.out.println("Updated: " + med.getName() + " → qty " + qty + ", usage: " + usage);
            }
        }

        System.out.println("Treatment updated successfully!");
    }
    
    private void searchTreatment() {
        System.out.print("Enter Treatment ID to search: ");
        String id = scanner.nextLine();
        searchTreatment(id);   // delegate to overloaded version
    }

    private void searchTreatment(String id) {
        Treatment t = control.findTreatmentByID(id);

        if (t != null) {
            System.out.println("\n=== Treatment Details ===");
            System.out.println("ID   : " + t.getTreatmentID());
            System.out.println("Type : " + t.getTreatmentType());
            System.out.println("Desc : " + t.getTreatmentDesc());

            if (t.getTreatmentMedicines().getNumberOfEntries() == 0) {
                System.out.println("No medicines associated with this treatment.");
            } else {
                System.out.println("Medicines:");
                for (TreatmentMedicine tm : t.getTreatmentMedicines()) {
                    System.out.println(" - " + tm.getMedicine().getName() 
                                       + " | Qty: " + tm.getQuantityNeeded() 
                                       + " | Usage: " + tm.getUsageNotes());
                }
            }
        } else {
            System.out.println("No treatment found with ID: " + id);
        }
    }

    // === Treatment Records ===
    private void addTreatmentRecord() {
        // Get Treatment
        System.out.print("Enter Treatment ID (must exist): ");
        String tid = scanner.nextLine();
        Treatment treatment = control.findTreatmentByID(tid);
        searchTreatment(tid);
        if (treatment == null) {
            System.out.println("Cannot add record. Treatment ID does not exist.");
            return;
        }
        listAvailableConsultations();

        // Get Consultation ID and find the consultation
        System.out.print("Enter Consultation ID: ");
        String cid = scanner.nextLine();
    
        // Use the new method from ManageConsultation
        Consultation consultation = consultationControl.findConsultationByID(cid);
        if (consultation == null) {
            System.out.println("Consultation ID not found!");
            return;
        }
    
        // Get patient and doctor info from the consultation
        String patientID = consultation.getPatientID();
        String doctorID = consultation.getDoctorID();
        
        Patient patient = patientControl.findPatientByID(patientID);
        Doctor doctor = doctorControl.findDoctorByID(doctorID);
    
        // Get names from PatientControl and DoctorControl
        String patientName = patientControl.findPatientByID(patientID).getName();
        String doctorName = doctorControl.findDoctorByID(doctorID).GetName();
        
        System.out.println("Patient: " + patientID + "|" + patientName);
        System.out.println("Doctor: " + doctorID + "|" + doctorName);

        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();

        System.out.print("Enter Perform Date (yyyy-MM-dd): ");
        String performDate = scanner.nextLine();

        System.out.print("Enter Status (1. Pending, 2. Completed, 3. Cancelled): ");
        int s = getIntInput();
        String status = switch (s) {
            case 1 -> "Pending";
            case 2 -> "Completed";
            case 3 -> "Cancelled";
            default -> "Unknown";
        };
        
        String rid = control.generateRecordID();

        // Create the record
        TreatmentRecord record = new TreatmentRecord(rid, diagnosis, performDate, status, 
                                               treatment, consultation, patient, doctor);
    
        control.addTreatmentRecord(record, consultation, patient, doctor);
        System.out.println("Treatment Record added successfully with id " + rid);
    }
    
    //Helper method
    private void listAvailableConsultations() {
        System.out.println("\n=== Available Consultations ===");
        QueueInterface<Consultation> consultations = consultationControl.getAllConsultations();

        for (int i = 0; i < consultations.getSize(); i++) {
            Consultation c = consultations.get(i);
            System.out.println("ID: " + c.getConsultationID() + 
                              " | Patient: " + c.getPatientID() + 
                              " | Doctor: " + c.getDoctorID() +
                              " | Date: " + c.getConsultDate() +
                              " | Diagnosis: " + c.getReason());
        }
    }
    
    private void removeTreatmentRecord() {
        System.out.print("Enter Record ID to remove: ");
        String rid = scanner.nextLine();
        if (control.removeTreatmentRecord(rid)) {
            System.out.println("Record removed!");
        } else {
            System.out.println("Record not found.");
        }
    }
    
    private void updateTreatmentRecord() {
        System.out.print("Enter Record ID to update: ");
        String rid = scanner.nextLine();
        TreatmentRecord record = control.findTreatmentRecordByID(rid);

        if (record == null) {
            System.out.println("Record not found.");
            return;
        }

        System.out.println("Updating Record: " + record.getTreatmentRecordID());

        // Diagnosis
        System.out.print("Enter new Diagnosis (leave blank to keep '" + record.getDiagnosis() + "'): ");
        String newDiagnosis = scanner.nextLine();
        if (!newDiagnosis.isBlank()) {
            record.setDiagnosis(newDiagnosis);
        }

        // Perform Date
        System.out.print("Enter new Perform Date (yyyy-MM-dd, leave blank to keep '" + record.getPerformDate() + "'): ");
        String newDate = scanner.nextLine();
        if (!newDate.isBlank()) {
            record.setPerformDate(newDate);
        }

        // Status
        System.out.print("Enter new Status (leave blank to keep '" + record.getStatus() + "'): ");
        String newStatus = scanner.nextLine();
        if (!newStatus.isBlank()) {
            record.setStatus(newStatus);
        }

        System.out.println("Treatment Record updated successfully!");
    }
    
    private void searchTreatmentRecord(){
        System.out.print("Enter Record ID to search: ");
        String rid = scanner.nextLine();
        
        TreatmentRecord record = control.findTreatmentRecordByID(rid);
        
        if (record == null) {
            System.out.println("No treatment record found with ID: " + rid);
            return;
        }
        
        System.out.println("\n================== TREATMENT RECORD DETAILS ==================");
        System.out.printf("Record ID       : %s\n", record.getTreatmentRecordID());
        System.out.printf("Diagnosis       : %s\n", record.getDiagnosis());
        System.out.printf("Perform Date    : %s\n", record.getPerformDate());
        System.out.printf("Status          : %s\n", record.getStatus());
        System.out.println("==============================================================");
        Patient p = record.getPatient();
        Doctor d = record.getDoctor();
        System.out.println("Patient : " + p.getId() + "\t" + p.getName());
        System.out.println("Doctor  : " + d.GetId() + "\t" + d.GetName());
        
        System.out.println("----------------------- TREATMENT INFO -----------------------");
        Treatment t = record.getTreatment();
        System.out.printf("Treatment ID    : %s\n", t.getTreatmentID());
        System.out.printf("Type            : %s\n", t.getTreatmentType());
        System.out.printf("Description     : %s\n", t.getTreatmentDesc());
        System.out.println("--------------------------------------------------------------");
        System.out.println("Medicines:");
        if (t.getTreatmentMedicines().getNumberOfEntries() == 0) {
            System.out.println("  No medicines associated with this treatment.");
        } else {
            System.out.printf("  %-15s %-10s %-30s\n", "Medicine Name", "Qty", "Usage");
            System.out.println("  ---------------------------------------------------------");
            for (TreatmentMedicine tm : t.getTreatmentMedicines()) {
                System.out.printf("  %-15s %-10d %-30s\n",
                                  tm.getMedicine().getName(),
                                  tm.getQuantityNeeded(),
                                  tm.getUsageNotes());
            }
        }
        System.out.println("==============================================================");
    }

    private void reportRecordsByDoctor() {
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        var records = control.findRecordsByDoctor(doctorId);

        if (records.getNumberOfEntries() == 0) {
            System.out.println("No treatment records found for Doctor " + doctorId);
        } else {
            String doctorName = records.getEntry(1).getDoctor().GetName();
            System.out.println("\n=== Treatment Records for Doctor " + doctorId + " (" + doctorName + ") ===");
            System.out.printf("%-10s %-15s %-20s %-20s %-12s %-10s\n",
                    "RecordID", "PatientID", "PatientName", "Treatment", "Date", "Status");
            System.out.println("---------------------------------------------------------------------------------------------------");

            for (TreatmentRecord r : records) {
                System.out.printf("%-10s %-15s %-20s %-20s %-12s %-10s\n",
                        r.getTreatmentRecordID(),
                        r.getPatient().getId(),
                        r.getPatient().getName(),
                        r.getTreatment().getTreatmentType(),
                        r.getPerformDate(),
                        r.getStatus());
            }

            // ✅ just call control for summary
            control.generateDoctorReport(doctorId);
        }
    }

    private void reportRecordsByPatient() {
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();
        var records = control.findRecordsByPatient(patientId);

        if (records.getNumberOfEntries() == 0) {
            System.out.println("No treatment records found for Patient " + patientId);
        } else {
            String patientName = records.getEntry(1).getPatient().getName();
            System.out.println("\n=== Treatment Records for Patient " + patientId + " (" + patientName + ") ===");
            System.out.printf("%-10s %-15s %-20s %-20s %-12s %-10s\n",
                    "RecordID", "DoctorID", "DoctorName", "Treatment", "Date", "Status");
            System.out.println("------------------------------------------------------------------------------------------------------------");

            for (TreatmentRecord r : records) {
                System.out.printf("%-10s %-15s %-20s %-20s %-12s %-10s\n",
                        r.getTreatmentRecordID(),
                        r.getDoctor().GetId(),
                        r.getDoctor().GetName(),
                        r.getTreatment().getTreatmentType(),
                        r.getPerformDate(),
                        r.getStatus());
            }

            // ✅ summary from control
            control.generatePatientReport(patientId);
        }
    }

    // === Helper methods ===
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}