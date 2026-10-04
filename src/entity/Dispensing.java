package entity;

import java.util.Date;

public class Dispensing {
    private String dispensingID;
    private String medicineID;
    private String consultationID;
    private String pharmacistID;
    private int quantityDispensed;
    private Date dispensedDate;
    private String instructions;

    public Dispensing(String dispensingID, String medicineID, String consultationID,
                      String pharmacistID,
                      int quantityDispensed, Date dispensedDate, String instructions) {
        this.dispensingID = dispensingID;
        this.medicineID = medicineID;
        this.consultationID = consultationID;
        this.pharmacistID = pharmacistID;
        this.quantityDispensed = quantityDispensed;
        this.dispensedDate = dispensedDate;
        this.instructions = instructions;
    }

    // Getters
    public String getDispensingID() { return dispensingID; }
    public String getMedicineID() { return medicineID; }
    public String getConsultationID() { return consultationID; }
    public String getPharmacistID() { return pharmacistID; }
    public int getQuantityDispensed() { return quantityDispensed; }
    public Date getDispensedDate() { return dispensedDate; }
    public String getInstructions() { return instructions; }

    // Setters
    public void setDispensingID(String dispensingID) { this.dispensingID = dispensingID; }
    public void setMedicineID(String medicineID) { this.medicineID = medicineID; }
    public void setConsultationID(String consultationID) { this.consultationID = consultationID; }
    public void setPharmacistID(String pharmacistID) { this.pharmacistID = pharmacistID; }
    public void setQuantityDispensed(int quantityDispensed) { this.quantityDispensed = quantityDispensed; }
    public void setDispensedDate(Date dispensedDate) { this.dispensedDate = dispensedDate; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    @Override
    public String toString() {
        return String.format("DispensingID: %s | MedicineID: %s | ConsultationID: %s | PharmacistID: %s | Qty: %d | Date: %s | Instructions: %s",
                dispensingID, medicineID, consultationID, pharmacistID,
                quantityDispensed, dispensedDate, instructions);
    }
}
