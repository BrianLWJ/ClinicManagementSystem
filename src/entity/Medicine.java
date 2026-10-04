package entity;

import java.util.Date;

public class Medicine {
    private String medicineID;
    private String name;
    private String type;
    private int stockQty;
    private double unitPrice;
    private Date expiryDate;
    private int stockReorderThreshold;
    private int qtyRestocked;
    private Date orderedDate;

    public Medicine(String medicineID, String name, String type, int stockQty, double unitPrice,
                    Date expiryDate, int stockReorderThreshold,
                    int qtyRestocked, Date orderedDate) {
        this.medicineID = medicineID;
        this.name = name;
        this.type = type;
        this.stockQty = stockQty;
        this.unitPrice = unitPrice;
        this.expiryDate = expiryDate;
        this.stockReorderThreshold = stockReorderThreshold;
        this.qtyRestocked = qtyRestocked;
        this.orderedDate = orderedDate;
    }

    // Getters & Setters
    public String getMedicineID() { return medicineID; }
    public void setMedicineID(String medicineID) { this.medicineID = medicineID; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public int getStockQty() { return stockQty; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
    
    public int getStockReorderThreshold() { return stockReorderThreshold; }
    public void setStockReorderThreshold(int stockReorderThreshold) { this.stockReorderThreshold = stockReorderThreshold; }
 
    public int getQtyRestocked() { return qtyRestocked; }
    public void setQtyRestocked(int qtyRestocked) { this.qtyRestocked = qtyRestocked; }
  
    public Date getOrderedDate() { return orderedDate; }
    public void setOrderedDate(Date orderedDate) { this.orderedDate = orderedDate; }

    @Override
    public String toString() {
        return String.format("%-10s %-15s %-10s %-5d %-8.2f %-10s %-12d %-5d %-12s",
            medicineID, name, type, stockQty, unitPrice,
            expiryDate, stockReorderThreshold, qtyRestocked, orderedDate);
    }
}
