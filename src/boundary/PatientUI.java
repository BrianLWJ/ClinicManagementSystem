package boundary;

import control.PatientControl;
import entity.Patient;
import java.time.LocalDate;
import java.util.Scanner;
/**
 *
 * @author weiyi
 */

public class PatientUI {
    private PatientControl patientControl;
    private Scanner sc = new Scanner(System.in);
    
    public PatientUI(PatientControl control) {
        patientControl = control;
        patientControl.addPatient(new Patient("", "Charlie Wong", "970403-10-9999", "Male", 72, "0112233445", "Penang", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Alice Tan", "940101-14-5678", "Female", 25, "0123456789", "KL", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Chan Mei Lee", "980202-08-1234", "Female", 42, "0198765432", "PJ", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Ong Kin Way", "970303-10-9999", "Male", 60, "0112233445", "Penang", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Daphne Lim", "050205-12-32133", "Female", 19, "0185554444", "Ipoh", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Ethan Tan", "030343-07-2222", "Male", 21, "0171112222", "Malacca", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Fiona Goh", "880208-05-4444", "Female", 35, "0162223333", "Johor Bahru", LocalDate.now()));
        patientControl.addPatient(new Patient("", "George Ho", "720101-06-5555", "Male", 52, "0126667777", "Perak", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Charlie Wong", "970303-10-9999", "Male", 28, "0112233445", "Penang", LocalDate.now()));
        patientControl.addPatient(new Patient("", "Gan Hee Yuan", "530131-06-5355", "Male", 52, "0126636432", "Perak", LocalDate.now()));
    }

    public void run() {
        int choice;
        do {
            try{
                System.out.println("\n=== Patient Management Menu ===");
                System.out.println("1. Register new patient");
                System.out.println("2. Update patient");
                System.out.println("3. Delete patient");
                System.out.println("4. Search patient by ID");
                System.out.println("5. List all patients");
                System.out.println("6. Report: Gender group");
                System.out.println("7. Report: Age group ");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                System.out.println();
            }catch (Exception e) {
                System.out.println("Invalid input! Please enter number 0 to 7");
                sc.nextLine();
                choice = -1;  
            }
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2: 
                    updatePatient();
                    break;
                case 3: 
                    deletePatient();
                    break;
                case 4:
                    searchPatient();
                    break;
                case 5: 
                    patientControl.listAllPatients();
                    break;
                case 6:
                    patientControl.reportGender();
                    break;
                case 7: 
                    patientControl.reportAge();
                    break;
                case 0: 
                    System.out.println("Exiting...\n\n");
                    break;
                default:
                    if(choice != -1)
                        System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 0);
    }

    private void registerPatient() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter IC: ");
        String ic = sc.nextLine();
        String gender = "";
        while (true) {
            System.out.print("Enter gender (Male/Female): ");
            gender = sc.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                break;
            } else {
                System.out.println("Invalid gender! Please enter Male or Female.");
            }
        }
        int age = -1;
        while (age < 0) {
            try {
                System.out.print("Enter age: ");
                age = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number");
                sc.nextLine();
                age = -1; 
            }
        }
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter address: ");
        String address = sc.nextLine();

        Patient newPatient = new Patient(null, name, ic, gender, age, phone, address, LocalDate.now());
        if (patientControl.addPatient(newPatient)) {
            System.out.println("Patient registered successfully.");
        }
    }

    private void updatePatient() {
        System.out.print("Enter ID of patient to update: ");
        String id = sc.nextLine();
        Patient targetPatient = patientControl.findPatientByID(id);
        if (targetPatient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        String gender = "";
        while (true) {
            System.out.print("Enter gender (Male/Female): ");
            gender = sc.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                break;
            } else {
                System.out.println("Invalid gender! Please enter Male or Female.");
            }
        }
        int age = -1;
        while (age < 0) {
            try {
                System.out.print("Enter new age: ");
                age = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number");
                sc.nextLine();
                age = -1; 
            }
        }
        System.out.print("Enter new phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter new address: ");
        String address = sc.nextLine();

        Patient updatedData = new Patient(targetPatient.getId(), name, targetPatient.getIc(), gender,
                age, phone, address, targetPatient.getRegisterDate());
        if (patientControl.updatePatient(id, updatedData)) {
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private void deletePatient() {
        System.out.print("Enter ID of patient to delete: ");
        String id = sc.nextLine();
        if (patientControl.removePatient(id)) {
            System.out.println("Patient removed successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private void searchPatient() {
        System.out.print("Enter ID of patient to search: ");
        String id = sc.nextLine();
        Patient targetPatient = patientControl.findPatientByID(id);
        if (targetPatient != null) {
            System.out.println("Patient found: " + targetPatient);
        } else {
            System.out.println("Patient not found.");
        }
    }
}
