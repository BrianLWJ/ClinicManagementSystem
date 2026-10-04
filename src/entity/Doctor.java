/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ThisPc
 */
public class Doctor {
    private String id;
    private String name;
    private String icNumber;
    private String gender;
    private String phoneNumber;
    private String address;
    private String specialization;
    private boolean isAvailable;
    
    public Doctor(String id, String name, String icNumber, String gender, String phoneNumber, String address, String specialization){
        this(id, name, icNumber, gender, phoneNumber, address, specialization, true);
    }
    
    public Doctor(String id, String name, String icNumber, String gender, String phoneNumber, String address, String specialization, boolean isAvailable){
        this.id = id;
        this.name = name;
        this.icNumber = icNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.specialization = specialization;
        this.isAvailable = isAvailable;
    }
    
    public String GetId() { return id; }
    public String GetName() { return name; }
    public String GetIcNumber() { return icNumber; }    
    public String GetGender() { return gender; }
    public String GetPhoneNumber() { return phoneNumber; }
    public String GetAddress() { return address; }
    public String GetSpecialization() { return specialization; }
    public boolean GetAvailability() { return isAvailable; }


//    public void SetId(String id) { this.id = id; }
    public void SetName(String name) { this.name = name; }
    public void SetIcNumber(String icNumber) { this.icNumber = icNumber; }    
    public void SetGender(String gender) { this.gender = gender; }
    public void SetPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void SetAddress(String address) { this.address = address; }
    public void SetSpecialization(String specialization) { this.specialization = specialization; }
    public void SetAvailability(boolean isAvailable) {  this.isAvailable = isAvailable; }
    
    @Override
    public String toString() {
        return String.format("""
        ID: %s
        Name: %s
        IC number: %s
        Gender: %s
        Phone Number: %s
        Address: %s
        Specialization: %s
        %s"""
        ,
        id, name, icNumber, gender, phoneNumber, address, specialization, isAvailable ? "Available" : "Not Available");
    }
    
    @Override
    public boolean equals(Object doctor) {
        if(this == doctor) return true;
        if(doctor instanceof Doctor d) return icNumber.equals(d.icNumber);
        return false;
    }
}
