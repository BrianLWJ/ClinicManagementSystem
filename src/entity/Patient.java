package entity;

import java.time.LocalDate;

/**
 *
 * @author weiyi
 */
public class Patient {
    private String id;          
    private String name;        
    private String ic;          
    private String gender;      
    private int age;          
    private String phone;       
    private String address;     
    private LocalDate registerDate;

    public Patient(String id, String name, String ic, String gender, int age, String phone, String address, LocalDate registerDate) {
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.registerDate = registerDate;
    }

    public String getId() {
        return id; 
    }
    
    public void setId(String id) {
        this.id = id; 
    }

    public String getName() {
        return name; 
    }
    public void setName(String name) {
        this.name = name; 
    }

    public String getIc() {
        return ic; 
    }
    public void setIc(String ic) {
        this.ic = ic; 
    }

    public String getGender() {
        return gender; }
    
    public void setGender(String gender) { 
        this.gender = gender;
    }

    public int getAge() { 
        return age; 
    }
    
    public void setAge(int age) {
        this.age = age; 
    }

    public String getPhone() {
        return phone; 
    }
    public void setPhone(String phone) {
        this.phone = phone; 
    }

    public String getAddress() {
        return address; 
    }
    
    public void setAddress(String address) {
        this.address = address; 
    }

    public LocalDate getRegisterDate() {
        return registerDate; 
    }
    
    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate; 
    }

    @Override
    public String toString() {
        return String.format("ID: %s \n Name: %s \n IC: %s \n Gender: %s \n"
                + " Age: %d \n Phone: %s \n Address: %s \n Registered Date: %s\n\n",
                id, name, ic, gender, age, phone, address, registerDate);
    }
}
