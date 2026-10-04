/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package entity;

/**
 *
 * @author chinw
 */
public class Consultation {

    private String consultationID;
    private String patientID;
    private String doctorID;
    private String consultDate;
    private String time;
    private String reason;
    private String notes;

    public Consultation(String consultationID, String patientID, String doctorID,
                        String consultDate, String time, String reason, String notes) {
        this.consultationID = consultationID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.consultDate = consultDate;
        this.time = time;
        this.reason = reason;
        this.notes = notes;
    }

    public String getConsultationID(){ 
        return consultationID; 
    }
    public String getPatientID(){ 
        return patientID;
    }
    public String getDoctorID(){ 
        return doctorID; 
    }
    public String getConsultDate(){ 
        return consultDate; 
    }
    public String getTime(){ 
        return time; 
    }
    public String getReason(){
        return reason; 
    }
    public String getNotes(){ 
        return notes; 
    }

    @Override
    public String toString() {
        return "ID: " + consultationID +
                " / Patient: " + patientID +
                " / Doctor: " + doctorID +
                " / Date: " + consultDate +
                " / Time: " + time +
                " / Reason: " + reason;
    }
}

