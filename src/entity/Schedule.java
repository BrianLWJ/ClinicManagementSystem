/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ThisPc
 */
public class Schedule implements Comparable<Schedule> {
    private String id;
    private String doctorId;
    private String roomNumber;
    private Time startTime;
    private Time endTime;
    
    public Schedule(String id, String doctorId, String roomNumber, Time startTime, Time endTime) {
        this.id = id;
        this.doctorId = doctorId;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public Schedule(String id, String doctorId, String roomNumber, String startTimeStr, String endTimeStr) {
        this.id = id;
        this.doctorId = doctorId;
        this.roomNumber = roomNumber;
        this.startTime = new Time(startTimeStr);
        this.endTime = new Time(endTimeStr);
    }
    
    public String getId() { return id; }
    public String getDoctorId() { return doctorId; }
    public String getRoomNumber() { return roomNumber; } 
    public Time getStartTime() { return startTime; } 
    public Time getEndTime() { return endTime; } 

    @Override
    public int compareTo(Schedule o) {
        return startTime.compareTo(o.startTime);
    }
    
    @Override
    public String toString(){
        return String.format("""
        ID: %s Doctor ID: %s Room Number: %s From: %s to %s"""
        ,
        id, doctorId, roomNumber, startTime.toString(), endTime.toString());
    }
}
