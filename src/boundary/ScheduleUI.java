/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import control.ScheduleControl;
import java.util.Scanner;

/**
 *
 * @author ThisPc
 */
public class ScheduleUI {
    private ScheduleControl control;
    
    public ScheduleUI(ScheduleControl control) {
        this.control = control;
        
        control.ForceAdd("D000", "R000", "10:00", "11:00");
        control.ForceAdd("D001", "R001", "10:00", "12:00");
        control.ForceAdd("D002", "R002", "8:00", "10:00");
        control.ForceAdd("D003", "R003", "8:00", "9:00");
        control.ForceAdd("D004", "R004", "9:00", "10:00");
        control.ForceAdd("D005", "R005", "12:00", "13:30");

        control.ForceAdd("D000", "R006", "13:00", "15:00");
        control.ForceAdd("D001", "R007", "12:00", "14:00");
        control.ForceAdd("D002", "R008", "14:00", "14:30");
        control.ForceAdd("D003", "R009", "10:30", "12:30");
        control.ForceAdd("D004", "R010", "11:00", "14:30");
        control.ForceAdd("D005", "R011", "14:30", "15:00");
    }
    
    public void run() {
        boolean r = true;
        while(r) {
            System.out.print("""
            1 > Display schedules
            2 > Display all doctors' schedules
            3 > Add schedule
            4 > Remove schedule
            0 > Exit
            >>>  """);

            Scanner s = new Scanner(System.in);

            String option = s.nextLine();

            switch(option){
                case "1" -> DisplaySchedules();
                case "2" -> DisplayDoctorsAndSchedules();
                case "3" -> AddSchedule();
                case "4" -> RemoveSchedule();
                case "0" -> r = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }
    
    public void DisplaySchedules() {
        var entries = control.GetSortedSchedules();
        System.out.println(String.format("Showing %d entries.\n", entries.getNumberOfEntries()));
        int i = 1;
        for(var s: entries){
            System.out.println(String.format("Entry %d", i++));
            System.out.println(s + "\n");
        }
    }
    
    public void DisplayDoctorsAndSchedules() {
        var entries = control.GetDoctorsAndTheirSchedules();
        
        System.out.println(String.format("Showing %d entries.\n", entries.getNumberOfEntries()));
        int i = 1;
        for(var p: entries){
            System.out.println(String.format("Entry %d", i++));
            System.out.println(p.doctor + "\n");
            System.out.println("Schedules: ");
            for(var s: p.schedules){
                System.out.println(s + "\n");
            }
        }
    }
    
    public void AddSchedule() {
        Scanner s = new Scanner(System.in);
        
        System.out.print(
        """
        Enter Schedule Information in the following format.
        [Doctor ID] [Room Number] [Start Time (24 hour) [hh:mm]] [End Time (24 hour) [hh:mm]]: """
        );
        
        String input = s.nextLine();
        
        s = new Scanner(input);
        
        try {
            if(!control.Add(s.next(), s.next(), s.next(), s.next())) {
                System.out.println("Doctor not found.");
                return;
            }
            System.out.println("Doctor record successfully added.");
        } catch(Exception e){
            System.out.println("Invalid format.\nFailed to add doctor record.");
        }
    }
    
    public void RemoveSchedule() {
        Scanner s = new Scanner(System.in);
        
        System.out.print("Enter schedule ID: ");
        
        String id = s.nextLine();
        
        boolean result = control.Remove(id);
        
        System.out.println((result ? "Schedule successfully removed." : "Schedule not found."));
    }
    
    public void RemoveSchedule(String doctorId){
        control.RemoveAllFromDoctor(doctorId);
    }
}
