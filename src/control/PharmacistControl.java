/**
 * Brian Liew Wen Jun
 */

package control;

import adt.ArrayList;
import adt.ListInterface;
import entity.Pharmacist;
import java.util.Iterator;

public class PharmacistControl {
    private ListInterface<Pharmacist> pharma = new ArrayList<>();
    
    // Auto-generate ID Starting from D001, D002... Scan for Total ID then Gen by +1
    public String generateNextPharmacistId() {
        int maxId = 0;
        Iterator<Pharmacist> it = pharma.getIterator();
        while (it.hasNext()) {
            Pharmacist p = it.next();
            try {
                String id = p.GetPharmacistId().substring(1); // remove "P"
                int idNum = Integer.parseInt(id);
                maxId = Math.max(maxId, idNum);
            } catch (NumberFormatException e) {}
        }
        return "P" + String.format("%03d", maxId + 1);
    }
    
    public boolean addPharmacist(String id, String name, String icNumber, String gender, String phone, String address) {
        Pharmacist newPharmacist = new Pharmacist(id, name, icNumber, gender, phone, address);
        if (!pharma.contains(newPharmacist)) {
            return pharma.add(newPharmacist);
        }
        return false;
    }

    public boolean editPharmacist(String id, String name, String icNumber, String gender, String phone, String address) {
        for (int i = 1; i <= pharma.getNumberOfEntries(); i++) {
            Pharmacist p = pharma.getEntry(i);
            if (p.GetPharmacistId().equals(id)) {
                p.SetName(name);
                p.SetIcNumber(icNumber);
                p.SetGender(gender);
                p.SetPhoneNumber(phone);
                p.SetAddress(address);
                return true;
            }
        }
        return false;
    }

    public boolean deletePharmacist(String id) {
        for (int i = 1; i <= pharma.getNumberOfEntries(); i++) {
            Pharmacist p = pharma.getEntry(i);
            if (p.GetPharmacistId().equals(id)) {
                pharma.remove(i);
                return true;
            }
        }
        return false;
    }

    //Search by ID ONLY
    public Pharmacist searchPharmacistById(String id) {
        for (int i = 1; i <= pharma.getNumberOfEntries(); i++) {
            Pharmacist p = pharma.getEntry(i);
            if (p.GetPharmacistId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public Pharmacist findPharmacistById(String id) {return searchPharmacistById(id);}
    public int getPharmacistCount() {return pharma.getNumberOfEntries();}
    public ListInterface<Pharmacist> getAllPharmacists() {return pharma;}
}
