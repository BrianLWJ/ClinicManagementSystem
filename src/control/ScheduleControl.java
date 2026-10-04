/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.Doctor;
import entity.Schedule;
import entity.Time;

/**
 *
 * @author ThisPc
 */
public class ScheduleControl {
    private DoctorControl doctorControl;
    private ListInterface<Schedule> schedules;
    
    private int globalId;

    public ScheduleControl(DoctorControl doctorControl){
        this.doctorControl = doctorControl;
        schedules = new ArrayList<>();
        globalId = 0;
    }
    
    public void ForceAdd(String doctorId, String roomNumber, String startTimeStr, String endTimeStr){
        schedules.add(new Schedule(String.format("S%03d", globalId++), doctorId, roomNumber, startTimeStr, endTimeStr));
    }
    
    public boolean Add(String doctorId, String roomNumber, String startTimeStr, String endTimeStr) {
        if(doctorControl.findDoctorByID(doctorId) == null) return false;
        ForceAdd(doctorId, roomNumber, startTimeStr, endTimeStr);
        return true;
    }
    
    public boolean Remove(String scheduleId) {
        for(int i = 1; i <= schedules.getNumberOfEntries(); i++){
            if(schedules.getEntry(i).getId().equals(scheduleId)){
                schedules.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public void RemoveAllFromDoctor(String doctorId) {
        StackInterface<Integer> indicesToRemove = new ArrayStack<>();
        int i = 1;
        for(var s: schedules){
            if(s.getDoctorId().equals(doctorId)) indicesToRemove.push(i);
            i++;
        }
        while(!indicesToRemove.isEmpty()) schedules.remove(indicesToRemove.pop());
    }
    
    public ListInterface<Schedule> FindSchedulesFromDoctor(String doctorId) {
        ListInterface<Schedule> list = new ArrayList<>();
        for(var s: schedules){
            if(s.getDoctorId().equals(doctorId)) list.add(s);
        }
        return list;
    }
    
    public ListInterface<Schedule> GetSortedSchedules(){
        return new MergeSort(schedules).sort();
    }
    
    public class DoctorAndSchedules {
        public Doctor doctor;
        public ListInterface<Schedule> schedules;
        
        public DoctorAndSchedules(Doctor doctor, ListInterface<Schedule> schedules){
            this.doctor = doctor;
            this.schedules = schedules;
        }
    }
    
    public ListInterface<DoctorAndSchedules> GetDoctorsAndTheirSchedules() {
        var pairs = new ArrayList<DoctorAndSchedules>();
        for(var d: doctorControl.GetEntries()){
            pairs.add(new DoctorAndSchedules(d, new MergeSort(FindSchedulesFromDoctor(d.GetId())).sort()));
        }
        return pairs;
    }
}