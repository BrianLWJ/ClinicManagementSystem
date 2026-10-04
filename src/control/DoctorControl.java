/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.Doctor;
import adt.*;

/**
 *
 * @author ThisPc
 */
public class DoctorControl {
    private ListInterface<Doctor> doctors = new ArrayList<>();
    private int globalId = 0;
    
    private String generateId(){ return String.format("D%03d", globalId++); }
    
    public DoctorControl() {}
    
    public boolean Add(Doctor d) {
        return doctors.add(d);
    }
    
    public boolean Add(String name, String icNumber, String gender, String phoneNumber, String address, String specialization) {
        return doctors.add(new Doctor(generateId(), name, icNumber, gender,phoneNumber, address, specialization));
    }
    
    public Doctor Remove(String id) {
        for(int i = 1; i <= doctors.getNumberOfEntries(); i++) {
            if(doctors.getEntry(i).GetId().equals(id)) return doctors.remove(i);
        }
        return null;
    }
    
    public Doctor Find(String icNumber) {
        for(Doctor d:doctors){
            if(d.GetIcNumber().equals(icNumber)) return d;
        }
        return null;
    }
    
    public Doctor findDoctorByID(String doctorID) {
        for (int i = 1; i <= doctors.getNumberOfEntries(); i++) {
            Doctor d = doctors.getEntry(i);
            if (d.GetId().equals(doctorID)) {
                return d;
            }
        }
        return null;
    }
    
    public boolean Edit(String id, String newName, String newIcNumber, String newGender, String newPhoneNumber, String newAddress, String newSpecialization) {
        var d = findDoctorByID(id);
        if(d != null) {
            if(newName != null) d.SetName(newName);
            if(newIcNumber != null) d.SetIcNumber(newIcNumber);
            if(newGender != null) d.SetGender(newGender);
            if(newPhoneNumber != null) d.SetPhoneNumber(newPhoneNumber);
            if(newAddress != null) d.SetAddress(newAddress);
            if(newSpecialization != null) d.SetSpecialization(newSpecialization);
            return true;
        }
        return false;
    }
    
    public boolean SetAvailability(String id, boolean isAvailable) {
        var d = findDoctorByID(id);
        if(d != null){
            d.SetAvailability(isAvailable);
            return true;
        }
        return false;
    }
    
    public ListInterface<Doctor> GetEntries() {
        return doctors;
    }
    
    public ListInterface<ListInterface<Doctor>> GroupDoctorsBySpecialization() {
        ListInterface<ListInterface<Doctor>> groups =  new ArrayList<>();
        SetInterface<String> specializationSet = new HashSet<>();
        
        for(Doctor d:doctors){
            specializationSet.add(d.GetSpecialization());
        }
        
        for(String s : specializationSet){
            ListInterface<Doctor> filtered = new ArrayList<>();
            for(Doctor d: doctors){
                if(d.GetSpecialization().equals(s)) {
                    filtered.add(d);
                }
            }
            groups.add(filtered);
        }
        
        return groups;
    }
    
    public ListInterface<Doctor> GroupAvailableDoctors() {
        ListInterface<Doctor> available = new ArrayList<>();
        for(Doctor d: doctors) {
            if(d.GetAvailability() == true) available.add(d);
        }
        return available;
    }
}
