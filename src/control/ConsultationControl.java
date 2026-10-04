/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.ArrayQueue;
import adt.QueueInterface;
import entity.Consultation;
import entity.Doctor;

/**
 *
 * @author chinw
 */
public class ConsultationControl {

    private QueueInterface<Consultation> queue = new ArrayQueue<>(50);
    private int currentId = 1; // for C1, C2, ...
    
    private PatientControl patientManager;
    private DoctorControl doctorManager;
    
    public ConsultationControl(PatientControl patientManager, DoctorControl doctorManager) {
        this.patientManager = patientManager;
        this.doctorManager = doctorManager;
    }

    public Consultation createConsultation(String patientID, String doctorID,
                                           String date, String time,
                                           String reason, String notes) {
        // validate patient ID
        if (patientManager.findPatientByID(patientID) == null) {
            System.out.println("Error: Patient ID not found.");
            return null;
        }
        // validate doctor ID
        if (doctorManager.findDoctorByID(doctorID) == null) {
            System.out.println("Error: Doctor ID 5 not found.");
            return null;
        }

        String consultationID = "C" + currentId++;
        Consultation consultation = new Consultation(consultationID, patientID, doctorID,
                                                     date, time, reason, notes);
        queue.enqueue(consultation);
        return consultation;
    }

    // READ (all consultations)
    public QueueInterface<Consultation> getAllConsultations() {
        return queue;
    }
    
    public Consultation findConsultationByID(String consultationID){
        boolean found = false;
        for (int i = 0; i < queue.getSize(); i++) {
            Consultation c = queue.get(i);
            if (c.getConsultationID().equals(consultationID)) {
                found = true;
                return c;
            }
        }
        if (!found) {
            System.out.println("No consultations found for Patient ID: " + consultationID);
        }
        return null;
    }

    // READ (by patient)
    public void findByPatient(String patientID) {
        boolean found = false;
        for (int i = 0; i < queue.getSize(); i++) {
            Consultation c = queue.get(i);
            if (c.getPatientID().equals(patientID)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No consultations found for Patient ID: " + patientID);
            System.out.println("(Note: Patient may exist, but no consultation has been created yet.)");
        }
    }

    // READ (by doctor)
    public void findByDoctor(String doctorID) {
        boolean found = false;
        for (int i = 0; i < queue.getSize(); i++) {
            Consultation c = queue.get(i);
            if (c.getDoctorID().equals(doctorID)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No consultations found for Doctor ID: " + doctorID);
            System.out.println("(Note: Doctor may exist, but no consultation has been created yet.)");
        }
    }

    // UPDATE
    public boolean updateConsultation(String consultationID,
                                      String newDate, String newTime,
                                      String newReason, String newNotes) {
        for (int i = 0; i < queue.getSize(); i++) {
            Consultation c = queue.get(i);
            if (c.getConsultationID().equals(consultationID)) {
                Consultation updated = new Consultation(
                        c.getConsultationID(), c.getPatientID(), c.getDoctorID(),
                        newDate, newTime, newReason, newNotes
                );
                queue.replaceAt(i, updated);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean deleteConsultation(String consultationID) {
        for (int i = 0; i < queue.getSize(); i++) {
            Consultation c = queue.get(i);
            if (c.getConsultationID().equals(consultationID)) {
                queue.removeAt(i);
                return true;
            }
        }
        return false;
    }
    
    // REPORT: Group consultations by doctor
    public void generateDoctorReport() {
    if (queue.isEmpty()) {
        System.out.println("No consultations available.");
        return;
    }

    System.out.println("\n=== Doctor Consultation Report ===");

    // Arrays for unique doctor IDs
    String[] doctorIDs = new String[queue.getSize()];
    int doctorUnique = 0;

    // Collect unique doctor IDs
    for (int i = 0; i < queue.getSize(); i++) {
        String dID = queue.get(i).getDoctorID();
        if (findIndex(doctorIDs, doctorUnique, dID) == -1) {
            doctorIDs[doctorUnique] = dID;
            doctorUnique++;
        }
    }

    // Print consultations grouped by doctor
    for (int i = 0; i < doctorUnique; i++) {
        String dID = doctorIDs[i];
        System.out.println("\nDoctor " + dID);

        for (int j = 0; j < queue.getSize(); j++) {
            Consultation c = queue.get(j);
            if (c.getDoctorID().equalsIgnoreCase(dID)) {
                System.out.println("   " + c.getConsultationID() +
                        " | Patient " + c.getPatientID() +
                        " | Date: " + c.getConsultDate() +
                        " | Reason: " + c.getReason());
            }
        }
    }
   }
    private int findIndex(String[] arr, int size, String target) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}
