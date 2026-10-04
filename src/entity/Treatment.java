package entity;
/**
 *
 * @author User
 */

import adt.LinkedList;

public class Treatment {
    private String treatmentID;
    private String treatmentType;
    private String treatmentDesc;
    private LinkedList<TreatmentMedicine> treatmentMedicines = new LinkedList<>();

    public Treatment(String treatmentID, String treatmentType, String treatmentDesc) {
        this.treatmentID = treatmentID;
        this.treatmentType = treatmentType;
        this.treatmentDesc = treatmentDesc;
    }

    // Add medicine to treatment
    public void addTreatmentMedicine(TreatmentMedicine tm) {
        treatmentMedicines.add(tm);
    }

    public LinkedList<TreatmentMedicine> getTreatmentMedicines() {
        return treatmentMedicines;
    }

    public String getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(String treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getTreatmentDesc() {
        return treatmentDesc;
    }

    public void setTreatmentDesc(String treatmentDesc) {
        this.treatmentDesc = treatmentDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Treatment ID   : ").append(treatmentID).append("\n")
          .append("Treatment Type : ").append(treatmentType).append("\n")
          .append("Treatment Desc : ").append(treatmentDesc).append("\n")
          .append("Medicines\n");

        for (TreatmentMedicine tm : treatmentMedicines) {
            sb.append(" - ").append(tm).append("\n");
        }
        return sb.toString();
    }
}