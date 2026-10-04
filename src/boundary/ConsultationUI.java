/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author chinw
 */

import adt.QueueInterface;
import control.DoctorControl;
import control.ConsultationControl;
import control.PatientControl;
import entity.Consultation;
import java.util.Scanner;

public class ConsultationUI {
    private ConsultationControl control;
    private Scanner sc = new Scanner(System.in);


    public ConsultationUI(ConsultationControl control) {
        this.control = control;
        control.createConsultation("P001", "D000", "10-10-2025", "10:00", "Heart checkup", "Don't eat anything high in carbohydrates before visiting.");
        control.createConsultation("P002", "D001", "10-23-2025", "10:00", "Nerve checkup", "Don't eat anything high in carbohydrates before visiting.");
        control.createConsultation("P003", "D002", "10-23-2025", "10:00", "Heart checkup", "Don't eat anything high in carbohydrates before visiting.");
        control.createConsultation("P001", "D002", "10-23-2025", "10:00", "Heart checkup", "Don't eat anything high in carbohydrates before visiting.");
        control.createConsultation("P002", "D000", "10-23-2025", "10:00", "Heart checkup", "Don't eat anything high in carbohydrates before visiting.");
        control.createConsultation("P003", "D001", "10-23-2025", "10:00", "Bladder checkup", "Don't drink alcohol.");

}

    public void run() {
        while (true) {
            System.out.println("\n=== Consultation Menu ===");
            System.out.println("1. Create Consultation");
            System.out.println("2. View All Consultations");
            System.out.println("3. Search by Patient ID");
            System.out.println("4. Update Consultation");
            System.out.println("5. Delete Consultation");
            System.out.println("6. Generate doctor Consultation Report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": createConsultation(); break;
                case "2": listAllConsultations(); break;
                case "3": searchByPatient(); break;
                case "4": updateConsultation(); break;
                case "5": deleteConsultation(); break;
                case "6": generateDoctorReport(); break;
                case "0": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // CREATE
    private void createConsultation() {
        System.out.print("Patient ID: ");
        String pId = sc.nextLine();
        System.out.print("Doctor ID: ");
        String dId = sc.nextLine();
        System.out.print("Date (yyyy-mm-dd): ");
        String date = sc.nextLine();
        System.out.print("Time (hh:mm): ");
        String time = sc.nextLine();
        System.out.print("Reason: ");
        String reason = sc.nextLine();
        System.out.print("Notes: ");
        String notes = sc.nextLine();

        while (true) {
            System.out.print("Confirm save consultation? (Y/N): ");
            String confirm = sc.nextLine().trim().toLowerCase();
            if (confirm.equals("y") || confirm.equals("yes")) {
                Consultation c = control.createConsultation(pId, dId, date, time, reason, notes);
                if (c != null) {
                    System.out.println("Consultation created (ID = " + c.getConsultationID() + ")");
                } else {
                    System.out.println("Consultation creation failed. Invalid Patient ID or Doctor ID.");
                }
                break;
            } else if (confirm.equals("n") || confirm.equals("no")) {
                System.out.println("Consultation creation cancelled.");
                break;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
        pause();
    }

    // READ (all)
    private void listAllConsultations() {
        QueueInterface<Consultation> consultations = control.getAllConsultations();
        if (consultations.isEmpty()) {
            System.out.println("No consultations available.");
        } else {
            System.out.println("\n=== All Consultations ===");
            for (int i = 0; i < consultations.getSize(); i++) {
            System.out.println(consultations.get(i));
            }
        }
        pause();
    }

    // READ (by patient)
    private void searchByPatient() {
        System.out.print("Enter Patient ID: ");
        String pId = sc.nextLine();
        control.findByPatient(pId);
        pause();
    }

    // UPDATE
    private void updateConsultation() {
        System.out.print("Enter Consultation ID to update: ");
        String cId = sc.nextLine();
        System.out.print("New Date (yyyy-mm-dd): ");
        String date = sc.nextLine();
        System.out.print("New Time (hh:mm): ");
        String time = sc.nextLine();
        System.out.print("New Reason: ");
        String reason = sc.nextLine();
        System.out.print("New Notes: ");
        String notes = sc.nextLine();

        while (true) {
            System.out.print("Confirm update? (Y/N): ");
            String confirm = sc.nextLine().trim().toLowerCase();
            if (confirm.equals("y") || confirm.equals("yes")) {
                if (control.updateConsultation(cId, date, time, reason, notes)) {
                    System.out.println("Consultation updated.");
                } else {
                    System.out.println("Consultation not found.");
                }
                break;
            } else if (confirm.equals("n") || confirm.equals("no")) {
                System.out.println("Update cancelled.");
                break;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
        pause();
    }

    // DELETE
    private void deleteConsultation() {
        System.out.print("Enter Consultation ID to delete: ");
        String cId = sc.nextLine();

        while (true) {
            System.out.print("Confirm delete? (Y/N): ");
            String confirm = sc.nextLine().trim().toLowerCase();
            if (confirm.equals("y") || confirm.equals("yes")) {
                if (control.deleteConsultation(cId)) {
                    System.out.println("Consultation deleted.");
                } else {
                    System.out.println("Consultation not found.");
                }
                break;
            } else if (confirm.equals("n") || confirm.equals("no")) {
                System.out.println("Delete cancelled.");
                break;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
        pause();
    }
    
    private void generateDoctorReport() {
    control.generateDoctorReport();
    pause();
}

    // Utility method to pause
    private void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}
