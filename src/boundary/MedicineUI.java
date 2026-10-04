package boundary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import control.MedicineControl;
import entity.Medicine;

public class MedicineUI {
    private MedicineControl control;
    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

    public MedicineUI(MedicineControl control) {
        this.control = control;
        loadDummyData(); // Load dummy data
    }

    private void loadDummyData() {
        try {
            // Expired medicine Test
            control.addMedicine(new Medicine("M001", "Expired Med", "Tablet", 25, 0.50,
                    date.parse("2023-12-31"), 20, 0, null));

            // Medicine low stock Test
            control.addMedicine(new Medicine("M002", "LowStock Med", "Capsule", 5, 1.20,
                    date.parse("2025-11-15"), 10, 0, null));

            // Normal Medicine
            control.addMedicine(new Medicine("M003", "Normal Med", "Syrup", 50, 3.50,
                    date.parse("2026-05-20"), 15, 0, null));
        } catch (ParseException e) {}
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n===== Medicine Management =====");
            System.out.println("1. Add Medicine");
            System.out.println("2. View All Medicines");
            System.out.println("3. Search Medicine by ID");
            System.out.println("4. Update Stock");
            System.out.println("5. Delete Medicine");
            System.out.println("6. Replenish Stock");
            System.out.println("7. Check Stock Reorder Alerts");
            System.out.println("8. Qty Restocked Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = inputCheck();

            switch (choice) {
                case 1: addMedicine(); break;
                case 2: viewAllMedicines(); break;
                case 3: searchMedicine(); break;
                case 4: updateStock(); break;
                case 5: deleteMedicine(); break;
                case 6: replenishStock(); break;
                case 7: checkStockAlerts(); break;
                case 8: qtyRestockedReport(); break;
                case 0: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice. Please Select A Valid Option!");
            }
        } while (choice != 0);
    }

    private void addMedicine() {
        try {
            String id = control.generateNextMedicineId();

            String name = inputNonEmptyString("Name");
            String type = inputNonEmptyString("Type");

            System.out.print("Stock Quantity: ");
            int stockQty = inputCheck();

            System.out.print("Unit Price: ");
            double price = getDoubleInput();

            System.out.print("Stock Reorder Threshold: ");
            int reorderThreshold = inputCheck();

            System.out.print("Expiry Date (yyyy-MM-dd): ");
            Date expiry = dateCheck();

            Medicine m = new Medicine(id, name, type, stockQty, price, expiry, reorderThreshold, 0, null);
            control.addMedicine(m);
            System.out.println("Medicine added successfully.");
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}


    private void viewAllMedicines() {
        System.out.println("=================================== View All Medicines ===================================");
        System.out.println("ID\tName\t\tType\t\tQty\tPrice\tExpiry\t\tReOrderThreshold\t");
        System.out.println("==========================================================================================");

        for (Medicine m : control.getAllMedicines()) {
            System.out.printf("%s\t%s\t%s\t\t%d\t%.2f\t%s\t%d\t\n",
                    m.getMedicineID(), m.getName(), m.getType(), m.getStockQty(), m.getUnitPrice(),
                    date.format(m.getExpiryDate()), m.getStockReorderThreshold());
        }
    }

    private void searchMedicine() {
        System.out.print("Enter Medicine ID: ");
        String id = scanner.nextLine();
        Medicine m = control.findMedicineById(id);

        if (m != null) {
            System.out.println("=================================== Search Result ========================================");
            System.out.println("ID\tName\t\tType\t\tQty\tPrice\tExpiry\t\tReOrderThreshold\t");
            System.out.println("==========================================================================================");
            System.out.printf("%s\t%s\t%s\t\t%d\t%.2f\t%s\t%d\t\n",
                m.getMedicineID(), m.getName(), m.getType(), m.getStockQty(), m.getUnitPrice(),
                date.format(m.getExpiryDate()), m.getStockReorderThreshold());
        } else {
            System.out.println("Medicine not found.");
        }
    }


    private void updateStock() {
        System.out.print("Enter Medicine ID: ");
        String id = scanner.nextLine();

        Medicine m = control.findMedicineById(id);
        if (m == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.print("New Stock Quantity: ");
        int qty = inputCheck();

        if (control.updateStock(id, qty)) {
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Failed to update stock.");
        }
    }


    private void deleteMedicine() {
        System.out.print("Enter Medicine ID: ");
        String id = scanner.nextLine();
        if (control.removeMedicine(id)) {
            System.out.println("Medicine deleted.");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    private void replenishStock() {
        System.out.print("Enter Medicine ID: ");
        String id = scanner.nextLine();

        Medicine m = control.findMedicineById(id);
        if (m == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.print("Enter Quantity to Replenish: ");
        int qty = inputCheck();

        if (control.replenishStock(id, qty)) {
            System.out.println("Stock replenished successfully.");
        } else {
            System.out.println("Failed to replenish stock. Please try again.");
        }
    }


    private void checkStockAlerts() {
        System.out.println("===== Stock Reorder Alerts =====");
        boolean alertLessAmt = false;
        for (Medicine m : control.getAllMedicines()) {
            if (m.getStockQty() <= m.getStockReorderThreshold()) {
                System.out.println("Medicine ID: " + m.getMedicineID() + " (" + m.getName() + ") needs replenishment.");
                alertLessAmt = true;
            }
        }

        if (!alertLessAmt) {
            System.out.println("No stocks are currently low.");
        }
    }

    private void qtyRestockedReport() {
        System.out.println("===== Qty Restocked Report =====");
        System.out.println("ID\tName\t\tType\tLastRestockAmt\tLast Restock Date");

        boolean found = false;
        for (Medicine m : control.getAllMedicines()) {
            if (m.getQtyRestocked() > 0 && m.getOrderedDate() != null) {
                System.out.printf("%s\t%s\t%s\t%d\t\t%s\n",
                        m.getMedicineID(), m.getName(), m.getType(), m.getQtyRestocked(),
                        date.format(m.getOrderedDate()));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No medicines have been replenished yet.");
        }
    }

    // Method to safely get an integer input
    private int inputCheck() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.nextLine(); // discard the invalid input
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }

    // Method to safely get a double input
    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // discard the invalid input
        }
        double n = scanner.nextDouble();
        scanner.nextLine();
        return n;
    }

    // Method to safely get a date input
    private Date dateCheck() {
        Date dateInput = null;
        while (dateInput == null) {
            String dateStr = scanner.nextLine();
            try {
                dateInput = date.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
        return dateInput;
    }

    // Method to safely get a non-empty string input
    private String inputNonEmptyString(String fieldName) {
        String input;
        while (true) {
            System.out.print(fieldName + ": ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(fieldName + " cannot be empty.");
            } else {
                return input;
            }
        }
    }
}
