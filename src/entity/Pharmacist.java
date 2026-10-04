package entity;

public class Pharmacist {
    private String pharmacistId;
    private String name;
    private String icNumber;
    private String gender;
    private String phoneNumber;
    private String address;

    public Pharmacist(String pharmacistId, String name, String icNumber, String gender, String phoneNumber, String address) {
        this.pharmacistId = pharmacistId;
        this.name = name;
        this.icNumber = icNumber;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters
    public String GetPharmacistId() { return pharmacistId; }
    public String GetName() { return name; }
    public String GetIcNumber() { return icNumber; }
    public String GetGender() { return gender; }
    public String GetPhoneNumber() { return phoneNumber; }
    public String GetAddress() { return address; }

    // Setters
    public void SetPharmacistId(String pharmacistId) { this.pharmacistId = pharmacistId; }
    public void SetName(String name) { this.name = name; }
    public void SetIcNumber(String icNumber) { this.icNumber = icNumber; }
    public void SetGender(String gender) { this.gender = gender; }
    public void SetPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void SetAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return String.format("""
        Pharmacist ID: %s
        Name: %s
        IC Number: %s
        Gender: %s
        Phone Number: %s
        Address: %s""",
                pharmacistId, name, icNumber, gender, phoneNumber, address);
    }

    @Override
    public boolean equals(Object pharmacist) {
        if (this == pharmacist) return true;
        if (pharmacist instanceof Pharmacist p) return icNumber.equals(p.icNumber);
        return false;
    }
}
