package Main;

import control.PatientControl;
import boundary.PatientUI;
import control.DoctorControl;
import boundary.DoctorUI;
import control.ConsultationControl;
import boundary.ConsultationUI;
import control.TreatmentControl;
import boundary.TreatmentUI;
import control.PharmacistControl; 
import boundary.PharmacistUI;
import control.MedicineControl; 
import boundary.MedicineUI; 
import control.DispensingControl; 
import boundary.DispensingUI; 
import boundary.ScheduleUI;
import control.ScheduleControl;
import java.text.ParseException;
import java.util.Scanner;

public class ClinicManagementSystem {    
    public static void main(String[] args) throws ParseException {
        int choice;
        Scanner sc = new Scanner(System.in);
        PatientControl patientManager = new PatientControl();
        PatientUI patientUI = new PatientUI(patientManager);
        DoctorControl doctorManager = new DoctorControl();
        ScheduleControl scheduleControl = new ScheduleControl(doctorManager);
        ScheduleUI scheduleUI = new ScheduleUI(scheduleControl);
        DoctorUI doctorUI = new DoctorUI(doctorManager, scheduleUI);
        ConsultationControl manageConsultation = new ConsultationControl(patientManager, doctorManager);
        ConsultationUI consultationUI = new ConsultationUI(manageConsultation);
        PharmacistControl pharmaCtrl = new PharmacistControl(); 
        PharmacistUI pharmaUI = new PharmacistUI(pharmaCtrl);
        MedicineControl medCtrl = new MedicineControl();
        MedicineUI medUI = new MedicineUI(medCtrl);
        DispensingControl dispensingCtrl = new DispensingControl(50);
        DispensingUI dispensingUI = new DispensingUI(
            dispensingCtrl,
            medCtrl,        
            manageConsultation,     
            pharmaCtrl
        );
        TreatmentControl treatmentControl = new TreatmentControl(patientManager, doctorManager, manageConsultation, medCtrl);
        TreatmentUI treatmentUI = new TreatmentUI(treatmentControl);
        
        do {
            try{
                System.out.println("Welcome to Clinic Management System");    
                System.out.println("1. Patient");
                System.out.println("2. Doctor");
                System.out.println("3. Consultation ");
                System.out.println("4. Medical Treatment");
                System.out.println("5. Pharmacy ");
                System.out.println("0. Exit");
                System.out.print("Enter your choice to start: ");
                choice = sc.nextInt();
                sc.nextLine();
            }catch (Exception e)
            {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                choice = -1;
            }
            switch(choice) {
                case 1:
                    patientUI.run();
                    break;
                case 2:
                    doctorUI.run();
                    break;
                case 3:
                    consultationUI.run();
                    break;
                case 4:
                    treatmentUI.run();
                    break;
               case 5:
                    int pharmaChoice;
                        do {
                        System.out.println("\n--- Pharmacy Module ---");
                        System.out.println("1. Pharmacist Management");
                        System.out.println("2. Medicine Management");
                        System.out.println("3. Dispensing Queue");
                        System.out.println("0. Back to Main Menu");
                        System.out.print("Enter your choice: ");
                        pharmaChoice = sc.nextInt();
                        sc.nextLine(); 
                        switch (pharmaChoice) {
                            case 1:
                                pharmaUI.PharmacistMenu();
                                break;
                            case 2:
                                medUI.menu();
                                break;
                            case 3:
                                dispensingUI.run();
                                break;
                            case 0:
                                System.out.println("Returning to main menu...\n");
                                break;
                            default:
                                System.out.println("Invalid choice. Please select 0-3.\n");
                        }
                    } while (pharmaChoice != 0);
                    break;
                case 0:
                    System.out.println("Exiting......\n\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Sorry... Please Enter Number (0 to 5)\n\n");
            }
        } while(choice != 0);
    }
}
