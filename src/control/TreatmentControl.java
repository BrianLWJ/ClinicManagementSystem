package control;
/**
 *
 * @author User
 */

import adt.LinkedList;
import entity.Treatment;
import entity.TreatmentRecord;
import entity.Consultation;
import entity.Doctor;
import entity.Patient;

public class TreatmentControl {
    private LinkedList<Treatment> treatments = new LinkedList<>();
    private LinkedList<TreatmentRecord> records = new LinkedList<>();
    private int treatmentCount = 0;
    private int recordCount = 0;
    
    private PatientControl patientControl;
    private DoctorControl doctorControl;
    private ConsultationControl consultationControl;
    private MedicineControl medicineControl;
    
    public TreatmentControl(PatientControl patientControl, DoctorControl doctorControl, 
                            ConsultationControl consultationControl, MedicineControl medicineControl){
        this.patientControl = patientControl;
        this.doctorControl = doctorControl;
        this.consultationControl = consultationControl;
        this.medicineControl = medicineControl;
    }
    
    public PatientControl getPatientControl() {
        return patientControl;
    }
    
    public DoctorControl getDoctorControl() {
        return doctorControl;
    }
    
    public ConsultationControl getConsultationControl() {
        return consultationControl;
    }
    
    public MedicineControl getMedicineControl() {
        return medicineControl;
    }

