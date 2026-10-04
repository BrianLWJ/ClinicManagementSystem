/**
 * Brian Liew Wen Jun
 */

package control;

import adt.ArrayList;
import adt.ListInterface;
import entity.Medicine;
import java.util.Date;

public class MedicineControl {
    private ListInterface<Medicine> med;

    public MedicineControl() {
        med = new ArrayList<>();
    }

    public void addMedicine(Medicine medicine) {
        med.add(medicine);
    }

    public Medicine findMedicineById(String medicineID) {
        for (int i = 1; i <= med.getNumberOfEntries(); i++) {
            Medicine m = med.getEntry(i);
            if (m.getMedicineID().equalsIgnoreCase(medicineID)) {
                return m;
            }
        }
        return null;
    }

    public boolean removeMedicine(String medicineID) {
        Medicine m = findMedicineById(medicineID);
        if (m != null) {
            // Find the index of the medicine and remove it from the ArrayList
            for (int i = 1; i <= med.getNumberOfEntries(); i++) {
                if (med.getEntry(i).getMedicineID().equalsIgnoreCase(medicineID)) {
                    med.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public ListInterface<Medicine> getAllMedicines() {return med;}

    public boolean updateStock(String medicineID, int newQty) {
        Medicine m = findMedicineById(medicineID);
        if (m != null) {
            m.setStockQty(newQty);
            return true;
        }
        return false;
    }

    // Replenish stock by updating stock; Add qtyRestocked values; Set restock date = Today
    public boolean replenishStock(String medicineID, int qtyRestocked) {
        Medicine m = findMedicineById(medicineID);
        if (m != null) {
            m.setStockQty(m.getStockQty() + qtyRestocked);
            m.setQtyRestocked(m.getQtyRestocked() + qtyRestocked); //Add Previous Qty Restocked If Have
            m.setOrderedDate(new Date()); //Today
            return true;
        }
        return false;
    }
    
    public String generateNextMedicineId() {
        int maxId = 0;
        // Auto-generate ID Starting from D001, D002... Scan for Total ID then Gen by +1
        for (int i = 1; i <= med.getNumberOfEntries(); i++) {
            Medicine m = med.getEntry(i);
            try {
                String id = m.getMedicineID().substring(1);  // Remove the "M" prefix
                int idNumber = Integer.parseInt(id);
                maxId = Math.max(maxId, idNumber);
            } catch (NumberFormatException e) {}
        }
        // Generate ID
        return "M" + String.format("%03d", maxId + 1); // Generate in format M001, M002, etc.
    }
}
