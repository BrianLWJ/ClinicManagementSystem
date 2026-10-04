package entity;

/**
 *
 * @author User
 */
public class TreatmentMedicine {
    private Medicine medicine;
    private int quantityNeeded;
    private String usageNotes;

    public TreatmentMedicine(Medicine medicine, int quantityNeeded, String usageNotes) {
        this.medicine = medicine;
        this.quantityNeeded = quantityNeeded;
        this.usageNotes = usageNotes;
    }

    //Getter and Setter
    public Medicine getMedicine() {
        return medicine;
    }

    public int getQuantityNeeded() {
        return quantityNeeded;
    }

    public String getUsageNotes() {
        return usageNotes;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setQuantityNeeded(int quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    public void setUsageNotes(String usageNotes) {
        this.usageNotes = usageNotes;
    }

    @Override
    public String toString() {
        return medicine.getMedicineID() + ": " + medicine.getName() +  
                ", Qty: " + quantityNeeded + 
                ", Usage: " + usageNotes;
    }
}