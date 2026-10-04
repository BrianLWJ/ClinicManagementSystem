package boundary;

import adt.ListInterface;
import adt.QueueInterface;
import control.DispensingControl;
import control.MedicineControl;
import control.ConsultationControl;
import control.PharmacistControl;
import entity.Dispensing;
import entity.Medicine;

import boundary.MedicineUI;
import boundary.ConsultationUI;
import boundary.PharmacistUI;
import entity.Consultation;
import entity.Pharmacist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class DispensingUI {
    private final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    
    private final DispensingControl dispensingCtrl;
    private final MedicineControl medCtrl;
    private final ConsultationControl consultCtrl;
    private final PharmacistControl pharmaCtrl;
    
    public DispensingUI(DispensingControl dispensingControl,
                        MedicineControl medCtrl,
                        ConsultationControl consultCtrl,
                        PharmacistControl pharmaCtrl) {
        this.dispensingCtrl = dispensingControl;
        this.medCtrl = medCtrl;
        this.consultCtrl = consultCtrl;
        this.pharmaCtrl = pharmaCtrl;
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n========= Dispensing Module =========");
            System.out.println("1. Add Dispensing Request");
            System.out.println("2. View Pending Queue (Undispensed)");
            System.out.println("3. Process Next Dispensing (Dequeue)");
            System.out.println("4. View Dispensed History Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                choice = -1;
            }

            switch (choice) {
                case 1 -> addDispensingRecord();
                case 2 -> viewPendingQueue();
                case 3 -> processNextDispensing();
                case 4 -> viewDispensedList();
                case 0 -> System.out.println("Exiting Dispensing Module...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void addDispensingRecord() {
        System.out.println("\n--- Add Dispensing Request ---");

        //Show For Easier Reference
        System.out.println("=================================== View All Medicines ===================================");
        System.out.println("ID\tName\t\tType\t\tQty\tPrice\tExpiry\t\tReOrderThreshold\t");
        System.out.println("==========================================================================================");

        for (Medicine m : medCtrl.getAllMedicines()) {
            System.out.printf("%s\t%s\t%s\t\t%d\t%.2f\t%s\t%d\t\n",
                    m.getMedicineID(), m.getName(), m.getType(), m.getStockQty(), m.getUnitPrice(),
                    date.format(m.getExpiryDate()), m.getStockReorderThreshold());
        }
        
        //MED ID INPUT
        System.out.print("\nEnter Medicine ID: ");
        String medicineID = scanner.nextLine();
        Medicine m = medCtrl.findMedicineById(medicineID);
        if (m == null) {
            System.out.println("Invalid Medicine ID. Returning to main menu.");
            return;
        }
        
        //Show For Easier Reference
        QueueInterface<Consultation> consultations = consultCtrl.getAllConsultations();
        if (consultations.isEmpty()) {
            System.out.println("No consultations available.");
        } else {
            System.out.println("\n=== All Consultations ===");
            for (int i = 0; i < consultations.getSize(); i++) {
            System.out.println(consultations.get(i));
            }
        }
        
        //CONSULT ID INPUT
        System.out.print("\nEnter Valid Consultation ID: ");
        String consultationID = scanner.nextLine();
        var consultation = consultCtrl.findConsultationByID(consultationID);
        if (consultation == null) {
            System.out.println("Invalid Consultation ID. Returning to main menu.");
            return;
        }

        //Show For Easier Reference
        System.out.println("\n================================ Pharmacist Records =================================");
        ListInterface<Pharmacist> pharmacists = pharmaCtrl.getAllPharmacists();
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
        
        //PHARMA ID INPUT
        System.out.print("\nEnter Valid Pharmacist ID: ");
        String pharmacistID = scanner.nextLine();
        var pharmacist = pharmaCtrl.findPharmacistById(pharmacistID);
        if (pharmacist == null) {
            System.out.println("Invalid Pharmacist ID. Returning to main menu.");
            return;
        }
        
        int availableStock = m.getStockQty();
        System.out.println("Available stock for medicine " + m.getName() + ": " + availableStock);
        int quantityDispensed = -1; // Initialize with an invalid value
        while (quantityDispensed <= 0) {
            System.out.print("Enter Quantity Dispensed: ");
            try {
                quantityDispensed = Integer.parseInt(scanner.nextLine());
                if (quantityDispensed <= 0) {
                    System.out.println("Quantity must be greater than 0. Please enter again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity. Please enter again.");
                }
            }

        System.out.print("Enter Instructions: ");
        String instructions = scanner.nextLine();

        String dispensingID = dispensingCtrl.generateNextDispensingId();
        Dispensing d = new Dispensing(dispensingID, medicineID, consultationID,
                                        pharmacistID, quantityDispensed, new Date(),instructions
        );
        dispensingCtrl.addDispensing(d); 
    }

    private void processNextDispensing() {
        System.out.println("\n--- Processing Next Dispensing ---");
        Dispensing next = dispensingCtrl.processNextDispensing();
        if (next == null) {
            System.out.println("No records in queue.");
        } else {
            System.out.println("Processed: " + next.getDispensingID());
        }
    }

    private void viewPendingQueue() {
        System.out.println("\n--- Pending Queue (Undispensed) ---");
        if (dispensingCtrl.isPendingEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("ID\tMedID\tConsID\tPharmID\tQty\tDate\t\tInstructions");
        Iterator<Dispensing> it = dispensingCtrl.getPendingIterator();
        while (it.hasNext()) {
            Dispensing d = it.next();
            System.out.printf("%s\t%s\t%s\t%s\t%d\t%s\t%s%n",
                    d.getDispensingID(),
                    d.getMedicineID(),
                    d.getConsultationID(),
                    d.getPharmacistID(),
                    d.getQuantityDispensed(),
                    date.format(d.getDispensedDate()),
                    d.getInstructions()
            );
        }
    }

    private void viewDispensedList() {
        System.out.println("\n--- Dispensed History ---");
        if (dispensingCtrl.getDispensedHistory().isEmpty()) {
            System.out.println("No records have been dispensed yet.");
            return;
        }
        System.out.println("ID\tMedID\tConsID\tPharmID\tQty\tDate\tInstructions");
        for (Dispensing d : dispensingCtrl.getDispensedHistory()) {
            System.out.printf("%s\t%s\t%s\t%s\t%d\t%s\t%s%n",
                    d.getDispensingID(),
                    d.getMedicineID(),
                    d.getConsultationID(),
                    d.getPharmacistID(),
                    d.getQuantityDispensed(),
                    date.format(d.getDispensedDate()),
                    d.getInstructions()
            );
        }
    }
}
