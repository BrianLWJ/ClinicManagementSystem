/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import control.DoctorControl;
import entity.Doctor;
import java.util.Scanner;

/**
 *
 * @author ThisPc
 */
public class DoctorUI {
    private DoctorControl control;
    private ScheduleUI scheduleUI;
    
    public DoctorUI(DoctorControl control, ScheduleUI scheduleUI) {
        this.control = control;
        this.scheduleUI = scheduleUI;
        
        control.Add("John", "1234", "male", "0123", "KLCC", "cardiologist");
        control.Add("Cassie", "5678", "female", "0456", "Gombak", "neurologist");
        control.Add("Steve", "9012", "male", "0789", "Damai", "cardiologist");
        control.Add("Maryn", "3456", "female", "0234", "Jelatek", "neurologist");
        control.Add("Brandon", "7890", "male", "0567", "Maluri", "neurologist");
        control.Add("Elvis", "0193", "male", "0698", "TRX", "urinologist");
    }
    
    public void run() {
        boolean r = true;
        while(r) {
            System.out.print("""
            1 > Display doctor records
            2 > Add dcotor record
            3 > Remove doctor record
            4 > Edit doctor record
            5 > Set doctor availability
            6 > Doctor specialization report
            7 > Doctor availability report
            8 > Schedules
            0 > Exit
            >>>  """);

            Scanner s = new Scanner(System.in);

            String option = s.nextLine();

            switch(option){
                case "1" -> DisplayDoctors();
                case "2" -> AddDoctor();
                case "3" -> RemoveDoctor();
                case "4" -> EditDoctor();
                case "5" -> SetDoctorAvailability();
                case "6" -> DoctorSpecializationReport();
                case "7" -> DoctorAvailabilityReport();
                case "8" -> scheduleUI.run();
                case "0" -> r = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }
    
    public void DisplayDoctors() {
        var entries = control.GetEntries();
        System.out.println(String.format("Showing %d entries.\n", entries.getNumberOfEntries()));
        int i = 1;
        for(Doctor d: entries){
            System.out.println(String.format("Entry %d", i++));
            System.out.println(d + "\n");
        }
    }
    
    public void AddDoctor() {
        Scanner s = new Scanner(System.in);
        
        System.out.print(
        """
        Enter Doctor Information in the following format.
        [name] [ic number] [male/female] [phone number] [address] [specialization]: """
        );
        
        String input = s.nextLine();
        
        s = new Scanner(input);
        
        try {
            control.Add(s.next(), s.next(), s.next(), s.next(), s.next(), s.next());
            System.out.println("Doctor record successfully added.");
        } catch(Exception e){
            System.out.println("Invalid format.\nFailed to add doctor record.");
        }
    }
    
    public void RemoveDoctor() {
        Scanner s = new Scanner(System.in);
        
        System.out.print("Enter Doctor ID :");
        
        String id = s.nextLine();
        
        Doctor d = control.findDoctorByID(id);
        
        if(d == null){
            System.out.println("Doctor not found");
        }else{
            System.out.println(d);
            System.out.print("Remove this doctor record? [y/n] : ");
            switch(s.nextLine()){
                case "y" -> {
                    control.Remove(id);
                    scheduleUI.RemoveSchedule(id);
                    System.out.println("Doctor record successfully removed.");
                }
                case "n" -> {
                    System.out.println("Remove operation cancelled");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
    
    public void EditDoctor() {
        Scanner s = new Scanner(System.in);
        
        System.out.print("Enter Doctor ID :");
        
                String id = s.nextLine();
        
        Doctor d = control.findDoctorByID(id);
        
        if(d == null){
            System.out.println("Doctor not found");
        }else{
            System.out.println(d);
            System.out.println("Enter Doctor Information in the following format (use \"-\" to keep original)");
            System.out.print("[name] [ic number] [male/female] [phone number] [address] [specialization]:");
            
            String input = s.nextLine();
        
            s = new Scanner(input);
        
            try {
                String newName = s.next();
                String newIcNumber = s.next();
                String newGender = s.next();
                String newPhoneNumber = s.next();
                String newAddress = s.next();
                String newSpecialization = s.next();
                
                if(newName.equals("-")) newName = null;
                if(newIcNumber.equals("-")) newIcNumber = null;
                if(newGender.equals("-")) newGender = null;
                if(newPhoneNumber.equals("-")) newPhoneNumber = null;
                if(newAddress.equals("-")) newAddress = null;
                if(newSpecialization.equals("-")) newSpecialization = null;
                
                control.Edit(d.GetId(), newName, newIcNumber, newGender, newPhoneNumber, newAddress, newSpecialization);
                
                System.out.println("Doctor record successfully edited.");
            } catch(Exception e){
                System.out.println("Invalid format.\nFailed to edit doctor record.");
            }
        }
    }
    
    public void SetDoctorAvailability() {
        Scanner s = new Scanner(System.in);
        
        System.out.print("Enter Doctor ID:");
        
        String id = s.nextLine();
        
        Doctor d = control.findDoctorByID(id);
        
        if(d == null){
            System.out.println("Doctor not found");
        }else{
            System.out.println(d);
            System.out.print("Enter availability for this doctor [y/n]: ");
            
            String input = s.nextLine();
        
            s = new Scanner(input);
        
            try {
                input = s.next();
                
                switch(input.toLowerCase()){
                    case "y" -> {
                        control.SetAvailability(id, true);
                        System.out.println("Doctor availability successfully set.");
                    }
                    case "n" -> {
                        control.SetAvailability(id, false);
                        System.out.println("Doctor availability successfully set.");
                    }
                    default -> System.out.println("Invalid Option.\nFailed to edit doctor availability.");
                }
            } catch(Exception e){
                System.out.println("Invalid format.\nFailed to edit doctor availability.");
            }
        }
    }
    
    public void DoctorSpecializationReport() {
        var groups = control.GroupDoctorsBySpecialization();
        
        for(var group: groups){
            System.out.println("======================================");
            System.out.println("Specialization : " + group.getEntry(1).GetSpecialization());
            System.out.println(group.getNumberOfEntries() + " entries");
            System.out.println("======================================\n");
            for(var d: group){
                System.out.println(d + "\n");
            }
        }
    }
    
    public void DoctorAvailabilityReport() {
        var available = control.GroupAvailableDoctors();
        System.out.println("======================================");
        System.out.println("Availabile dcotors (showing " + available.getNumberOfEntries() + " entries)");
        System.out.println("======================================\n");
        for(var d: available){
            System.out.println(d + "\n");
        }
    }
}
