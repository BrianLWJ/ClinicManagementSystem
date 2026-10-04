package control;

import adt.ArrayList;
import adt.ListInterface;
import entity.Patient;
import java.util.Iterator;

/**
 *
 * @author weiyi
 */
public class PatientControl {
    private ListInterface<Patient> patientList = new ArrayList<>();
    private static int patientIDCount = 0;
    
    private String generatePatientID(){
        patientIDCount++;
        return String.format("P%03d", patientIDCount);
    }
    
    public boolean addPatient(Patient newPatient) {
            newPatient.setId(generatePatientID());
            return patientList.add(newPatient);
    }

    public boolean updatePatient(String id, Patient newData) {
        for (int i = 1; i <= patientList.getNumberOfEntries(); i++) {
            Patient patient = patientList.getEntry(i);
            if (patient.getId().equals(id)) {
                patientList.replace(i, newData);
                return true;
            }
        }
        return false;
    }

    public boolean removePatient(String id) {
        for (int i = 1; i <= patientList.getNumberOfEntries(); i++) {
            Patient targetPatient = patientList.getEntry(i);
            if (targetPatient.getId().equals(id)) {
                patientList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public void listAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.println("=== Patient List ===");
        Iterator<Patient> it = patientList.getIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
    
    public Patient findPatientByID(String patientID) {
        for (int i = 1; i <= patientList.getNumberOfEntries(); i++) {
            Patient patient = patientList.getEntry(i);
            if (patient.getId().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }

    public void reportGender() {
        int male = 0, female = 0;
        int total = patientList.getNumberOfEntries();

        System.out.printf("%-6s %-15s %-8s\n", "ID", "Name", "Gender");
        System.out.println("----------------------------------");

        Iterator<Patient> it = patientList.getIterator();
        while (it.hasNext()) {
            Patient patient = it.next();
            System.out.printf("%-6s %-15s %-8s\n", patient.getId(), patient.getName(), patient.getGender());

            if (patient.getGender().equalsIgnoreCase("Male")) male++;
            else if (patient.getGender().equalsIgnoreCase("Female")) female++;
        }
        System.out.println("\n=== Summary ===");
        System.out.println("Total Patients : " + total);
        System.out.println("Male           : " + male);
        System.out.println("Female         : " + female);

        if (female > 0) {
            double ratio = (double) male / female;
            System.out.printf("Male : Female  = %.2f : 1\n", ratio);
        } else if (male > 0) {
            System.out.println("Male : Female  = ∞ : 0");
        } else {
            System.out.println("No patients registered yet.");
        }
    }

    public void reportAge() {
        int below20 = 0, between20To40 = 0, between41To60 = 0, above60 = 0;
        int sumAge = 0;
        int total = patientList.getNumberOfEntries();

        System.out.printf("%-6s %-15s %-5s\n", "ID", "Name", "Age");
        System.out.println("----------------------------------");

        Iterator<Patient> it = patientList.getIterator(); 
        while (it.hasNext()) {
            Patient p = it.next();
            int age = p.getAge();
            System.out.printf("%-6s %-15s %-5d\n", p.getId(), p.getName(), age);

            sumAge += age;
            if (age < 20){
                below20++;
            }
            else if (age <= 40){
                between20To40++;
            }
            else if (age <= 60){
                between41To60++;
            }
            else above60++;
        }
        System.out.println("\nAge Distribution:");
        System.out.println("Below 20     : " + below20);
        System.out.println("20 - 40      : " + between20To40);
        System.out.println("41 - 60      : " + between41To60);
        System.out.println("Above 60     : " + above60);

        System.out.println("Total        : " + total);
        if (total > 0) {
            double avgAge = (double) sumAge / total;
            System.out.printf("Average Age  : %.1f\n", avgAge);
        }
    }
}
