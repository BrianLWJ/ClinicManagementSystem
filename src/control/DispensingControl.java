/**
 * Brian Liew Wen Jun
 */

package control;

import adt.ArrayQueue;
import adt.ArrayList;
import entity.Dispensing;
import java.util.Iterator;

public class DispensingControl {
    private final ArrayQueue<Dispensing> pendingQueue;     // Undispensed Queue
    private final ArrayList<Dispensing> dispensedHistory;  // For FINISH Dispensed Queue
    private ConsultationControl consultManager;

    public DispensingControl(int capacity) {
        this.pendingQueue = new ArrayQueue<>(capacity);
        this.dispensedHistory = new ArrayList<>();
    }

    // Auto-generate ID Starting from D001, D002... Scan for Total ID UnDis & Dis then Gen by +1
    public String generateNextDispensingId() {
        int max = 0;

        // Scan UnDispensed Queue
        Iterator<Dispensing> it = pendingQueue.iterator();
        while (it.hasNext()) {
            String id = it.next().getDispensingID();
            if (id != null && id.length() > 1) {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) max = n;
                } catch (NumberFormatException ignored) { }
            }
        }

        // Scan Already Dispensed
        for (int i = 1; i <= dispensedHistory.getNumberOfEntries(); i++) {
            String id = dispensedHistory.getEntry(i).getDispensingID();
            if (id != null && id.length() > 1) {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) max = n;
                } catch (NumberFormatException ignored) { }
            }
        }

        return "D" + String.format("%03d", max + 1);
    }
    
    //Enqueue
    public void addDispensing(Dispensing dispensing) {pendingQueue.enqueue(dispensing);}

    //Dequeue Object
    public Dispensing processNextDispensing() {
        Dispensing d = pendingQueue.dequeue();
        if (d != null) {
            dispensedHistory.add(d); 
        }
        return d;
    }

    //For UI
    public Iterator<Dispensing> getPendingIterator() {return pendingQueue.iterator(); }
    //Return ArrayList
    public ArrayList<Dispensing> getDispensedHistory() {return dispensedHistory;}
    public boolean isPendingEmpty() {return pendingQueue.isEmpty(); }
}
