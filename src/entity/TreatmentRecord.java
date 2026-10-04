package entity;

/**
 *
 * @author User
 */
public class TreatmentRecord {
    private String treatmentRecordID;
    private String diagnosis;
    private String performDate;
    private String status;
    private Treatment treatment;
    private Consultation consultation;
    private Patient patient;
    private Doctor doctor;

    public TreatmentRecord(String treatmentRecordID, String diagnosis, String performDate, String status, 
                            Treatment treatment, Consultation consultation, Patient patient, Doctor doctor) {
        this.treatmentRecordID = treatmentRecordID;
        this.diagnosis = diagnosis;
        this.performDate = performDate;
        this.status = status;
        this.treatment = treatment;
        this.consultation = consultation;
        this.patient = patient;
        this.doctor = doctor;
    }

    // Getter and setter
    public String getTreatmentRecordID() {
        return treatmentRecordID;
    }

    public void setTreatmentRecordID(String treatmentRecordID) {
        this.treatmentRecordID = treatmentRecordID;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPerformDate() {
        return performDate;
    }

    public void setPerformDate(String performDate) {
        this.performDate = performDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {

        String treatmentId = (treatment != null) ? treatment.getTreatmentID() : "N/A";
        String consultationId = (consultation != null) ? consultation.getConsultationID() : "N/A";
        String patientName = (patient != null) ? patient.getName() : "N/A";
        String doctorName = (doctor != null) ? doctor.GetName() : "N/A";
        
        return "Treatment Record ID : " + treatmentRecordID + '\n' +
                "Treatment ID        : " + treatmentId + '\n' +
                "Consultation ID     : " + consultationId + '\n' +
                "Patient Name        : " + patientName + '\n' +
                "Doctor Name         : " + doctorName + '\n' +
                "Diagnosis           : " + diagnosis + '\n' +
                "Perform Date        : " + performDate + '\n' +
                "Status              : " + status;
    }
}