    // === Treatment functions ===
    public String generateTreatmentID() {
        int max = 0;
        for (int i = 1; i <= treatments.getNumberOfEntries(); i++) {
            String tid = treatments.getEntry(i).getTreatmentID();
            int num = Integer.parseInt(tid.substring(1));
            if (num > max) max = num;
        }
        treatmentCount = max + 1;
        return String.format("T%03d", treatmentCount);
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public boolean removeTreatment(String treatmentID) {
        for (int i = 1; i <= treatments.getNumberOfEntries(); i++) {
            Treatment t = treatments.getEntry(i);
            if (t.getTreatmentID().equals(treatmentID)) {
                treatments.remove(i);
                return true;
            }
        }
        return false;
    }

    public Treatment findTreatmentByID(String treatmentID) {
        for (int i = 1; i <= treatments.getNumberOfEntries(); i++) {
            Treatment t = treatments.getEntry(i);
            if (t.getTreatmentID().equals(treatmentID)) {
                return t;
            }
        }
        return null;
    }
    
    public boolean updateTreatment(String treatmentID, String newType, String newDesc) {
        Treatment treatment = findTreatmentByID(treatmentID);
        if (treatment != null) {
            if (newType != null && !newType.isEmpty()) {
                treatment.setTreatmentType(newType);
            }
            if (newDesc != null && !newDesc.isEmpty()) {
                treatment.setTreatmentDesc(newDesc);
            }
            return true;
        }
        return false;
    }

    public void listTreatments() {
        System.out.println("=== Treatments ===");
        for (int i = 1; i <= treatments.getNumberOfEntries(); i++) {
            System.out.println(treatments.getEntry(i) + "\n");
        }
    }

    // === Treatment Record functions ===
    public String generateRecordID() {
        int max = 0;
        for (TreatmentRecord record : records) {
            String id = record.getTreatmentRecordID(); // e.g. "TR005"
            int num = Integer.parseInt(id.substring(2)); 
            if (num > max) {
                max = num;
            }
        }
        return String.format("TR%03d", max + 1);
    }
    
    // Updated method signature to match what UI is calling
    public void addTreatmentRecord(TreatmentRecord record, Consultation consultation,
                                    Patient patient, Doctor doctor) {
        record.setConsultation(consultation);
        record.setPatient(patient);
        record.setDoctor(doctor);
        
        records.add(record);
    }
    
    public boolean removeTreatmentRecord(String recordID) {
        for (int i = 1; i <= records.getNumberOfEntries(); i++) {
            TreatmentRecord r = records.getEntry(i);
            if (r.getTreatmentRecordID().equals(recordID)) {
                records.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public TreatmentRecord findTreatmentRecordByID(String recordID) {
        for (int i = 1; i <= records.getNumberOfEntries(); i++) {
            TreatmentRecord record = records.getEntry(i);
            if (record.getTreatmentRecordID().equals(recordID)) {
                return record;
            }
        }
        return null;
    }
    
    public LinkedList<TreatmentRecord> findRecordsByDoctor(String doctorId) {
        LinkedList<TreatmentRecord> result = new LinkedList<>();
        for (int i = 1; i <= records.getNumberOfEntries(); i++) {
            TreatmentRecord r = records.getEntry(i);
            if (r.getDoctor().GetId().equalsIgnoreCase(doctorId)) {
                result.add(r);
            }
        }
        return result;
    }

    public LinkedList<TreatmentRecord> findRecordsByPatient(String patientId) {
        LinkedList<TreatmentRecord> result = new LinkedList<>();
        for (int i = 1; i <= records.getNumberOfEntries(); i++) {
            TreatmentRecord r = records.getEntry(i);
            if (r.getPatient().getId().equalsIgnoreCase(patientId)) {
                result.add(r);
            }
        }
        return result;
    }
    
    public void listTreatmentRecords() {
        System.out.println("=== Treatment Records ===");
        for (int i = 1; i <= records.getNumberOfEntries(); i++) {
            System.out.println(records.getEntry(i) + "\n");
        }
    }
    
    public boolean updateTreatmentRecord(String recordID, Treatment newTreatment, 
                                        Consultation newConsultation, 
                                        Patient newPatient, Doctor newDoctor) {
       TreatmentRecord record = findTreatmentRecordByID(recordID);
       if (record != null) {
           if (newTreatment != null) record.setTreatment(newTreatment);
           if (newConsultation != null) record.setConsultation(newConsultation);
           if (newPatient != null) record.setPatient(newPatient);
           if (newDoctor != null) record.setDoctor(newDoctor);
           return true;
       }
       return false;
   }
    
    public void generateDoctorReport(String doctorId) {
        int total = 0, completed = 0, pending = 0, cancelled = 0, totalMedicines = 0;
        LinkedList<Frequency> treatmentFrequency = new LinkedList<>();
        Doctor doctor = null;

        for (TreatmentRecord r : records) {
            if (!r.getDoctor().GetId().equalsIgnoreCase(doctorId)) continue;
            if (doctor == null) doctor = r.getDoctor();

            total++;

            // Status count
            switch (r.getStatus().toLowerCase()) {
                case "completed": completed++; break;
                case "pending": pending++; break;
                case "cancelled": cancelled++; break;
            }

            // Treatment frequency
            addOrUpdateFrequency(treatmentFrequency, r.getTreatment().getTreatmentType());

            // Medicines prescribed
            totalMedicines += r.getTreatment().getTreatmentMedicines().getNumberOfEntries();
        }

        if (total == 0) {
            System.out.println("No treatment records found for Doctor " + doctorId);
            return;
        }

        System.out.println("\n=== Summary for Doctor " + doctorId + " (" + doctor.GetName() + ") ===");
        System.out.println("Total Records: " + total);
        System.out.println("Completed: " + completed + " | Pending: " + pending + " | Cancelled: " + cancelled);
        System.out.println("Most Common Treatment: " + getMostCommonTreatment(treatmentFrequency));
        System.out.println("Total Medicines Prescribed: " + totalMedicines);
    }

    // === Report by Patient ===
    public void generatePatientReport(String patientId) {
        int total = 0, completed = 0, pending = 0, cancelled = 0, totalMedicines = 0;
        LinkedList<Frequency> treatmentFrequency = new LinkedList<>();
        Patient patient = null;

        for (TreatmentRecord r : records) {
            if (!r.getPatient().getId().equalsIgnoreCase(patientId)) continue;
            if (patient == null) patient = r.getPatient();

            total++;

            // Status count
            switch (r.getStatus().toLowerCase()) {
                case "completed": completed++; break;
                case "pending": pending++; break;
                case "cancelled": cancelled++; break;
            }

            // Treatment frequency
            addOrUpdateFrequency(treatmentFrequency, r.getTreatment().getTreatmentType());

            // Medicines prescribed
            totalMedicines += r.getTreatment().getTreatmentMedicines().getNumberOfEntries();
        }

        if (total == 0) {
            System.out.println("No treatment records found for Patient " + patientId);
            return;
        }

        System.out.println("\n=== Summary for Patient " + patientId + " (" + patient.getName() + ") ===");
        System.out.println("Total Records: " + total);
        System.out.println("Completed: " + completed + " | Pending: " + pending + " | Cancelled: " + cancelled);
        System.out.println("Most Common Treatment: " + getMostCommonTreatment(treatmentFrequency));
        System.out.println("Total Medicines Prescribed: " + totalMedicines);
    }

    // === Helper methods ===
    private void addOrUpdateFrequency(LinkedList<Frequency> freqList, String treatment) {
        for (Frequency f : freqList) {
            if (f.treatment.equalsIgnoreCase(treatment)) {
                f.count++;
                return;
            }
        }
        freqList.add(new Frequency(treatment, 1));
    }

    private String getMostCommonTreatment(LinkedList<Frequency> freqList) {
        if (freqList.getNumberOfEntries() == 0) return "N/A";
        Frequency max = freqList.getEntry(1);
        for (Frequency f : freqList) {
            if (f.count > max.count) max = f;
        }
        return max.treatment + " (" + max.count + " times)";
    }

    // Simple helper class (not a new entity, just private inside control)
    private static class Frequency {
        String treatment;
        int count;
        Frequency(String treatment, int count) {
            this.treatment = treatment;
            this.count = count;
        }
    }
    
    public void clearMedicine(Treatment treatment) {
        treatment.getTreatmentMedicines().clear();
    }
    
    public int getNumberOfTreatments() {
        return treatments.getNumberOfEntries();
    }
    
    public int getNumberOfRecords() {
        return records.getNumberOfEntries();
    }
}