/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author ThisPc
 */
public class ArraySet<T> implements SetInterface {
    private ArrayList<T> entries = new ArrayList<>();

    @Override
    public boolean add(Object entry) {
        if(!contains((T)entry)) return entries.add((T)entry);
        return false;
    }

    @Override
    public boolean contains(Object entry) {
        for(int i = 1; i <= entries.getNumberOfEntries(); i++) {
            if(entries.contains((T)entry)) return true;
        }
        return false;
    }

    @Override
    public T remove(Object entry) {
        for(int i = 1; i <= entries.getNumberOfEntries(); i++) {
            if(entries.getEntry(i).equals(entry)) return entries.remove(i);
        }
        return null;
    }
    
    @Override
    public Iterator<T> getIterator(){
        return new ArraySetIterator();
    }

    @Override
    public int getNumberOfEntries() {
        return entries.getNumberOfEntries();
    }

    @Override
    public Iterator iterator() {
        return getIterator();
    }

    private class ArraySetIterator implements Iterator<T>{
        private Iterator<T> iter  = entries.getIterator();
        @Override public boolean hasNext(){ return iter.hasNext(); }
        @Override public T next(){ return iter.next(); }
    }
    
}
