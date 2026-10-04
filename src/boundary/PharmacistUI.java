package boundary;

import control.PharmacistControl;
import entity.Pharmacist;
import adt.ListInterface;

import java.util.Iterator;
import java.util.Scanner;

public class PharmacistUI {
    private final PharmacistControl control;
    private final Scanner scanner;

    public PharmacistUI(PharmacistControl pharmacistControl) {
        this.control = pharmacistControl;
        loadDummyData();
        this.scanner = new Scanner(System.in);
    }

    // ✅ load dummy data
    public void loadDummyData() {
        control.addPharmacist("P001", "Ali Bin Abu", "990101011111", "M", "0123456789", "No.1 Jalan ABC, KL");
        control.addPharmacist("P002", "Siti Binti ", "980202022222", "F", "0134567890", "No.2 Jalan DEF, KL");
        control.addPharmacist("P003", "John Smith", "970303033333", "M", "0145678901", "No.3 Jalan GHI, KL");
    }

    public void PharmacistMenu() {
        int choice;
        do {
            System.out.println("\n===== Pharmacist Management =====");
            System.out.println("1. Add Pharmacist");
            System.out.println("2. Edit Pharmacist");
            System.out.println("3. View All Pharmacists");
            System.out.println("4. Search Pharmacist by ID");
            System.out.println("5. Delete Pharmacist");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = inputCheck();

            switch (choice) {
                case 1 -> addPharmacistUI();
                case 2 -> editPharmacistUI();
                case 3 -> viewPharmacistsUI();
                case 4 -> searchPharmacistUI();
                case 5 -> deletePharmacistUI();
                case 0 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice. Please Select A Valid Option!");
            }
        } while (choice != 0);
    }

    private void addPharmacistUI() {
        String id = control.generateNextPharmacistId();
        System.out.println("\n--- Add Pharmacist ---");

        String name;
        do {
            System.out.print("Name: ");
            name = scanner.nextLine();
            if (!name.matches("^[A-Za-z ]+$")) {
                System.out.println("Name must contain only letters and spaces.");
                name = null;
            }
        } while (name == null);

        String ic;
        do {
            System.out.print("IC Number (12 digits): ");
            ic = scanner.nextLine();
            if (!ic.matches("\\d{12}")) {
                System.out.println("IC Number must be exactly 12 digits.");
                ic = null;
            }
        } while (ic == null);

        String gender;
        do {
            System.out.print("Gender (M/F): ");
            gender = scanner.nextLine().toUpperCase();
            if (!(gender.equals("M") || gender.equals("F"))) {
                System.out.println("Gender must be 'M' or 'F'.");
                gender = null;
            }
        } while (gender == null);

        String phone;
        do {
            System.out.print("Phone Number (9 to 10 digits): ");
            phone = scanner.nextLine();
            if (!phone.matches("\\d{9,10}")) {
                System.out.println("Phone number must be 9 or 10 digits.");
                phone = null;
            }
        } while (phone == null);

        System.out.print("Address: ");
        String address = scanner.nextLine();

        if (control.addPharmacist(id, name, ic, gender, phone, address)) {
            System.out.println("Pharmacist added successfully with ID: " + id);
        }
    }

    private void editPharmacistUI() {
        System.out.println("\n--- Edit Pharmacist ---");
        System.out.print("Enter Pharmacist ID to edit: ");
        String id = scanner.nextLine();

        Pharmacist existing = control.searchPharmacistById(id);
        if (existing == null) {
            System.out.println("Pharmacist not found.");
            return;
        }

        String name;
        do {
            System.out.print("New Name: ");
            name = scanner.nextLine();
            if (!name.matches("^[A-Za-z ]+$")) {
                System.out.println("Name must contain only letters and spaces.");
                name = null;
            }
        } while (name == null);

        String ic;
        do {
            System.out.print("New IC Number (12 digits): ");
            ic = scanner.nextLine();
            if (!ic.matches("\\d{12}")) {
                System.out.println("IC Number must be exactly 12 digits.");
                ic = null;
            }
        } while (ic == null);

        String gender;
        do {
            System.out.print("New Gender (M/F): ");
            gender = scanner.nextLine().toUpperCase();
            if (!(gender.equals("M") || gender.equals("F"))) {
                System.out.println("Gender must be 'M' or 'F'.");
                gender = null;
            }
        } while (gender == null);

        String phone;
        do {
            System.out.print("New Phone Number (9 to 10 digits): ");
            phone = scanner.nextLine();
            if (!phone.matches("\\d{9,10}")) {
                System.out.println("Phone number must be 9 or 10 digits.");
                phone = null;
            }
        } while (phone == null);

        System.out.print("New Address: ");
        String address = scanner.nextLine();

        boolean updatedFlag = control.editPharmacist(id, name, ic, gender, phone, address);
        if (updatedFlag) {
            System.out.println("Pharmacist updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private void viewPharmacistsUI() {
        System.out.println("\n================================ Pharmacist Records =================================");
        ListInterface<Pharmacist> pharmacists = control.getAllPharmacists();
        if (pharmacists.getNumberOfEntries() == 0) {
            System.out.println("No pharmacists found.");
            return;
        }

        System.out.println("ID\tName\t\tIC Number\tGender\tPhone\t\tAddress");
        System.out.println("====================================================================================");

        Iterator<Pharmacist> it = pharmacists.getIterator();
        while (it.hasNext()) {
            Pharmacist p = it.next();
            System.out.printf("%s\t%-15s\t%s\t%s\t%s\t%s\n",
                    p.GetPharmacistId(),
                    p.GetName(),
                    p.GetIcNumber(),
                    p.GetGender(),
                    p.GetPhoneNumber(),
                    p.GetAddress());
        }
    }

    private void searchPharmacistUI() {
        System.out.println("\n--- Search Pharmacist by ID ---");
        System.out.print("Enter Pharmacist ID: ");
        String id = scanner.nextLine();

        Pharmacist p = control.searchPharmacistById(id);
        if (p != null) {
            System.out.println("\n================================ Pharmacist Records =================================");
            System.out.println("ID\tName\t\tIC Number\tGender\tPhone\t\tAddress");
            System.out.println("====================================================================================");

            System.out.printf("%s\t%-15s\t%s\t%s\t%s\t%s\n",
                    p.GetPharmacistId(),
                    p.GetName(),
                    p.GetIcNumber(),
                    p.GetGender(),
                    p.GetPhoneNumber(),
                    p.GetAddress());
        } else {
            System.out.println("No pharmacist found with that ID.");
        }
    }


    private void deletePharmacistUI() {
        System.out.println("\n--- Delete Pharmacist ---");
        System.out.print("Enter Pharmacist ID to delete: ");
        String id = scanner.nextLine();

        Pharmacist p = control.searchPharmacistById(id);
        if (p == null) {
            System.out.println("Pharmacist not found.");
            return;
        }

        boolean deleted = control.deletePharmacist(id);
        if (deleted) {
            System.out.println("Pharmacist deleted successfully.");
        } else {
            System.out.println("Delete failed.");
        }
    }

    //Safe input check
    private int inputCheck() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }
}
