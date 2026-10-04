/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Scanner;

/**
 *
 * @author ThisPc
 */
public class Time implements Comparable<Time> {
    private int hour;   // 0–23
    private int minute; // 0–59

    // Constructor
    public Time(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid time format");
        }
        this.hour = hour;
        this.minute = minute;
    }
    
    public Time(String str){
        String[] parts = str.split(":");
        
        this.hour = Integer.parseInt(parts[0]);
        this.minute = Integer.parseInt(parts[1]);
    }

    // Getters
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    // Convert to 24-hour format string
    public String to24HourString() {
        return String.format("%02d:%02d", hour, minute);
    }

    // Convert to 12-hour format string
    public String to12HourString() {
        int h = hour % 12;
        if (h == 0) h = 12;
        String amPm = (hour < 12) ? "AM" : "PM";
        return String.format("%02d:%02d%s", h, minute, amPm);
    }

    @Override
    public String toString() {
        return to12HourString();
    }

    @Override
    public int compareTo(Time o) {
        int hourDiff = hour - o.hour;
        if(hourDiff == 0) return minute - o.minute;
        return hourDiff;
    }
}

